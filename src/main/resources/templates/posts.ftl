<#import "parts/common.ftl" as c>
<@c.commonTemplate>
    <#list publishedPosts as post>
    <div class="card m-3 shadow">
        <a href="post/${post.id}">
            <h4 class="card-header">${post.header}</h4>
        </a>
        <div class="card-body">
            <h6 class="card-subtitle text-muted mb-2">${post.getPublicationDateAsString()}</h6>
            <p class="card-text">${post.text[0..500]}...</p>
        </div>
    </div>
    </#list>
</@c.commonTemplate>