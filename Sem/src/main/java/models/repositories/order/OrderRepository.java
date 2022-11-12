package models.repositories.order;

import models.entities.Game;
import models.entities.Order;

import java.util.List;

public interface OrderRepository {
    Long save(Order order);
    void SignUpGameToLibrary(Long id_of_user,Long id_of_game);
    void SignUpGamesFromBasket(Long orderId,Long gameId);
    List<Order> getEntitiesOrdersOfThisUser(Long idOfUser);
    List<Long> getGamesOfThisOrders(List<Long> orders);
    List<Long> getOrdersOfThisUser(Long idOfUser);
    List<Game> getEntitiesGamesOfCertainOrder(Order order);
}
