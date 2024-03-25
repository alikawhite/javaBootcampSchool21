package edu.school21.chat.repositories;

import edu.school21.chat.models.Chatroom;
import edu.school21.chat.models.Message;
import edu.school21.chat.models.User;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Optional;

public class MessagesRepositoryJdbcImpl implements MessagesRepository {
    private DataSource dataSource;
    public MessagesRepositoryJdbcImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Optional<Message> findById(Long id) {
        String mQuery = "SELECT * FROM chat.message WHERE id = " + id;

        try (Connection connection = dataSource.getConnection();
             Statement st = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(mQuery);
            if (!resultSet.next()) return Optional.empty();
            Long userId = resultSet.getLong(2);
            Long roomId = resultSet.getLong(3);
            User user = findUser(userId);
            Chatroom room = findChat(roomId);
            return Optional.of(new Message(resultSet.getLong(1), user, room,
                    resultSet.getString(4), resultSet.getTimestamp(5).toLocalDateTime()));
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return Optional.empty();
    }

    private Chatroom findChat(Long id) {
        String mQuery = "SELECT * FROM chat.chatroom WHERE id = " + id;
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(mQuery);
            if (!resultSet.next()) return null;
            return new Chatroom(id, resultSet.getString(2));
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    private User findUser(Long id) {
        String mQuery = "SELECT * FROM chat.user WHERE id = " + id;
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(mQuery);
            if (!resultSet.next()) return null;
            return new User(id, resultSet.getString(2), resultSet.getString(3));
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

}
