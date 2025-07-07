package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexaoDB {
    // URL de ligação à base de dados MySQL
    private static final String URL = "jdbc:mysql://localhost:3306/ci-java";
    private static final String USER = "root"; // Substituir pelo seu utilizador
    private static final String PASSWORD = "*"; // Substituir pela sua password

    /**
     * Retorna uma conexão ativa à base de dados.
     */
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
