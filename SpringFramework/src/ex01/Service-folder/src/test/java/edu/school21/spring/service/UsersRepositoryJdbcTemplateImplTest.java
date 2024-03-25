package edu.school21.spring.service;import edu.school21.spring.service.models.User;import edu.school21.spring.service.repositories.UsersRepository;import org.junit.jupiter.api.Test;import org.springframework.context.ApplicationContext;import org.springframework.context.support.ClassPathXmlApplicationContext;import java.util.Arrays;import java.util.List;import static org.junit.jupiter.api.Assertions.assertEquals;public class UsersRepositoryJdbcTemplateImplTest {    ApplicationContext context = new ClassPathXmlApplicationContext("context.xml");    UsersRepository repository = context.getBean("usersRepositoryJdbcTemplate", UsersRepository.class);    final List<User> EXPECTED_FIND_ALL_USERS = Arrays.asList(            new User(1L, "proixiweinnaugri-9680@yopmail.com"),            new User(2L, "grayubroiddeitta-7342@yopmail.com"),            new User(3L, "wapellewussu-1518@yopmail.com"),            new User(4L, "goitrekegreiha-2040@yopmail.com"),            new User(5L, "neiviteibilau-2854@yopmail.com"),            new User(6L, "nihahilopri-8440@yopmail.com")    );    final User EXPECTED_FIND_BY_ID_USER = new User(4L, "goitrekegreiha-2040@yopmail.com");    final User EXPECTED_UPDATED_USER = new User(2L, "aboba@yopmail.com");    final User EXPECTED_SAVE_USER = new User(7L, "banana@mail.ru");    final List<User> EXPECTED_FIND_ALL_AFTER_DELETE = Arrays.asList(            new User(2L, "grayubroiddeitta-7342@yopmail.com"),            new User(3L, "wapellewussu-1518@yopmail.com"),            new User(4L, "goitrekegreiha-2040@yopmail.com"),            new User(5L, "neiviteibilau-2854@yopmail.com"),            new User(6L, "nihahilopri-8440@yopmail.com"),            new User(7L, "banana@mail.ru")    );    @Test    public void testFindAll() {        List<User> actual = repository.findAll();        assertEquals(EXPECTED_FIND_ALL_USERS, actual);    }    @Test    public void testFindById() {        assertEquals(EXPECTED_FIND_BY_ID_USER, repository.findById(4L));        assertEquals(null, repository.findById(100L));        assertEquals(null, repository.findById(null));    }    @Test    public void testUpdate() {        repository.update(new User(2L, "aboba@yopmail.com"));        assertEquals(EXPECTED_UPDATED_USER, repository.findById(2L));    }    @Test    public void testSave() {        repository.save(new User(7L, "banana@mail.ru"));        assertEquals(EXPECTED_SAVE_USER, repository.findById(7L));    }    @Test    public void testDelete() {        repository.delete(1L);        assertEquals(EXPECTED_FIND_ALL_AFTER_DELETE, repository.findAll());    }}