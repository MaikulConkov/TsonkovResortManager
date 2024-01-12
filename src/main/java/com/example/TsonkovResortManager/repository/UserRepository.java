package com.example.TsonkovResortManager.repository;

import com.example.TsonkovResortManager.models.User;
import com.example.TsonkovResortManager.models.enums.Role;
import com.example.TsonkovResortManager.repository.interfaces.UserRepositoryInterface;

import java.security.Permission;
import java.sql.*;

public class UserRepository extends CRUDRepository<User, Long>{

    public UserRepository(Connection connection){
        super(connection);
    }


    @Override
    String saveSQL() {
        return "INSERT INTO Users (first_name, last_name, email, password, phone_number, role) VALUES (?, ?, ?, ?, ?, ?)";
    }

    @Override
    String findByIdSQL() {
        return "SELECT ID, FIRST_NAME, LAST_NAME, EMAIL, PASSWORD, PHONE_NUMBER, ROLE, CREATED_AT FROM USERS WHERE ID=?";
    }

    @Override
    String findAllSQL() {
        return "SELECT ID, FIRST_NAME, LAST_NAME, EMAIL, PASSWORD, PHONE_NUMBER, ROLE, CREATED_AT FROM USERS";
    }

    @Override
    String deleteEntitySQL() {
        return "DELETE FROM USERS WHERE ID=?";
    }

    @Override
    String updateEntitySQL() {
        return "UPDATE USERS SET FIRST_NAME=?, LAST_NAME=?, EMAIL=?, PASSWORD=?, PHONE_NUMBER=? WHERE ID=?";
    }


    @Override
    void mapForSave(User entity, PreparedStatement ps) throws SQLException {
            ps.setString(1,entity.getFirstName());
            ps.setString(2,entity.getLastName());
            ps.setString(3,entity.getEmail());
            ps.setString(4,entity.getPassword());
            ps.setString(5,entity.getPhoneNumber());
            ps.setString(6,entity.getRole().toString());
    }

    @Override
    User extractEntityFromResultSet(ResultSet rs) throws SQLException {
        Long id = rs.getLong("ID");
        String firstName= rs.getString("FIRST_NAME");
        String lastName= rs.getString("LAST_NAME");
        String email= rs.getString("EMAIL");
        String password= rs.getString("PASSWORD");
        String phoneNumber= rs.getString("PHONE_NUMBER");
        Role role = Role.valueOf(rs.getString("ROLE"));
        Date date = new Date(rs.getTimestamp("CREATED_AT").getTime());
        return new User(id,firstName,lastName,email,password,phoneNumber,role,date);
    }

    @Override
    void mapForUpdate(User entity, PreparedStatement ps) throws SQLException {
        ps.setString(1,entity.getFirstName());
        ps.setString(2,entity.getLastName());
        ps.setString(3,entity.getEmail());
        ps.setString(4,entity.getPassword());
        ps.setString(5,entity.getPhoneNumber());
    }
}
