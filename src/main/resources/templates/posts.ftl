<#import "parts/common.ftl" as c>
<@c.commonTemplate>
<div class="col-md-8 offset-md-2 p-0">
    <div class="card-deck">
    <#list publishedPosts as post>
        <div class="card m-md-2 mt-md-3 shadow">
            <a href="post/${post.id}">
                <h4 class="card-header">${post.header}</h4>
            </a>
            <small class="card-text text-muted p-1 pl-md-2">${post.getPublicationDateAsString()}</small>
            <div class="card-body p-1 p-md-2">
                <p class="card-text">
                <#if post.text?length gte 300>
                    ${post.text[0..300]}...
                <#else>
                    ${post.text}
                </#if>
                </p>
            </div>
        </div>
    </#list>
    </div>
</div>
</@c.commonTemplate>