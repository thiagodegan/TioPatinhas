package br.com.duckchain.dao;

import br.com.duckchain.factory.ConnectionFactory;
import br.com.duckchain.model.*;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TransacaoDao extends ConnectionFactory {
    private Transacao parseTransacao(ResultSet result) throws SQLException {
        int id = result.getInt("id");
        int idConta = result.getInt("id_conta");
        int idTipo = result.getInt("id_tipotransacao");
        int idTransferencia = result.getInt("id_transferencia");
        LocalDateTime data = result.getTimestamp("data").toLocalDateTime();
        double montante = result.getDouble("montante");
        double preco = result.getDouble("preco");
        String observacao = result.getString("observacao");
        return new Transacao(id, idConta, idTipo, idTransferencia, data, montante, preco, observacao);
    }

    public List<Transacao> listar (int idConta) {
        try (Connection con = getConnection()){
            TipoTransacaoDao tipoTransacaoDao = new TipoTransacaoDao();
            ContaDao contaDao = new ContaDao();
            // aqui eu faria com joins para ser mais rápido, mas vamos por partes
            PreparedStatement stm = con.prepareStatement("SELECT * FROM TRANSACAO WHERE id_conta = ? order by id");
            stm.setInt(1, idConta);

            ResultSet result = stm.executeQuery();

            List<Transacao> transacoes = new ArrayList<>();

            while (result.next()) {
                transacoes.add(parseTransacao(result));
            }

            for(Transacao transacao : transacoes) {
                // carrega o tipo da transacao que poderia ser um join
                transacao.setTipoTransacao(tipoTransacaoDao.findById(transacao.getIdTipoTransacao()));
                // carrega se foi uma transferencia, que poderia ser um join
                if (transacao.getIdTransferencia() > 0) {
                    stm = con.prepareStatement("SELECT * FROM TRANSFERENCIA WHERE id = ?");
                    stm.setInt(1, transacao.getIdTipoTransacao());

                    result = stm.executeQuery();

                    if (result.next()) {
                        int idContaOrigem = result.getInt("id_contaorigem");
                        int idContaDestino = result.getInt("id_contadestino");
                        Conta contaOrigem = contaDao.findById(idContaOrigem);
                        Conta contaDestino = contaDao.findById(idContaDestino);
                        transacao.setTransferencia(new Transferencia(transacao.getIdTransferencia(), contaOrigem, contaDestino, transacao.getData(), transacao.getMontante(), transacao.getPreco(), transacao.getObservacao()));
                    }
                }
            }
            return transacoes;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public Deposito depositar(Deposito deposito) {
        ContaDao contaDao = new ContaDao();
        TipoMoedaDao tipoMoedaDao = new TipoMoedaDao();

        try (Connection con = getConnection()){
            PreparedStatement stm;

            stm = con.prepareStatement("SELECT SEQ_TRANSACAO_ID.nextval as id from dual");

            ResultSet result = stm.executeQuery();

            if (!result.next()) {
                throw new SQLException("Não foi possível obter o próximo id de transacao");
            }

            deposito.setId(result.getInt("id"));

            TipoMoeda tipoMoeda = tipoMoedaDao.findById(deposito.getConta().getIdTipoMoeda());

            deposito.setPreco(tipoMoeda.getPrecoAtual());

            stm = con.prepareStatement("INSERT INTO TRANSACAO (ID, ID_CONTA, ID_TIPOTRANSACAO, DATA, MONTANTE, PRECO, OBSERVACAO) VALUES (?, ?, ?, ?, ?, ?, ?)");
            stm.setInt(1, deposito.getId());
            stm.setInt(2, deposito.getConta().getId());
            stm.setInt(3, deposito.getTipoTransacao().getId());
            stm.setTimestamp(4, Timestamp.valueOf(deposito.getData()));
            stm.setDouble(5, deposito.getMontante());
            stm.setDouble(6, deposito.getPreco());
            stm.setString(7, deposito.getObservacao());

            stm.executeUpdate();

            contaDao.update(deposito.getConta());

            return deposito;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public Saque sacar(Saque saque) {
        ContaDao contaDao = new ContaDao();
        TipoMoedaDao tipoMoedaDao = new TipoMoedaDao();

        try (Connection con = getConnection()){
            PreparedStatement stm;

            stm = con.prepareStatement("SELECT SEQ_TRANSACAO_ID.nextval as id from dual");

            ResultSet result = stm.executeQuery();

            if (!result.next()) {
                throw new SQLException("Não foi possível obter o próximo id de transacao");
            }

            saque.setId(result.getInt("id"));

            TipoMoeda tipoMoeda = tipoMoedaDao.findById(saque.getConta().getIdTipoMoeda());

            saque.setPreco(tipoMoeda.getPrecoAtual());

            stm = con.prepareStatement("INSERT INTO TRANSACAO (ID, ID_CONTA, ID_TIPOTRANSACAO, DATA, MONTANTE, PRECO, OBSERVACAO) VALUES (?, ?, ?, ?, ?, ?, ?)");
            stm.setInt(1, saque.getId());
            stm.setInt(2, saque.getConta().getId());
            stm.setInt(3, saque.getTipoTransacao().getId());
            stm.setTimestamp(4, Timestamp.valueOf(saque.getData()));
            stm.setDouble(5, saque.getMontante());
            stm.setDouble(6, saque.getPreco());
            stm.setString(7, saque.getObservacao());

            stm.executeUpdate();

            contaDao.update(saque.getConta());

            return saque;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public Transferencia transferencia(Transferencia transferencia) {
        ContaDao contaDao = new ContaDao();
        TipoMoedaDao tipoMoedaDao = new TipoMoedaDao();
        try (Connection con = getConnection()){
            PreparedStatement stm;

            stm = con.prepareStatement("SELECT SEQ_TRANSACAO_ID.nextval as id from dual");

            ResultSet result = stm.executeQuery();

            if (!result.next()) {
                throw new SQLException("Não foi possível obter o próximo id de transacao");
            }

            transferencia.setId(result.getInt("id"));

            stm = con.prepareStatement("SELECT SEQ_TRANSFERENCIA_ID.nextval as id from dual");

            result = stm.executeQuery();

            if (!result.next()) {
                throw new SQLException("Não foi possível obter o próximo id de transferencia");
            }

            transferencia.setIdTransferencia(result.getInt("id"));

            stm = con.prepareStatement("INSERT INTO TRANSFERENCIA (id, id_contaorigem, id_contadestino) values (?, ?, ?)");
            stm.setInt(1, transferencia.getIdTransferencia());
            stm.setInt(2, transferencia.getContaOrigem().getId());
            stm.setInt(3, transferencia.getContaDestino().getId());

            stm.executeUpdate();

            TipoMoeda tipoMoeda = tipoMoedaDao.findById(transferencia.getContaOrigem().getIdTipoMoeda());

            transferencia.setPreco(tipoMoeda.getPrecoAtual());

            stm = con.prepareStatement("INSERT INTO TRANSACAO (ID, ID_CONTA, ID_TIPOTRANSACAO, ID_TRANSFERENCIA, DATA, MONTANTE, PRECO, OBSERVACAO) VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
            stm.setInt(1, transferencia.getId());
            stm.setInt(2, transferencia.getContaOrigem().getId());
            stm.setInt(3, transferencia.getTipoTransacao().getId());
            stm.setInt(4, transferencia.getIdTransferencia());
            stm.setTimestamp(5, Timestamp.valueOf(transferencia.getData()));
            stm.setDouble(6, transferencia.getMontante());
            stm.setDouble(7, transferencia.getPreco());
            stm.setString(8, transferencia.getObservacao());

            stm.executeUpdate();

            transferencia.getContaOrigem().saque(transferencia.getMontante());
            contaDao.update(transferencia.getContaOrigem());

            double totalOrigem = transferencia.getMontante() * transferencia.getPreco();
            tipoMoeda = tipoMoedaDao.findById(transferencia.getContaDestino().getIdTipoMoeda());
            transferencia.setPreco(tipoMoeda.getPrecoAtual());
            transferencia.setMontante(totalOrigem/tipoMoeda.getPrecoAtual());

            transferencia.getContaDestino().deposita(transferencia.getMontante());

            stm = con.prepareStatement("SELECT SEQ_TRANSACAO_ID.nextval as id from dual");

            result = stm.executeQuery();

            if (!result.next()) {
                throw new SQLException("Não foi possível obter o próximo id de transacao");
            }

            transferencia.setId(result.getInt("id"));

            stm = con.prepareStatement("INSERT INTO TRANSACAO (ID, ID_CONTA, ID_TIPOTRANSACAO, ID_TRANSFERENCIA, DATA, MONTANTE, PRECO, OBSERVACAO) VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
            stm.setInt(1, transferencia.getId());
            stm.setInt(2, transferencia.getContaDestino().getId());
            stm.setInt(3, transferencia.getTipoTransacao().getId());
            stm.setInt(4, transferencia.getIdTransferencia());
            stm.setTimestamp(5, Timestamp.valueOf(transferencia.getData()));
            stm.setDouble(6, transferencia.getMontante());
            stm.setDouble(7, transferencia.getPreco());
            stm.setString(8, transferencia.getObservacao());

            stm.executeUpdate();

            contaDao.update(transferencia.getContaDestino());

            return transferencia;

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
}
