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
public class WorkerSQL {
    public String url;
    public String login;
    public String pass;
    public Connection con;
    Map config;
    boolean iscon;
    public WorkerSQL() throws ClassNotFoundException, SQLException{
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
            rowDriver.put("driver_callsign", rs.getString("driver_callsign"));
            rowDriver.put("id_car", rs.getString("number"));
            rowDriver.put("driver_carnumber", rs.getString("driver_carnumber"));
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
    public void addPayDriver(int driverId, int sum, int source, int userId) throws SQLException{
        Statement st = con.createStatement();
        st.execute("INSERT INTO `pay` (`type`, `date`, `source`, `sum`, `driverId`, `user`) "
                + "VALUES ('1', NOW(), '"+source+"', '"+sum+"', '"+driverId+"', '"+userId+"')");
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
    public void addAccrual() throws SQLException{
        Statement lockTable = con.createStatement();
        ResultSet rsLock = lockTable.executeQuery("SELECT `paramValue` FROM `param` WHERE `paramName` = 'workingAccural'");
        if(rsLock.next()){
            int marker = Integer.parseInt(rsLock.getString("paramValue"));
            if(marker==0){
                Statement stLock = con.createStatement();
                stLock.execute("UPDATE `param` SET `paramValue` = '1'");
                stLock.close();
            }
            else{
                rsLock.close();
                lockTable.close();
                return;
            }
            rsLock.close();
            lockTable.close();    
        }
        Statement stGetDriverAndRent = con.createStatement();
        ResultSet rsGetDriverAndRent = stGetDriverAndRent.executeQuery("SELECT `drivers`.*, TO_DAYS(current_date())-TO_DAYS(driverStartDate)+1 as `dayWork` FROM `drivers` "
                + "WHERE `driver_deleted`=0 AND `driver_id` NOT IN "
                + "(SELECT `driverId` FROM `pay` WHERE `type`=2 and `date` > CURDATE())");
        while(rsGetDriverAndRent.next()){
            int rentSum;
            int number = rsGetDriverAndRent.getInt("dayWork");
            int ost = number % 11;
            if(ost==0){
                rentSum=0;
            }
            else{
                rentSum=rsGetDriverAndRent.getInt("driver_day_rent")*-1;  
            }
            System.out.println("Driver id = "+rsGetDriverAndRent.getInt("driver_id")+" Driver Last Name = "+rsGetDriverAndRent.getString("driver_lastname")+" WorkDay = "+ number +"  RentSum = "+rentSum);
            Statement stAddAccrual = con.createStatement();
            stAddAccrual.execute("INSERT INTO `pay` (`type`, `date`, `source`, `sum`, `driverId`) "
                    + "VALUES ('2', NOW(), 0, '"+rentSum+"', '"+rsGetDriverAndRent.getInt("driver_id")+"')");
            Statement stUpdateCurrentDebt = con.createStatement();
            stUpdateCurrentDebt.execute("UPDATE `drivers` SET `driver_current_debt`=(SELECT sum(`sum`) FROM `pay` WHERE driverId="+rsGetDriverAndRent.getInt("driver_id")+" and type!=3)"
                    + " WHERE `driver_id`="+rsGetDriverAndRent.getInt("driver_id"));
            stAddAccrual.close();
            stUpdateCurrentDebt.close();
        }
        rsGetDriverAndRent.close();
        stGetDriverAndRent.close();
        Statement stUnLock = con.createStatement();
        stUnLock.execute("UPDATE `param` SET `paramValue` = '0'");
        stUnLock.close();
    }
    public void addAccrual(int her) throws SQLException{
        Statement lockTable = con.createStatement();
        ResultSet rsLock = lockTable.executeQuery("SELECT `paramValue` FROM `param` WHERE `paramName` = 'workingAccural'");
        if(rsLock.next()){
            int marker = Integer.parseInt(rsLock.getString("paramValue"));
            if(marker==0){
                Statement stLock = con.createStatement();
                stLock.execute("UPDATE `param` SET `paramValue` = '1'");
                stLock.close();
            }
            else{
                rsLock.close();
                lockTable.close();
                return;
            }
            rsLock.close();
            lockTable.close();    
        }
        Statement stGetDriverAndRent = con.createStatement();
        ResultSet rsGetDriverAndRent = stGetDriverAndRent.executeQuery("SELECT `driver_id`, `driver_day_rent` FROM `drivers` WHERE `driver_id` NOT IN "
                + "(SELECT `driverId` FROM `pay` WHERE `type`=2 and `date` > CURDATE())");
        while(rsGetDriverAndRent.next()){
            if(getDayOff(rsGetDriverAndRent.getInt("driver_id")))
                continue;
            Statement stAddAccrual = con.createStatement();
            stAddAccrual.execute("INSERT INTO `pay` (`type`, `date`, `source`, `sum`, `driverId`) "
                    + "VALUES ('2', NOW(), 0, '-"+rsGetDriverAndRent.getInt("driver_day_rent")+"', '"+rsGetDriverAndRent.getInt("driver_id")+"')");
            Statement stUpdateCurrentDebt = con.createStatement();
            stUpdateCurrentDebt.execute("UPDATE `drivers` SET `driver_current_debt`=(SELECT sum(`sum`) FROM `pay` WHERE driverId="+rsGetDriverAndRent.getInt("driver_id")+" and type!=3)"
                    + " WHERE `driver_id`="+rsGetDriverAndRent.getInt("driver_id"));
            stAddAccrual.close();
            stUpdateCurrentDebt.close();
        }
        rsGetDriverAndRent.close();
        stGetDriverAndRent.close();
        Statement stUnLock = con.createStatement();
        stUnLock.execute("UPDATE `param` SET `paramValue` = '0'");
        stUnLock.close();
    }
    private boolean getDayOff(int driverId) throws SQLException{
        Statement stCheckDayOff = con.createStatement();
        ResultSet rsCheckDayOff = stCheckDayOff.executeQuery("SELECT `id` FROM `pay` WHERE `type`=2 and `driverId`="+driverId);
        rsCheckDayOff.last();
        int number = rsCheckDayOff.getRow();
        if(number==0){
            rsCheckDayOff.close();
            stCheckDayOff.close();
            return false;
        }
        int ost = number % 11;
        rsCheckDayOff.close();
        stCheckDayOff.close();
        if(ost==0)
            return true;
        else
            return false;
        
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
            rowDriver.put("driver_carnumber", rs.getString("driver_carnumber"));
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
    public Map getPayList(int driverId) throws SQLException{
        Statement stPayList = con.createStatement();
        ResultSet rsPayList = stPayList.executeQuery("SELECT DATE_FORMAT(`date`, '%Y-%m-%d') as date_f, "
                + "`pay`.* FROM `pay` WHERE `driverId`="+driverId+" ORDER BY `id`");
        Map payList = new HashMap<String, HashMap>();
        while(rsPayList.next()){
            Map payRaw = new HashMap<String, String>();
            payRaw.put("type", rsPayList.getString("type"));
            payRaw.put("source", rsPayList.getString("source"));
            payRaw.put("sum", rsPayList.getString("sum"));
            payRaw.put("date", rsPayList.getString("date_f"));
            payList.put(rsPayList.getString("id"), payRaw);
        }
        return payList;
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
    public void delDriver(String driverId) throws SQLException{
        Statement stDelDriver = con.createStatement();
        stDelDriver.execute("UPDATE drivers SET `driver_deleted`=1, `driverEndDate`=CURRENT_DATE() WHERE `driver_id`="+driverId);
        stDelDriver.close();
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
