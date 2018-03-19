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
public class DriverSQL {
    public String url;
    public String login;
    public String pass;
    public Connection con;
    Map config;
    boolean iscon;
    public DriverSQL() throws ClassNotFoundException, SQLException{
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
    public void writeDriver(String limit, 
            String carId, 
            String callsign, 
            String name, 
            String lastname, 
            String phone,
            String dayRent,
            String schedule) throws SQLException{
        Statement st = con.createStatement();
        st.execute("INSERT INTO `drivers` (`driver_lastname`, `driver_firstname`, "
                + "`driver_callsign`, `carId`, `driver_limit`, `driver_phone_number`, "
                + "`driver_day_rent`, `driver_current_debt`, `driverStartDate`, `driverDayOffPeriod`) "
                + "VALUES ('"+lastname+"', '"+name+"', '"+callsign+"', '"+carId+"', '"+limit+"', '"+phone+"', "+dayRent+", 0, CURRENT_DATE(), "+schedule+")  ");
        ResultSet rs = st.executeQuery("SELECT LAST_INSERT_ID() as driverId");
        if(rs.next()){
            con.createStatement().execute("UPDATE `cars` SET `driverId`="+rs.getInt("driverId")+" WHERE `id` = "+carId);
            System.out.println(rs.getInt("driverId")+"");
        }
        
    }
    public Map listDriver() throws SQLException{
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery("SELECT `drivers`.*, `cars`.`number`   FROM `drivers`  \n" +
                                        "LEFT JOIN `cars`\n" +
                                        "ON `cars`.`id`=`drivers`.`carId` \n" +
                                        "WHERE `drivers`.`driver_deleted`=0");
        Map listDriver = new HashMap<String, HashMap>();
        while(rs.next()){
            Map rowDriver = new HashMap<String, HashMap>();
            rowDriver.put("driver_id", rs.getString("driver_id"));
            rowDriver.put("driver_lastname", rs.getString("driver_lastname"));
            rowDriver.put("driver_firstname", rs.getString("driver_firstname"));
            rowDriver.put("id_car", rs.getString("number"));
            rowDriver.put("driver_current_debt", rs.getString("driver_current_debt"));
            rowDriver.put("driver_limit", rs.getString("driver_limit"));
            rowDriver.put("driver_day_rent", rs.getString("driver_day_rent"));
            rowDriver.put("driver_phone_number", rs.getString("driver_phone_number"));
            rowDriver.put("driver_deposit", rs.getString("driver_deposit"));
            rowDriver.put("dayOff", rs.getString("driverDayOff"));
            listDriver.put(rs.getString("driver_id"), rowDriver);
        }
        return listDriver;
    }    
    public Map getAllDataDriver(int driverId) throws SQLException{
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery("SELECT * FROM `drivers` WHERE `driver_id`="+driverId);
        Map rowDriver = new HashMap<String, HashMap>();
        if(rs.next()){
            rowDriver.put("driver_id", rs.getString("driver_id"));
            rowDriver.put("driver_lastname", rs.getString("driver_lastname"));
            rowDriver.put("driver_firstname", rs.getString("driver_firstname"));
            rowDriver.put("driver_callsign", rs.getString("driver_callsign"));
            rowDriver.put("driver_current_debt", rs.getString("driver_current_debt"));
            rowDriver.put("driver_day_rent", rs.getString("driver_day_rent"));
            rowDriver.put("driver_limit", rs.getString("driver_limit"));
            rowDriver.put("driver_phone_number", rs.getString("driver_phone_number"));
            rowDriver.put("driverDayOffPeriod", rs.getString("driverDayOffPeriod"));
        }
        return rowDriver;
    }

    public void getEditDataDriver(int driverId, String limit, 
            String carId, 
            String callsign, 
            String name, 
            String lastname, String phone) throws SQLException{
        Statement st = con.createStatement();
        st.execute("UPDATE `drivers` SET `driver_lastname`='"+lastname+"', `driver_firstname`='"+name+"', "
                + "`driver_callsign`='"+callsign+"', `carId`='"+carId+"', `driver_limit`='"+limit+"', `driver_phone_number`='"+phone+"' WHERE driver_id="+driverId);
    }
    public void getEditDataDriver(int driverId, String limit, 
            String carId, 
            String callsign, 
            String name, 
            String lastname, String phone, String rentPay, String driver_schedule) throws SQLException{
        System.out.println(rentPay);
        Statement st = con.createStatement();
        st.execute("UPDATE `drivers` SET `driverDayOffPeriod`="+driver_schedule+", `driver_day_rent`="+rentPay+", `driver_lastname`='"+lastname+"', `driver_firstname`='"+name+"', "
                + "`driver_callsign`='"+callsign+"', `carId`='"+carId+"', `driver_limit`='"+limit+"', `driver_phone_number`='"+phone+"' WHERE driver_id="+driverId);
    }
    public void delDriver(String driverId) throws SQLException{
        Statement stDelDriver = con.createStatement();
        stDelDriver.execute("UPDATE drivers SET `driver_deleted`=1, `driverEndDate`=CURRENT_DATE() WHERE `driver_id`="+driverId);
        stDelDriver.close();
    }
}
