<!DOCTYPE html>
<%@page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html lang="ru">
<head>
    <meta charset="UTF-8" name="theme-color" content="#8FF7A7">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/styles/style.css">
    <title>IT blog</title>

    <script>
        (function(i,s,o,g,r,a,m){i['GoogleAnalyticsObject']=r;i[r]=i[r]||function(){
            (i[r].q=i[r].q||[]).push(arguments)},i[r].l=1*new Date();a=s.createElement(o),
            m=s.getElementsByTagName(o)[0];a.async=1;a.src=g;m.parentNode.insertBefore(a,m)
        })(window,document,'script','https://www.google-analytics.com/analytics.js','ga');

        ga('create', 'UA-104202172-1', 'auto');
        ga('send', 'pageview');

    </script>

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
            <div class="socialbutton"><a href="https://t.me/fonark94" target="_blank" rel="noopener">
                <img src="${pageContext.request.contextPath}/resources/images/telegram.png" width=36px height=36px alt="Telegram"></a></div>
            <div class="socialbutton"><a href="https://twitter.com/Fonarik94" target="_blank" rel="noopener">
                <img src="${pageContext.request.contextPath}/resources/images/twitter.png" width=36px height=36px alt="Twitter"></a></div>
            <div class="socialbutton"><a href="https://www.instagram.com/fonarik94" target="_blank" rel="noopener">
                <img src="${pageContext.request.contextPath}/resources/images/instagram.png" width=36px height=36px alt="Instagram"></a></div>
            <div class="socialbutton"><a href="https://github.com/Fonarik94" target="_blank" rel="noopener">
                <img src="${pageContext.request.contextPath}/resources/images/github.png" width=36px height=36px alt="Github"></a></div>
        </div>
    </div>
</div>
<div id="content">
    <jsp:include page="${requestedPage}"/>
</div>
</body>
</html>