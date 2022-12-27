package web_app;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class FormirateAnswer {
    public static String answer(List<String> collumns, ResultSet rs) throws SQLException {
        String s = "";

        while (rs.next()){
            for (String c:collumns) {
                s = s + c + ": " + rs.getObject(c)+"\n";
                System.out.println(rs.getObject(c));
            }
            s+="\n";
        }
        return s;
    }
}
