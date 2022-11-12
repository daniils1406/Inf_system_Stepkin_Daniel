package models.service.game;

import models.entities.Game;

import java.util.List;

public interface GameService {
    public Long signUp(String name, String developer, String publisher,String genre,Long cost,String img);
    public List<Long> purchasedGames(Long idOfUser);
    public List<Game> getListOfGames(String column,String order,String columnForFilter,String argumentOfFilter);
    public void hideById(Long idOfGame);
    public void showById(Long idOfGame);
    public Game findById(Long id);
    public void addImgToGame(List<String> images,Long idOfGame);
    public List<String> getImageOfGame(Long id_of_game);
    public void deleteImage(String nameOfImage,String nameOfGame);
    void saveMainImg(String img,Long id);
    void update(Game game);
}
