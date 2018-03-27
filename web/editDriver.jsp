<%-- 
    Document   : editDriver
    Created on : 16.03.2018, 18:10:23
    Author     : korgan
--%>

<%@page import="ru.leasicar.workerSql.CarSQL"%>
<%@page import="ru.leasicar.workerSql.DriverSQL"%>
<%@page import="java.util.Map"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="ru.leasicar.authorization.*" %>
<% 
    AccessControl ac = new AccessControl();
    if(!ac.isLogIn(request.getSession().getId())){%>
        <meta http-equiv="refresh" content="1; URL=auth.jsp" />
        <%
            return ; 
        }
    int driverId = Integer.parseInt(request.getParameter("driverId"));
    DriverSQL dsql = new DriverSQL();
    CarSQL csql = new CarSQL();
    Map dataDriver = dsql.getAllDataDriver(driverId);
    String editClick = "onClick='editDriverSend("+dataDriver.get("driver_id")+")'";
 %>
<form class='addDriverForm'>
    <div class='formItem'>
        <label>Имя</label><br>
        <input type='text' id='driver_name' value='"+dataDriver.get("driver_firstname")+"'/>
    </div>
    <div class='formItem'>
        <label>Фамилия</label><br>
        <input type='text' id='driver_lastname' value='"+dataDriver.get("driver_lastname")+"'/>
    </div>
    <div class='formItem'>
        <label>Позывной</label><br>
        <input type='text' id='driver_callsign' value='"+dataDriver.get("driver_callsign")+"' />
    </div>
    <div class='formItem'><label>Номер машины</label><br>
        <select id='carId' >
            
        </select>
    </div>
    <div class='formItem'>
        <label>Лимит</label><br>
        <input type='text' id='driver_limit' value='"+dataDriver.get("driver_limit")+"' />
    </div>
    <div class='formItem'>
        <label>Номер телефона</label><br>
        <input type='text' id='driver_phone_number' value='"+dataDriver.get("driver_phone_number")+"' />
    </div> 
    <%
     if(ac.checkPermission(ac.getUserId(request.getSession().getId()), "editRent")){  %>   
         <div class='formItem'><label>Аренда</label><br><input type='text' id='driver_day_rent' value='"+dataDriver.get("driver_day_rent")+"' /></div>
        <%editClick =  "onClick='editDriverSendRP("+dataDriver.get("driver_id")+")'"; %>
         <div class='formItem'><label>График</label><br><select id='driver_schedule'><%= getOptions((String)dataDriver.get("driverDayOffPeriod")) %></select></div>
    <%}%>
    <div class='formItem'><br>
        <input type='button' <%= editClick %> value='Изменить' />
        <input type='button' id='cancelEditDriver' value='Отмена' onClick='clearEditForm()' />
    </div>
 </form>
