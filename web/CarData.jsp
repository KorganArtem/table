<%-- 
    Document   : CarList
    Created on : 17.11.2017, 11:42:15
    Author     : korgan
--%>

<%@page import="java.util.Map"%>
<%@page import="ru.leasicar.workerSql.CarSQL"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ page import="ru.leasicar.authorization.*" %>
<% 
Map carData = null;
AccessControl ac = new AccessControl();
if(!ac.isLogIn(request.getSession().getId())){%>
    <meta http-equiv="refresh" content="1; URL=auth.jsp" />
    <%
        return ; 
    }
    String modelList = "";
if(request.getParameter("id")!=null){
System.out.println("Get car data id="+request.getParameter("id"));
    CarSQL wsql = new CarSQL();
    carData = wsql.getCarData(Integer.parseInt(request.getParameter("id")));
    modelList = wsql.modelLisc(Integer.parseInt(carData.get("model").toString()));
}
else{
    %>Error <% 
    return;
} 
String mkpp = "";
if(carData.get("transmission")=="1")
    mkpp="selected";
String akpp = "";
if(carData.get("transmission")=="1")
    akpp="selected";
%>
    <div class="carEditForm">
        <label>ID</label><input id='carIdn' type='text' disabled='true' value='<%=carData.get("id")%>'/><br>
        <label>Гос. Номер</label><input id='carNumber' type='text'  value='<%= carData.get("number") %>'/><br>
        <label>Модель</label>
        <select id='carModel' type='text'  value='<%= carData.get("model") %>'>
            <%= modelList %>
        </select><br>
        <label>VIN код</label><input id='carVIN' type='text'  value='<%= carData.get("VIN") %>'/><br>
        <label>Тип КПП</label>
        <select id="carTransmission">
            <option value="1" <%= mkpp %> >МКПП</option>
            <option value="2" <%= akpp %> >АКПП</option>
        </select>
        <label>Год выпуска</label>
        <select id="carYear">
            <option value="2015" <% if(carData.get("year").equals("2015")){ %> selected <%}%> >2015</option>
            <option value="2016" <% if(carData.get("year").equals("2016")){ %> selected <%}%> >2016</option>
            <option value="2017" <% if(carData.get("year").equals("2017")){ %> selected <%}%> >2017</option>
            <option value="2018" <% if(carData.get("year").equals("2018")){ %> selected <%}%> >2018</option>
        </select>
        <label>Суточная Аренда</label><input id='carCost' type='text'  value='<%= carData.get("cost") %>'/><br>
        <label>ID в ГЛАНАС</label><input id='carGlanasId' type='text'  value='<%= carData.get("glanasId") %>'/><br>
        <label>СТС</label><input id='carStsNumber' type='text'  value='<%= carData.get("sts") %> '/><br>
        <label>ОСАГО</label><input id='carOsagoNumber' type='text'  value=' <%= carData.get("insuranceNamber") %>'/>
        <label>Срок действия ОСАГО</label><input id='carOsagoEnd' type='date' value="<%= carData.get("insuranceDateEnd") %>"/><br>
        <label>Номе талона ТО</label><input id='ttoNumber' type='text' value='<%= carData.get("ttoNumber") %>'/><br>
        <div class="buttonBlock">
            <input id='editCarButton' type='button' value="Сохранить"/>
            <input id='cancelCarButton' type='button' onClick="closeModWind()" value="Отменить"/>
        </div>
    </div>
        <script>
            $("#editCarButton").click(function (){
                var carNumber = $("#carNumber").val();
                var carModel = $("#carModel").val();
                var carVIN = $("#carVIN").val();
                var carTransmission = $("#carTransmission").val();
                var carYear = $("#carYear").val();
                var carCost = $("#carCost").val();
                var carGlanasId = $("#carGlanasId").val();
                var carId = $("#carIdn").val();
                var carStsNumber = $("#carStsNumber").val();
                var carOsagoNumber = $("#carOsagoNumber").val();
                var carOsagoEnd = $("#carOsagoEnd").val();
                var ttoNumber = $("#ttoNumber").val();
                $.ajax({
                    type: 'POST',
                    url: 'CES',
                    data: 'carNumber='+carNumber+'&carModel='+carModel+'&carVIN='+carVIN
                            +'&carTransmission='+carTransmission+'&carYear='+carYear
                            +'&carCost='+carCost+'&carGlanasId='+carGlanasId+'&carId='+carId
                            +'&carStsNumber='+carStsNumber
                            +'&carOsagoNumber='+carOsagoNumber
                            +'&carOsagoEnd='+carOsagoEnd
                            +'&ttoNumber='+ttoNumber,
                    success: function(){
                        alert('Изменения сохранены');
                        closeModWind();
                        getCarList();
                    },
                    error: function(msg){
                        alert(msg);
                    }
                });
            });
        </script>
