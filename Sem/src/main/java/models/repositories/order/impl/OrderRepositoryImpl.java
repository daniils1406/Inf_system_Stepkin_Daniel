package models.repositories.order.impl;

import models.entities.Game;
import models.entities.Order;
import models.repositories.SqlGenerator;
import models.repositories.connection.PostgresProvider;
import models.service.game.GameService;
import models.repositories.order.OrderRepository;
import models.service.game.impl.GameServiceImpl;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class OrderRepositoryImpl implements OrderRepository {
    private static final Connection connection= PostgresProvider.getConnection();

    private static final String SQLInsertToLibreary="INSERT INTO library (id_of_user, id_of_game) VALUES (?,?)";

    private static final String SQLInsertToBasket="INSERT INTO basket (id_of_order,id_of_game) VALUES (?,?);";

    private static final String SQLFindGamesOfCertainOrder="SELECT * FROM basket WHERE id_of_order=?";

    private static final String SQLFindGamesOfOrders="SELECT id_of_game FROM basket WHERE id_of_order=?";

    private static final String SQLForFindOrdersId = "SELECT id FROM orderofgames WHERE id_of_user=?";

    private static final String SQLForFindOrders = "SELECT * FROM orderofgames WHERE id_of_user=?";

    public Long save(Order order) {
        try {
            PreparedStatement statement=connection.prepareStatement(SqlGenerator.insert(order));
            ResultSet resultSet=statement.executeQuery();
            resultSet.next();
            return resultSet.getLong("id");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void SignUpGameToLibrary(Long id_of_user,Long id_of_game){
        try{
            PreparedStatement statement=connection.prepareStatement(SQLInsertToLibreary);
            statement.setLong(1,id_of_user);
            statement.setLong(2,id_of_game);
            statement.execute();
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public void SignUpGamesFromBasket(Long orderId,Long gameId){
        try {
            PreparedStatement statement=connection.prepareStatement(SQLInsertToBasket);
            statement.setLong(1,orderId);
            statement.setLong(2,gameId);
            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Game> getEntitiesGamesOfCertainOrder(Order order){
        List<Game> games=new LinkedList<>();
        GameService gameService=new GameServiceImpl();
        try {

            PreparedStatement statement=connection.prepareStatement(SQLFindGamesOfCertainOrder);
            statement.setLong(1,order.getId());
            ResultSet resultSet=statement.executeQuery();
            while (resultSet.next()){
                Game game=gameService.findById(resultSet.getLong("id_of_game"));
                games.add(game);
            }
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
        return games;
    }


    public List<Long> getGamesOfThisOrders(List<Long> orders){
        List<Long> games=new LinkedList<>();
        try {
            for(Long idOfOrder: orders){
                PreparedStatement statement=connection.prepareStatement(SQLFindGamesOfOrders);
                statement.setLong(1,idOfOrder);
                ResultSet resultSet=statement.executeQuery();
                while (resultSet.next()){
                    games.add(Long.valueOf(resultSet.getString("id_of_game")));
                }
            }
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
        return games;
    }


    public List<Long> getOrdersOfThisUser(Long idOfUser){
        try {
            List<Long> p=new LinkedList<>();
            PreparedStatement statement = connection.prepareStatement(SQLForFindOrdersId);
            statement.setLong(1,idOfUser);
            ResultSet resultSet=statement.executeQuery();
            while(resultSet.next()){
                p.add(Long.valueOf(resultSet.getString("id")));
            }
            return p;
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public List<Order> getEntitiesOrdersOfThisUser(Long idOfUser){
        try {
            List<Order> p=new LinkedList<>();
            PreparedStatement statement = connection.prepareStatement(SQLForFindOrders);
            statement.setLong(1,idOfUser);
            ResultSet resultSet=statement.executeQuery();
            while(resultSet.next()){
                Order order= Order.builder()
                        .id(resultSet.getLong("id"))
                        .UserId(resultSet.getLong("id_of_user"))
                        .date(resultSet.getDate("date"))
                        .time(resultSet.getTime("time"))
                        .totalCost(resultSet.getLong("total_cost"))
                        .build();
                p.add(order);
            }
            return p;
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }
}