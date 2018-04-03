<%@page import="ru.leasicar.authorization.AccessControl"%>
<%@page import="java.util.Map"%>
<%@page import="ru.leasicar.workerSql.*"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<% 
    AccessControl ac = new AccessControl();
    if(!ac.isLogIn(request.getSession().getId())){%>
        <meta http-equiv="refresh" content="1; URL=auth.jsp" />
    <%
        return ; 
    }
    DriverSQL dsql = new DriverSQL();
    CarSQL csql = new CarSQL(); 
    int driverId = Integer.parseInt(request.getParameter("driverId"));
    Map dataDriver = dsql.getAllDataDriver(driverId);
    int dayOffCicle = Integer.parseInt(dataDriver.get("driverDayOffPeriod").toString());
    String editClick = "onClick='editDriverSend("+dataDriver.get("driver_id")+")'";
    String editRent = "";
    if(ac.checkPermission(ac.getUserId(request.getSession().getId()), "editRent")){ 
        editRent = "<div class='formItem'>"
                    + "<label>Аренда</label><br>"
                    + " <input type='text' id='driver_day_rent' value='"+dataDriver.get("driver_day_rent")+"' /></div>";
        editRent = editRent+"<div class='formItem'>"
                        + "<label>График</label><br>"
                        + "<select id='driver_schedule'>"+dsql.getOptions(dayOffCicle)+ "</select></div>";
    }
    editClick =  "onClick='editDriverSendRP("+dataDriver.get("driver_id")+")'"; 
%>
<form class='addDriverForm'>
    <div class='formItem'>
        <label>Имя</label><br>
        <input type='text' id='driver_name' value='<%= dataDriver.get("driver_firstname") %>'/>
    </div>
    <div class='formItem'>
        <label>Фамилия</label><br>
        <input type='text' id='driver_lastname' value='<%= dataDriver.get("driver_lastname") %>'/>
    </div>
    <div class='formItem'>
        <label>Позывной</label><br>
        <input type='text' id='driver_callsign' value='<%= dataDriver.get("driver_callsign") %>' />
    </div>
    <div class='formItem'>
        <label>Номер машины</label><br>
        <select id='carId' >
            <%= csql.getFreeCarList(driverId) %>
        </select>
    </div> 
    <div class='formItem'>
        <label>Лимит</label><br>
        <input type='text' id='driver_limit' value='<%= dataDriver.get("driver_limit") %>' />
    </div>
    <div class='formItem'>
        <label>Номер телефона</label><br>
        <input type='text' id='driver_phone_number' value='<%= dataDriver.get("driver_phone_number") %>' />
    </div> 
    <%= editRent %>  
    <div class='formItem'>
        <label>ID Yandex</label><br>
        <input type='text' id='yaId' value='<%= dataDriver.get("yaId") %>' />
    </div>
    <div class='formItem'>
            <label>Комментарий</label><br>
            <textarea height="5" id='driverComment' ><%= dataDriver.get("comment") %></textarea>
    </div> 
    <div class='formItem'><br><input type='button' <%= editClick %> value='Изменить' />             
    <input type='button' id='cancelEditDriver' value='Отмена' onClick='clearEditForm()' /></div>
</form>