<%-- 
    Document   : aut1
    Created on : Oct 8, 2018, 10:36:02 PM
    Author     : Artem
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no">
    <title>RTS: Вход в систему</title>
    <link rel="stylesheet" href="css/app.css?id=97df80879d9fc021ddb2">
    <link rel="stylesheet" href="css/login-promo.css">
</head>

<body style="background: url(img/promo.jpg) no-repeat center center fixed;">
<section id="login">
    <div class="">
        <div class="">
            <div class="block-login">
                <form role="form" class="register-form" action="AT" method="post">
                    <!--input type="hidden" name="_token" value="1TnfSZJnszskC8caL6fwcNIExcNa8E77zgc2B7Pp"-->
                    <div class="row">
                        <div class="col-12">
                            <div class="logo"><!--img src="https://gettpartner.ru/theme/img/logo1.png" alt="RTS" width="100"/--><h1>RTS</h1></div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-12">
                            <div class="form-group">
                                <input type="text" name="login" id="email" class="form-control input-lg" value=""
                                       placeholder="Логин" tabindex="1" autocomplete="off">
                            </div>
                            <div class="form-group">
                                <input type="password" name="pass" class="form-control input-lg"
                                       placeholder="Пароль"
                                       value="" tabindex="1" autocomplete="off"/>
                            </div>
                            <div class="row">
                                <div class="col-xs-6 col-sm-6 col-md-6">
                                    <input type="submit" value="Войти" class="btn btn-gt btn-block btn-small" tabindex="7">
							</div>
                                
                            </div>
                        </div>
                    </div>
                </form>
            </div>
        </div>

    </div>
</section>
</body>
</html>
