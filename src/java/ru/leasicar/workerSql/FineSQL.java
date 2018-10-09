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
    public JsonArray getAllFineOG() throws SQLException{Statement fineListSt = con.createStatement();
        ResultSet fineListRs = fineListSt.executeQuery("SELECT finecar.*, drivers.driver_lastname FROM drivers "
                + "RIGHT JOIN (SELECT `offenses`.*, cars.number as carNumber FROM `offenses`  " +
                "INNER JOIN cars \n" +
                "ON offenses.carId=cars.id \n" +
                "WHERE `gis_status`='nopayed' AND (bill_id like '188101%' OR bill_id like '188105%' OR bill_id like '035%')) as finecar\n" +
                "ON finecar.driverId=drivers.driver_id");   //
        JsonArray fineList = new JsonArray();
        while(fineListRs.next()){
            JsonObject oneFine = new JsonObject();
            oneFine.addProperty("carNumber", fineListRs.getString("carNumber"));
            oneFine.addProperty("driver_lastname", fineListRs.getString("driver_lastname"));
            oneFine.addProperty("bill_id", "<div class='billid' id='"+fineListRs.getString("bill_id")+"'>"+fineListRs.getString("bill_id")+"</div>");
            oneFine.addProperty("gis_status", fineListRs.getString("gis_status"));
            oneFine.addProperty("pay_bill_date", fineListRs.getString("pay_bill_date"));
            oneFine.addProperty("last_bill_date" , fineListRs.getString("last_bill_date"));
            oneFine.addProperty("pay_bill_amount", fineListRs.getString("pay_bill_amount"));
            oneFine.addProperty("gis_discount", fineListRs.getString("gis_discount"));
            oneFine.addProperty("gis_discount_uptodate", fineListRs.getString("gis_discount_uptodate"));
            oneFine.addProperty("pay_bill_amount_with_discount", fineListRs.getString("pay_bill_amount_with_discount"));
            oneFine.addProperty("offense_location", fineListRs.getString("offense_location"));
            oneFine.addProperty("offense_article", fineListRs.getString("offense_article"));
            oneFine.addProperty("offense_date", fineListRs.getString("offense_date"));
            oneFine.addProperty("offense_time", fineListRs.getString("offense_time"));
            oneFine.addProperty("offense_article_number", fineListRs.getString("offense_article_number"));
            oneFine.addProperty("carId", fineListRs.getString("carId"));
            oneFine.addProperty("driverId", fineListRs.getString("driverId"));
            
            
            
            
            fineList.add(oneFine);
        }
        return fineList;
    }
    public JsonObject getOneFine(String bill_id) throws SQLException{
        Statement fineSt = con.createStatement();
        ResultSet fineRs = fineSt.executeQuery("SELECT finecar.*, drivers.driver_lastname FROM drivers "
                + "RIGHT JOIN (SELECT `offenses`.*, cars.number as carNumber FROM `offenses`  " +
                "INNER JOIN cars \n" +
                "ON offenses.carId=cars.id \n" +
                "WHERE bill_id='"+bill_id+"') as finecar\n" +
                "ON finecar.driverId=drivers.driver_id");
        JsonObject oneFine = new JsonObject();
        if(fineRs.next()){
            oneFine.addProperty("carNumber", fineRs.getString("carNumber"));
            oneFine.addProperty("driver_lastname", fineRs.getString("driver_lastname"));
            oneFine.addProperty("bill_id", fineRs.getString("bill_id"));
            oneFine.addProperty("gis_status", fineRs.getString("gis_status"));
            oneFine.addProperty("pay_bill_date", fineRs.getString("pay_bill_date"));
            oneFine.addProperty("last_bill_date" , fineRs.getString("last_bill_date"));
            oneFine.addProperty("pay_bill_amount", fineRs.getString("pay_bill_amount"));
            oneFine.addProperty("gis_discount", fineRs.getString("gis_discount"));
            oneFine.addProperty("gis_discount_uptodate", fineRs.getString("gis_discount_uptodate"));
            oneFine.addProperty("pay_bill_amount_with_discount", fineRs.getString("pay_bill_amount_with_discount"));
            oneFine.addProperty("offense_location", fineRs.getString("offense_location"));
            oneFine.addProperty("offense_article", fineRs.getString("offense_article"));
            oneFine.addProperty("offense_date", fineRs.getString("offense_date"));
            oneFine.addProperty("offense_time", fineRs.getString("offense_time"));
            oneFine.addProperty("offense_article_number", fineRs.getString("offense_article_number"));
            oneFine.addProperty("carId", fineRs.getString("carId"));
            oneFine.addProperty("driverId", fineRs.getString("driverId"));
        }
        return oneFine;
    }
}
