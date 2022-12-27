package console_handler.commands;

import actions_with_db.Data;
import converters.UploadDataFromDB;
import player_structure.*;

import java.sql.SQLException;
import java.util.*;

public enum UPDATE_CASH {
    PLAYER("player") {
        @Override
        public void add(Data data, PlayerMap cash, List<String> columns, List<String> values) throws SQLException {
            int key = Integer.parseInt(values.get(columns.indexOf(data.getPlayerKey())));
            if(cash.getPlayer().containsKey(key)){
                return;
            }
            cash.getPlayer().put(key, values.get(1));
            //cash = new PlayerMap(UploadDataFromDB.upload(data));
        }

        @Override
        public void update(Data data, PlayerMap cash, List<String> columns1, List<String> values1, List<String> columns2, List<String> values2) throws SQLException {

        }

        @Override
        public void remove(Data data, PlayerMap cash, List<String> columns, List<String> values) throws SQLException {
            Map<Integer, String> map = cash.getPlayer();
            if(columns.contains(data.getPlayerKey())){
                int key = Integer.parseInt(values.get(columns.indexOf(data.getPlayerKey())));
                String name = map.getOrDefault(key, "");
                if(columns.contains(data.getCollumns(PLAYER.tableName).get(1))){
                    String inputName = values.get(columns.indexOf(data.getCollumns(PLAYER.tableName).get(1)));
                    if (name.equals(inputName))
                        map.remove(key);
                }else
                    map.remove(key);
            }else {
                Set<Integer> keySet = new HashSet<>(map.keySet());
                String inputName = values.get(columns.indexOf(data.getCollumns(PLAYER.tableName).get(1)));
                for (Integer k :keySet) {

                    if(map.get(k).equals(inputName)) {
                        System.out.println("key="+k);

                        map.remove(k);
                    }
                }
            }
            cash.setPlayer(map);
        }
    },

    CURRENCIES("currencies") {
        @Override
        public void add(Data data, PlayerMap cash, List<String> columns, List<String> values) throws SQLException {
            int primaryKey = Integer.parseInt(values.get(columns.indexOf(data.getCurrenciesKey())));
            int key = Integer.parseInt(values.get(columns.indexOf(data.getPlayerKey())));
            if(cash.getCurrencies().containsKey(primaryKey) || !cash.getPlayer().containsKey(key)){
                return;
            }

            Currencies c = new Currencies(
                    primaryKey,
                    key,
                    Integer.parseInt(values.get(columns.indexOf(data.getCollumns(CURRENCIES.tableName).get(2)))),
                    values.get(columns.indexOf(data.getCollumns(CURRENCIES.tableName).get(3))),
                    Integer.parseInt(values.get(columns.indexOf(data.getCollumns(CURRENCIES.tableName).get(4))))
            );
            List<Currencies> currencies = cash.getCurrencies().getOrDefault(key, new ArrayList<>());
            currencies.add(c);
            cash.getCurrencies().put(key, currencies);

        }

        @Override
        public void update(Data data, PlayerMap cash, List<String> columns1, List<String> values1, List<String> columns2, List<String> values2) throws SQLException {
            cash = new PlayerMap(UploadDataFromDB.upload(data));
        }

        @Override
        public void remove(Data data, PlayerMap cash, List<String> columns, List<String> values) throws SQLException {
            cash = new PlayerMap(UploadDataFromDB.upload(data));
        }
    },
    ITEMS("items") {
        @Override
        public void add(Data data, PlayerMap cash, List<String> columns, List<String> values) throws SQLException {
            int primaryKey = Integer.parseInt(values.get(columns.indexOf(data.getItemsKey())));
            int key = Integer.parseInt(values.get(columns.indexOf(data.getPlayerKey())));
            if(cash.getItems().containsKey(primaryKey) || !cash.getPlayer().containsKey(key)){
                return;
            }

            Items i = new Items(
                    primaryKey,
                    key,
                    Integer.parseInt(values.get(columns.indexOf(data.getCollumns(ITEMS.tableName).get(2)))),
                    Integer.parseInt(values.get(columns.indexOf(data.getCollumns(ITEMS.tableName).get(3)))),
                    Integer.parseInt(values.get(columns.indexOf(data.getCollumns(ITEMS.tableName).get(4))))
            );
            List<Items> items = cash.getItems().getOrDefault(key, new ArrayList<>());
            items.add(i);
            cash.getItems().put(key, items);
        }

        @Override
        public void update(Data data, PlayerMap cash, List<String> columns1, List<String> values1, List<String> columns2, List<String> values2) throws SQLException {
            cash = new PlayerMap(UploadDataFromDB.upload(data));
        }

        @Override
        public void remove(Data data, PlayerMap cash, List<String> columns, List<String> values) throws SQLException {
            cash = new PlayerMap(UploadDataFromDB.upload(data));
        }
    },
    PROGRESSES("progresses") {
        @Override
        public void add(Data data, PlayerMap cash, List<String> columns, List<String> values) throws SQLException {
            int primaryKey = Integer.parseInt(values.get(columns.indexOf(data.getProgressesKey())));
            int key = Integer.parseInt(values.get(columns.indexOf(data.getPlayerKey())));
            if(cash.getProgresses().containsKey(primaryKey) || !cash.getPlayer().containsKey(key)){
                return;
            }

            Progresses p = new Progresses(
                    primaryKey,
                    key,
                    Integer.parseInt(values.get(columns.indexOf(data.getCollumns(CURRENCIES.tableName).get(2)))),
                    Integer.parseInt(values.get(columns.indexOf(data.getCollumns(CURRENCIES.tableName).get(3)))),
                    Integer.parseInt(values.get(columns.indexOf(data.getCollumns(CURRENCIES.tableName).get(4))))
            );
            List<Progresses> progresses = cash.getProgresses().getOrDefault(key, new ArrayList<>());
            progresses.add(p);
            cash.getProgresses().put(key, progresses);
        }

        @Override
        public void update(Data data, PlayerMap cash, List<String> columns1, List<String> values1, List<String> columns2, List<String> values2) throws SQLException {
            cash = new PlayerMap(UploadDataFromDB.upload(data));
        }

        @Override
        public void remove(Data data, PlayerMap cash, List<String> columns, List<String> values) throws SQLException {
            cash = new PlayerMap(UploadDataFromDB.upload(data));
        }

    };


    private String tableName;

    UPDATE_CASH(String tableName) {
        this.tableName = tableName;
    }

    public abstract void add(Data data, PlayerMap cash, List<String> columns, List<String> values) throws SQLException;
    public abstract void update(Data data, PlayerMap cash, List<String> columns1, List<String> values1, List<String> columns2, List<String> values2) throws SQLException;
    public abstract void remove(Data data, PlayerMap cash, List<String> columns, List<String> values) throws SQLException;


    public static UPDATE_CASH value(String tableName) {
        return Arrays.stream(values())
                .filter(it -> it.tableName.equals(tableName))
                .findFirst()
                .orElseThrow();
    }
}
