package test;

import actions_with_db.Data;
import actions_with_db.DataBaseManager;
import actions_with_db.Requests;
import converters.InsertDataToDB;
import json_reader.JsonReader;
import player_structure.Player;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class Test {

    public static void deleteAllTabels(Data d){
        DataBaseManager.deleteTabel(Requests.getDropTabelRequest(d.getPlayerTabelName()), d.getConnection());
        DataBaseManager.deleteTabel(Requests.getDropTabelRequest(d.getItemsTabelName()), d.getConnection());
        DataBaseManager.deleteTabel(Requests.getDropTabelRequest(d.getCurrenciesTabelName()), d.getConnection());
        DataBaseManager.deleteTabel(Requests.getDropTabelRequest(d.getProgressesTabelName()), d.getConnection());
    }

    public static void createAllTabels(Data d) throws SQLException {
        DataBaseManager.createTabel(d.getCreatePlayerTabel(), d.getConnection());
        DataBaseManager.createTabel(d.getCreateItemTabel(), d.getConnection());
        DataBaseManager.createTabel(d.getCreateCurrencieTabel(), d.getConnection());
        DataBaseManager.createTabel(d.getCreateProgressesTabel(), d.getConnection());
    }

    public static void insertData(Data data, int n) throws SQLException, IOException {
        List<Player> l = JsonReader.getpostgres("src\\main\\java\\files\\player.json");
        InsertDataToDB.insertInformation(l, data, n);
    }



}
