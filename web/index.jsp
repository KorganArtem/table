<%-- 
    Document   : index
    Created on : 19.12.2017, 15:31:57
    Author     : korgan
--%>

<%@ page language="java" contentType="text/html; charset=utf-8"%>
<!DOCTYPE html>
<!doctype html>
<%@ page import="ru.leasicar.authorization.*" %>
<% 
    AccessControl ac = new AccessControl();
    if(!ac.isLogIn(request.getSession().getId())){%>
        <meta http-equiv="refresh" content="1; URL=auth.jsp" />
        <%
        return ; 
    }
%>
<html lang="us">
<head>
	<meta charset="utf-8">
        <!-- index.jsp -->
        
	<title>EyePrint v1.0.9</title>
		<link href="css/jquery-ui.css" rel="stylesheet">
		<link href="css/project.css" rel="stylesheet">
		<link href="css/grid.css" rel="stylesheet">
		<link href="css/jquery.dataTables.min.css" rel="stylesheet">
		<link href="css/scroller.dataTables.min.css" rel="stylesheet">

		<script src="js/jquery-1.10.2.js"></script>
		<script src="js/jquery-ui.js"></script>	
		<script src="js/project.js"></script>	
		<script src='/js/grid.js'></script>
		<script src="js/jquery.dataTables.min.js"></script>	
		<script src="js/dataTables.scroller.min.js"></script>	
		<script src="js/dataTables.select.min.js"></script>	
		<!--script src="js/printThis/printThis.js"></script-->	
    <link class="include" rel="stylesheet" type="text/css" href="css/jquery.jqplot.css" />
    <script  type="text/javascript" src="js/jqplot/jquery.jqplot.js"></script>
    <script language="javascript" type="text/javascript" src="js/jqplot/jqplot.canvasTextRenderer.js"></script>
    <script language="javascript" type="text/javascript" src="js/jqplot/jqplot.canvasAxisTickRenderer.js"></script>
    <script language="javascript" type="text/javascript" src="js/jqplot/jqplot.dateAxisRenderer.js"></script>
    <script language="javascript" type="text/javascript" src="js/jqplot/jqplot.cursor.js"></script>
    <script language="javascript" type="text/javascript" src="js/jqplot/jqplot.highlighter.js"></script>
    <script language="javascript" type="text/javascript" src="js/jqplot/jqplot.pyramidAxisRenderer.js"></script>
    <script language="javascript" type="text/javascript" src="js/jqplot/jqplot.pyramidGridRenderer.js"></script>     <script type="text/javascript" src="js/jqplot/jqplot.json2.js"></script>

    <link rel="stylesheet" href="css/font/css/font-awesome.min.css">
	<link rel='stylesheet' href='/css/grid.css'>
	<script>
            function cubes(a) {
                if ($('DIV.elem').hasClass('elem') || a==1) {
                    $('#blank').show();
                    $.get( "DCH", { a : 1 },function( d ) {
                        $('#output').html(d);
                        $( '.progress' ).each(function() {
                            var arg = $( this ).data('value');
                            $( this ).progressbar({ value: arg});
                        });
                        $( '.progress' ).each(function() {
                            var PB=$( this );
                            var col = PB.data('color');
                            var low = PB.data('lowtoner');
                            if (low==true) {
                                PB.css('border-color','red');
                            } else {
                                PB.css('border-color','#999');
                            }
                            PB.children( '.ui-progressbar-value' ).css('background-color',col);
                        });
                        $('.elem').click(function() {
                            $("#example tbody").trigger("tr:eq(0) td.sorting_1:eq(0)", "click", function() {
                            });
                        });
                        $('#blank').hide();
                    });

                }
            }
            function showDetales(idkfa) {


            }
            $(document).ready(function(){
                cubes();
                $('#getGrid').click(function(){
                    cubes(1);
                });
                $('#getHelp').click(function(){
                    getHelp();
                });
            });
            function logout(){
                $.ajax({
                    type: 'POST',
                    url: 'LO',
                    success: function(data){
                        if(data.trim()==='success'){
                            location.href='/';
                        }
                        else{ 
                            alert('Logout Filed!');
                        }
                    }
                });
            }
        </script>
    </head>
