<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" %>
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
            <a class="item" href="${pageContext.request.contextPath}/">Главная</a>
            <a class="item" href="${pageContext.request.contextPath}/administration/wol">Wake-on-lan</a>
            <a class="item" href="${pageContext.request.contextPath}/administration/postWriter">Редактор постов</a>
            <c:if test="${requestedPage == '/jsp/redactor.jsp'}">
                <a class="item" href="postWriter/addPost">Добавить</a>
                <a class="item" href="postWriter/edit?editById=1">Редактировать aboutpage</a>
            </c:if>
        </div>
    </div>
</div>
<div id="content">
    <jsp:include page="${requestedPage}"/>
</div>
</body>
</html>