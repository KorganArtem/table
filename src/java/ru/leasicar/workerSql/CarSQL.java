/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.leasicar.workerSql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author korgan
 */
public class CarSQL {
    
    public String url;
    public String login;
    public String pass;
    public Connection con;
    Map config;
    boolean iscon;
    public CarSQL() throws ClassNotFoundException, SQLException{
        ConfigurationReader cr = new ConfigurationReader();
        config=cr.readFile();
        url="jdbc:mysql://"+config.get("dbhost")+":"+config.get("dbport")+"/"+config.get("dbname")+"?useUnicode=true&characterEncoding=UTF-8";
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
    public Map carList() throws SQLException {
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery("SELECT *  FROM `cars`");
        Map listDriver = new HashMap<String, HashMap>();
        while(rs.next()){
            Map rowDriver = new HashMap<String, HashMap>();
            rowDriver.put("id", rs.getString("id"));
            rowDriver.put("number", rs.getString("number"));
            rowDriver.put("model", rs.getString("model"));
            rowDriver.put("VIN", rs.getString("VIN"));
            rowDriver.put("transmission", rs.getString("transmission"));
            rowDriver.put("year", rs.getString("year"));
            rowDriver.put("cost", rs.getString("cost"));
            rowDriver.put("glanasId", rs.getString("glanasId"));
            listDriver.put(rs.getString("id"), rowDriver);
        }
        return listDriver;
    }
    public Map getCarData(int id) throws SQLException{
        Map carData = new HashMap<String, String>();
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery("SELECT *  FROM `cars` WHERE `id`="+id);
        if(rs.next()){
            carData.put("id", rs.getString("id"));
            carData.put("number", rs.getString("number"));
            carData.put("model", rs.getString("model"));
            carData.put("VIN", rs.getString("VIN"));
            carData.put("transmission", rs.getString("transmission"));
            carData.put("year", rs.getString("year"));
            carData.put("cost", rs.getString("cost"));
            carData.put("glanasId", rs.getString("glanasId"));
        }
        return carData;
    }

    public void writeCarData(String carNumber, 
            String carVIN, String carModel, String carTransmission,
            String carYear, String carCost, String carGlanasId, String carId) throws SQLException {
        System.out.println("Попытка внести изменения в информации о машине()");
        Statement st = con.createStatement();
        st.execute("UPDATE `cars` SET `number`='" +carNumber+ "'"+
                                            ", `model`='" + carModel+ "'"+
                                            ", `VIN`='" + carVIN+"'"+
                                            ", `transmission`='" + carTransmission+"'"+
                                            ", `year`='" + carYear+"'"+
                                            ", `cost`='" + carCost+"'"+
                                            ", `glanasId`= '"+ carGlanasId+"'"+
                                            " WHERE `id`="+carId);
    }
    public String modelLisc(int currentIds){
        Map<Integer, String> carData = new HashMap();
        carData.put(1, "Kia Rio");
        carData.put(2, "Kia Optima");
        String forRet="";
        for (Map.Entry<Integer, String> entry : carData.entrySet()) {
            forRet =forRet+"<option value='"+entry.getKey()+"' ";
            if(entry.getKey()== currentIds)
                forRet = forRet+" selected "; 
            forRet =forRet+ ">" + entry.getValue()+"</option>";           
        }
        return forRet;
    }
    
    public String modelLisc(){
        Map<Integer, String> carData = new HashMap();
        carData.put(1, "Kia Rio");
        carData.put(2, "Kia Optima");
        String forRet="";
        for (Map.Entry<Integer, String> entry : carData.entrySet()) {
            forRet =forRet+"<option value='"+entry.getKey()+"' ";
            forRet =forRet+ ">" + entry.getValue()+"</option>";           
        }
        return forRet;
    }
    public void addCar(String carNumber, 
            String carVIN, String carModel, String carTransmission,
            String carYear, String carCost, String carGlanasId) throws SQLException {
        System.out.println("Попытка внести изменения в информации о машине()");
        Statement st = con.createStatement();
        st.execute("INSERT INTO `cars` (`number`, `model`, `VIN`, `transmission`, `year`, `cost`, `glanasId`)"
                + " VALUES ('" +carNumber+ "', '"+carModel+ "', "+
                                            " '" + carVIN+"', "+
                                            " '" + carTransmission+"', "+
                                            " '" + carYear+"', "+
                                            " '" + carCost+"', "+
                                            " '"+ carGlanasId+"')");
    }
    public String getFreeCarList() throws SQLException{
        String carData = "";
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery("SELECT * FROM `cars` WHERE `id` not in (SELECT `carId` FROM `drivers` WHERE `driver_deleted`=0)");
        while(rs.next()){
            carData = carData +"<option value='"+rs.getString("id")+"'>"+rs.getString("model")+"("+rs.getString("number")+")</option>";
        }
        return carData;
    }
    public String getFreeCarList(int driverId) throws SQLException{
        String carData = "";
        Statement st = con.createStatement();
        int currentCarId=0;
        ResultSet carentCarRes = con.createStatement().executeQuery("SELECT * FROM `cars` "
                + "WHERE `id` in (SELECT carId FROM drivers WHERE driver_id= "+driverId+")");
        if(carentCarRes.next())
            currentCarId=carentCarRes.getInt("id");
        ResultSet rs = st.executeQuery("SELECT * FROM `cars` WHERE `id` not in (SELECT `carId` FROM `drivers` WHERE `driver_deleted`=0) or  "
                + " `id` in (SELECT `carId` FROM `drivers` WHERE `driver_id`="+driverId+")");//"SELECT *  FROM `cars` WHERE `driverId`= 0 OR `driverId`="+driverId
        while(rs.next()){
            String selected = "";
            if(rs.getInt("id")==currentCarId)
                selected="selected";
            carData = carData +"<option value='"+rs.getString("id")+"' "+selected+">"+rs.getString("model")+"("+rs.getString("number")+")</option>";
        }
        return carData;
    }
}
