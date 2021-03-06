/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
$('#driver_add').click(function(){
  addAjax();
});
////////////////////////////////////////////////////////////////////////////////
/////                    Добавление Водителя                               /////
////////////////////////////////////////////////////////////////////////////////
function clock() {
    var d = new Date();
    var month_num = d.getMonth()
    var day = d.getDate();
    var hours = d.getHours();
    var minutes = d.getMinutes();
    var seconds = d.getSeconds();

    month=new Array("января", "февраля", "марта", "апреля", "мая", "июня",
    "июля", "августа", "сентября", "октября", "ноября", "декабря");

    if (day <= 9) day = "0" + day;
    if (hours <= 9) hours = "0" + hours;
    if (minutes <= 9) minutes = "0" + minutes;
    if (seconds <= 9) seconds = "0" + seconds;

    date_time = "Сегодня - " + day + " " + month[month_num] + " " + d.getFullYear() +
    " г.&nbsp;&nbsp;&nbsp;Текущее время - "+ hours + ":" + minutes + ":" + seconds;
    $('#clickBox').val();
}
function addAjax(){   
    var limit = $('#driver_limit').val();
    var carnumber = $('#driver_carnumber').val();
    var callsign = $('#driver_callsign').val();
    var name = $('#driver_name').val();
    var lastname = $('#driver_lastname').val();
    var phone = $('#driver_phone_number').val();
    var dayRent = $('#driver_day_rent').val();
    $.ajax({
        type: 'POST',
        url: 'DA',
        data: 'lastname='+lastname+'&name='+name+'&callsign='+callsign
                +'&carnumber='+carnumber+'&limit='+limit+'&phone='+phone
                +'&dayRent='+dayRent,
        success: function(data){
            location.reload();
        }
    });
    listDriverShow();
}
////////////////////////////////////////////////////////////////////////////////
/////                    Вывод списка водителей                            /////
////////////////////////////////////////////////////////////////////////////////
$(document).ready(function() {
    listDriverShow();
});
function listDriverShow(){
        $.ajax({
        type: 'POST',
        url: 'LD',
        success: function(data){
            $('#listDriver').html(data);
            $('#listDriverTabel').DataTable();
        }
    });
}

////////////////////////////////////////////////////////////////////////////////
/////                    Вывод формы оплаты                                /////
////////////////////////////////////////////////////////////////////////////////
function takePay(driverId){
    $('#fromDeposit').css('display', 'block');
    $('#typePay').val('1');
    $('#takePayDriverId').val(driverId);
    $('#takePayDriverName').val($('#listDriverFirstName'+driverId).html()
            +' '+$('#listDriverLastName'+driverId).html());
    $('#takePay').css('display', 'block')
}
////////////////////////////////////////////////////////////////////////////////
/////                    Добавления Оплаты От Водителя                     /////
////////////////////////////////////////////////////////////////////////////////
$('#takePayButton').click(function(){
    var typePay = $('#typePay').val();
    var driverId=$('#takePayDriverId').val();
    var sum=$('#takePayDriverSum').val();
    var sourcePay=$('#takePayDriverType').val();
    $.ajax({
        type: 'POST',
        url: 'AddPay',
        data: 'driverId='+driverId+'&sum='+sum+'&source='+sourcePay+'&typePay='+typePay,
        success: function(data){
            if(data=='OK'){
                $('#takePay').css('display', 'none')
                $('#takePayDriverId').val('');
                $('#takePayDriverSum').val('');
                $('#takePayDriverType').val('');
                listDriverShow();
            }
        }
    });
    
});
////////////////////////////////////////////////////////////////////////////////
/////                    Вывод формы изменения водителя                    /////
////////////////////////////////////////////////////////////////////////////////
function editDriver(driverId){
    $.ajax({
        type: 'POST',
        url: 'ED',
        data: 'driverId='+driverId,
        success: function(data){
            $('#formDriver').html(data);
        }
    });
}
////////////////////////////////////////////////////////////////////////////////
/////                    Изменение данных о водителе                       /////
////////////////////////////////////////////////////////////////////////////////
function editDriverSend(id){
    var limit = $('#driver_limit').val();
    var carnumber = $('#driver_carnumber').val();
    var callsign = $('#driver_callsign').val();
    var name = $('#driver_name').val();
    var lastname = $('#driver_lastname').val();
    var phone = $('#driver_phone_number').val();
    $.ajax({
        type: 'POST',
        url: 'EDS',
        data: 'driver_id='+id+'&lastname='+lastname+'&name='+name+'&callsign='
                +callsign+'&carnumber='+carnumber+'&limit='+limit
                +'&phone='+phone,
        success: function(data){
            if(data=='notpermit'){
                alert('У вас нет прав на изменение!');
                return;
            }
            else
                location.reload();
        }
    });
    listDriverShow();
}
function editDriverSendRP(id){
    var limit = $('#driver_limit').val();
    var carnumber = $('#driver_carnumber').val();
    var callsign = $('#driver_callsign').val();
    var name = $('#driver_name').val();
    var lastname = $('#driver_lastname').val();
    var phone = $('#driver_phone_number').val();
    var dayRent = $('#driver_phone_number').val();
    $.ajax({
        type: 'POST',
        url: 'EDSP',
        data: 'driver_id='+id+'&lastname='+lastname+'&name='+name+'&callsign='
                +callsign+'&carnumber='+carnumber+'&limit='+limit
                +'&phone='+phone+'&dayRent='+dayRent,
        success: function(data){
            if(data=='notpermit'){
                alert('У вас нет прав на изменение!');
                return;
            }
            else
                location.reload();
        }
    });
    listDriverShow();
}
function clearEditForm(){
    location.reload();
}
function getReport(driverId){
    window.open('DriverPayReport?driverId='+driverId);
}
function addDeposit(driverId){
    $('#fromDeposit').css('display', 'none');
    $('#typePay').val('3');
    $('#takePayDriverId').val(driverId);
    $('#takePayDriverName').val($('#listDriverFirstName'+driverId).html()
            +' '+$('#listDriverLastName'+driverId).html());
    $('#takePay').css('display', 'block')
}
setTimeout("clock()", 1000);

////////////////////////////////////////////////////////////////////////////////
/////                              Удаление водителе                       /////
////////////////////////////////////////////////////////////////////////////////
function delDriver(druverId){
    var qustion = 'Вы уверенны что хотите удалить водителя ?';
    if(confirm(qustion)){
        alert('Now i will remove driver!');
        $.ajax({
        type: 'POST',
        url: 'DD',
        data: 'driverId='+druverId,
        success: function(data){
        }
    });
    }
}