package converters;

import actions_with_db.Data;
import actions_with_db.DataBaseManager;
import actions_with_db.Requests;
import player_structure.Currencies;
import player_structure.Items;
import player_structure.Player;
import player_structure.Progresses;

import java.sql.SQLException;
import java.util.List;

public class InsertDataToDB {

   public static void insertInformation(List<Player> postgres, Data data, int n) throws SQLException {
       int count = 0;
       System.out.println("Insert");
       for (Player player: postgres) {
            if(count == n)
                break;
           DataBaseManager.executeUpdate(Requests.getInsertRequestForPlayer(data.getPlayerTabelName(), data.getPlayerTabelColumns(), player.getPlayerId(), player.getNickname()), data.getConnection());
           for (Currencies currencie : player.getCurrencies()) {
               DataBaseManager.executeUpdate(Requests.getInsertRequestCurrencies(data.getCurrenciesTabelName(), data.getCurrenciesTabelColumns(), currencie), data.getConnection());
           }
           for (Items item : player.getItems()) {
               DataBaseManager.executeUpdate(Requests.getInsertRequestItems(data.getItemsTabelName(), data.getItemsTabelColumns(), item), data.getConnection());
           }
           for (Progresses progress: player.getProgresses()) {
               DataBaseManager.executeUpdate(Requests.getInsertRequestProgresses(data.getProgressesTabelName(), data.getProgressesTabelColumns(), progress), data.getConnection());
           }

           count++;
           if(count%100==0)
            System.out.println(count/100+"%");

       }

       System.out.println("finish");
   }

}
