package edu.school21.repositories;

import edu.school21.models.Product;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProductRepositoryJdbcImpl implements ProductRepository {
    private DataSource dataSource;
    private final String findByIdQuery = "SELECT * FROM shop.product WHERE identifier = ";
    private final String findAllQuery = "SELECT * FROM shop.product";
    private final String updateQuery = "UPDATE shop.product SET name = ?, price = ? WHERE identifier = ?";
    private final String saveQuery = "INSERT INTO shop.product(name, price) VALUES (?, ?)";
    private final String deleteQuery = "DELETE FROM shop.product WHERE identifier = ";

    public ProductRepositoryJdbcImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public List<Product> findAll() {
        List<Product> list = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(findAllQuery)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Product product = new Product(resultSet.getLong(1),
                        resultSet.getString(2), resultSet.getInt(3));
                list.add(product);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return list;
    }


    @Override
    public Optional<Product> findById(Long id) {
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(findByIdQuery + id);
            if (!resultSet.next()) return Optional.empty();
            return Optional.of(new Product(resultSet.getLong(1),
                    resultSet.getString(2), resultSet.getInt(3)));
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return Optional.empty();
    }

    @Override
    public void update(Product product) {
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(updateQuery);
            statement.setString(1, product.getName());
            statement.setInt(2, product.getPrice());
            statement.setLong(3, product.getIdentifier());
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void save(Product product) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(saveQuery)) {
            preparedStatement.setString(1, product.getName());
            preparedStatement.setLong(2, product.getPrice());
            preparedStatement.execute();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void delete(Long id) {
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {
            statement.executeQuery(deleteQuery + id);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
