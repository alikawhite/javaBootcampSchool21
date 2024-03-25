package edu.school21.chat.app;

import edu.school21.chat.exceptons.NotSavedSubEntityException;
import edu.school21.chat.models.Chatroom;
import edu.school21.chat.models.Message;
import edu.school21.chat.models.User;
import edu.school21.chat.repositories.JdbcDataSource;
import edu.school21.chat.repositories.MessagesRepository;
import edu.school21.chat.repositories.MessagesRepositoryJdbcImpl;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        JdbcDataSource dataSource = new JdbcDataSource();
        updateData("/scheme.sql", dataSource);
        updateData("/data.sql", dataSource);
        MessagesRepository repository = new MessagesRepositoryJdbcImpl(dataSource.getDataSource());
        createNewMessage(repository);
    }

    private static void updateData(String file, JdbcDataSource dataSource) {
        try (Connection connection = dataSource.getConnection(); InputStream inputStream = Main.class.getResourceAsStream(file)) {
            StringBuilder scriptContent = new StringBuilder();
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
                String line;
                while ((line = reader.readLine()) != null)
                    scriptContent.append(line).append("\n");
            }
            try (Statement statement = connection.createStatement()) {
                statement.execute(scriptContent.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private static void createNewMessage(MessagesRepository repository) {
        User creator = new User(1L, "abo", "admin", new ArrayList<>(), new ArrayList<>());
        User author = creator;
        Chatroom room = new Chatroom(1L, "Chat1", creator, new ArrayList<>());
        Message message = new Message(null, author, room, "hi!", LocalDateTime.now());

        System.out.println("---NEW MESSAGE---");
        try {
            repository.save(message);
            System.out.println("New message id = " + message.getId());
        } catch (NotSavedSubEntityException e) {
            System.out.println(e.getMessage());
        }

        System.out.println("---AUTHOR IS EMPTY---");
        try {
            creator = new User(2L, "abobus", "admin", null, null);
            author = null;
            room = new Chatroom(2L, "Chat2", creator, null);
            message = new Message(null, author, room, "hi!", LocalDateTime.now());
            repository.save(message);
        } catch (NotSavedSubEntityException e) {
            System.out.println(e.getMessage());
        }

        System.out.println("---CHATROOM DOESN'T EXIST---");
        try {
            creator = new User(2L, "abobus", "admin", null, null);
            author = creator;
            room = new Chatroom(21L, "Chat22", creator, null);
            message = new Message(null, author, room, "No Chatroom", LocalDateTime.now());
            repository.save(message);
        } catch (NotSavedSubEntityException e) {
            System.out.println(e.getMessage());
        }

        System.out.println("---AUTHOR DOESN'T EXIST---");
        try {
            creator = new User(22L, "cuscus", "admin", null, null);
            author = creator;
            room = new Chatroom(2L, "Chat2", creator, null);
            message = new Message(null, author, room, "Unknown author", LocalDateTime.now());
            repository.save(message);
        } catch (NotSavedSubEntityException e) {
            System.out.println(e.getMessage());
        }

        System.out.println("---ANOTHER MESSAGE FROM Abo---");
        try {
            creator = new User(1L, "Abo", "admin", null, null);
            author = creator;
            room = new Chatroom(6L, "Chat1", creator, null);
            message = new Message(null, author, room, "abobobobo", LocalDateTime.now());
            repository.save(message);
            System.out.println("New message id = " + message.getId());
        } catch (NotSavedSubEntityException e) {
            System.out.println(e.getMessage());
        }
    }
}
