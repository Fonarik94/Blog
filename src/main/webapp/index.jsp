<%@ page import="java.text.DateFormat" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Date" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/styles/style.css">
    <title>Go away</title>
</head>
<body>
<div class="text-area">
    <h1>Сайт в разработке</h1>
    <p>Не знаю, когда сделаю. Уходи.</p>
    <p><% DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();%>
    <%=dateFormat.format(date)%></p>

</div>
<div class="image">
    <img src="${pageContext.request.contextPath}/resources/images/cat.png" width="456" height="512" alt="Fuck you"/>
</div>
<div class="footer">
    <p>fonarik94@gmail.com</p>
</div>
</body>
</html>