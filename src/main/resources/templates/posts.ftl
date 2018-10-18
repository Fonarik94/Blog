<#import "parts/common.ftl" as c>
<@c.commonTemplate>
<#list publishedPosts as post>
    <div class="square">
        <a href="post/${post.id}">
            <h1>${post.header}</h1>
            <div class="postPublicationDate">${post.getPublicationDateAsString()}<br></div>
            <div class="text"><p>${post.text}</p></div>
        </a>
    </div>
</#list>
</@c.commonTemplate>