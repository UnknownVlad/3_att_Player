package web_app.servlets;

import actions_with_db.Data;
import actions_with_db.DataBaseManager;
import web_app.FormirateAnswer;
import web_app.Requests;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/PlayerServlet")
public class PlayerServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private Data data;

    public PlayerServlet(Data data) {
        this.data = data;
    }

    //получить
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        List<String> collumns = new ArrayList<>(Requests.PLAYER.collumns());
        List<Object> values = values(request);
        Requests.cutNullValues(collumns, values);


        ResultSet rs = null;

        try {
            rs = DataBaseManager.getResultSet(Requests.read(Requests.PLAYER.tableName(), collumns, values), data.getConnection());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


        String ans = "";
        try {
            ans = FormirateAnswer.answer(Requests.PLAYER.collumns(), rs);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        if(ans.equals(""))
            ans = " Not found";

        response.getWriter().println(ans);

    }

//положить
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        List<String> collumns = new ArrayList<>(Requests.PLAYER.collumns());
        List<Object> values = values(request);
        Requests.cutNullValues(collumns, values);

        if (collumns.size() != Requests.PLAYER.collumns().size()){
            response.getWriter().println(
                    "you haven't filled in all the fields\n" +
                    "fill all: "+Requests.PLAYER.collumns());
            return;
        }

        try {
            DataBaseManager.executeUpdate(Requests.insert(Requests.PLAYER.tableName(), collumns, values), data.getConnection());
        } catch (SQLException e) {
            response.getWriter().println("This player already exist");
        }

    }

    //удалить
    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.getWriter().println(request);
        List<String> collumns = new ArrayList<>(Requests.PLAYER.collumns());
        List<Object> values = values(request);
        Requests.cutNullValues(collumns, values);

        int ind = collumns.indexOf("playerId");

        if(ind == -1){
            response.getWriter().println("Cant do this. Enter field playerId");
            return;
        }

        try {
            DataBaseManager.executeUpdate(Requests.remove(Requests.PLAYER.tableName(), collumns, values), data.getConnection());
            DataBaseManager.executeUpdate(Requests.remove(Requests.CURRENCIE.tableName(), List.of("playerId"), List.of(values.get(ind))), data.getConnection());
            DataBaseManager.executeUpdate(Requests.remove(Requests.ITEM.tableName(), List.of("playerId"), List.of(values.get(ind))), data.getConnection());
            DataBaseManager.executeUpdate(Requests.remove(Requests.PROGRESS.tableName(), List.of("playerId"), List.of(values.get(ind))), data.getConnection());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    //изменение
    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException {
        List<String> collumns1 = new ArrayList<>(Requests.PLAYER.collumns());
        List<Object> values1 = values(request);
        Requests.cutNullValues(collumns1, values1);

        List<String> collumns2 = new ArrayList<>(Requests.PLAYER.collumns());
        List<Object> values2 = values(request, "C");
        Requests.cutNullValues(collumns2, values2);

        int ind = collumns2.indexOf("playerId");
        if(ind != -1){
            response.getWriter().println("U can't change playerId");
            return;
        }
        try {
            DataBaseManager.executeUpdate(Requests.update(Requests.PLAYER.tableName(), collumns2, values2, collumns1, values1), data.getConnection());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }

    protected List<Object> values(HttpServletRequest request){
        return values(request, "");
    }
    protected List<Object> values(HttpServletRequest request, String s){

        List<Object> values = new ArrayList<>();
        String v;
        v = request.getParameter(s+"playerId");
        values.add(v);
        v = request.getParameter(s+"nickname");
        if(v != null)
            v = "'"+v.replace("'", "")+"'";
        values.add(v);

        return values;
    }
}

