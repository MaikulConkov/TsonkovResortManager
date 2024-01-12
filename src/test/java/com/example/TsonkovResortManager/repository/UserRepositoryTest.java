package com.example.TsonkovResortManager.repository;


import com.example.TsonkovResortManager.models.User;
import com.example.TsonkovResortManager.models.enums.Role;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class UserRepositoryTest {
    private Connection connection;
    UserRepository repo;

    @BeforeEach
    void setUp() throws SQLException{
        connection = DriverManager.getConnection("jdbc:h2:C:\\Users\\maiki\\OneDrive\\Desktop\\DBeaverDb\\TsonkovResortManagerDB");
        connection.setAutoCommit(false);
        repo=new UserRepository(connection);
    }

    @AfterEach
    void tearDown() throws SQLException{
        if(connection!=null){
            connection.rollback();
            connection.close();
        }
    }

    @Test
    public void canSaveOneUser() throws SQLException{
        User user = new User();
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setEmail("johnn.doe@example.com");
        user.setPassword("dsoioioiio");
        user.setPhoneNumber("1234567890");
        user.setRole(Role.User);

        User savedPerson=repo.save(user);

        assertThat(savedPerson.getId()).isGreaterThan(0);
    }

    @Test
    public void canFindUserById()throws SQLException{
        User user = new User();
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setEmail("joh.doe@example.com");
        user.setPassword("1202010");
        user.setPhoneNumber("1234567890");
        user.setRole(Role.User);
        User savedPerson=repo.save(user);

        User foundPerson=repo.findById(savedPerson.getId()).get();

        assertThat(foundPerson.getId()).isEqualTo(savedPerson.getId());
    }

    @Test
    public  void canFindAllUsers()throws SQLException{
        User user = new User();
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setEmail("joh.doe@example.com");
        user.setPassword("1202010");
        user.setPhoneNumber("1234567890");
        user.setRole(Role.User);
        User savedPerson=repo.save(user);

        List<User> users=repo.findAll();

        assertThat(users.size()).isNotEqualTo(0);
    }

    @Test
    public void canDeleteUser() throws SQLException{
        User user = new User();
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setEmail("joh.doe@example.com");
        user.setPassword("1202010");
        user.setPhoneNumber("1234567890");
        user.setRole(Role.User);
        User savedPerson=repo.save(user);
        int userTableSize=repo.findAll().size();

        repo.delete(savedPerson);

        int userTableSizeAfterDelete=repo.findAll().size();

        assertThat(userTableSize).isNotEqualTo(userTableSizeAfterDelete);
    }
    @Test
    public void canDeleteUserById() throws SQLException{
        User user = new User();
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setEmail("joh.doe@example.com");
        user.setPassword("1202010");
        user.setPhoneNumber("1234567890");
        user.setRole(Role.User);
        User savedPerson=repo.save(user);
        int userTableSize=repo.findAll().size();

        repo.deleteById(savedPerson.getId());

        int userTableSizeAfterDelete=repo.findAll().size();

        assertThat(userTableSize).isNotEqualTo(userTableSizeAfterDelete);
    }

    @Test
    public void canUpdateUserById() throws  SQLException{
        User user = new User();
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setEmail("joh.doe@example.com");
        user.setPassword("1202010");
        user.setPhoneNumber("1234567890");
        user.setRole(Role.User);
        User user1=repo.save(user);

        User user2 = repo.findById(user1.getId()).get();

        user1.setFirstName("Jpnathan");
        repo.update(user1);

        User user3=repo.findById(user2.getId()).get();

        assertThat(user3.getFirstName()).isNotEqualTo(user2.getFirstName());
    }
}