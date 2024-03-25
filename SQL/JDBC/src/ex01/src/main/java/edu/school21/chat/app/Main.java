package edu.school21.chat.app;

import edu.school21.chat.models.Message;
import edu.school21.chat.repositories.JdbcDataSource;
import edu.school21.chat.repositories.MessagesRepository;
import edu.school21.chat.repositories.MessagesRepositoryJdbcImpl;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.Statement;
import java.util.Optional;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        JdbcDataSource dataSource = new JdbcDataSource();
        updateData("/scheme.sql", dataSource);
        updateData("/data.sql", dataSource);
        MessagesRepository repository = new MessagesRepositoryJdbcImpl(dataSource.getDataSource());
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Enter a message ID");
            try {
                String str = scanner.nextLine();
                if ("exit".equals(str)) System.exit(0);
                Optional<Message> message = repository.findById(Long.parseLong(str));
                if 
                    (message != null && message.isPresent()) System.out.println(message.get());
                else 
                    System.out.println("Message not found");
            } catch (NumberFormatException e) {
                System.out.print("Wrong id ");
                System.out.println(e.getMessage());
            }
        }
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
}
