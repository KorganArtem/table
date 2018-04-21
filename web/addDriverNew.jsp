<%-- 
    Document   : addDriverNew
    Created on : 17.04.2018, 20:10:58
    Author     : korgan
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Untitled Form</title>
<link rel="stylesheet" type="text/css" href="css/view.css" media="all">
<script type="text/javascript" src="js/view.js"></script>

</head>
<body id="main_body" >
    <div id="form_container">
        <form id="form_6362" class="appnitro"  method="post" action="">
            <div class="form_description">
                <h2>Добавление водителя</h2>
            </div>	
            <ul >
                <li id="li_1" >
                    <span>
			<input id="element_1_1" name= "element_1_1" class="element text" maxlength="255" size="14" value=""/>
			<label>Фамилия</label>
                    </span>
                    <span>
                        <input id="element_1_2" name= "element_1_2" class="element text" maxlength="255" size="14" value=""/>
                        <label>Имя</label>
                    </span> 
                    <span>
                        <input id="element_1_2" name= "element_1_2" class="element text" maxlength="255" size="14" value=""/>
                        <label>Отчество</label>
                    </span> 
		</li>
                <li id="li_1" >
                    <span>
			<input id="element_1_1" name= "element_1_1" class="element text" maxlength="255" size="14" value=""/>
			<label>Фамилия</label>
                    </span>
                    <span>
                        <input id="element_1_2" name= "element_1_2" class="element text" maxlength="255" size="14" value=""/>
                        <label>Имя</label>
                    </span> 
                    <span>
                        <input id="element_1_2" name= "element_1_2" class="element text" maxlength="255" size="14" value=""/>
                        <label>Отчество</label>
                    </span> 
		</li>
                <li id="li_1" >
                    <span>
			<input type="date" id="element_1_1" name= "element_1_1" class="element date" maxlength="255" size="14" value=""/>
			<label>Дата рождения</label>
                    </span>
                    <span>
                        <input type="date" id="element_1_2" name= "element_1_2" class="element date" maxlength="255" size="14" value=""/>
                        <label>Дата принятия</label>
                    </span> 
                    <span>
                        <input type="date" id="element_1_2" name= "element_1_2" class="element date" maxlength="255" size="14" value=""/>
                        <label>Дата увольнения</label>
                    </span> 
		</li>
                <li id="li_1" >
                    <label class="description" for="element_3">Паспорт </label>
                    <span>
			<input id="element_1_1" name= "element_1_1" class="element text" maxlength="255" size="14" value=""/>
			<label>Серия номер</label>
                    </span>
                    <span>
                        <input id="element_1_2" name= "element_1_2" class="element text" maxlength="255" size="14" value=""/>
                        <label>Дата выдачи</label>
                    </span> 
                    <span>
                        <input id="element_1_2" name= "element_1_2" class="element text" maxlength="255" size="14" value=""/>
                        <label>Email</label>
                    </span> 
		</li>			
                <li id="li_3" >
                    <label class="description" for="element_3">Адрес </label>
                    <div class="left">
                            <input id="element_3_3" name="element_3_3" class="element text medium" value="" type="text">
                            <label for="element_3_3">Страна</label>
                    </div>
                    <div class="right">
                            <input id="element_3_4" name="element_3_4" class="element text medium" value="" type="text">
                            <label for="element_3_4">Область</label>
                    </div>
                    <div class="left">
                            <input id="element_3_5" name="element_3_5" class="element text medium" maxlength="15" value="" type="text">
                            <label for="element_3_5">Город</label>
                    </div>
                    <div class="right">
                            <select class="element select medium" id="element_3_6" name="element_3_6"> </select>
                        <label for="element_3_6">Улица</label>
                    </div> 
                    <span>
			<input id="element_1_1" name= "element_1_1" class="element text" maxlength="255" size="14" value=""/>
			<label>Дом</label>
                    </span>
                    <span>
                        <input id="element_1_2" name= "element_1_2" class="element text" maxlength="255" size="14" value=""/>
                        <label>Корпус/Строение</label>
                    </span> 
                    <span>
                        <input id="element_1_2" name= "element_1_2" class="element text" maxlength="255" size="14" value=""/>
                        <label>Квартира</label>
                    </span> 
		</li>		
                <li id="li_5" >
                    <label class="description" for="element_5">Аренда </label>
                    <span>
                        <select class="element  " style="width: 100px;" id="element_5" name="element_5"> 
                            <option value="" selected="selected"></option>
                            <option value="1" >First option</option>
                            <option value="2" >Second option</option>
                            <option value="3" >Third option</option>
                        </select>
                        <label>Машина</label>
                    </span> 
                    <span>
                        <select class="element  " style="width: 100px;" id="element_5" name="element_5"> 
                            <option value="" selected="selected"></option>
                            <option value="1" >First option</option>
                            <option value="2" >Second option</option>
                            <option value="3" >Third option</option>
                        </select>
                        <label>График</label>
                    </span> 
                    <span>
                        <input id="element_1_2" name= "element_1_2" class="element text" maxlength="255" size="9" value=""/>
                        <label>Аренда</label>
                    </span> 
                    <span>
                        <input id="element_1_2" name= "element_1_2" class="element text" maxlength="255" size="8" value=""/>
                        <label>Лимит</label>
                    </span> 
		</li>		
                <li id="li_4" >
                    <label class="description" for="element_4">Paragraph </label>
                    <div>
			<textarea id="element_4" name="element_4" class="element textarea medium"></textarea> 
                    </div> 
		</li>
                <li class="buttons">
                    <input type="hidden" name="form_id" value="6362" />
                    <input id="saveForm" class="button_text" type="submit" name="submit" value="Submit" />
		</li>
            </ul>
	</form>	
    </div>
    <img id="bottom" src="bottom.png" alt="">
</body>
</html>