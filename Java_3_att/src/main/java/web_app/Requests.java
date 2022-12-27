package web_app;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum Requests {

    PLAYER("player") {
        @Override
        public String tableName() {
            return PLAYER.tableName;
        }

        @Override
        public List<String> collumns() {
            return List.of("playerId", "nickname");
        }

        @Override
        public String create() {
            return "CREATE TABLE " + PLAYER.tableName
                    + "("
                    + "playerId int NOT NULL,"
                    + "nickname varchar(45) DEFAULT NULL,"
                    + " PRIMARY KEY (playerId)" +
                    ")";
        }
    },
    CURRENCIE("currencies") {
        @Override
        public String tableName() {
            return CURRENCIE.tableName;
        }

        @Override
        public List<String> collumns() {
            return List.of("id", "playerId", "resourceId", "name", "count");
        }

        @Override
        public String create() {
            return "CREATE TABLE " + CURRENCIE.tableName +
                    "(" +
                    "  id int NOT NULL," +
                    "  playerId int NOT NULL," +
                    "  resourceId int NOT NULL," +
                    "  name varchar(45) DEFAULT NULL," +
                    "  count int DEFAULT NULL," +
                    "  PRIMARY KEY (id)" +
                    ")";
        }
    },
    ITEM("items") {
        @Override
        public String tableName() {
            return ITEM.tableName;
        }

        @Override
        public List<String> collumns() {
            return List.of("id", "playerId", "resourceId", "count", "level");
        }

        @Override
        public String create() {
            return "CREATE TABLE " + ITEM.tableName +
                    "(" +
                    "  id int NOT NULL," +
                    "  playerId int NOT NULL," +
                    "  resourceId int NOT NULL," +
                    "  count int DEFAULT NULL," +
                    "  level int DEFAULT NULL," +
                    "  PRIMARY KEY (id)" +
                    ")";
        }
    },
    PROGRESS("progresses") {
        @Override
        public String tableName() {
            return PROGRESS.tableName;
        }

        @Override
        public List<String> collumns() {
            return List.of("id", "playerId", "resourceId", "score", "maxScore");
        }

        @Override
        public String create() {
            return "CREATE TABLE " +PROGRESS.tableName +
                    "(" +
                    "  id int NOT NULL," +
                    "  playerId int NOT NULL," +
                    "  resourceId int NOT NULL," +
                    "  score int DEFAULT NULL," +
                    "  maxScore int DEFAULT NULL," +
                    "  PRIMARY KEY (id)" +
                    ")";
        }
    };


    private String tableName;

    public abstract String tableName();
    public abstract List<String> collumns();
    public abstract String create();

    private static List<String> cast(List<Object> values){
        List<String> result = new ArrayList<>();
        for (Object o:values) {
            if(o.getClass().equals(String.class)){
                o = ((String) o).replace("'", "");
                result.add("'"+o+"'");
            }else {
                result.add(Integer.toString((Integer) o));
            }
        }
        return result;
    }

    public static String insert(String tableName, List<Object> values){

        return insert(tableName, getByValue(tableName).collumns(), values);
    }
    public static String insert(String tableName, List<String> collumns, List<Object> values){
        String request ="INSERT INTO "+ tableName +"(";
        List<Object> val = values;
        for (int i = 0; i < collumns.size()-1; i++) {
            request +=collumns.get(i)+",";
        }
        request +=collumns.get(collumns.size()-1)+") VALUES(";

        for (int i = 0; i < val.size()-1; i++) {
            request+=val.get(i)+",";
        }
        request+= val.get(val.size()-1)+")";
        return request;
    }
    public static String remove(String tableName, List<String> collumns, List<Object> values){
        List<Object> val = values;
        String request = "DELETE FROM " + tableName + " WHERE ";
        for (int i = 0; i < collumns.size()-1; i++) {
            request += collumns.get(i)+" = "+val.get(i)+" AND ";
        }
        request +=collumns.get(collumns.size()-1)+" = "+val.get(val.size()-1);
        return request;
    }
    public static String read(String tableName){
        return "SELECT * FROM " + tableName;
    }

    public static String read(String tableName, String collumn, Object key){
        return "SELECT * FROM " + tableName + " WHERE "+ collumn + " = "+key.toString();
    }

    public static String read(String tableName, List<String> collumns, List<Object> values){
        List<Object> val = values;
        String request ="SELECT * FROM " + tableName + " WHERE ";
        for (int i = 0; i < collumns.size()-1; i++) {
            request += collumns.get(i)+" = "+val.get(i)+" AND ";
        }
        request +=collumns.get(collumns.size()-1)+" = "+val.get(val.size()-1);
        return request;
    }

    public static String drop(String tableName){
        return "DROP TABLE "+tableName;
    }
    public static String update(String tableName, List<String> collumns, List<Object> values, List<String> conditionCollumns, List<Object> conditionValues){
        String s = "UPDATE "+tableName+" SET ";

        for (int i = 0; i < collumns.size()-1; i++) {
            s+= collumns.get(i)+" = "+values.get(i)+" , ";
        }

        s+= collumns.get(collumns.size()-1)+" = "+values.get(values.size()-1)+" WHERE ";

        for (int i = 0; i < conditionCollumns.size()-1; i++) {
            s+=conditionCollumns.get(i)+" = "+conditionValues.get(i)+" AND ";
        }

        s+=conditionCollumns.get(conditionCollumns.size()-1)+" = "+conditionValues.get(conditionValues.size()-1);

        System.out.println(s);
        return s;
    }

    public static String update(String tableName, List<String> collumns, List<Object> values, List<Object> conditionValues){
        String s = "UPDATE "+tableName+" SET ";

        for (int i = 0; i < collumns.size()-1; i++) {
            s+= collumns.get(i)+" = "+values.get(i)+" , ";
        }

        s+= collumns.get(collumns.size()-1)+" = "+values.get(values.size()-1)+" WHERE ";

        for (int i = 0; i < collumns.size()-1; i++) {
            s+=collumns.get(i)+" = "+conditionValues.get(i)+" AND ";
        }

        s+=collumns.get(collumns.size()-1)+" = "+conditionValues.get(conditionValues.size()-1);

        return s;
    }

    public static void cutNullValues(List<String> collumns, List<Object> values){
        List<Integer> indexes = new ArrayList<>();

        for (int i = 0; i < values.size(); i++) {
            if(values.get(i) == null) {
                indexes.add(i);
            }
        }
        removeAll(collumns, indexes);
        removeAll(values, indexes);
    }

    private static<T> void removeAll(List<T> list, List<Integer> indexes){
        for (int i = 0; i < indexes.size(); i++) {
            list.remove(indexes.get(i)-i);
        }
    }

    Requests(String tableName) {
        this.tableName = tableName;
    }



    public static Requests getByValue(String tableName) {
        return Arrays.stream(values())
                .filter(it -> it.tableName.equals(tableName))
                .findFirst()
                .orElseThrow();
    }
}
