<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/styles/style.css">
    <script src="${pageContext.request.contextPath}/resources/js/jquery/2.2.4/jquery.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/common.js"></script>
    <script src="${pageContext.request.contextPath}/resources/js/common.js"></script>
    <title>Admin</title>
</head>
<body>
<div id="topbar">
    <div id="logo">ADMINISTRATION</div>
    <div id="menubar">
        <div id="menu">
            <a class="item" href="${pageContext.request.contextPath}/">Главная</a>
            <a class="item" href="${pageContext.request.contextPath}/administration">Wake-on-lan</a>
            <a class="item" href="${pageContext.request.contextPath}/administration/postwriter">Редактор постов</a>
            <c:if test="${requestedPage == '/jsp/redactor.jsp'}">
                <a class="item" href="${pageContext.request.contextPath}/administration/postwriter/addpost">Добавить</a>
                <a class="item" href="${pageContext.request.contextPath}/administration/postwriter/edit?editbyid=1">Редактировать aboutpage</a>
            </c:if>
        </div>
    </div>
</div>
<div id="content">
    <jsp:include page="${requestedPage}"/>
</div>
</body>
</html>