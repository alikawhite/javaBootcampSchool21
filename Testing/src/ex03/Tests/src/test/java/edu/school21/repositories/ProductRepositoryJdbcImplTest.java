package edu.school21.repositories;

import edu.school21.models.Product;
import edu.school21.repositories.ProductRepository;
import edu.school21.repositories.ProductRepositoryJdbcImpl;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class ProductRepositoryJdbcImplTest {
    private ProductRepository repository;
    private EmbeddedDatabase dataSource;


    final List<Product> EXPECTED_FIND_ALL_PRODUCTS = Arrays.asList(
            new Product(1L, "chocolate", 200),
            new Product(2L, "eggs", 100),
            new Product(3L, "bread", 50),
            new Product(4L, "butter", 100),
            new Product(5L, "cheese", 150)
    );
    final Product EXPECTED_FIND_BY_ID_PRODUCT = new Product(4L, "butter", 100);
    final Product EXPECTED_UPDATED_PRODUCT = new Product(2L, "tea", 120);
    final Product EXPECTED_SAVE_PRODUCT = new Product(6L, "banana", 80);
    final List<Product> EXPECTED_FIND_ALL_AFTER_DELETE = Arrays.asList(
            new Product(2L, "eggs", 100),
            new Product(3L, "bread", 50),
            new Product(4L, "butter", 100),
            new Product(5L, "cheese", 150)
    );

    @BeforeEach
    public void init() {
        dataSource = new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.HSQL)
                .addScript("schema.sql").addScript("data.sql").build();
        repository = new ProductRepositoryJdbcImpl(dataSource);
    }

    @Test
    public void testFindAll() {
        List<Product> actual = repository.findAll();
        assertEquals(EXPECTED_FIND_ALL_PRODUCTS, actual);
    }

    @Test
    public void testFindById() {
        assertEquals(EXPECTED_FIND_BY_ID_PRODUCT, repository.findById(4L).get());
        assertEquals(Optional.empty(), repository.findById(100L));
        assertEquals(Optional.empty(), repository.findById(null));
    }

    @Test
    public void testUpdate() {
        repository.update(new Product(2L, "tea", 120));
        assertEquals(EXPECTED_UPDATED_PRODUCT, repository.findById(2L).get());
    }

    @Test
    public void testSave() {
        repository.save(new Product(6L, "banana", 80));
        assertEquals(EXPECTED_SAVE_PRODUCT, repository.findById(6L).get());
    }

    @Test
    public void testDelete() {
        repository.delete(1L);
        assertEquals(EXPECTED_FIND_ALL_AFTER_DELETE, repository.findAll());
        assertFalse(repository.findById(1L).isPresent());
    }
    @AfterEach
    void close() {
        dataSource.shutdown();
    }
}