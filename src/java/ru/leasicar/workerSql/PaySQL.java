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
public class PaySQL {
    public String url;
    public String login;
    public String pass;
    public Connection con;
    Map config;
    boolean iscon;
    public PaySQL() throws ClassNotFoundException, SQLException{
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
    
    public void addPayDriver(int driverId, int sum, int source, int userId) throws SQLException{
        int balanceDriver = 0;
        if(source!=6){
            Statement stGetBalance = con.createStatement();
            ResultSet rsGetBalance = stGetBalance.executeQuery("SELECT `driver_current_debt` FROM `drivers` "
                + "WHERE `driver_id`="+driverId);
            if(rsGetBalance.next())
                balanceDriver=rsGetBalance.getInt("driver_current_debt")+sum;
        }
        Statement st = con.createStatement();
        st.execute("INSERT INTO `pay` (`type`, `date`, `source`, `sum`, `driverId`, `user`, `balance`) "
                + "VALUES ('1', NOW(), '"+source+"', '"+sum+"', '"+driverId+"', '"+userId+"', '"+balanceDriver+"')");
        st.close();
        if(source==6){
           Statement stUpdateCurrentDebt = con.createStatement();
            stUpdateCurrentDebt.execute("UPDATE `drivers` SET `driver_deposit`=`driver_deposit`-"+sum+" WHERE `driver_id`="+driverId);
            stUpdateCurrentDebt.close(); 
        }
        Statement stUpdateCurrentDebt = con.createStatement();
        stUpdateCurrentDebt.execute("UPDATE `drivers` SET `driver_current_debt`=(SELECT sum(`sum`) FROM `pay` WHERE driverId="+driverId+" and type!=3) WHERE `driver_id`="+driverId);
        stUpdateCurrentDebt.close();
    }


    public void addPayDeposit(int driverId, int sum, int source, int userId) throws SQLException {
        Statement st = con.createStatement();
        st.execute("INSERT INTO `pay` (`type`, `date`, `source`, `sum`, `driverId`, `user`) "
                + "VALUES ('3', NOW(), '"+source+"', '"+sum+"', '"+driverId+"', '"+userId+"')");
        st.close();
        Statement stUpdateCurrentDebt = con.createStatement();
        stUpdateCurrentDebt.execute("UPDATE `drivers` SET `driver_deposit`=`driver_deposit`+"+sum+" WHERE `driver_id`="+driverId);
        stUpdateCurrentDebt.close();
    }

    
}
