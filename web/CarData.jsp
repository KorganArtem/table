<%-- 
    Document   : CarList
    Created on : 17.11.2017, 11:42:15
    Author     : korgan
--%>

<%@page import="java.util.Map"%>
<%@page import="ru.leasicar.workerSql.WorkerSQL"%>
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
    WorkerSQL wsql = new WorkerSQL();
    carData = wsql.getCarData(Integer.parseInt(request.getParameter("id")));
    
    modelList = wsql.modelLisc();
}
else{
    %>Error <% 
    return;
} 
%>
    <div class="carEditForm">
        <label>ID</label><input id='carId' type='text' disabled='true' value='<%=carData.get("id")%>'/><br>
        <label>Гос. Номер</label><input id='carNumber' type='text'  value='<%= carData.get("number") %>'/><br>
        <label>Модель</label>
        <select id='carModel' type='text'  value='<%= carData.get("model") %>'>
            <%= modelList %>
        </select><br>
        <label>VIN код</label><input id='carVIN' type='text'  value='<%= carData.get("VIN") %>'/><br>
        <label>Тип КПП</label><input id='carTransmission' type='text'  value='<%= carData.get("transmission") %>'/><br>
        <label>Год выпуска</label><input id='carYear' type='text'  value='<%= carData.get("year") %>'/><br>
        <label>Суточная Аренда</label><input id='carCost' type='text'  value='<%= carData.get("cost") %>'/><br>
        <label>ID в ГЛАНАС</label><input id='carGlanasId' type='text'  value='<%= carData.get("glanasId") %>'/><br>
        <div class="buttonBlock">
            <input id='editCarButton' type='button' value="Изменить"/>
            <input id='cancelCarButton' type='button' value="Отменить"/>
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
                var carId = $("#carId").val();
                alert(carNumber);
                $.ajax({
                    type: 'POST',
                    url: 'CES',
                    data: 'carNumber='+carNumber+'&carModel='+carModel+'&carVIN='+carVIN
                            +'&carTransmission='+carTransmission+'&carYear='+carYear
                            +'&carCost='+carCost+'&carGlanasId='+carGlanasId+'&carId='+carId,
                    success: function(){
                        alert('Изменения сохранены');
                    },
                    error: function(msg){
                        console.log(msg)
                    }
                });
            });
        </script>
