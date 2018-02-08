<%-- 
    Document   : start-page
    Created on : 19.12.2017, 15:19:07
    Author     : korgan
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>EyePrint</title>
        <meta charset="UTF-8">
        <meta http-equiv="Cache-Control" content="no-cache"/>
        <meta http-equiv='Cache-Control' content='private'/>
        <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
        <link rel="stylesheet" type="text/css" media="screen" href="css/auth.css"/>
        <script src="js/jquery-1.10.2.js"></script>
    </head>
    <body>
        <div class="logconteiner">
            <div class='logForm'>
                <form>
                    <label>Имя пользователя</label><input class='inp' type='text' id='login'/><br>
                    <label>Пароль</label><input class='inp' type='password' id='pass'/>
                    <input class='inp' type='button' value='Вход' id='logIn'/>
                </form>
            </div>       
        </div>
        <script>
            $('#logIn').click(function(){
                var login = $('#login').val();
                var password = $('#pass').val();
                $.ajax({
                    type: 'POST',
                    url: 'AT',
                    data: 'login='+login+'&pass='+password,
                    success: function(data){
                        if(data.trim()==='1'){
                            location.href='index.jsp';
                        }
                        else{ 
                            $('#pass').css('background-color', 'red');
                            alert('Login Filed!');
                        }
                    }
                });
            });
            
        </script>
    </body>
</html>