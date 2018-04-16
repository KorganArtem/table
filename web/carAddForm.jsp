<%-- 
    Document   : carAddForm
    Created on : 30.01.2018, 15:38:40
    Author     : korgan
--%>

<%@page import="java.util.Date"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="ru.leasicar.workerSql.CarSQL"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
    CarSQL wsql = new CarSQL();
    String modelList = wsql.modelLisc();
    SimpleDateFormat dtf = new SimpleDateFormat("yyyy-MM-dd");
    Date dt = new Date();
    String dateYerMore =dtf.format(dt.getTime());
%>
<html>
    <div class="carEditForm">
        <label>Гос. Номер</label><input id='carNumber' type='text'  value=''/><br>
        <label>Модель</label>
        <select id='carModel' type='text'  value=''>
            <%= modelList %>
        </select><br>
        <label>VIN код</label><input id='carVIN' type='text'  value=''/><br>
        <label>Тип КПП</label>
        <select id="carTransmission">
            <option value="1">МКПП</option>
            <option value="2">АКПП</option>
        </select>
        <label>Год выпуска</label>
        <select id="carYear">
            <option value="2015">2015</option>
            <option value="2016">2016</option>
            <option value="2017">2017</option>
            <option value="2018">2018</option>
        </select>
        <label>Суточная Аренда</label><input id='carCost' type='text'  value='0'/><br>
        <label>ID в ГЛАНАС</label><input id='carGlanasId' type='text'  value='0'/><br>
        <label>СТС</label><input id='carStsNumber' type='text'  value=' '/><br>
        <label>ОСАГО</label><input id='carOsagoNumber' type='text'  value=' '/>
        <label>Срок действия ОСАГО</label><input id='carOsagoEnd' type='date' value="<%= dateYerMore %>"/><br>
        <label>Номе талона ТО</label><input id='ttoNumber' type='text' value=''/><br>
        <div class="buttonBlock">
            <input id='carAddButton' type='button' value="Сохранить"/>
            <input id='cancelCarButton' type='button' onClick="closeModWind()" value="Отменить"/>
        </div>
    </div>
        <script>
            $("#carAddButton").click(function (){
                var carNumber = $("#carNumber").val();
                var carModel = $("#carModel").val();
                var carVIN = $("#carVIN").val();
                var carTransmission = $("#carTransmission").val();
                var carYear = $("#carYear").val();
                var carCost = $("#carCost").val();
                var carGlanasId = $("#carGlanasId").val();
                var carStsNumber = $("#carStsNumber").val();
                var carOsagoNumber = $("#carOsagoNumber").val();
                var carOsagoEnd = $("#carOsagoEnd").val();
                var ttoNumber = $("#ttoNumber").val();
                alert(carNumber);
                $.ajax({
                    type: 'POST',
                    url: 'CAS',
                    data: 'carNumber='+carNumber
                            +'&carModel='+carModel
                            +'&carVIN='+carVIN
                            +'&carTransmission='+carTransmission
                            +'&carYear='+carYear
                            +'&carCost='+carCost
                            +'&carGlanasId='+carGlanasId
                            +'&carStsNumber='+carStsNumber
                            +'&carOsagoNumber='+carOsagoNumber
                            +'&carOsagoEnd='+carOsagoEnd
                            +'&ttoNumber='+ttoNumber,
                    success: function(data){
                        alert('Изменения сохранены'+data);
                    },
                    error: function(msg){
                        console.log(msg)
                    }
                });
            });
        </script>
</html>
