package models.entities;

@TableName(nameOfTable = "overviewImage")
public class OverviewImage {
    @ColumnName(name = "id_of_game")
    Long idOfGame;
    @ColumnName(name = "name_of_image")
    String nameOfImage;
}
