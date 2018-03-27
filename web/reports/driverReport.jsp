<%-- 
    Document   : driverReport
    Created on : 02.02.2018, 19:39:18
    Author     : korgan
--%>

<%@page import="java.util.Date"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Map"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ page import="ru.leasicar.authorization.*" %>
<% 
AccessControl ac = new AccessControl();
if(!ac.isLogIn(request.getSession().getId())){%> 
    <meta http-equiv="refresh" content="1; URL=auth.jsp" />
    <%
    return ; 
}
if(request.getParameter("driverId")==null){
    return;
}   
SimpleDateFormat dtf = new SimpleDateFormat("yyyy-MM-dd");
Date dt = new Date();
String dateNow =dtf.format(dt);
String dateWeek =dtf.format(dt.getTime()-604800000);
%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel='stylesheet' type='text/css'  href='../css/datatabel.css'/>
        <link rel='stylesheet' type='text/css' href='../css/main.css'/>
        <script src='https://code.jquery.com/jquery-1.12.4.js'></script>
        <script src='../js/main.js'></script>
        <script type='text/javascript' src='https://cdn.datatables.net/1.10.16/js/jquery.dataTables.min.js'></script>
    </head>
    <body>
        <div id="driverReportParam">
            <input id="beginPeriod" type="date" value="<%= dateWeek %>"/>
            <input id="endPeriod" type="date" value="<%= dateNow %>"/>
            <input id="getDriverReport" type="button" value="Показать" />
        </div>
        <div id="reportPlace">
        </div>
        <script> 
            function getDriverReport(begin, end){
                $.ajax({
                    type: 'POST',
                    url: '../DriverPayReport',
                    data: 'begin='+begin+'&end='+end+'&driverId='+<%= request.getParameter("driverId")%>,
                    success: function(data){
                        $("#reportPlace").html(data);
                        $("#driverReport").DataTable({paging: false});
                    },
                    error: function(msg){
                        alert(msg);
                    }
                });
            }
            $(document).ready(function (){
                getDriverReport($("#beginPeriod").val(), $("#endPeriod").val());
            });
            $("#getDriverReport").click(function (){
                getDriverReport($("#beginPeriod").val(), $("#endPeriod").val());
            })
        </script>
    </body>
</html>
