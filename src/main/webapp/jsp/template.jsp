<%@page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <meta charset="UTF-8" name="theme-color" content="#8FF7A7">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/styles/style.css">
    <title>Go away</title>
</head>
<body>
<div id="sidebar">
    <div id="logo">¯\_(ツ)_/¯</div>
    <div id="menubar">
        <div id="menu">
            <%--<a class="item" href="/">Главная</a>--%>
            <a class="item" href="/blog">Блог</a>
            <a class="item" href="/about">О проекте</a>
            <%--<a class="item" href="/login">Войти</a>--%>
        </div>
        <div id="social">
            <div class="socialbutton"><a href="https://twitter.com/Fonarik94" target="_blank"><img
                    src="${pageContext.request.contextPath}/resources/images/twitter.png" width=36px height=36px alt="Twitter"></a></div>
            <div class="socialbutton"><a href="https://www.instagram.com/fonarik94" target="_blank"><img
                    src="${pageContext.request.contextPath}/resources/images/instagram.png" width=36px height=36px alt="Instagram"></a></div>
            <div class="socialbutton"><a href="https://github.com/Fonarik94" target="_blank"><img
                    src="${pageContext.request.contextPath}/resources/images/github.png" width=36px height=36px alt="Github"></a></div>
        </div>
    </div>
</div>
<div id="content">
    <jsp:include page="${requestedPage}"/>
</div>
</body>
</html>