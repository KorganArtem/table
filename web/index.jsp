<%-- 
    Document   : index
    Created on : 19.12.2017, 15:31:57
    Author     : korgan
--%>

<%@page import="ru.leasicar.workerSql.CarSQL"%>
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
    CarSQL crk = new CarSQL();
    carlist = crk.getFreeCarList();
%>
<html lang="us">
<head>
	<meta charset="utf-8">
        <!-- index.jsp -->
        
        <link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
        <link rel="stylesheet" href="/resources/demos/style.css">
	<title>Lease Car</title>
        <meta http-equiv='Cache-Control' content='no-cache'>
        <meta http-equiv='Cache-Control' content='private'>
        <script src='https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js'></script>
        <script src='https://code.jquery.com/jquery-1.12.4.js'></script>
        <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
        <script type='text/javascript' src='https://cdn.datatables.net/1.10.16/js/jquery.dataTables.min.js'></script>
        <link rel='stylesheet' type='text/css' href='https://cdn.datatables.net/v/dt/dt-1.10.16/datatables.min.css'/>
        <link rel='stylesheet' type='text/css'  href='css/datatabel.css'/>
        <link rel='stylesheet' type='text/css'  href='css/main.css'/>
        <link rel="stylesheet" type="text/css" href="css/view.css" media="all">
        <script type="text/javascript" src="js/view.js"></script>
    </head>
<body>
    <div class='place'>
        <div id="leftMenu">
            <input id='driverListButton' class='itemMenu' type='button' disabled='true' value='Driver list'/>
            <input id='carListButton' class='itemMenu' type='button' value='Car list'/>
            <input id='mainProp' class='itemMenu' type='button' value='Настройка'/>
        </div>
        <div id='mainContainer'>
            <div id='listDriver' class='itemDisplay'></div>
            <div id='carList' class='itemDisplay'></div>
            <div id='prop' class='itemDisplay'></div>
        </div>
        <div id='editDriver'></div>
    </div>
    <div id='modal_form'><!-- Сaмo oкнo --> 
        <!-- Тут любoе сoдержимoе -->
    </div>
    <div id='overlay'>   
    </div>
    <div id="menuBox" title="Документы">
	<ul id="docMenu">
		<li class="menuItem" id="getDog" data="dogovor">Договор</li>
		<li class="menuItem" data="aktvidachi">Акт выдачи</li>
		<li class="menuItem" data="aktpriema">Акт приемки</li>
		<li class="menuItem" data="putevoilist">Путевые листы</li>
	</ul>
    </div>
</body>
<script>
    $( "#menuBox" ).dialog({
      autoOpen: false,
      width: 200,
      show: {
        effect: "blind",
        duration: 1
      },
      hide: {
        effect: "explode",
        duration: 1
      }
    });
</script>
    <script src='js/main.js'></script>
</html>