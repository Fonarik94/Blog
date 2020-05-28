<#import "parts/common.ftl" as c>
<@c.commonTemplate>
<div class="col-md-6 offset-md-3">
<#list allPosts as post>
    <div class="card p-3 m-3 shadow">
        <div id="${post.id}">
            <h4 class="card-title">${post.header}</h4>
            <h6 class="card-subtitle text-muted mb-2">
                <#if post.publicationDate??>
                    ${post.getPublicationDateAsString()}
                <#else>
                    Not published
                </#if>
                <br>
            </h6>
            <hr>
            <a class="btn btn-primary" href="postwriter/edit?editbyid=${post.id}">Редактировать</a>
            <div class="btn btn-danger" onclick="deletePost(${post.id})">Удалить</div>
        </div>
    </div>
</#list>
</div>
</@c.commonTemplate>