<%@ page contentType="text/html;charset=UTF-8"%>
<html>
<head>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/styles/style.css">
    <title>Login</title>
</head>
<body>
<div class="center-area">
    <div class="center">
        <form id="authform" name="authform"  action="j_security_check" method="post">
            <h2>Авторизация</h2><br>
            Login <input type="Login" name="j_username"><br>
            Pass <input type="password" name="j_password"><br>
            <input id="block" type="submit" autofocus="hit" value="Войти">
        </form>
    </div>
</div>
</body>
</html>