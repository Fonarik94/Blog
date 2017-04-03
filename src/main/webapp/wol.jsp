<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/styles/style.css">
        <title>Wake-on-lan</title>
    </head>
    <body>
        <div class="center-area">
            <form id="wol" name="wol" method="post">
                <h2> Wake-on-lan </h2>
                MAC: <input type="text" name="MAC" value="BC-AE-C5-74-0E-BB"><br>
                <button id="block" type="submit" autofocus="hit">Разбудить</button>
            </form>
        </div>
    </body>
</html>
