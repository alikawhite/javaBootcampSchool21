package edu.school21.services;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import edu.school21.models.Product;
import edu.school21.models.User;
import edu.school21.repositories.UsersRepository;
import edu.school21.exceptions.AlreadyAuthenticatedException;
import edu.school21.exceptions.EntityNotFoundException;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;

public class UsersServiceImpl implements UsersRepository {
    private DataSource dataSource;
    private String updateQuery = "UPDATE shop.user SET auth_status = ? WHERE identifier = ?";
    private String findByLoginQuery = "SELECT * FROM shop.user WHERE login = \'";

    public UsersServiceImpl(EmbeddedDatabase dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public User findByLogin(String login) {
        System.out.println(findByLoginQuery + login + "\'");
        try (Connection connection = dataSource.getConnection();
             ResultSet resultSet = connection.createStatement().executeQuery(findByLoginQuery + login + "\'")) {
            if (!resultSet.next()) throw new EntityNotFoundException();
            System.out.println(resultSet.getString(2));
            return new User(resultSet.getLong(1), resultSet.getString(2),
                    resultSet.getString(3), resultSet.getBoolean(4));
        } catch (SQLException e) {
            System.out.println(e.getStackTrace());
        }
        return null;
    }

    @Override
    public void update(User user) {
        findByLogin(user.getLogin());
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(updateQuery);
            statement.setBoolean(1, true);
            statement.setLong(2, user.getIdentifier());
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.print(e.getStackTrace());
        }
    }

    public boolean authenticate(String login, String password) {
        User user = findByLogin(login);
        if (user.isAuthStatus()) throw new AlreadyAuthenticatedException();
        if (!user.getPassword().equals(password)) return false;
        user.setAuthStatus(true);
        update(user);
        return true;
    }
}
