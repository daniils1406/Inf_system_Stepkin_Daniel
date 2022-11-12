package models.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.Date;


@AllArgsConstructor
@Data
@Builder
@TableName(nameOfTable = "game")
public class Game {
    @ColumnName(name = "id",primary = true,identity = true)
    Long id;
    @ColumnName(name = "name")
    String name;
    @ColumnName(name = "developer")
    String developer;
    @ColumnName(name = "publisher")
    String publisher;
    @ColumnName(name = "date")
    Date date;
    @ColumnName(name = "genre")
    String genre;
    @ColumnName(name = "cost")
    Long cost;
    @ColumnName(name = "show",defaultBooleanFalse = true)
    boolean show;
}