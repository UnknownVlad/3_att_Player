package console_handler;

import actions_with_db.Data;
import player_structure.PlayerMap;

import java.sql.SQLException;
import java.util.Scanner;

public class ConsoleProcessor {

    public static void go(Data d, PlayerMap cash) throws SQLException {
        Handler.startCommands();
        boolean b = true;
        while (b){
            Scanner s = new Scanner(System.in);
            b = Handler.processing(d, s.nextLine().trim(), cash);
        }

        System.out.println("Окончено");
    }
}
