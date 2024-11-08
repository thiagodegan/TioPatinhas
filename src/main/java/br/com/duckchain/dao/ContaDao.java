package br.com.duckchain.dao;

import br.com.duckchain.factory.ConnectionFactory;
import br.com.duckchain.model.Conta;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ContaDao extends ConnectionFactory {

    private Conta parseConta(ResultSet result) throws SQLException {
        int id = result.getInt("id");
        int id_usuario = result.getInt("id_usuario");
        int id_tipomoeda = result.getInt("id_tipomoeda");
        LocalDateTime dataCriacao = result.getTimestamp("datacriacao").toLocalDateTime();
        String numeroConta = result.getString("numeroconta");
        double saldo = result.getDouble("saldo");
        return new Conta(id, id_usuario, id_tipomoeda, dataCriacao, numeroConta, saldo);
    }

    // Implementação do método de busca por ID
    public Conta findById(int id) {
        // Lógica para buscar a conta no banco de dados
        try (Connection con = getConnection()){
            PreparedStatement stm;
            stm = con.prepareStatement("SELECT * FROM CONTA WHERE ID = ?");
            stm.setInt(1, id);
            ResultSet result = stm.executeQuery();

            if (!result.next()) {
                return  null;
            }

            return parseConta(result);

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return  null;
        }

    }

    public List<Conta> findByIdUsuario(int idUsuario) {
        // Lógica para buscar a conta no banco de dados
        try (Connection con = getConnection()){
            PreparedStatement stm;
            stm = con.prepareStatement("SELECT * FROM CONTA WHERE ID_USUARIO = ?");
            stm.setInt(1, idUsuario);
            ResultSet result = stm.executeQuery();

            List<Conta> contas = new ArrayList<>();

            while (result.next()) {
                contas.add(parseConta(result));
            }

            return contas;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public List<Conta> findByIdMoeda(int idMoeda) {
        // Lógica para buscar a conta no banco de dados
        try (Connection con = getConnection()){
            PreparedStatement stm;
            stm = con.prepareStatement("SELECT * FROM CONTA WHERE ID_TIPOMOEDA = ?");
            stm.setInt(1, idMoeda);
            ResultSet result = stm.executeQuery();

            List<Conta> contas = new ArrayList<>();

            while (result.next()) {
                contas.add(parseConta(result));
            }

            return contas;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public List<Conta> findAll() {
        try (Connection con = getConnection()){
            PreparedStatement stm;
            stm = con.prepareStatement("SELECT * FROM CONTA ORDER BY ID");
            ResultSet result = stm.executeQuery();
            List<Conta> lista = new ArrayList<>();
            while (result.next()) {
                lista.add(parseConta(result));
            }

            return  lista;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return  null;
        }
    }

    // Implementação do método de salvamento
    public Conta create(Conta conta) {
        // Lógica para salvar a conta no banco de dados
        try (Connection con = getConnection()){
            PreparedStatement stm;
            stm = con.prepareStatement("SELECT SEQ_CONTA_ID.nextval as id FROM DUAL");
            ResultSet resultSet = stm.executeQuery();
            if (!resultSet.next()) {
                throw new SQLException("Não foi possível obter o próximo id de conta!");
            }

            conta.setId(resultSet.getInt("id"));

            stm = con.prepareStatement("INSERT INTO CONTA (id, id_usuario, id_tipomoeda, datacriacao, numeroconta, saldo) VALUES (?, ?, ?, ?, ?, ?)");
            stm.setInt(1, conta.getId());
            stm.setInt(2, conta.getIdUsuario());
            stm.setInt(3, conta.getIdTipoMoeda());
            stm.setTimestamp(4, Timestamp.valueOf(conta.getDataCriacao()));
            stm.setString(5, conta.getNumeroConta());
            stm.setDouble(6, conta.getSaldo());

            stm.executeUpdate();

            // Conta o próximo ID
            return  conta;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public Conta update(Conta conta) {
        try (Connection con = getConnection()){
            PreparedStatement stm;
            stm = con.prepareStatement("UPDATE CONTA SET id_usuario=?, id_tipomoeda=?, datacriacao=?, numeroconta=?, saldo=? where id = ?");
            stm.setInt(1, conta.getIdUsuario());
            stm.setInt(2, conta.getIdTipoMoeda());
            stm.setTimestamp(3, Timestamp.valueOf(conta.getDataCriacao()));
            stm.setString(4, conta.getNumeroConta());
            stm.setDouble(5, conta.getSaldo());
            stm.setInt(6, conta.getId());

            stm.executeUpdate();

            return conta;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public void delete(Conta conta) {
        try (Connection con = getConnection()){
            PreparedStatement stm;
            stm = con.prepareStatement("DELETE FROM CONTA where id = ?");
            stm.setInt(1, conta.getId());

            stm.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
