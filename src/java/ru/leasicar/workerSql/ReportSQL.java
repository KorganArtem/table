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
public class ReportSQL {
     public String url;
    public String login;
    public String pass;
    public Connection con;
    Map config;
    boolean iscon;
    public ReportSQL() throws ClassNotFoundException, SQLException{
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
    
    public Map getPayList(int driverId, String begin, String end) throws SQLException{
        System.out.println(begin + "  " +end);
        Statement stPayList = con.createStatement();
        ResultSet rsPayList = stPayList.executeQuery("SELECT zap1.`date_f`, zap1.`id`, zap1.`payName`, zap1.`user`, payType.payTypeName, zap1.`sum`  FROM payType " +
                                "INNER JOIN (SELECT DATE_FORMAT(`date`, '%Y-%m-%d') as date_f, `pay`.*, `paySource`.`payName` FROM `pay` " +
                                "LEFT join `paySource` " +
                                "ON `paySource`.`payId`=`pay`.`source`) as zap1\n" +
                                "ON zap1.`type`=payType.payTapeId\n" +
                                "WHERE `driverId`="+driverId+" AND `date` > '"+begin+"' AND `date` < '"+end+" 23:59:59' ORDER BY `id`");
        Map payList = new HashMap<String, HashMap>();
        while(rsPayList.next()){
            Map payRaw = new HashMap<String, String>();
            payRaw.put("payTypeName", rsPayList.getString("payTypeName"));
            payRaw.put("payName", rsPayList.getString("payName"));
            payRaw.put("sum", rsPayList.getString("sum"));
            payRaw.put("date", rsPayList.getString("date_f"));
            payList.put(rsPayList.getString("id"), payRaw);
        }
        return payList;
    }
    public Map getGroupPay(int driverId, String begin, String end) throws SQLException{
        Statement stPayList = con.createStatement();
        ResultSet rsPayList = stPayList.executeQuery("SELECT `source`, SUM(`sum`) as `sum` FROM `pay` "
                + "WHERE `driverId`="+driverId+" AND `date` > '"+begin+"' AND `date` < '"+end+"' GROUP BY `source`");
        Map payList = new HashMap<String, HashMap>();
        int indRep = 0;
        while(rsPayList.next()){
            Map payRaw = new HashMap<String, String>();
            payRaw.put("source", rsPayList.getString("source"));
            payRaw.put("sum", rsPayList.getString("sum"));
            payList.put(rsPayList.getString("source"), payRaw);
        }
        return payList;
    }
}
