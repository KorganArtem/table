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
import java.util.Map;

/**
 *
 * @author Artem
 */
public class UserSQL {
     public String url;
    public String login;
    public String pass;
    public Connection con;
    Map config;
    boolean iscon;
    public UserSQL() throws ClassNotFoundException, SQLException{
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
    public String getUserForSelect() throws SQLException{
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery("SELECT * FROM `users`");
        String forRet="";
        while(rs.next()){
            forRet = forRet+"<option value='"+rs.getInt("id")+"' >"+rs.getString("ful_name")+"</option>";
        }
        return forRet;
    }
}
