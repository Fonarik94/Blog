<#import "parts/common.ftl" as c>
<@c.adminTemplate>
<#list allPosts as post>
    <div class="square all">
        <div id="${post.id}">
            <div class="postHeader">
                <b>
                    ${post.header}
                </b>
                <br>
            </div>
            <div class="publicationDate">
                ${post.getPublicationDateAsString()}
                <br>
            </div>
            <p>
                ${post.text}
            </p>
            <hr>
            <div class="editorItems">
                <div class="postEditorItem"><a href="postwriter/edit?editbyid=${post.id}">Редактировать</a></div>
                <div class="postEditorItem" style="background-color: #f09b9b" onclick="deleteEntity('post',${post.id})">Удалить</div>
            </div>
        </div>
    </div>
</#list>
</@c.adminTemplate>