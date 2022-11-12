package models.repositories.game;

import models.entities.Game;

import java.util.List;

public interface GameRepository {
    Long save(Game game);
    Game findById(Long id);
    void hide(Long id);
    void show(Long id);
    List<Game> getListOfGames(String column,String order,String columnForFilter,String argumentOfFilter);
    void delete(Long idOfGame);

    void addImgToGame(String image,Long idOfGame);

    List<String> getImageOfGame(Long id_of_game);
    void deleteImage(String nameOfImage);
    void update(Game game);
}
