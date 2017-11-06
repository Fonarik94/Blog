<html>
<head>
    <link rel="stylesheet" type="text/css" href="/resources/styles/style.css">
    <title>Login</title>
</head>
<body>
<div class="center-area">
    <div class="center">
        <form id="authform" name="authform"  action="@{/login}" method="post">
            <h2>Авторизация</h2><br>
            <label for="user">Login</label>
            <input id="user" type="text" name="username"><br>
            <label for="password">Password</label>
            <input id="password" type="password" name="password"><br>
            <input id="block" type="submit" autofocus value="Войти">
        </form>
    </div>
</div>
</body>
</html>