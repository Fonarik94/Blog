<html>
    <head>
        <meta charset="UTF-8">
        <link rel="stylesheet" type="text/css" href="/resources/styles/style.css">
        <script defer src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
        <script defer type="text/javascript" src="/resources/js/common.js"></script>
        <script defer src="/resources/js/administration.js"></script>
        <title>Admin</title>
    </head>
    <body>
        <div id="topbar">
            <div id="logo">ADMINISTRATION</div>
        </div>
        <div id="menubar">
            <div id="menu">
                <a class="item" href="/">Главная</a>
                <a class="item" href="/administration">Wake-on-lan</a>
                <a class="item" href="/administration/postwriter">Редактор постов</a>
                    <#if requestedPage == "/redactor.ftl">
                        <a class="item" href="/administration/postwriter/addpost">Добавить</a>
                        <a class="item" href="/administration/postwriter/edit?editbyid=1">Edit aboutpage</a>
                    </#if>
                <a class="item" href="/logout" style="background-color: #f09b9b">Выйти</a>
            </div>
        </div>
        <div id="content">
            <#include "${requestedPage}">
        </div>
    </body>
</html>