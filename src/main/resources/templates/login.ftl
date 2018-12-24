<html class="mdl-js">
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css"
          integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">
    <link rel="stylesheet" href="https://getbootstrap.com/docs/4.1/examples/sign-in/signin.css">
    <title>Login</title>
</head>
<body class="text-center">
<form class="form-signin" name="authform" action="/login" method="post">
    <h1 class="h3 mb-3 font-weight-normal">Please sign in</h1>
    <input class="form-control" placeholder="Login" type="text" name="username">
    <br>
    <input class="form-control" placeholder="Password" type="password" name="password">
    <br>
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
    <button class="btn btn-lg btn-primary btn-block" autofocus type="submit">Sign in</button>
</form>

</body>
</html>