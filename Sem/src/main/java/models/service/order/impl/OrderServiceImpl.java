package models.service.order.impl;


import models.entities.Game;
import models.entities.Order;
import models.service.order.OrderService;
import models.repositories.order.OrderRepository;
import models.repositories.order.impl.OrderRepositoryImpl;
import controllers.servlets.Listener;

import java.util.*;

public class OrderServiceImpl implements OrderService {
    OrderRepository orderRepository=new OrderRepositoryImpl();
    public Long signUp(Long UserId) {
        Map<Long, Set<Game>> SelectedGamesOfAllUsers=Listener.getSetOfSelectedGames();
        Collection<Game> games= SelectedGamesOfAllUsers.get(UserId);
        Long costOfSelectedGames=0L;
        for(Game game: games){
            costOfSelectedGames=costOfSelectedGames+game.getCost();
        }
        Order order=Order.builder()
                .UserId(UserId)
                .totalCost(costOfSelectedGames)
                .build();
         return orderRepository.save(order);
    }

    public void SignUpGameToLibrary(Long id_of_user,Long id_of_game){
        orderRepository.SignUpGameToLibrary(id_of_user,id_of_game);
    }

    public void SignUpGamesFromBasket(Long idOfOrder,Long gameId){
        orderRepository.SignUpGamesFromBasket(idOfOrder,gameId);
    }

    public List<Long> getGamesOfThisOrders(List<Long> orders){
        return orderRepository.getGamesOfThisOrders(orders);
    }

    public List<Long> getOrdersOfThisUser(Long idOfUser){
       return orderRepository.getOrdersOfThisUser(idOfUser);
    }


    public List<Game> getEntitiesGamesOfCertainOrder(Order order){
        return orderRepository.getEntitiesGamesOfCertainOrder(order);
    }

    public List<Order> getEntitiesOrdersOfThisUser(Long idOfUser){
       return orderRepository.getEntitiesOrdersOfThisUser(idOfUser);
    }

    public void deleteGamesFromBasket(Long idOfUser, String[] gamesToDelete){
        Set<Game> Games= Listener.getSetOfSelectedGames().get(idOfUser);
        Set<Game> deleteGames=new HashSet<>();
        for(Game game:Games){
            for(int i=0;i< gamesToDelete.length;i++){
                if(Long.parseLong(gamesToDelete[i])==game.getId()){
                    deleteGames.add(game);
                }
            }
        }
        for(Game game: deleteGames){
            Listener.getSetOfSelectedGames().get(idOfUser).remove(game);
        }
    }
}
