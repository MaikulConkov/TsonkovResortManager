package com.example.TsonkovResortManager.repository;


import com.example.TsonkovResortManager.models.Entity;
import com.example.TsonkovResortManager.repository.interfaces.CRUDRepositoryInterface;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public abstract class CRUDRepository<T extends Entity, ID> implements CRUDRepositoryInterface<T,ID> {

    private Connection connection;

    public CRUDRepository(Connection connection){
        this.connection=connection;
    }

    @Override
    public T save(T entity) {
        try{
            PreparedStatement ps=connection.prepareStatement(saveSQL(), Statement.RETURN_GENERATED_KEYS);
            mapForSave(entity,ps);
            int recordsAffected=ps.executeUpdate();
            ResultSet rs= ps.getGeneratedKeys();
            while (rs.next()){
                long id=rs.getLong("ID");
                entity.setId(id);
            }
            System.out.println("Records affected"+ recordsAffected);
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return entity;
    }

    @Override
    public Optional<T> findById(ID id) {
        T entity=null;
        try{
            PreparedStatement ps = connection.prepareStatement(findByIdSQL());
            ps.setLong(1,(Long)id);
            ResultSet rs=ps.executeQuery();
            while (rs.next()){
                entity=extractEntityFromResultSet(rs);
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }

        return Optional.ofNullable(entity);
    }

    @Override
    public List findAll() {
        List<T> list = new ArrayList<>();
        try {
            PreparedStatement ps = connection.prepareStatement(findAllSQL());
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                T entity=extractEntityFromResultSet(rs);
                list.add(entity);
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public void delete(T entity) {
        try{
            PreparedStatement ps = connection.prepareStatement(deleteEntitySQL());
            ps.setLong(1,entity.getId());
            ps.executeUpdate();
        }
        catch(SQLException e){
            e.printStackTrace();
        }

    }

    @Override
    public void deleteById(ID id) {
        try{
            PreparedStatement ps = connection.prepareStatement(deleteEntitySQL());
            ps.setLong(1,(Long)id);
            ps.executeUpdate();
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public void update(T entity) {
        try {
            PreparedStatement ps = connection.prepareStatement(updateEntitySQL());
            mapForUpdate(entity,ps);
            ps.setLong(6,entity.getId());
            ps.executeUpdate();
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }

    abstract void mapForSave(T entity, PreparedStatement ps) throws SQLException;

    abstract T extractEntityFromResultSet(ResultSet rs) throws SQLException;

    abstract  void mapForUpdate(T entity, PreparedStatement ps) throws  SQLException;

    abstract String saveSQL();

    abstract String findByIdSQL();

    abstract String findAllSQL();

    abstract String deleteEntitySQL();

    abstract String updateEntitySQL();

}
