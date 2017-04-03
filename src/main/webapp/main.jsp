<%@page contentType="text/html;charset=UTF-8"%>
<html>
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/styles/style.css">
    <title>Go away</title>
</head>
<body>
<div id="sidebar">
    <div id="logo">Logo</div>
    <div id="menu">
        <a class="item" href="main">Главная</a>
        <a class="item" href="blog">Блог</a>
        <a class="item" href="about">О проекте</a>
        <a class="item" href="login">Войти</a>
    </div>
    <div id="social"></div>
</div>
<div class="content">
    <% for (int i=0; i<10; i++){
        out.print("                <div class=\"post\">\n" +
                "                    <p>\n" +
                "                            Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod\n" +
                "                    tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam,\n" +
                "                            quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo\n" +
                "                    consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse\n" +
                "                    cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non\n" +
                "                    proident, sunt in culpa qui officia deserunt mollit anim id est laborum.Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod\n" +
                "                    tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam,\n" +
                "                            quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo\n" +
                "                    consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse\n" +
                "                    cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non\n" +
                "                    proident, sunt in culpa qui officia deserunt mollit anim id est laborum.\n" +
                "                    </p>\n" +
                "                </div>");
    }%>
</div>
</body>
</html>