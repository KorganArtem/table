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
        <div id="leftMenu">
            <input id='driverListButton' type='button' disabled='true' value='Driver list'/>
            <input id='carListButton' type='button' value='Car list'/>
            <input id='mainProp' type='button' value='Настройка'/>
        </div>
        <div id='clickBox'></div>
            <div id='mainContainer'>
                <div id='listDriver'></div>
                <div id='carList'></div>
            </div>
        <div id='editDriver'></div>
    </div>
    <div id='modal_form'><!-- Сaмo oкнo --> 
        <!-- Тут любoе сoдержимoе -->
    </div>
    <div id='overlay'>   
    </div>
</body>
    <script src='js/main.js'></script>
    <script>listDriverShow()</script>
</html>