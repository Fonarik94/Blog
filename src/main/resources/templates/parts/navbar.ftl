<#include "security.ftl">
<nav class="navbar navbar-expand-md navbar-dark bg-secondary">
    <div class="navbar-header">
        <a class="navbar-brand" href="/">Блог</a>
    </div>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent"
            aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>

    <div class="collapse navbar-collapse" id="navbarSupportedContent">
        <ul class="navbar-nav mr-auto">
            <li class="nav-item">
                <a class="nav-link" href="/about">О проекте</a>
            </li>
                <#if admin>
                    <li class="nav-item">
                        <a class="nav-link" href="/administration/wol">WOL</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="/administration/postwriter">Редактор</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="/administration/postwriter/addpost">Добавить</a>
                    </li>
                </#if>
        </ul>
        <div class="navbar-right">
        <#if known>
            <button class="btn btn-outline-danger" onclick="logout()">Выйти</button>
        <#else>
            <a class="btn btn-outline-success" href="/login">Войти</a>
        </#if>
            <a class="ml-3" href="https://t.me/fonark94" target="_blank" rel="noopener">
                <img src="/resources/images/telegram.png" width=30px height=30px alt="Telegram">
            </a>
            <a class="ml-3" href="https://twitter.com/Fonarik94" target="_blank" rel="noopener">
                <img src="/resources/images/twitter.png" width=30px height=30px alt="Twitter">
            </a>
            <a class="ml-3" href="https://www.instagram.com/fonarik94" target="_blank" rel="noopener">
                <img src="/resources/images/instagram.png" width=30px height=30px alt="Instagram">
            </a>
            <a class="ml-3" href="https://github.com/Fonarik94" target="_blank" rel="noopener">
                <img src="/resources/images/github.png" width=30px height=30px alt="Github">
            </a>
        </div>
    </div>
</nav>