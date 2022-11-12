package models.repositories.user.impl;


import models.entities.User;
import models.repositories.connection.PostgresProvider;
import models.repositories.user.UserRepository;
import models.repositories.SqlGenerator;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class UserRepositoryImpl implements UserRepository {
    private static final Connection connection= PostgresProvider.getConnection();

    private static final String SQLFind="SELECT * FROM playuser WHERE mail=?";

    private static final String SQLUpdateToAdmin="UPDATE playuser SET admin=true WHERE id=?";

    private static final String SQLUpdateToUser="UPDATE playuser SET admin=false WHERE id=?";

    private static final String SQLFindAvailableGames="SELECT id_of_game FROM library WHERE id_of_user=?";


    public  Long save(User user) {
        try {
            PreparedStatement statement=connection.prepareStatement(SqlGenerator.insert(user));
            ResultSet resultSet= statement.executeQuery();
            resultSet.next();
            return resultSet.getLong("id");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public List<User> findAllUsers(String column,String order,String columnForFilter,String argumentOfFilter){
        List<User> p=new LinkedList<>();
        try{
            PreparedStatement statement=connection.prepareStatement(SqlGenerator.findAll("playuser",column,order,columnForFilter,argumentOfFilter));
            ResultSet resultSet= statement.executeQuery();
            while (resultSet.next()){
                User user=User.builder()
                        .id(Long.valueOf(resultSet.getString("id")))
                        .nick(resultSet.getString("nick"))
                        .firstName(resultSet.getString("first_name"))
                        .lastName(resultSet.getString("last_name"))
                        .mail(resultSet.getString("mail"))
                        .password(resultSet.getString("password"))
                        .isAdmin(resultSet.getBoolean("admin"))
                        .salt(resultSet.getString("salt"))
                        .build();
                p.add(user);
            }
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
        return p;
    }

    public User findById(Long idOfUser){
        User user=User.builder()
                .id(idOfUser)
                .build();
        try{
            PreparedStatement statement=connection.prepareStatement(SqlGenerator.findById(user));
            ResultSet resultSet=statement.executeQuery();
            while (resultSet.next()){
                user=User.builder()
                        .id(Long.valueOf(resultSet.getString("id")))
                        .nick(resultSet.getString("nick"))
                        .firstName(resultSet.getString("first_name"))
                        .lastName(resultSet.getString("last_name"))
                        .mail(resultSet.getString("mail"))
                        .password(resultSet.getString("password"))
                        .isAdmin(resultSet.getBoolean("admin"))
                        .salt(resultSet.getString("salt"))
                        .build();
            }
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
        return user;
    }

    public User findByUniq(String mail){
        try {
            User user = null;
            PreparedStatement statement=connection.prepareStatement(SQLFind);
            statement.setString(1,mail);
            ResultSet resultSet=statement.executeQuery();
            try{
                while (resultSet.next()){
                    user=User.builder()
                            .id(Long.valueOf(resultSet.getString("id")))
                            .nick(resultSet.getString("nick"))
                            .firstName(resultSet.getString("first_name"))
                            .lastName(resultSet.getString("last_name"))
                            .mail(mail)
                            .password(resultSet.getString("password"))
                            .isAdmin(resultSet.getBoolean("admin"))
                            .salt(resultSet.getString("salt"))
                            .build();
                }
                return user;
            }catch (SQLException e){
                throw new RuntimeException(e);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }




    public void update(User user) {
        try {
            PreparedStatement statement=connection.prepareStatement(SqlGenerator.update(user));
            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public void signAsAdmin(Long id){
        try {
            PreparedStatement statement=connection.prepareStatement(SQLUpdateToAdmin);
            statement.setLong(1,id);
            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void signAsUser(Long id){
        try {
            PreparedStatement statement=connection.prepareStatement(SQLUpdateToUser);
            statement.setLong(1,id);
            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Long> getIdOfAvailableGames(Long id_of_user){
        List<Long> p=new LinkedList<>();
        try{
            PreparedStatement statement=connection.prepareStatement(SQLFindAvailableGames);
            statement.setLong(1,id_of_user);
            ResultSet resultSet=statement.executeQuery();
            while (resultSet.next()){
                p.add(resultSet.getLong("id_of_game"));
            }
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
        return p;
    }

    public List<String> findAll(String requiredColumn){
        List<String> p=new LinkedList<>();
        try{
            String SQL="SELECT "+requiredColumn+" FROM playuser order by id";
            PreparedStatement statement=connection.prepareStatement(SQL);
            ResultSet resultSet=statement.executeQuery();
            while (resultSet.next()){
                p.add(resultSet.getString(requiredColumn));
            }
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
        return p;
    }

    public void delete(Long idOfUser){
        try {
            PreparedStatement statement=connection.prepareStatement(SqlGenerator.deleteById(idOfUser,"playuser"));
            statement.execute();
        }catch (SQLException e){
            throw new RuntimeException(e);
        }


    }

}
