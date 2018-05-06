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
    // Новый механизм добавления водителя
    public void writeDriver(Map<String, String> driverData) throws SQLException{
        Statement st = con.createStatement();
        String insertQuery = "INSERT INTO `drivers` SET `driver_lastname`='"+ driverData.get("lastName") +"', "
                        + "`driver_firstname`='"+ driverData.get("firstName") +"', "
                        + "`driver_midName`='"+ driverData.get("midlName") +"', "
                        + "`driver_bornDate`='"+ driverData.get("bornDate") +"', "
                        + "`carId`='"+ driverData.get("car") +"', "
                        + "`driver_limit`='"+ driverData.get("debtLimit") +"', "
                        + "`driver_phone_number`='"+ driverData.get("mainPhone") +"', "
                        + "`driver_addPhone_number`='"+ driverData.get("addPhone") +"', "
                        + "`driver_email`='"+ driverData.get("email") +"', "
                        + "`driver_day_rent`='"+ driverData.get("dayRent") +"', "
                        + "`driver_current_debt`=0, "
                        + "`driverStartDate`='"+ driverData.get("addDate") +"', "
                        + "`driverDayOffPeriod`='"+ driverData.get("shedule") +"', "
                        + "`comment`='"+ driverData.get("comment") +"'";
        st.execute(insertQuery);
        Map<String, String> address =new HashMap<>();
        ResultSet rs = st.executeQuery("SELECT LAST_INSERT_ID() as driverId");
        int driveID=0;
        if(rs.next()){
            driveID=rs.getInt("driverId");
            con.createStatement().execute("UPDATE `cars` SET `driverId`="+rs.getInt("driverId")+" WHERE `id` = "+driverData.get("car"));
            System.out.println(rs.getInt("driverId")+"");
        }
        address.put("country", driverData.get("country"));
        address.put("province", driverData.get("province"));
        address.put("city", driverData.get("city"));
        address.put("strit", driverData.get("strit"));
        address.put("house", driverData.get("house"));
        address.put("building", driverData.get("building"));
        address.put("flat", driverData.get("flat"));
        writeDriverAddres(address, driveID, 1);
        writeDriverPassport(driveID, driverData.get("passportNumber"),
                driverData.get("passportDate"), driverData.get("passportFrom"));
    }
    private void writeDriverAddres(Map<String, String> address, int driverId, int type) throws SQLException{
        String insertQuery = "INSERT INTO `driverAddress` SET `driverId`="+ driverId +", "
                + "`country`='"+ address.get("country") +"', "
                + "`province`='"+ address.get("province") +"', "
                + "`city`='"+ address.get("city") +"', "
                + "`strit`='"+ address.get("strit") +"', "
                + "`house`='"+ address.get("house") +"', "
                + "`building`='"+ address.get("building") +"', "
                + "`flat`='"+ address.get("flat") +"', "
                + "`type`="+ type + " ON DUPLICATE KEY UPDATE "
                + "`country`='"+ address.get("country") +"', "
                + "`province`='"+ address.get("province") +"', "
                + "`city`='"+ address.get("city") +"', "
                + "`strit`='"+ address.get("strit") +"', "
                + "`house`='"+ address.get("house") +"', "
                + "`building`='"+ address.get("building") +"', "
                + "`flat`='"+ address.get("flat") +"' ";
        Statement stAddAddress = con.createStatement();
        stAddAddress.execute(insertQuery);
    }
    
    private void writeDriverPassport(int driverId, String passportNumber, String passportDate, String passportFrom) throws SQLException{
        String insertQuery = "INSERT INTO `passports` SET `driverId`="+driverId+", "
                + "`passportNumber`='"+passportNumber+"', "
                + "`passportDate`='"+passportDate+"', "
                + "`passportFrom`='"+passportFrom+"'  ON DUPLICATE KEY UPDATE "
                + "`passportNumber`='"+passportNumber+"', "
                + "`passportDate`='"+passportDate+"', " 
                + "`passportFrom`='"+passportFrom+"' ";
        Statement stAddPassport = con.createStatement();
        
        stAddPassport.execute(insertQuery);
    }
    ////////////////////////////////////////////////////////////////////////////
    public void writeDriver(String limit, 
            String carId, 
            String callsign, 
            String name, 
            String lastname, 
            String phone,
            String dayRent,
            String schedule,
            String comment) throws SQLException{
        Statement st = con.createStatement();
        st.execute("INSERT INTO `drivers` (`driver_lastname`, `driver_firstname`, "
                + "`driver_callsign`, `carId`, `driver_limit`, `driver_phone_number`, "
                + "`driver_day_rent`, `driver_current_debt`, `driverStartDate`, `driverDayOffPeriod`, `comment`) "
                + "VALUES ('"+lastname+"', '"+name+"', '"+callsign+"', '"
                +carId+"', '"+limit+"', '"+phone+"', "+dayRent
                +", 0, CURRENT_DATE(), "+schedule+", '"+comment+"')");
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
            Map<String, String> rowDriver = new HashMap();
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
    // Плучение данных для формы изменения водителя
    public Map getAllDataDriver(int driverId) throws SQLException{
        Statement st = con.createStatement();
        String query = "SELECT zap.*, driverAddress.*  FROM driverAddress "
                + "RIGHT JOIN (SELECT * FROM `drivers` "
                + "LEFT JOIN passports "
                + "ON passports.driverId=drivers.driver_id WHERE drivers.driver_id="+ driverId +") as zap "
                + "ON zap.driver_id=driverAddress.driverId";
        ResultSet rs = st.executeQuery(query);
        Map<String, String> rowDriver = new HashMap();
        if(rs.next()){
            rowDriver.put("driver_id", rs.getString("driver_id"));
            rowDriver.put("driver_lastname", rs.getString("driver_lastname"));
            rowDriver.put("driver_firstname", rs.getString("driver_firstname"));
            rowDriver.put("driver_midName", rs.getString("driver_midName"));
            rowDriver.put("driver_current_debt", rs.getString("driver_current_debt"));
            rowDriver.put("driver_day_rent", rs.getString("driver_day_rent"));
            rowDriver.put("driver_limit", rs.getString("driver_limit"));
            rowDriver.put("driver_phone_number", rs.getString("driver_phone_number"));
            rowDriver.put("driver_addPhone_number", rs.getString("driver_addPhone_number"));
            rowDriver.put("driver_email", rs.getString("driver_email"));
            rowDriver.put("driver_bornDate", rs.getString("driver_bornDate"));
            rowDriver.put("driverStartDate", rs.getString("driverStartDate"));
            rowDriver.put("driverDayOffPeriod", rs.getString("driverDayOffPeriod"));
            rowDriver.put("comment", rs.getString("comment"));
            rowDriver.put("yaId", rs.getString("yaId"));
            rowDriver.put("passportNumber", rs.getString("passportNumber"));
            rowDriver.put("passportDate", rs.getString("passportDate"));
            rowDriver.put("passportFrom", rs.getString("passportFrom"));
            rowDriver.put("country", rs.getString("country"));
            rowDriver.put("province", rs.getString("province"));
            rowDriver.put("city", rs.getString("city"));
            rowDriver.put("strit", rs.getString("strit"));
            rowDriver.put("house", rs.getString("house"));
            rowDriver.put("building", rs.getString("building"));
            rowDriver.put("flat", rs.getString("flat"));
        }
        return rowDriver;
    }
    //Измененте данных водителя без изменения аренды и графика
    public void getEditDataDriver(int driverId, String limit, 
            String carId,  
            String name, 
            String lastname, 
            String phone, 
            String comment) throws SQLException{
        Statement st = con.createStatement();
        st.execute("UPDATE `drivers` SET `driver_lastname`='"+lastname+"', `driver_firstname`='"+name+"', "
                + " `carId`='"+carId+"', `driver_limit`='"+limit
                +"', `comment` ='"+comment+"', `driver_phone_number`='"+phone+"' WHERE driver_id="+driverId);
    }
    //Измененте данных водителя с изменением аренды и графика
    public void getEditDataDriver(int driverId, 
            String limit, 
            String carId, 
            String name, 
            String lastname, 
            String phone, 
            String rentPay, 
            String driver_schedule,
            String comment,
            String yaId) throws SQLException{
        System.out.println("UPDATE driver id:"+driverId
                +" comment: "+comment);
        Statement st = con.createStatement();
        st.execute("UPDATE `drivers` SET `driverDayOffPeriod`="+driver_schedule
                +", `driver_day_rent`="+rentPay
                +", `driver_lastname`='"+lastname
                +"', `driver_firstname`='"+name+"', "
                +" `carId`='"+carId
                +"', `driver_limit`='"+limit
                +"', `driver_phone_number`='"+phone
                +"', `comment`='"+comment
                +"', `yaId`='"+yaId
                +"' WHERE driver_id="+driverId);
    }
    public void delDriver(String driverId) throws SQLException{
        Statement stDelDriver = con.createStatement();
        stDelDriver.execute("UPDATE drivers SET `driver_deleted`=1, `driverEndDate`=CURRENT_DATE() WHERE `driver_id`="+driverId);
        stDelDriver.close();
    }
    public String getOptions(int dayOff) throws SQLException {
        String options = "";
        Statement stSch = con.createStatement();
        ResultSet rsSch = stSch.executeQuery("SELECT * FROM `driverSchedule`");
        while(rsSch.next()){
            String selected = "";
            if(rsSch.getInt("dayInCiсle")==dayOff){
                selected = "selected";
            }
            options = options + "<option value='" + rsSch.getInt("dayInCiсle") + "' "+ selected +" >" + rsSch.getString("scheduleName")+ "</option>";
        }
        rsSch.close();
        stSch.close();
        return options;
    }

    public void changeStartDate(int driverId) throws SQLException {
        Statement st = con.createStatement();
        st.execute("UPDATE drivers SET `driverStartDate`=CURRENT_DATE() WHERE `driver_id`="+driverId);
    }

    public void editDriver(Map<String, String> driverData, int driverId) throws SQLException {
        Statement st = con.createStatement();
        String updateQuery = "UPDATE`drivers` SET `driver_lastname`='"+ driverData.get("lastName") +"', "
                        + "`driver_firstname`='"+ driverData.get("firstName") +"', "
                        + "`driver_midName`='"+ driverData.get("midlName") +"', "
                        + "`driver_bornDate`='"+ driverData.get("bornDate") +"', "
                        + "`carId`='"+ driverData.get("car") +"', "
                        + "`driver_limit`='"+ driverData.get("debtLimit") +"', "
                        + "`driver_phone_number`='"+ driverData.get("mainPhone") +"', "
                        + "`driver_addPhone_number`='"+ driverData.get("addPhone") +"', "
                        + "`driver_email`='"+ driverData.get("email") +"', "
                        + "`driver_day_rent`='"+ driverData.get("dayRent") +"', "
                        + "`driverStartDate`='"+ driverData.get("addDate") +"', "
                        + "`driverDayOffPeriod`='"+ driverData.get("shedule") +"', "
                        + "`yaId`='"+ driverData.get("yaId") +"', "
                        + "`comment`='"+ driverData.get("comment") +"'  WHERE driver_id="+driverId;
        st.execute(updateQuery);
        Map<String, String> address =new HashMap<>();
        address.put("country", driverData.get("country"));
        address.put("province", driverData.get("province"));
        address.put("city", driverData.get("city"));
        address.put("strit", driverData.get("strit"));
        address.put("house", driverData.get("house"));
        address.put("building", driverData.get("building"));
        address.put("flat", driverData.get("flat"));
        try{
        writeDriverAddres(address, driverId, 1);
        writeDriverPassport(driverId, driverData.get("passportNumber"),
                driverData.get("passportDate"), driverData.get("passportFrom"));
        }
        catch(Exception ex){
            System.out.println("Не удалось записать данные об адресе/паспорте водителя\n"+ex.getMessage());
        }
    }
}