<body style="visibility : hidden;" id="BDY" onResize="window.location.reload(true);">
<div class="ui-widget-overlay ui-front" id="blank"><img src="css/images/loader.gif" id="progress"></div>					
 <div id="container">
                                <div id="listH">Устройства</div>
	<div id="list">
	
                <table id="example" class="display nowrap" cellspacing="0" width="100%">
                    <thead>
                        <tr>
                           <th style="background-color:lightgray;"></th>
                           <th style="background-color:lightgray;"></th>
                           <th style="background-color:lightgray;"></th>
                        </tr>
                    </thead>
                </table>
												
	</div>
	
	<div id="manipulate">
		<div id="filterH">Фильтры</div>
		<div id="reportH">Отчеты
                    <img src='images/logout-icon.png' class='helper' id='goOut' style="background-color: grey;" onClick="logout()">
                    <a href="/prop.jsp"><img src='images/preferences.png' class='helper' id='goToSet'></a>
                    <img src='images/help.png' class='helper' id='getHelp'>
                    <img src='images/grid.png' class='helper' id='getGrid'>
                    <img src='images/logout-icon.png' class='helper' id='goOut' onClick="logout()">
                </div>
		


		
								
	<table border=0 cellpadding=0 cellspacing=0 width=100% style="border: none;min-width:500px;background : none;">
	<tr><th style="border: none;"></th><th style="border: none;"></th></tr>
	<tr><td width=50% style="min-width:250px;background : none;" valign="top">
							  <!--div id="OnOff" style="margin-bottom:4px;">
								<input type="radio" id="On" name="OnOff"><label for="On">On-Line</label>
								<input type="radio" id="Off" name="OnOff"><label for="Off">Off-Line</label>
								<input type="radio" id="All" checked="checked" name="OnOff"><label for="All">Все</label>
							  </div-->

							<select name="vendor" id="vendor" data-role="select"  title='фильтр - по Вендору'>
                                                            <option selected='selected' >вендор</option>
							</select>
							<select name="colors" id="colors" data-role="select" title='фильтр - по цветности'>
                                                            <option selected='selected' >цветность</option>
							</select><br>
							<select name="tech" id="tech" data-role="select" title='фильтр - по технологии печати'>
                                                            <option selected='selected' >технология</option>
							</select>					
							<select name="format" id="format" data-role="select"  title='фильтр - по формату'>
                                                            <option selected='selected' >формат</option>
							</select>					<br>
							<select name="model" id="model" data-role="select" title='фильтр - по модели устройства'>
                                                            <option selected='selected' >модель</option>
							</select>
							<select name="active" id="active" data-role="select"  title='фильтр - включено ли устройство'>
                                                            <option selected='selected' >в сети</option>
							</select>

	</td>
	
	<td width=50% style="min-width:250px;background : none;" id='reports'>
								
				
	<input id="search" style="margin-bottom:4px;" placeholder="поиск"><br>
							<select name="dates" id="dates" data-role="select">
                                                            <!--option selected='selected' value='all' DISABLED>даты</option>
                                                            <option value='1' title='Даты обнаружения устройства' DISABLED>был обнаружен</option>
                                                            <option value='2' title='Даты последней активности устройства' DISABLED>последний раз замечен</option>
                                                            <option value='3' title='Даты, когда устройство было отключено' DISABLED>был выключен</option-->
                                                            <option value='4' title='Даты, вывод данных по аппарату'>данные по аппарату</option>
                                                            <option value='5' title='Даты, отчет по кликам' SELECTED>отчет по кликам</option>
							</select>&nbsp;<sup>С</sup>&nbsp;<input id="from" class="ui-button ui-widget ui-state-default ui-corner-all" title='дата - не ранее'>&nbsp;<sup>по</sup>&nbsp;<input id="to" class="ui-button ui-widget ui-state-default ui-corner-all" title='дата - не позже'>
                                                        <input type='button' value='->' id='get' title='отфильтровать по дате'>        <!--input type='button' value='<>' id='filtered'><input type='button' value='><' id='nonfiltered'>ЧекБокс - выбор по всем аппаратам или по текущей выборке слева?<br>
	По кликам - по месяцам, по неделям, за произвольный период<br>
        По расходке - по месяцам, по неделям, за произвольный период<br>
        Подозрительная активность/простой - пиковые нагрузки, работа в неурочное время, слишком долгий простой<br>
	Сколько были включены по времени - по месяцам, по неделям, за произвольный период<br>
	Расчет КПД - количество кликов ко времени работы учитывая заявленную производительность<br-->
        <br>
	<input type='button' value='Обновить список устройств' id='update' class="ui-button ui-widget ui-state-default ui-corner-all"><br>
	<span id="tbl_length" style="visibility:hidden;"></span>
	</td></tr>
	</table>
	</div>
	<div id="output">
        
	</div>
	<div id="clear"></div>
	
</div>

<input type='hidden' id='source' value=''>
<input type='hidden' id='source_param' value=''>
<input type='hidden' id='vendor_V' value='00'>
<input type='hidden' id='model_V' value='00'>
<input type='hidden' id='format_V' value='00'>
<input type='hidden' id='tech_V' value='00'>
<input type='hidden' id='colors_V' value='00'>
<input type='hidden' id='active_V' value='00'>

</body>
</html>