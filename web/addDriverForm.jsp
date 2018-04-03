<%-- 
    Document   : addDriverForm
    Created on : Feb 6, 2018, 10:06:43 PM
    Author     : Artem
--%>

<%@page import="ru.leasicar.workerSql.*"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<% DriverSQL dsql = new DriverSQL(); %>
<% CarSQL csql = new CarSQL(); %>
<div id='formDriver'>
    <!--h3>Добавление водителя</h3-->
    <form class='addDriverForm'>
        <div class='formItem'>
            <label>Имя</label><br><input type='text' id='driver_name' />
        </div>
        <div class='formItem'>
            <label>Фамилия</label><br><input type='text' id='driver_lastname' />
        </div>
        <div class='formItem'>
            <label>Позывной</label><br><input type='text' id='driver_callsign' />
        </div>
        <div class='formItem'>
            <label>Номер машины</label><br>
            <select id='carId' >
                <%= csql.getFreeCarList()%>
            </select>
        </div>
        <div class='formItem'>
            <label>Лимит</label><br><input type='text' id='driver_limit' />
        </div>  
        <div class='formItem'>
            <label>Номер телефона</label><br><input type='text' id='driver_phone_number' />
        </div>    
        <div class='formItem'>
            <label>Суточная Аренда</label><br><input type='text' id='driver_day_rent' />
        </div>     
        <div class='formItem'>
            <label>График</label><br>
            <select id='driver_schedule'>
                <option value='0'>Без выходных</option>
		<option value='11'>10/1</option>
		<option value='7'>6/1</option>
            </select>
        </div>
        <div class='formItem'>
            <label>Комментарий</label><br>
            <textarea height="5" id='driverComment' ></textarea>
        </div> 
        <div class='formItem'><br><input type='button' id='driver_add' value='Добавить' onClick="addDriver()"/></div>
        <div class='formItem'><br><input type='button' id='driver_add' value='Отмена' onClick="closeModWind()"/></div>
    </form>
</div>
