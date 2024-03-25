package edu.school21.chat.repositories;

import java.sql.Connection;
import java.sql.SQLException;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class JdbcDataSource {
    private HikariDataSource dataSource;

    public JdbcDataSource() {
       HikariConfig config = new HikariConfig();
       config.setJdbcUrl("jdbc:postgresql://localhost:5431/postgres");
       config.setUsername("postgres");
       config.setPassword("postgres");
       dataSource = new HikariDataSource(config);
    }

   public HikariDataSource getDataSource() {
       return dataSource;
   }

   public Connection getConnection() throws SQLException {
       return dataSource.getConnection();
   }
}