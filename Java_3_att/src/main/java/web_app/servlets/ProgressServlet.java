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

@WebServlet("/ProgressServlet")
public class ProgressServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private Data data;

    public ProgressServlet(Data data) {
        this.data = data;
    }

    //получить
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<String> collumns = new ArrayList<>(Requests.PROGRESS.collumns());
        List<Object> values = values(request);
        Requests.cutNullValues(collumns, values);
        ResultSet rs = null;

        try {
            rs = DataBaseManager.getResultSet(Requests.read(Requests.PROGRESS.tableName(), collumns, values), data.getConnection());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        if(rs == null) {
            response.getWriter().println("Not found");
            return;
        }
        String ans = "";
        try {
            ans = FormirateAnswer.answer(Requests.PROGRESS.collumns(), rs);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        response.getWriter().println(ans);



    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        List<String> collumns = new ArrayList<>(Requests.PROGRESS.collumns());
        List<Object> values = values(request);
        Requests.cutNullValues(collumns, values);

        if (collumns.size() != Requests.PROGRESS.collumns().size()){
            response.getWriter().println(
                    "you haven't filled in all the fields\n" +
                            "fill all: "+Requests.PROGRESS.collumns());
            return;
        }

        int ind = collumns.indexOf("playerId");
        ResultSet rs;
        try {
            rs = DataBaseManager.getResultSet(Requests.read(Requests.PROGRESS.tableName(), collumns.get(ind), values.get(ind)), data.getConnection());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        try {
            if(!rs.next()){
                response.getWriter().println("This player doesn't exist");
                return;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        try {
            DataBaseManager.executeUpdate(Requests.insert(Requests.PROGRESS.tableName(), collumns, values), data.getConnection());
        } catch (SQLException e) {
            response.getWriter().println("This progress already exist");
        }

    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.getWriter().println(request);
        List<String> collumns = new ArrayList<>(Requests.PROGRESS.collumns());
        List<Object> values = values(request);
        Requests.cutNullValues(collumns, values);

        try {
            DataBaseManager.executeUpdate(Requests.remove(Requests.PROGRESS.tableName(), collumns, values), data.getConnection());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException {
        List<String> collumns1 = new ArrayList<>(Requests.PROGRESS.collumns());
        List<Object> values1 = values(request);
        Requests.cutNullValues(collumns1, values1);

        List<String> collumns2 = new ArrayList<>(Requests.PROGRESS.collumns());
        List<Object> values2 = values(request, "C");
        Requests.cutNullValues(collumns2, values2);

        int ind = collumns2.indexOf("id");
        if(ind != -1){
            response.getWriter().println("U can't change id");
            return;
        }

        try {
            DataBaseManager.executeUpdate(Requests.update(Requests.PROGRESS.tableName(), collumns2, values2, collumns1, values1), data.getConnection());
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

        v = request.getParameter(s+"id");
        values.add(v);

        v = request.getParameter(s+"playerId");
        values.add(v);

        v = request.getParameter(s+"resourceId");
        values.add(v);

        v = request.getParameter(s+"score");

        values.add(v);

        v = request.getParameter(s+"maxScore");
        values.add(v);

        return values;
    }

    //http://localhost:8080/currencie?playerId=10008
    /***
     * private int id;
     *     private int playerId;
     *     private int resourceId;
     *     private int score;
     *     private int maxScore;
     */
}