<html>
        <head>
            <meta charset="UTF-8">
            <link rel="stylesheet" type="text/css" href="/resources/styles/style.css">
            <script src="/resources/js/jquery/2.2.4/jquery.min.js"></script>
            <script type="text/javascript" src="/resources/js/common.js"></script>
            <script src="/resources/js/common.js"></script>
            <title>Admin</title>
        </head>
    <body>
        <div id="topbar">
            <div id="logo">ADMINISTRATION</div>
        <div id="menubar">
        <div id="menu">
            <a class="item" href="/">Главная</a>
            <a class="item" href="/administration">Wake-on-lan</a>
            <a class="item" href="/administration/postwriter">Редактор постов</a>
            <#if requestedPage == "/redactor.ftl">
                 <a class="item" href="/administration/postwriter/addpost">Добавить</a>
                 <a class="item" href="/administration/postwriter/edit?editbyid=1">Редактировать aboutpage</a>
              </#if>
        </div>
        </div>
        </div>
        <div id="content">
             <#include "${requestedPage}">
        </div>
    </body>
</html>