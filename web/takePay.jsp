<%-- 
    Document   : takePay
    Created on : 05.02.2018, 18:18:13
    Author     : korgan
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<div id='takePay1'>
    <form class='takePayForm'>
        <input type='text' style='display: none' disabled id='typePay'/><br>
        <input type='text' disabled id='takePayDriverId'/><br>
        <input type='text' disabled id='takePayDriverName'/><br>
        <input type='text' id='takePayDriverSum'/><br>
        <select id='takePayDriverType'><br>
            <option value='1'>Наличными</option>
            <option value='2'>Яндекс</option>
            <option value='3'>Gett</option>
            <option value='4'>Uber</option>
            <option value='5'>На карту</option>
            <option value='6'>С депозита</option>
            <option value='8'>Скидка</option>
        </select><br>
        <input type='button' id='takePayButton' onclick="takePaySend()" value='Принять'/>
        <input type='button' id='takePayButton' onclick="closeModWind()" value='Отмена'/>
    </form>
</div>
