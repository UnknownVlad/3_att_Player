import actions_with_db.Data;
import web_app.Server;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;

public class Main {

    public static void main(String[] args) throws IOException, ClassNotFoundException, SQLException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {

        Data data = new Data();
        Server.start(data);

    }


}
