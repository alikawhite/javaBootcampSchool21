package edu.school21.chat.repositories;

import edu.school21.chat.exceptons.NotSavedSubEntityException;
import edu.school21.chat.models.Chatroom;
import edu.school21.chat.models.Message;
import edu.school21.chat.models.User;

import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;

public class MessagesRepositoryJdbcImpl implements MessagesRepository {
    private DataSource dataSource;
    private final String chatQuery = "SELECT * FROM chat.chatroom WHERE id = ";
    private final String userQuery = "SELECT * FROM chat.user WHERE id = ";
    private final String messageQuery = "SELECT * FROM chat.message WHERE id = ";

    public MessagesRepositoryJdbcImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Optional<Message> findById(Long id) {
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(messageQuery + id);
            if (!resultSet.next()) return Optional.empty();

            Long userId = resultSet.getLong(2);
            Long roomId = resultSet.getLong(3);
            User user = findUserById(userId);
            Chatroom room = findChatById(roomId);
            return Optional.of(new Message(resultSet.getLong(1), user, room,
                    resultSet.getString(4), resultSet.getTimestamp(5).toLocalDateTime()));
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return Optional.empty();
    }

    private Chatroom findChatById(Long id) {
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(chatQuery + id);
            if (!resultSet.next()) return null;
            return new Chatroom(id, resultSet.getString(2));
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    private User findUserById(Long id) {
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(userQuery + id);
            if (!resultSet.next()) return null;
            return new User(id, resultSet.getString(2), resultSet.getString(3));
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public void save(Message message) {
        checkMessage(message);
        String localDateTime = "";
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(userQuery + message.getAuthor().getId());
            if (!resultSet.next())
                throw new NotSavedSubEntityException("User with id = " +  message.getAuthor().getId() + " doesn't exist");
            resultSet = statement.executeQuery(chatQuery + message.getChatroom().getId());
            if (!resultSet.next())
                throw new NotSavedSubEntityException("User with id = " +  message.getChatroom().getId() + " doesn't exist");
            if (message.getLocalDateTime() != null)
                localDateTime = "'" + Timestamp.valueOf(message.getLocalDateTime()) + "'";
            resultSet = statement.executeQuery("INSERT INTO chat.message (author, room, text, localDateTime) VALUES ("
                   +  message.getAuthor().getId() + ", " + message.getChatroom().getId() + ", '" + message.getText() + "', " + localDateTime + ") RETURNING id");
            if (!resultSet.next())
                throw new NotSavedSubEntityException("Internal Error");
            message.setId(resultSet.getLong(1));
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private void checkMessage(Message message) {
        if (message.getAuthor() == null || message.getAuthor().getId() == null)
            throw new NotSavedSubEntityException("Author doesn't exist");
        if (message.getChatroom() == null || message.getChatroom().getId() == null)
            throw new NotSavedSubEntityException("Chatroom doesn't exist");
        if (message.getChatroom().getOwner() == null || message.getChatroom().getOwner().getId() == null)
            throw new NotSavedSubEntityException("Chatroom's owner doesn't exist");
        if (message.getText().isEmpty())
            throw new NotSavedSubEntityException("Text is empty");
    }
}
