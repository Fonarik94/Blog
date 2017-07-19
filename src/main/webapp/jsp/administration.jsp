<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/styles/style.css">
    <title>Admin</title>
</head>
<body>
<div id="sidebar">
    <div id="logo">ADMINISTRATION</div>
    <div id="menubar">
        <div id="menu">
            <a class="item" href="/">Главная</a>
            <a class="item" href="/administration/wol">Wake-on-lan</a>
            <a class="item" href="/administration/postWriter">Редактор постов</a>
        </div>
    </div>
</div>
<div id="content">
    <jsp:include page="${requestedPage}"/>
</div>
</body>
</html>