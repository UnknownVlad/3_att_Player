package console_handler.commands;


import actions_with_db.Data;
import actions_with_db.DataBaseManager;
import actions_with_db.Requests;
import player_structure.PlayerMap;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public enum CRUD {
    DELETE("-rm") {
        @Override
        public ResultSet execute(Data data, String tableName, String command, PlayerMap cash) throws SQLException {
            List<String> parts = split(command, ",");
            List<String> collumns = new LinkedList<>();
            List<String> values = new LinkedList<>();
            for (String s:parts) {
                int pos = s.indexOf("=");
                collumns.add(s.substring(0, pos));
                values.add(s.substring(pos+1));
            }
            DataBaseManager.executeUpdate(Requests.getDeleteDataRequest(tableName, collumns, values), data.getConnection());
            UPDATE_CASH.value(tableName).remove(data, cash, collumns, values);
            return null;
        }
    },
    READ("-get") {
        @Override
        public ResultSet execute(Data data, String tableName, String command, PlayerMap cash) throws SQLException {
            List<String> parts = split(command, ",");
            List<String> collumns = new LinkedList<>();
            List<String> values = new LinkedList<>();

            for (String s:parts) {
                int pos = s.indexOf("=");
                collumns.add(s.substring(0, pos));
                values.add(s.substring(pos+1, s.length()));
            }


            ResultSet rs = DataBaseManager.getResultSet(Requests.getQueryRequest(tableName, collumns, values), data.getConnection());
            collumns = data.getCollumns(tableName);
            /*while (rs.next()){
                for (String c:collumns) {
                    System.out.print(c+"="+rs.getObject(c) + " ");
                }
                System.out.println();
            }*/
            return rs;
        }
    },
    CREATE("-add") {
        @Override
        public ResultSet execute(Data data, String tableName, String command, PlayerMap cash) throws SQLException {
            List<String> parts = split(command, ",");
            List<String> collumns = new LinkedList<>();
            List<String> values = new LinkedList<>();


            //System.out.println(parts);
            int count = 0;
            for (String s : parts) {
                int pos = s.indexOf("=");
                if(pos == -1) {
                    collumns.add(data.getCollumns(tableName).get(count));
                    count++;
                }
                else
                    collumns.add(s.substring(0, pos));
                values.add(s.substring(pos + 1));
            }

            UPDATE_CASH.value(tableName).add(data, cash, collumns, values);
            DataBaseManager.executeUpdate(Requests.getInsertRequest(tableName, collumns, values), data.getConnection());
            return null;
        }
    },
    UPDATE("-chng") {
        @Override
        public ResultSet execute(Data data, String tableName, String command, PlayerMap cash) throws SQLException {
            List<String> parts = split(command.substring(0, command.indexOf("where")).trim(), ",");
            List<String> condition = split(command.substring(command.indexOf("where")+6).trim(), ",");
            List<String> collumns1 = new LinkedList<>();
            List<String> values1 = new LinkedList<>();

            for (String s:parts) {
                int pos = s.indexOf("=");
                collumns1.add(s.substring(0, pos));
                values1.add(s.substring(pos+1));
            }

            List<String> collumns2 = new LinkedList<>();
            List<String> values2 = new LinkedList<>();

            for (String s:condition) {
                int pos = s.indexOf("=");
                collumns2.add(s.substring(0, pos));
                values2.add(s.substring(pos+1));
            }

            UPDATE_CASH.value(tableName).update(data, cash, collumns1, values1, collumns2, values2);
            DataBaseManager.executeUpdate(Requests.getUpdateRequest(tableName, collumns1, values1, collumns2, values2), data.getConnection());
            return null;
        }
    };


    private String typeCommand;

    CRUD(String command) {
        this.typeCommand = command;
    }


    private static List<String> split(String command, String regex){
        int i = -1;
        command+=regex;
        List<String> l = new LinkedList<>();
        String[] strs = command.split(regex);
        for (String s:strs) {
            l.add(s.replace(" ", ""));
        }
        return l;
    }
    public abstract ResultSet execute(Data data, String tableName, String command, PlayerMap cash) throws SQLException;

    public static CRUD value(String typeCommand) {
        return Arrays.stream(values())
                .filter(it -> it.typeCommand.equals(typeCommand))
                .findFirst()
                .orElseThrow();
    }
}
