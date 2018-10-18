<html>
<head>
    <link rel="stylesheet" type="text/css" href="/resources/styles/style.css">
    <script defer src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script type="text/javascript" src="/resources/js/common.js"></script>
    <title>Login</title>
</head>
<body>

<div class="center-area">
    <div class="center">

        <div id="loginmethod">

            <div id="auth" onclick="chooseLoginMethod(1)">Sing in</div>
            <div id="register" onclick="chooseLoginMethod(2)">Sing up</div>
        </div>
        <div id="authblock">
            <form id="authform" name="authform" action="/login" method="post">
                <label for="user">Login</label>
                <input id="user" type="text" name="username">
                <br>
                <label for="password">Password</label>
                <input id="password" type="password" name="password">
                <br>
                <input id="block" type="submit" autofocus value="Войти">
            </form>
        </div>
        <div id="registerblock" style="display: none">
            <form id="registerform" name="registerform" action="/register" method="post" >
                <label for="email">E-mail</label>
                <input id="email" type="text" name="email">
                <br>
                <label for="username">Имя пользователя</label>
                <input id="username" type="text" name="username">
                <br>
                <label for="password">Пароль</label>
                <input id="password" type="password" name="password">
                <br>
                <input id="block" type="submit" autofocus value="Зарегистрироваться">
            </form>
        </div>
    </div>
</div>
</body>
<#if requestedURI == "/register">
<script language="JavaScript">chooseLoginMethod(2);</script>
</#if>
</html>