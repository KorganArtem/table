<%-- 
    Document   : index
    Created on : 19.12.2017, 15:31:57
    Author     : korgan
--%>

<%@page import="ru.leasicar.workerSql.WorkerSQL"%>
<%@ page language="java" contentType="text/html; charset=utf-8"%>
<!DOCTYPE html>
<!doctype html>
<%@ page import="ru.leasicar.authorization.*" %>
<% 
    AccessControl ac = new AccessControl();
    if(!ac.isLogIn(request.getSession().getId())){%>
        <meta http-equiv="refresh" content="1; URL=auth.jsp" />
        <%
        return ; 
    }
    String carlist = "";
    WorkerSQL wrk = new WorkerSQL();
    carlist = wrk.getFreeCarList();
%>
<html lang="us">
<head>
	<meta charset="utf-8">
        <!-- index.jsp -->
        
	<title>Lease Car</title>
        <meta http-equiv='Cache-Control' content='no-cache'>
        <meta http-equiv='Cache-Control' content='private'>
        <script src='https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js'></script>
        <script src='https://code.jquery.com/jquery-1.12.4.js'></script>
        <script type='text/javascript' src='https://cdn.datatables.net/1.10.16/js/jquery.dataTables.min.js'></script>
        <link rel='stylesheet' type='text/css' href='https://cdn.datatables.net/v/dt/dt-1.10.16/datatables.min.css'/>
        <link rel='stylesheet' type='text/css'  href='css/datatabel.css'/>
        <link rel='stylesheet' type='text/css'  href='css/main.css'/>
    </head>
<body>
    <div class='place'>
        <!--div id='formDriver'>
            <form class='addDriverForm'>
                <h3>Добавление водителя</h3>
                <div class='formItem'><label>Имя</label><br><input type='text' id='driver_name' /></div>
                <div class='formItem'><label>Фамилия</label><br><input type='text' id='driver_lastname' /></div>
                <div class='formItem'><label>Позывной</label><br><input type='text' id='driver_callsign' /></div>
                <div class='formItem'><label>Номер машины</label><br>
                    <select id='carId'>
                        <%= carlist %>
                    </select>
                </div>
                <div class='formItem'><label>Лимит</label><br><input type='text' id='driver_limit' /></div>
                <div class='formItem'><label>Номер телефона</label><br><input type='text' id='driver_phone_number' /></div>
                <div class='formItem'><label>Суточная Аренда</label><br><input type='text' id='driver_day_rent' /></div>
                <div class='formItem'><label>График</label><br>
                    <select id='driver_schedule'>
                        <option value='0'>Без выходных</option>
                        <option value='11'>10/1</option>
                    </select></div>
                <div class='formItem'><br><input type='button' id='driver_add' value='Добавить' /></div>
            </form>
        </div-->
        <div id='clickBox'><input id='carListButton' type='button' value='Car list'/>
            <input id='driverListButton' type='button' disabled='true' value='Driver list'/></div>
            <div id='mainContainer'>
                <div id='listDriver'></div>
                <div id='carList'></div>
            </div>
        <div id='editDriver'></div>
    </div><div id='modal_form'><!-- Сaмo oкнo --> 
        <!-- Тут любoе сoдержимoе -->
    </div>
    <div id='overlay'>
        
    </div>
</body>
    <script src='js/main.js'></script>
    <script>listDriverShow()</script>
</html>