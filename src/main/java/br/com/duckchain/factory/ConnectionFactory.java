package br.com.duckchain.factory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public abstract class ConnectionFactory {
    private static final String URL =
            "jdbc:oracle:thin:@oracle.fiap.com.br:1521:orcl";
    private static final String USUARIO = "rm99380";
    private static final String SENHA = "220887";
    protected Connection getConnection() throws
            SQLException {
        return DriverManager.getConnection(URL, USUARIO, SENHA);
    }
}