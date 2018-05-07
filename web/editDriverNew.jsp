<%-- 
    Document   : addDriverNew
    Created on : 17.04.2018, 20:10:58
    Author     : korgan
--%>

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
    String editRent = "";
    if(ac.checkPermission(ac.getUserId(request.getSession().getId()), "editRent")){ 
        editRent = "<div class='formItem'>"
                    + "<label>Аренда</label><br>"
                    + " <input type='text' id='driver_day_rent' value='"+dataDriver.get("driver_day_rent")+"' /></div>";
        editRent = editRent+"<div class='formItem'>"
                        + "<label>График</label><br>"
                        + "<select id='driver_schedule'>"+dsql.getOptions(dayOffCicle)+ "</select></div>";
    }
%>
    <div id="form_container">
        <form id="addDriverFormN" class="appnitro"  method="post" >
            <div class="form_description">
                <h2>Добавление водителя</h2>
            </div>	
            <ul >
                <li id="li_1" >
                    <span>
			<input id="element_1_1" name= "lastName" class="element text" maxlength="255" size="14" value="<%= dataDriver.get("driver_lastname") %>"/>
			<label>Фамилия</label>
                    </span>
                    <span>
                        <input id="element_1_2" name= "firstName" class="element text" maxlength="255" size="14" value="<%= dataDriver.get("driver_firstname") %>"/>
                        <label>Имя</label>
                    </span> 
                    <span>
                        <input id="element_1_2" name= "midlName" class="element text" maxlength="255" size="14" value="<%= dataDriver.get("driver_midName") %>"/>
                        <label>Отчество</label>
                    </span> 
		</li>
                <li id="li_1" >
                    <span>
			<input id="element_1_1" name= "mainPhone" class="element text" maxlength="255" size="14" value="<%= dataDriver.get("driver_phone_number") %>"/>
			<label>Моб. Тел.</label>
                    </span>
                    <span>
                        <input id="element_1_2" name= "addPhone" class="element text" maxlength="255" size="14" value="<%= dataDriver.get("driver_addPhone_number") %>"/>
                        <label>Доп. Тел.</label>
                    </span> 
                    <span>
                        <input id="element_1_2" name= "email" class="element text" maxlength="255" size="14" value="<%= dataDriver.get("driver_email") %>"/>
                        <label>Email</label>
                    </span> 
		</li>
                <li id="li_1" >
                    <span>
			<input type="date" id="element_1_1" name= "bornDate" class="element date" maxlength="255" size="14" value="<%= dataDriver.get("driver_bornDate") %>"/>
			<label>Дата рождения</label>
                    </span>
                    <span>
                        <input type="date" id="element_1_2" name= "addDate" class="element date" maxlength="255" size="14" value="<%= dataDriver.get("driverStartDate") %>"/>
                        <label>Дата принятия</label>
                    </span> 
                    <span>
                        <input type="date" id="element_1_2" name= "delDate" class="element date" maxlength="255" size="14" value="<%= dataDriver.get("driverEndDate") %>"/>
                        <label>Дата увольнения</label>
                    </span> 
		</li>
                <li id="li_1" >
                    <label class="description" for="element_3">Паспорт </label>
                    <span>
			<input id="element_1_1" name= "passportNumber" class="element text" maxlength="255" size="14" value="<%= dataDriver.get("passportNumber") %>"/>
			<label>Серия номер</label>
                    </span>
                    <span>
                        <input type="date" id="element_1_2" name= "passportDate" class="element text" maxlength="255" size="14" value="<%= dataDriver.get("passportDate") %>"/>
                        <label>Дата выдачи</label>
                    </span> 
                    <span>
                        <input id="element_1_2" name= "passportFrom" class="element text" maxlength="255" size="14" value="<%= dataDriver.get("passportFrom") %>"/>
                        <label>Выдан</label>
                    </span> 
		</li>		
                <li id="li_3" >
                    <div id="address"><div class="left">
                            <input id="element_3_3" name="country" class="element text medium" value="<%= dataDriver.get("country") %>" type="text">
                            <label for="element_3_3">Страна</label>
                    </div>
                    <div class="right">
                            <input id="element_3_4" name="province" class="element text medium" value="<%= dataDriver.get("province") %>" type="text">
                            <label for="element_3_4">Область</label>
                    </div>
                    <div class="left">
                            <input id="element_3_5" name="city" class="element text medium" maxlength="15" value="<%= dataDriver.get("city") %>" type="text">
                            <label for="element_3_5">Город</label>
                    </div>
                    <div class="right">
                        <input id="element_3_5" name="strit" class="element text medium" maxlength="15" value="<%= dataDriver.get("strit") %>" type="text">
                        <label for="element_3_6">Улица</label>
                    </div> 
                    <span>
			<input id="element_1_1" name= "house" class="element text" maxlength="255" size="9" value="<%= dataDriver.get("house") %>"/>
			<label>Дом</label>
                    </span>
                    <span>
                        <input id="element_1_2" name= "building" class="element text" maxlength="255" size="9" value="<%= dataDriver.get("building") %>"/>
                        <label>Корпус/Строение</label>
                    </span> 
                    <span>
                        <input id="element_1_2" name= "flat" class="element text" maxlength="255" size="9" value="<%= dataDriver.get("flat") %>"/>
                        <label>Квартира</label>
                    </span> 
                        <span>
                            <input id="element_1_2" name= "postCode" class="element text" maxlength="255" size="9" value="<%= dataDriver.get("postCode") %>"/>
                            <label>Индекс</label>
                        </span> 
                    </div>
                    <div id="addAddress">
                        <label class="description" for="element_3">Почтовый адрес </label>
                        <div class="left">
                                <input id="element_3_3" name="addCountry" class="element text medium" value="<%= dataDriver.get("addCountry") %>" type="text">
                                <label for="element_3_3">Страна</label>
                        </div>
                        <div class="right">
                                <input id="element_3_4" name="addProvince" class="element text medium" value="<%= dataDriver.get("addProvince") %>" type="text">
                                <label for="element_3_4">Область</label>
                        </div>
                        <div class="left">
                                <input id="element_3_5" name="addCity" class="element text medium" maxlength="15" value="<%= dataDriver.get("addCity") %>" type="text">
                                <label for="element_3_5">Город</label>
                        </div>
                        <div class="right">
                            <input id="element_3_5" name="addStrit" class="element text medium" maxlength="15" value="<%= dataDriver.get("addStrit") %>" type="text">
                            <label for="element_3_6">Улица</label>
                        </div> 
                        <span>
                            <input id="element_1_1" name= "addHouse" class="element text" maxlength="255" size="9" value="<%= dataDriver.get("addHouse") %>"/>
                            <label>Дом</label>
                        </span>
                        <span>
                            <input id="element_1_2" name= "addBuilding" class="element text" maxlength="255" size="9" value="<%= dataDriver.get("addBuilding") %>"/>
                            <label>Корпус/Строение</label>
                        </span> 
                        <span>
                            <input id="element_1_2" name= "addFlat" class="element text" maxlength="255" size="9" value="<%= dataDriver.get("addFlat") %>"/>
                            <label>Квартира</label>
                        </span> 
                        <span>
                            <input id="element_1_2" name= "addPostCode" class="element text" maxlength="255" size="9" value="<%= dataDriver.get("addPostCode") %>"/>
                            <label>Индекс</label>
                        </span> 
                    </div>
		</li>			
                <li id="li_5" >
                    <label class="description" for="element_5">Аренда </label>
                    <span>
                        <select class="element  " style="width: 100px;" id="element_5" name="car"> 
                            <%= csql.getFreeCarList(driverId) %>
                        </select>
                        <label>Машина</label>
                    </span> 
                    <span>
                        <select class="element  " style="width: 100px;" id="element_5" name="shedule"> 
                            <%= dsql.getOptions(dayOffCicle) %>
                        </select>
                        <label>График</label>
                    </span> 
                    <span>
                        <input id="element_1_2" name= "dayRent" class="element text" maxlength="255" size="9" value="<%= dataDriver.get("driver_day_rent") %>"/>
                        <label>Аренда</label>
                    </span> 
                    <span>
                        <input id="element_1_2" name= "debtLimit" class="element text" maxlength="255" size="8" value="<%= dataDriver.get("driver_limit") %>"/>
                        <label>Лимит</label>
                    </span> 
                    <span>
                        <input type='text' id='yaId' name='yaId' value='<%= dataDriver.get("yaId") %>' />
                        <label>yaId</label>
                    </span> 
		</li>		
                <li id="li_4" >
                    <label class="description" for="element_4">Комментарий </label>
                    <div>
			<textarea id="element_4" name="comment" class="element textarea medium"><%= dataDriver.get("comment") %></textarea> 
                    </div> 
		</li>
                <li class="buttons">
                    <input id="saveForm" class="button_text" type="button" onClick="editDriverSendNew()" name="submit" value="Сохранить" />
                    <input id="saveForm" class="button_text" type="button" onClick="closeModWind()" name="submit" value="Отмена" />
		</li>
            </ul>  
            <div class='formItem' style="display: none;">
                <input type='text' name="changeSchedule" id='changeSchedule' value="0"/>
                <input type='text' name="driverId" id='driverId' value='<%= dataDriver.get("driver_id") %>' />
            </div> 
	</form>	
    </div>
    <img id="bottom" src="bottom.png" alt="">
    <script>
        var oldSchedule = $('#driver_schedule').val();
        $('#driver_schedule').change(function(){
            var schdule = $('#driver_schedule').val();
            if(schdule!==oldSchedule)
                $('#changeSchedule').val(1);
            else
                $('#changeSchedule').val(0);
        });
    </script>