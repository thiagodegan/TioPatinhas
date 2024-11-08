package br.com.duckchain.dao;

import br.com.duckchain.factory.ConnectionFactory;
import br.com.duckchain.model.Usuario;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDao extends ConnectionFactory {
    private Usuario parseUsuario(ResultSet result) throws SQLException {
        int id = result.getInt("id");
        String nome = result.getString("nome");
        String email = result.getString("email");
        String senha = result.getString("senha");
        LocalDateTime dataCriacao = result.getTimestamp("datacriacao").toLocalDateTime();

        return  new Usuario(id, nome, email, senha, dataCriacao);
    }

    // Método para buscar um usuário por email
    public Usuario findByEmail(String email) {
        try (Connection con = getConnection()){
            PreparedStatement stm;
            stm = con.prepareStatement("SELECT * FROM USUARIO WHERE EMAIL = ?");
            stm.setString(1, email);

            ResultSet result = stm.executeQuery();

            if (!result.next()) {
                return null;
            }

            return parseUsuario(result);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    // Método para buscar um usuário por id
    public Usuario findById(int id) {
        try (Connection con = getConnection()){
            PreparedStatement stm;
            stm = con.prepareStatement("SELECT * FROM USUARIO WHERE id = ?");
            stm.setInt(1, id);

            ResultSet result = stm.executeQuery();

            if (!result.next()) {
                return null;
            }

            return parseUsuario(result);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    // Método para buscar todos os usuários
    public List<Usuario> findAll() {
        try (Connection con = getConnection()){
            PreparedStatement stm;
            stm = con.prepareStatement("SELECT * FROM USUARIO order by id");

            List<Usuario> usuarios = new ArrayList<>();
            ResultSet result = stm.executeQuery();

            while (result.next()) {
                usuarios.add(parseUsuario(result));
            }

            return usuarios;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    // Método para salvar o usuário na lista
    public Usuario create(Usuario usuario) {
        try (Connection con = getConnection()){
            PreparedStatement stm;
            stm = con.prepareStatement("SELECT SEQ_USUARIO_ID.nextval as id FROM DUAL");
            ResultSet result = stm.executeQuery();

            if (!result.next()) {
                throw new SQLException("Não foi possível obter o próximo id de usuário");
            }

            usuario.setId(result.getInt("id"));

            stm = con.prepareStatement("INSERT INTO USUARIO (id, nome, email, senha, datacriacao) VALUES (?, ?, ?, ?, ?)");
            stm.setInt(1, usuario.getId());
            stm.setString(2, usuario.getNome());
            stm.setString(3, usuario.getEmail());
            stm.setString(4, usuario.getSenha());
            stm.setTimestamp(5, Timestamp.valueOf(usuario.getDataCriacao()));

            stm.executeUpdate();

            return  usuario;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public Usuario update(Usuario usuario) {
        try (Connection con = getConnection()){
            PreparedStatement stm;
            stm = con.prepareStatement("UPDATE USUARIO SET nome=?, email=?, senha=?, datacriacao=? WHERE id = ?");
            stm.setString(1, usuario.getNome());
            stm.setString(2, usuario.getEmail());
            stm.setString(3, usuario.getSenha());
            stm.setTimestamp(4, Timestamp.valueOf(usuario.getDataCriacao()));
            stm.setInt(5, usuario.getId());

            stm.executeUpdate();

            return usuario;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return  null;
        }
    }

    public void delete(Usuario usuario) {
        try (Connection con = getConnection()){
            PreparedStatement stm;
            stm = con.prepareStatement("DELETE FROM USUARIO WHERE id = ?");
            stm.setInt(1, usuario.getId());

            stm.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
