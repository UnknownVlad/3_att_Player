package actions_with_db;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class Data {
    private Connection connection = null;
    private Statement statement = null;

    private Map<String, List<String>> collumns = new TreeMap<>();

    public Data() throws SQLException {
        this.connection = DataBaseManager.getDBConnection();
        this.statement = connection.createStatement();

        collumns = new TreeMap<>();
        collumns.put(playerTabelName, List.of("playerId", "nickname"));
        collumns.put(currenciesTabelName, List.of("id", "playerId", "resourceId", "name", "count"));
        collumns.put(itemsTabelName, List.of("id", "playerId", "resourceId", "count", "level"));
        collumns.put(progressesTabelName, List.of("id", "playerId", "resourceId", "score", "maxScore"));
    }

    public void closeConnection() throws SQLException {
        if(connection != null)
            connection.close();
        if(statement != null)
            statement.close();

        connection = null;
        statement = null;

    }

    public void setConnection(){
        connection = DataBaseManager.getDBConnection();
    }
    public void setStatement() throws SQLException {
        setConnection();
        statement = connection.createStatement();
    }

    private String playerTabelName = "player";
    private String playerKey = "playerId";
   // private List<String> playerTabelColumns = List.of("playerId", "nickname");
    private String createPlayerTabel =
            "CREATE TABLE " + playerTabelName
            + "("
            + "playerId int NOT NULL,"
            + "nickname varchar(45) DEFAULT NULL,"
            + " PRIMARY KEY (playerId)" +
            ")";

    private String currenciesTabelName = "currencies";
    private String currenciesKey = "id";
    //private List<String> currenciesTabelColumns = List.of("id", "playerId", "resourceId", "name", "count");
    private String createCurrencieTabel =
            "CREATE TABLE " + currenciesTabelName +
            "(" +
            "  id int NOT NULL," +
            "  playerId int NOT NULL," +
            "  resourceId int NOT NULL," +
            "  name varchar(45) DEFAULT NULL," +
            "  count int DEFAULT NULL," +
            "  PRIMARY KEY (id)" +
            ")";

    private String itemsTabelName = "items";
    private String itemsKey = "id";
    //private List<String> itemsTabelColumns = List.of("id", "playerId", "resourceId", "count", "level");
    private String  createItemTabel =
            "CREATE TABLE " + itemsTabelName +
            "(" +
            "  id int NOT NULL," +
            "  playerId int NOT NULL," +
            "  resourceId int NOT NULL," +
            "  count int DEFAULT NULL," +
            "  level int DEFAULT NULL," +
            "  PRIMARY KEY (id)" +
            ")";

    private String progressesTabelName = "progresses";
    private String progressesKey = "id";
    //private List<String> progressesTabelColumns = List.of("id", "playerId", "resourceId", "score", "maxScore");
    private String createProgressesTabel =
            "CREATE TABLE " +progressesTabelName +
            "(" +
            "  id int NOT NULL," +
            "  playerId int NOT NULL," +
            "  resourceId int NOT NULL," +
            "  score int DEFAULT NULL," +
            "  maxScore int DEFAULT NULL," +
            "  PRIMARY KEY (id)" +
            ")";

    public List<String> getCollumns(String tableName){

        return collumns.getOrDefault(tableName, null);
    }

    public Connection getConnection() {
        return connection;
    }

    public String getPlayerTabelName() {
        return playerTabelName;
    }

    public List<String> getPlayerTabelColumns() {
        return collumns.get(playerTabelName);
    }

    public String getCreatePlayerTabel() {

        return createPlayerTabel;
    }

    public String getCurrenciesTabelName() {
        return currenciesTabelName;
    }

    public List<String> getCurrenciesTabelColumns() {
        return collumns.get(currenciesTabelName);
    }

    public String getCreateCurrencieTabel() {
        return createCurrencieTabel;
    }

    public String getItemsTabelName() {
        return itemsTabelName;
    }

    public List<String> getItemsTabelColumns() {
        return collumns.get(itemsTabelName);
    }

    public String getCreateItemTabel() {
        return createItemTabel;
    }

    public String getProgressesTabelName() {
        return progressesTabelName;
    }

    public List<String> getProgressesTabelColumns() {
        return collumns.get(progressesTabelName);
    }

    public String getCreateProgressesTabel() {
        return createProgressesTabel;
    }

    public String getPlayerKey() {
        return playerKey;
    }

    public String getCurrenciesKey() {
        return currenciesKey;
    }

    public String getItemsKey() {
        return itemsKey;
    }

    public String getProgressesKey() {
        return progressesKey;
    }
}
