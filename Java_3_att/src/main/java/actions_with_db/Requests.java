package actions_with_db;

import player_structure.Currencies;
import player_structure.Items;
import player_structure.Progresses;

import java.util.List;

public class Requests {

    public static String getQueryAllDataRequest(String tabelName){
        return "SELECT * FROM "+tabelName;
    }

    private static String getInsertModel(String tabelName, List<String> col){
        String request ="INSERT INTO "+ tabelName+"(";
        for (int i = 0; i < col.size()-1; i++) {
            request +=col.get(i)+",";
        }
        request +=col.get(col.size()-1)+") VALUES(";
        return request;
    }
    private static String getSelectModel(String tableName, List<String> col){
        String request ="SELECT ";
        for (int i = 0; i < col.size()-1; i++) {
            request +=col.get(i)+",";
        }
        request +=col.get(col.size()-1)+" FROM " + tableName + " WHERE ";
        return request;
    }

    private static String getSelectModel(String tableName){
        String request ="SELECT * FROM " + tableName + " WHERE ";
        return request;
    }

    public static String getDropTabelRequest(String tabelName){
        return "DROP TABLE "+tabelName;
    }

    public static String getDeleteDataRequest(String tabelName, String nameKey, String value){
        return "DELETE FROM " + tabelName + " WHERE "+nameKey+" = " + value;
    }

    public static String getDeleteDataRequest(String tabelName, List<String> collumns, List<String> values){
        String s = "DELETE FROM " + tabelName + " WHERE ";
        for (int i = 0; i < collumns.size()-1; i++) {
            s+= collumns.get(i)+" = "+values.get(i)+" AND ";
        }
        s+=collumns.get(collumns.size()-1)+" = "+values.get(values.size()-1);
        return s;
    }




    public static String getInsertRequest(String tableName, List<String> collumns, List<String> values){
        String s = getInsertModel(tableName, collumns);
        for (int i = 0; i < values.size()-1; i++) {
            s+=values.get(i)+",";
        }
        s+= values.get(values.size()-1)+")";
        return s;
    }



    public static String getUpdateRequest(String tableName, List<String> collumns, List<String> values, List<String> conditionCollumns, List<String> conditionValues){
        String s = "UPDATE "+tableName+" SET ";
        for (int i = 0; i < collumns.size()-1; i++) {
            s+= collumns.get(i)+" = "+values.get(i)+" , ";
        }
        s+= collumns.get(collumns.size()-1)+" = "+values.get(values.size()-1)+" WHERE ";
        for (int i = 0; i < conditionCollumns.size()-1; i++) {
            s+=conditionCollumns.get(i)+" = "+conditionValues.get(i)+" AND ";
        }
        s+=conditionCollumns.get(conditionCollumns.size()-1)+" = "+conditionValues.get(conditionValues.size()-1);
        return s;
    }






    public static String getRequestPlayer(String tabelName, List<String> columns){
        String annotation = "SELECT ";
        for (int i = 0; i < columns.size()-1; i++) {
            annotation+=columns.get(i)+",";
        }
        annotation+=columns.get(columns.size()-1)+" from "+tabelName;

        return annotation;
    }


    public static String getInsertRequestForPlayer(String tabelName, List<String> columns, Integer playerId, String nickname){
        String annotation ="INSERT INTO "+ tabelName+"(";
        for (int i = 0; i < columns.size()-1; i++) {
            annotation+=columns.get(i)+",";
        }
        annotation+=columns.get(columns.size()-1)+") VALUES("+ playerId+", '" + nickname + "')";
        return annotation;
    }

    public static String getInsertRequestCurrencies(String tabelName, List<String> col, Currencies c){
        return getInsertModel(tabelName, col) +
                c.getId()+", " + c.getPlayerId() + ", " + c.getResourceId() + ", '" + c.getName() +"', " + c.getCount()
                + ")";
    }

    public static String getInsertRequestItems(String tabelName, List<String> col, Items item){
        return getInsertModel(tabelName, col) +
                item.getId()+", " + item.getPlayerId() + ", " + item.getResourceId() + ", " + item.getCount() +", " + item.getLevel()
                + ")";
    }

    public static String getInsertRequestProgresses(String tabelName, List<String> col, Progresses p){
        return getInsertModel(tabelName, col) +
                p.getId()+", " + p.getPlayerId() + ", " + p.getResourceId() + ", " + p.getScore() +", " + p.getMaxScore()
                + ")";
    }

    public static String getQueryRequest(String tableName, List<String> col, String key, String val){
        return getSelectModel(tableName, col) + key + "="+val;
    }

    public static String getQueryRequest(String tableName, String key, String val){
        return getSelectModel(tableName) + key + "="+val;
    }

    public static String getQueryRequest(String tableName, List<String> collumns, List<String> value){
        String s = getSelectModel(tableName);
        for (int i = 0; i < collumns.size()-1; i++) {
            s+= collumns.get(i)+"="+value.get(i)+",";
        }

        s+= collumns.get(collumns.size()-1)+"="+value.get(value.size()-1);
        return  s;
    }
}
