<%-- 
    Document   : mainProps
    Created on : May 3, 2018, 8:56:35 PM
    Author     : Artem
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<div>
    Основные настройки
    <div id="tabs">
        <div>
           <ul class="tabsNav">
            <li><a href="#tabs-1">Основные</a></li>
            <li><a href="#tabs-2">Proin dolor</a></li>
            <li><a href="#tabs-3">Пользователи</a></li>
          </ul> 
        </div>
    <div id="tabs-1">
      <div> 
          <h3>Организация</h3>
          <form>
              <ul >
                <li id="li_1" >
                    <span>
			<input id="element_1_1" name= "companyName" class="element text" maxlength="255" size="14" value=""/>
			<label>Название</label>
                    </span>
                    <span>
                        <input id="element_1_2" name= "companyINN" class="element text" maxlength="255" size="14" value=""/>
                        <label>ИНН</label>
                    </span> 
                    <span>
                        <input id="element_1_2" name= "companyKPP" class="element text" maxlength="255" size="14" value=""/>
                        <label>КПП</label>
                    </span> 
		</li>
              </ul>
          </form>
      </div>
    </div>
    <div id="tabs-2">
      <div>Morbi tincidunt, dui sit amet facilisis feugiat, odio metus gravida ante, ut pharetra massa metus id nunc. Duis scelerisque molestie turpis. Sed fringilla, massa eget luctus malesuada, metus eros molestie lectus, ut tempus eros massa ut dolor. Aenean aliquet fringilla sem. Suspendisse sed ligula in ligula suscipit aliquam. Praesent in eros vestibulum mi adipiscing adipiscing. Morbi facilisis. Curabitur ornare consequat nunc. Aenean vel metus. Ut posuere viverra nulla. Aliquam erat volutpat. Pellentesque convallis. Maecenas feugiat, tellus pellentesque pretium posuere, felis lorem euismod felis, eu ornare leo nisi vel felis. Mauris consectetur tortor et purus.</div>
    </div>
    <div id="tabs-3">
      <div>Mauris eleifend est et turpis. Duis id erat. Suspendisse potenti. Aliquam vulputate, pede vel vehicula accumsan, mi neque rutrum erat, eu congue orci lorem eget lorem. Vestibulum non ante. Class aptent taciti sociosqu ad litora torquent per conubia nostra, per inceptos himenaeos. Fusce sodales. Quisque eu urna vel enim commodo pellentesque. Praesent eu risus hendrerit ligula tempus pretium. Curabitur lorem enim, pretium nec, feugiat nec, luctus a, lacus.</div>
      <div>Duis cursus. Maecenas ligula eros, blandit nec, pharetra at, semper at, magna. Nullam ac lacus. Nulla facilisi. Praesent viverra justo vitae neque. Praesent blandit adipiscing velit. Suspendisse potenti. Donec mattis, pede vel pharetra blandit, magna ligula faucibus eros, id euismod lacus dolor eget odio. Nam scelerisque. Donec non libero sed nulla mattis commodo. Ut sagittis. Donec nisi lectus, feugiat porttitor, tempor ac, tempor vitae, pede. Aenean vehicula velit eu tellus interdum rutrum. Maecenas commodo. Pellentesque nec elit. Fusce in lacus. Vivamus a libero vitae lectus hendrerit hendrerit.</div>
    </div>
  </div>
</div>
<script>
  $( function() {
    $( "#tabs" ).tabs();
  } );
</script>