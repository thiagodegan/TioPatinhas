package br.com.duckchain.dao;

import br.com.duckchain.factory.ConnectionFactory;
import br.com.duckchain.model.TipoMoeda;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TipoMoedaDao extends ConnectionFactory {
    private TipoMoeda parseTipoMoeda(ResultSet result) throws SQLException {
        int id = result.getInt("id");
        String nome = result.getString("nome");
        String codigo = result.getString("codigo");
        double precoAtual = result.getDouble("precoatual");

        return new TipoMoeda(id, nome, codigo, precoAtual);
    }

    public TipoMoeda findById (int idMoeda) {
        try (Connection con = getConnection()){
            PreparedStatement stm;
            stm = con.prepareStatement("SELECT * FROM TIPOMOEDA WHERE ID = ?");
            stm.setInt(1, idMoeda);

            ResultSet result = stm.executeQuery();

            if (!result.next()) {
                return  null;
            }

            return parseTipoMoeda(result);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return  null;
        }
    }

    public List<TipoMoeda> findAll () {
        try (Connection con = getConnection()){
            PreparedStatement stm;
            stm = con.prepareStatement("SELECT * FROM TIPOMOEDA");

            ResultSet result = stm.executeQuery();
            List<TipoMoeda> tipoMoedas = new ArrayList<>();

            while (result.next()) {
                tipoMoedas.add(parseTipoMoeda(result));
            }

            return tipoMoedas;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return  null;
        }
    }

    public TipoMoeda create(TipoMoeda tipoMoeda) {
        try (Connection con = getConnection()){
            PreparedStatement stm;

            stm = con.prepareStatement("SELECT SEQ_TIPOMOEDA_ID.nextval as id FROM DUAL");
            ResultSet resultSet = stm.executeQuery();

            if (!resultSet.next()) {
                throw  new SQLException("Não foi possível obter o próximo id de moeda");
            }

            tipoMoeda.setId(resultSet.getInt("id"));

            stm = con.prepareStatement("INSERT INTO TIPOMOEDA (ID, NOME, CODIGO, PRECOATUAL) VALUES (?, ?, ?, ?)");
            stm.setInt(1, tipoMoeda.getId());
            stm.setString(2, tipoMoeda.getNome());
            stm.setString(3, tipoMoeda.getCodigo());
            stm.setDouble(4, tipoMoeda.getPrecoAtual());

            stm.executeUpdate();

            return tipoMoeda;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return  null;
        }
    }

    public TipoMoeda update(TipoMoeda tipoMoeda) {
        try (Connection con = getConnection()){
            PreparedStatement stm;
            stm = con.prepareStatement("UPDATE TIPOMOEDA SET NOME=?, CODIGO=?, PRECOATUAL=? WHERE ID=?");
            stm.setString(1, tipoMoeda.getNome());
            stm.setString(2, tipoMoeda.getCodigo());
            stm.setDouble(3, tipoMoeda.getPrecoAtual());
            stm.setInt(4, tipoMoeda.getId());

            stm.executeUpdate();

            return  tipoMoeda;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return  null;
        }
    }

    public void delete(TipoMoeda tipoMoeda) {
        try (Connection con = getConnection()){
            PreparedStatement stm;
            stm = con.prepareStatement("DELETE FROM TIPOMOEDA WHERE ID=?");
            stm.setInt(1, tipoMoeda.getId());

            stm.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
