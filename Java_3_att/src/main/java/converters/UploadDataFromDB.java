package converters;

import actions_with_db.Data;
import actions_with_db.DataBaseManager;
import actions_with_db.Requests;
import player_structure.Currencies;
import player_structure.Items;
import player_structure.Player;
import player_structure.Progresses;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class UploadDataFromDB {

    public static List<Player> upload(Data data) throws SQLException {

        Map<Integer, String> pn = new TreeMap<>();
        List<Player> postgres = new ArrayList<>();
        ResultSet set = DataBaseManager.getResultSet(Requests.getQueryAllDataRequest(data.getPlayerTabelName()),data.getConnection());

        int count = 0;
        System.out.println("Upload");
        while (set.next()){
            pn.put(set.getInt(data.getPlayerTabelColumns().get(0)), set.getString(data.getPlayerTabelColumns().get(1)));
        }

        for (Integer key:pn.keySet()) {
            List<Currencies> lc = new ArrayList<>();
            set = DataBaseManager.getResultSet(Requests.getQueryRequest(data.getCurrenciesTabelName(), "playerId", Integer.toString(key)), data.getConnection());

            while (set.next()){
                Currencies c = new Currencies();
                c.setId(set.getInt(data.getCurrenciesTabelColumns().get(0)));
                c.setPlayerId(key);
                c.setResourceId(set.getInt(data.getCurrenciesTabelColumns().get(2)));
                c.setName(set.getString(data.getCurrenciesTabelColumns().get(3)));
                c.setCount(set.getInt(data.getCurrenciesTabelColumns().get(4)));
                lc.add(c);
            }

            List<Items> li = new ArrayList<>();
            set = DataBaseManager.getResultSet(Requests.getQueryRequest(data.getItemsTabelName(), "playerId", Integer.toString(key)), data.getConnection());

            while (set.next()){
                Items i = new Items();
                i.setId(set.getInt(data.getItemsTabelColumns().get(0)));
                i.setPlayerId(key);
                i.setResourceId(set.getInt(data.getItemsTabelColumns().get(2)));
                i.setCount(set.getInt(data.getItemsTabelColumns().get(3)));
                i.setLevel(set.getInt(data.getItemsTabelColumns().get(4)));
                li.add(i);
            }

            List<Progresses> lp = new ArrayList<>();
            set = DataBaseManager.getResultSet(Requests.getQueryRequest(data.getProgressesTabelName(), "playerId", Integer.toString(key)), data.getConnection());

            while (set.next()){
                Progresses p = new Progresses();
                p.setId(set.getInt(data.getProgressesTabelColumns().get(0)));
                p.setPlayerId(key);
                p.setResourceId(set.getInt(data.getProgressesTabelColumns().get(2)));
                p.setScore(set.getInt(data.getProgressesTabelColumns().get(3)));
                p.setMaxScore(set.getInt(data.getProgressesTabelColumns().get(4)));
                lp.add(p);
            }
            Player p = new Player(key, pn.get(key), lp, lc, li);
            postgres.add(p);
            if(count <= 10){
                System.out.println(p.getPlayerId() + " " + p.getNickname());
                System.out.println("Currensies");
                for (Currencies c:p.getCurrencies()) {
                    System.out.println(c);
                }
                System.out.println();
                System.out.println("Items");
                for (Items c:p.getItems()) {
                    System.out.println(c);
                }
                System.out.println();
                System.out.println("Progresses");
                for (Progresses c:p.getProgresses()) {
                    System.out.println(c);
                }
                System.out.println();
                System.out.println();
            }

             count++;
             /*if(count%100==0)
                System.out.println(count/100+"%");*/
        }
        //System.out.println("finish");

        return postgres;
    }

}
