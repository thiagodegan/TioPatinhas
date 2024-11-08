package br.com.duckchain.dao;

import br.com.duckchain.factory.ConnectionFactory;
import br.com.duckchain.model.TipoTransacao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TipoTransacaoDao extends ConnectionFactory {
    private TipoTransacao parseTipoTransacao(ResultSet result) throws SQLException {
        int id = result.getInt("id");
        String descricao = result.getString("descricao");
        return new TipoTransacao(id, descricao);
    }
    public TipoTransacao findById(int idTipoTransacao) {
        try (Connection con = getConnection()){
            PreparedStatement stm = con.prepareStatement("SELECT * FROM TIPOTRANSACAO WHERE id =?");
            stm.setInt(1, idTipoTransacao);

            ResultSet result = stm.executeQuery();

            if (!result.next()) {
                return  null;
            }

            return parseTipoTransacao(result);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return  null;
        }
    }
}
