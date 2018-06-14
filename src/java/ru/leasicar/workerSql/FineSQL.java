/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.leasicar.workerSql;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;

/**
 *
 * @author korgan
 */
public class FineSQL {
    public String url;
    public String login;
    public String pass;
    public Connection con;
    Map config;
    boolean iscon;
    public FineSQL() throws ClassNotFoundException, SQLException{
        ConfigurationReader cr = new ConfigurationReader();
        config=cr.readFile();
        url="jdbc:mysql://"+config.get("dbhost")+":"+config.get("dbport")+"/"+config.get("dbname")+"?useUnicode=true&characterEncoding=UTF-8&zeroDateTimeBehavior=convertToNull";
        try
        {
            Class.forName("com.mysql.jdbc.Driver"); 
            login=config.get("dbuser").toString();
            pass=config.get("dbpassword").toString();
            con = DriverManager.getConnection(url, login, pass);
            iscon = true;
        }
        catch(SQLException ex)
        {
            System.out.println("Mysql ERROR: "+ex.getMessage());
        }
    }
    public JsonArray getAllFine() throws SQLException{
        Statement fineListSt = con.createStatement();
        ResultSet fineListRs = fineListSt.executeQuery("SELECT finecar.*, drivers.driver_lastname FROM drivers " +
                            "RIGHT JOIN (SELECT fine.*, cars.number as carNumber FROM fine  " +
                            "INNER JOIN cars " +
                            "ON fine.fineCar=cars.id " +
                            "WHERE fineState=0) as finecar " +
                            "ON finecar.driver_id=drivers.driver_id");
        JsonArray fineList = new JsonArray();
        while(fineListRs.next()){
            JsonObject oneFine = new JsonObject();
            oneFine.addProperty("fineUis", fineListRs.getString("fineUis"));
            oneFine.addProperty("fineSum", fineListRs.getString("fineSum"));
            oneFine.addProperty("fineDate", fineListRs.getString("fineDate"));
            oneFine.addProperty("fineReason", fineListRs.getString("fineReason"));
            oneFine.addProperty("fineState", fineListRs.getString("fineState"));
            oneFine.addProperty("fineDatePay", fineListRs.getString("fineDatePay"));
            oneFine.addProperty("finePlace", fineListRs.getString("finePlace"));
            oneFine.addProperty("fineDescription", fineListRs.getString("fineDescription"));
            oneFine.addProperty("carNumber", fineListRs.getString("carNumber"));
            oneFine.addProperty("fineOffender", fineListRs.getString("fineOffender"));
            oneFine.addProperty("driver_lastname", fineListRs.getString("driver_lastname"));
            fineList.add(oneFine);
        }
        return fineList;
    }
}
