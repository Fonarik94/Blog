<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/styles/style.css">
    <title>Wake-on-lan</title>
</head>
<body>
<h1> Wake-on-lan </h1>
<div class="WOLarea">
    <form action="wol" method="post">
        MAC: <input type="text" name="MAC" value="BC-AE-C5-74-0E-BB">
        <br><br>
        <button type="submit"  autofocus="hit">Разбудить!</button>
    </form>
</div>
</body>
</html>
