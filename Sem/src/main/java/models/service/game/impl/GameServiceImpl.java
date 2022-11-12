package models.service.game.impl;

import models.entities.Game;
import models.service.game.GameService;
import models.service.order.OrderService;
import models.service.order.impl.OrderServiceImpl;
import models.repositories.game.GameRepository;
import models.repositories.game.impl.GameRepositoryImpl;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.List;

public class GameServiceImpl implements GameService {
    GameRepository gameRepository=new GameRepositoryImpl();
    OrderService orderService=new OrderServiceImpl();
    public Long signUp(String name, String developer, String publisher,String genre,Long cost,String img) {
        Game game=Game.builder()
                .name(name)
                .developer(developer)
                .publisher(publisher)
                .genre(genre)
                .cost(cost)
                .build();
        Long idOfNewGame= gameRepository.save(game);
        saveMainImg(img,idOfNewGame);
        return idOfNewGame;
    }

    public void saveMainImg(String img,Long id){
        try{
            BufferedInputStream inputStream=new BufferedInputStream(new URL(img).openStream());
            new File("..\\fileForImages\\PreviewGame\\"+id).mkdirs();
            File file=new File("..\\fileForImages\\PreviewGame\\"+id+"\\"+id+".png");
            Files.copy(inputStream, file.toPath(), StandardCopyOption.REPLACE_EXISTING);
        }catch (IOException e){
            throw new RuntimeException(e);
        }
    }


    public List<Long> purchasedGames(Long idOfUser){
        List<Long> OrdersOfUser=orderService.getOrdersOfThisUser(idOfUser);
        return orderService.getGamesOfThisOrders(OrdersOfUser);
    }



    public List<Game> getListOfGames(String column,String order,String columnForFilter,String argumentOfFilter){
        return gameRepository.getListOfGames(column,order,columnForFilter,argumentOfFilter);
    }


    public void hideById(Long idOfGame){
        gameRepository.hide(idOfGame);
    }

    public void showById(Long idOfGame){
        gameRepository.show(idOfGame);
    }

    public Game findById(Long id){
        return gameRepository.findById(id);
    }

    public void addImgToGame(List<String> images,Long idOfGame){
        Game game=findById(idOfGame);
        new File("..\\fileForImages\\PreviewGame\\"+game.getId()).mkdirs();
        for(String img:images){
            try{
                BufferedInputStream inputStream=new BufferedInputStream(new URL(img).openStream());
                String[] fullNameOfPicture=img.split("/");
                String[] name=fullNameOfPicture[fullNameOfPicture.length-1].split("\\.");
                File file=new File("..\\fileForImages\\PreviewGame\\"+game.getId()+"\\"+name[0]+".png");
                Files.copy(inputStream, file.toPath(), StandardCopyOption.REPLACE_EXISTING);

                gameRepository.addImgToGame(name[0],idOfGame);
            }catch (IOException e){
                throw new RuntimeException(e);
            }
        }
    }

    public List<String> getImageOfGame(Long id_of_game){
        return gameRepository.getImageOfGame(id_of_game);
    }

    public void deleteImage(String nameOfImage,String nameOfGame){
        gameRepository.deleteImage(nameOfImage);
        File file=new File("..\\fileForImages\\PreviewGame\\"+nameOfGame+"\\"+nameOfImage+".png");
        file.delete();
    }
    public void update(Game game){
        gameRepository.update(game);
    }
}
