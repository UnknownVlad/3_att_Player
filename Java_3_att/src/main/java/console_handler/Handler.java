package console_handler;

import actions_with_db.Data;
import console_handler.commands.CRUD;
import player_structure.PlayerMap;

import java.sql.SQLException;
import java.util.List;



public class Handler {
    private static String color_green = "\u001B[32m";
    private static String color_red = "\u001B[31m";
    private static String reset = "\u001B[0m";
    public static void startCommands(){
        System.out.println(color_red+"ENTER: "+reset);
        System.out.println(color_green+"help      - ������ ���");
        System.out.println("info      - ���������� � ��������");
        System.out.println("commands  - ���������� � ���������"+reset);
        System.out.println(color_red+"exit      - ������� ���������"+reset);
    }



    private static void p(int n){
        for (int i = 0; i < n; i++) {
            System.out.print("-");
        }
        System.out.println();
    }

    private static void p(List<String> s){
        for (String p:s) {
            System.out.print(p+"   ");
        }
        System.out.println();
    }
    public static void getInfo(Data d){
        System.out.println(color_red+"                          INFO               "+reset);
        p(60);
        System.out.println(color_green+"��� �� �������  : "+reset + d.getPlayerTabelName());
        System.out.println(color_green+"��� �� �����    : "+reset + d.getCurrenciesTabelName());
        System.out.println(color_green+"��� �� ���������: "+reset + d.getItemsTabelName());
        System.out.println(color_green+"��� �� �������� : "+reset + d.getProgressesTabelName());
        System.out.println();
        System.out.println(color_green+"���� �� �������: "+reset);
        p(d.getPlayerTabelColumns());
        System.out.println();
        System.out.println(color_green+"���� �� �����: "+reset);
        p(d.getCurrenciesTabelColumns());
        System.out.println();
        System.out.println(color_green+"���� �� ���������: "+reset);
        p(d.getItemsTabelColumns());
        System.out.println();
        System.out.println(color_green+"���� �� ���������: "+reset);
        p(d.getProgressesTabelColumns());
        p(60);

        System.out.println();
    }

    public static void getCommands(Data d){
        System.out.println(color_red+"                                        COMMANDS               "+reset);
        p(100);
        System.out.println(color_green+"������ �������� ����� � �������:"+reset);
        System.out.println("-col ���_�������");
        System.out.println();

        System.out.println(color_green + "��������:"+reset);
        System.out.println("-add ���_������� ����_1=? , ����_2=? , ... , ����_n=?");
        System.out.println("���");
        System.out.println("-add ���_������� ? , ? , ... , ? (��� '?' �������� ���������������� ���� � ������� �� �������)");
        System.out.println();

        System.out.println(color_green +"��������:"+reset);
        System.out.println("-get ���_������� ����_1=? , ����_2=? , ... , ����_n=?");
        System.out.println();

        System.out.println(color_green +"�������:"+reset);
        System.out.println("-rm ���_������� ����_1=? , ����_2=? , ... , ����_n=?");
        System.out.println("���");
        System.out.println("-rm ���_������� ? , ? , ... , ? (��� '?' �������� ���������������� ���� � ������� �� �������)");
        System.out.println();

        System.out.println(color_green +"��������:"+reset);
        System.out.println("-chng ���_������� ����_1=? , ����_2=? , ... , ����_n=? where ����_1=? , ... , ����_n=?");

        p(100);
    }

    public static void getCommand(String command, Data d, PlayerMap cash) throws SQLException {
        int i = -1;

        command = command.trim()+" ";

        i = command.indexOf(" ");
        String typeCommand = command.substring(0, i);

        command = command.substring(i+1).trim()+" ";

        i = command.indexOf(" ");
        String tableName = command.substring(0, i).trim();

        command = command.substring(i+1, command.length()).trim();

        CRUD.value(typeCommand.trim()).execute(d, tableName, command, cash);


    }
    public static boolean processing(Data d, String command, PlayerMap cash) throws SQLException {

        if(command.trim().equals("help")){
            getInfo(d);
            getCommands(d);
            return true;
        }
        if(command.trim().equals("info")){
            getInfo(d);
            return true;
        }
        if(command.trim().equals("commands")){
            getCommands(d);
            return true;
        }
        if(command.trim().equals("exit"))
            return false;

        try {
            getCommand(command, d, cash);
        } catch (SQLException e) {
            System.out.println("����������");
        }


        return true;
    }
}
