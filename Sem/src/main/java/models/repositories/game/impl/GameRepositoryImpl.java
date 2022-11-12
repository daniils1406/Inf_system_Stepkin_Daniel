package models.repositories.game.impl;

import models.entities.Game;
import models.repositories.SqlGenerator;
import models.repositories.connection.PostgresProvider;
import models.repositories.game.GameRepository;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class GameRepositoryImpl implements GameRepository {
    private static final Connection connection = PostgresProvider.getConnection();

    private static final String SQLHide = "UPDATE game SET show=false WHERE id=?";

    private static final String SQLShow = "UPDATE game SET show=true WHERE id=?";

    private static final String SQLInsert = "INSERT INTO overviewImage (id_of_game,name_of_image) VALUES (?,?)";

    private static final String SQLSelect = "SELECT name_of_image FROM overviewImage WHERE id_of_game=?";


    private static final String SQLDelete = "DELETE FROM overviewImage WHERE name_of_image=?";

    public Long save(Game game) {
        try {
            PreparedStatement statement = connection.prepareStatement(SqlGenerator.insert(game));
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            return resultSet.getLong("id");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }


    public Game findById(Long id) {
        try {
            Game game = Game.builder()
                    .id(id)
                    .build();
            Game findGame = null;
            PreparedStatement statement = connection.prepareStatement(SqlGenerator.findById(game));
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                findGame = Game.builder()
                        .id(resultSet.getLong("id"))
                        .name(resultSet.getString("name"))
                        .developer(resultSet.getString("developer"))
                        .publisher(resultSet.getString("publisher"))
                        .date(resultSet.getDate("date"))
                        .genre(resultSet.getString("genre"))
                        .cost(resultSet.getLong("cost"))
                        .show(resultSet.getBoolean("show"))
                        .build();
            }
            return findGame;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public void hide(Long id) {
        try {
            PreparedStatement statement = connection.prepareStatement(SQLHide);
            statement.setLong(1, id);
            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void show(Long id) {
        try {
            PreparedStatement statement = connection.prepareStatement(SQLShow);
            statement.setLong(1, id);
            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public List<Game> getListOfGames(String column, String order, String columnForFilter, String argumentOfFilter) {
        try {
            List<Game> games = new LinkedList<>();
            PreparedStatement statement = connection.prepareStatement(SqlGenerator.findAll("game", column, order, columnForFilter, argumentOfFilter));
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Game game = Game.builder()
                        .id(resultSet.getLong("id"))
                        .name(resultSet.getString("name"))
                        .developer(resultSet.getString("developer"))
                        .publisher(resultSet.getString("publisher"))
                        .date(resultSet.getDate("date"))
                        .genre(resultSet.getString("genre"))
                        .cost(resultSet.getLong("cost"))
                        .show(resultSet.getBoolean("show"))
                        .build();
                games.add(game);
            }
            return games;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void delete(Long idOfGame) {
        try {
            PreparedStatement statement = connection.prepareStatement(SqlGenerator.deleteById(idOfGame, "game"));
            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void addImgToGame(String image, Long idOfGame) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SQLInsert);
            preparedStatement.setLong(1, idOfGame);
            preparedStatement.setString(2, image);
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<String> getImageOfGame(Long id_of_game) {
        List<String> nameOfImages = new LinkedList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SQLSelect);
            preparedStatement.setLong(1, id_of_game);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                nameOfImages.add(resultSet.getString("name_of_image"));
            }
            return nameOfImages;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteImage(String nameOfImage) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SQLDelete);
            preparedStatement.setString(1, nameOfImage);
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void update(Game game) {
        try {
            PreparedStatement statement = connection.prepareStatement(SqlGenerator.update(game));
            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
