/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
////////////////////////////////////////////////////////////////////////////////
/////                    Добавление Водителя                               /////
////////////////////////////////////////////////////////////////////////////////
function addDriver(){   
    var limit = $('#driver_limit').val();
    var carId = $('#carId').val();
    var callsign = $('#driver_callsign').val();
    var name = $('#driver_name').val();
    var lastname = $('#driver_lastname').val();
    var phone = $('#driver_phone_number').val();
    var dayRent = $('#driver_day_rent').val();
    var schedule = $('#driver_schedule').val();
    var driverComment = $('#driverComment').val();
    $.ajax({
        type: 'POST',
        url: 'DA',
        data: 'lastname='+lastname+'&name='+name+'&callsign='+callsign
                +'&carId='+carId+'&limit='+limit+'&phone='+phone
                +'&dayRent='+dayRent+'&schedule='+schedule+'&driverComment='+driverComment,
        success: function(data){
            listDriverShow();
            closeModWind();
        }
    });
    listDriverShow();
}
function addDriverN(){   
    var msg   = $('#addDriverFormN').serialize();
    $.ajax({
        type: 'POST',
        url: 'AddDriverNew',
        data: msg,
        success: function(data){
            listDriverShow();
            closeModWind();
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
            $('#listDriverTabel').DataTable( {
                "paging":   false
            });
            docListener();
        }
    });
}
////////////////////////////////////////////////////////////////////////////////
/////                    Вывод формы оплаты                                /////
////////////////////////////////////////////////////////////////////////////////
function takePay(driverId){
    $.ajax({
        type: 'POST',
        url: 'takePay.jsp',
        success: function(data){
            $('#modal_form').html(data);
            $('#typePay').val('1');
            $('#takePayDriverId').val(driverId);
            $('#takePayDriverName').val($('#listDriverFirstName'+driverId).html()
                +' '+$('#listDriverLastName'+driverId).html());
            openModWind(200, 200);
        },
        error: function(msg){
            alert(msg);
        }
    });
}
////////////////////////////////////////////////////////////////////////////////
/////                    Добавления Оплаты От Водителя                     /////
////////////////////////////////////////////////////////////////////////////////
function takePaySend(){
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
                closeModWind();
            }
        }
    });
    
};
////////////////////////////////////////////////////////////////////////////////
/////                    Вывод формы изменения водителя                    /////
////////////////////////////////////////////////////////////////////////////////
function editDriver(driverId){
    $.ajax({
        type: 'POST',
        url: 'editDriverNew.jsp',
        data: 'driverId='+driverId,
        success: function(data){
            $('#modal_form').html(data);
            openModWind(470, 500);
        }
    });
}
////////////////////////////////////////////////////////////////////////////////
/////                    Изменение данных о водителе                       /////
////////////////////////////////////////////////////////////////////////////////
function editDriverSend(id){
    var limit = $('#driver_limit').val();
    var carnumber = $('#carId').val();
    var callsign = $('#driver_callsign').val();
    var name = $('#driver_name').val();
    var lastname = $('#driver_lastname').val();
    var phone = $('#driver_phone_number').val();
    var driverComment = $('driverComment').val();
    var yaId = $('#yaId').val();
    $.ajax({
        type: 'POST',
        url: 'EDS',
        data: 'driver_id='+id+'&lastname='+lastname+'&name='+name+'&callsign='
                +callsign+'&carId='+carnumber+'&limit='+limit
                +'&phone='+phone+'&driverComment='+driverComment
                +'&yaId='+yaId,
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
    var carId = $('#carId').val();
    var callsign = $('#driver_callsign').val();
    var name = $('#driver_name').val();
    var lastname = $('#driver_lastname').val();
    var phone = $('#driver_phone_number').val();
    var dayRent = $('#driver_day_rent').val();
    var schedule = $('#driver_schedule').val();
    var driverComment = $('#driverComment').val();
    var changetSchedule = 0;
    if($('#changeSchedule').prop('checked'))
        changetSchedule = 1;
    var yaId = $('#yaId').val();
    $.ajax({
        type: 'POST',
        url: 'EDSP',
        data: 'driver_id='+id+'&lastname='+lastname+'&name='+name+'&callsign='
                +callsign+'&carId='+carId+'&limit='+limit
                +'&phone='+phone+'&dayRent='+dayRent
                +'&schedule='+schedule+'&driverComment='+driverComment
                +'&yaId='+yaId+'&changetSchedule='+changetSchedule,
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
function editDriverSendNew(){
    var msg   = $('#addDriverFormN').serialize();
    $.ajax({
        type: 'POST',
        url: 'EDSPN',
        data: msg,
        success: function(data){
            if(data=='notpermit'){
                alert('У вас нет прав на изменение!');
                return;
            }
            else
                closeModWind();
        }
    });
    listDriverShow();
}
function clearEditForm(){
    location.reload();
}
function getReport(driverId){
    window.open('reports/driverReport.jsp?driverId='+driverId);
}
function addDeposit(driverId){
    /*$('#fromDeposit').css('display', 'none');
    $('#typePay').val('3');
    $('#takePayDriverId').val(driverId);
    $('#takePayDriverName').val($('#listDriverFirstName'+driverId).html()
            +' '+$('#listDriverLastName'+driverId).html());
    $('#takePay').css('display', 'block');*/
    $.ajax({
        type: 'POST',
        url: 'takePay.jsp',
        success: function(data){
            $('#modal_form').html(data);
            $('#typePay').val('3');
            $('#takePayDriverId').val(driverId);
            $('#takePayDriverName').val($('#listDriverFirstName'+driverId).html()
                +' '+$('#listDriverLastName'+driverId).html());
            openModWind(200, 200);
        },
        error: function(msg){
            alert(msg);
        }
    });
}
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
////////////////////////////////////////////////////////////////////////////////
/////                             Show Car list                            /////
////////////////////////////////////////////////////////////////////////////////
function getCarList(){
    $.ajax({
        type: 'POST',
        url: 'CL',
        success: function(data){
            $('#carList').html(data);
            $('#carListTabel').DataTable( {
                "paging":   false,
            });
        },
        error:function (msg){
            alert('Error in geting car list!'+msg);
        }
    });
}
$('#carListButton').click(function(){
    getCarList();
    $('.itemDisplay').css('display', 'none');
    $('.itemMenu').attr('disabled', false);
    $('#carListButton').attr('disabled', true);
    $('#carList').css('display', 'block');
});
////////////////////////////////////////////////////////////////////////////////
/////                             Show Driver list                         /////
////////////////////////////////////////////////////////////////////////////////
$('#driverListButton').click(function (){
    listDriverShow();
    $('.itemDisplay').css('display', 'none');
    $('.itemMenu').attr('disabled', false);
    $('#driverListButton').attr('disabled', true);
    $('#listDriver').css('display', 'block');
});
////////////////////////////////////////////////////////////////////////////////
/////                             Show prop list                           /////
////////////////////////////////////////////////////////////////////////////////
$('#mainProp').click(function (){
    listDriverShow();
    $('.itemDisplay').css('display', 'none');
    $('.itemMenu').attr('disabled', false);
    $('#mainProp').attr('disabled', true);
    $('#prop').css('display', 'block');
    $.ajax({
        type: 'POST',
        url: 'props/mainProps.jsp',
        success: function(data){
            $('#prop').html(data);
        },
        error:function (msg){
            alert('Error in geting car list!'+msg);
        }
    });
});
////////////////////////////////////////////////////////////////////////////////
/////                             Show add car form                        /////
////////////////////////////////////////////////////////////////////////////////
function addCar(){
    $.ajax({
        type: 'POST',
        url: 'carAddForm.jsp',
        success: function(data){
            $('#modal_form').html(data);
            openModWind();
        },
        error:function (msg){
            alert('Error in geting car list!'+msg);
        }
    });
}
////////////////////////////////////////////////////////////////////////////////
/////                             Show edit car form                       /////
////////////////////////////////////////////////////////////////////////////////
function editShow(carId){ // лoвим клик пo ссылки с id="go"
    //event.preventDefault(); // выключaем стaндaртную рoль элементa
    $.ajax({
        type: 'POST',
        url: 'CarData.jsp',
        data: 'id='+carId,
        success: function(data){
            $('#modal_form').html(data);
            openModWind();
        },
        error:function (msg){
            alert('Error in geting car list!'+msg);
        }
    });
}
/* Открытие мoдaльнoгo oкнa, тут делaем тo же сaмoе нo в oбрaтнoм пoрядке */
function openModWind(){
    $('#overlay').fadeIn(400, // снaчaлa плaвнo пoкaзывaем темную пoдлoжку
        function(){ // пoсле выпoлнения предъидущей aнимaции
            $('#modal_form') 
                    .css('display', 'block') // убирaем у мoдaльнoгo oкнa display: none;
                    .animate({opacity: 1}, 200); // плaвнo прибaвляем прoзрaчнoсть oднoвременнo сo съезжaнием вниз
    });
}
function openModWind(width, heigh){
    $('#overlay').fadeIn(400, // снaчaлa плaвнo пoкaзывaем темную пoдлoжку
        function(){ // пoсле выпoлнения предъидущей aнимaции
            $('#modal_form') 
                    .css('display', 'block') // убирaем у мoдaльнoгo oкнa display: none;
                    .animate({opacity: 1}, 200); // плaвнo прибaвляем прoзрaчнoсть oднoвременнo сo съезжaнием вниз
    });
    $('#modal_form').css('width', width);
    $('#modal_form').css('height', heigh);
    $('#modal_form').css('margin-left', width/-2+'px');
}
/* Зaкрытие мoдaльнoгo oкнa, тут делaем тo же сaмoе нo в oбрaтнoм пoрядке */
function closeModWind(){
    $('#modal_form')
        .animate({opacity: 0}, 200,  // плaвнo меняем прoзрaчнoсть нa 0 и oднoвременнo двигaем oкнo вверх
            function(){ // пoсле aнимaции
                    $(this).css('display', 'none'); // делaем ему display: none;
                    $('#overlay').fadeOut(400); // скрывaем пoдлoжку
            }
	);
}
function showAddDriverForm(){
    $.ajax({
        type: 'POST',
        url: 'addDriverNew.jsp',
        success: function(data){
            $('#modal_form').html(data);
            openModWind(470, 500);
        },
        error:function (msg){
            alert('Error in geting car list!'+msg);
        }
    });
}
////////////////////////////////////////////////////////////////////////////////
//////////////////////Docs menu ////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////

function docListener(){
    
    $(".docsCol").click(function(e){
	var driverId = $(this).attr('driverId');
        $('#menuBox').dialog("open");
        console.log('Showed'+driverId);
        $(".menuItem").click(function(){
            if($(this).attr('data')==="dogovor"){
                downloadDogowor(driverId);
                driverId = null;
            }
            if($(this).attr('data')==="aktvidachi"){
                doenloadAktvidachi(driverId);
                driverId = null;
            }
            if($(this).attr('data')==="aktpriema"){
                doenloadAktpriema(driverId);
                driverId = null;
            }
            if($(this).attr('data')==="putevoilist"){
                doenloadPutevoilist(driverId);
                driverId = null;
            }
        });
    });
    $("#closeMenu").click(function(){
        $('#menuBox').hide();
    });
}
function downloadDogowor(driverIdForDog){
    if(driverIdForDog==null)
        return;
    $.ajax({
        type: 'POST',
        url: 'DM',
        data: 'driverId='+driverIdForDog,
        success: function(data){
            window.open(data);
        },
        error:function (msg){
            alert('Error in geting dogovor!'+msg);
        }
    });
}