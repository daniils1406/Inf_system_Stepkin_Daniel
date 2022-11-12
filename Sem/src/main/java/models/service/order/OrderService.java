package models.service.order;

import models.entities.Game;
import models.entities.Order;

import java.util.List;

public interface OrderService {
    public Long signUp(Long UserId);
    public List<Order> getEntitiesOrdersOfThisUser(Long idOfUser);
    public List<Game> getEntitiesGamesOfCertainOrder(Order order);
    public void SignUpGameToLibrary(Long id_of_user,Long id_of_game);
    public void SignUpGamesFromBasket(Long idOfOrder,Long gameId);
    public List<Long> getGamesOfThisOrders(List<Long> orders);
    public List<Long> getOrdersOfThisUser(Long idOfUser);
    public void deleteGamesFromBasket(Long idOfUser, String[] gamesToDelete);

}
