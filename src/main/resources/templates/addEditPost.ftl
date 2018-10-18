<#--@ftlvariable name="comments" type="List"-->
<#--@ftlvariable name="post" type="com.fonarik94.domain.Post"-->
<#import "/spring.ftl" as spring/>
<#import "parts/common.ftl" as c>
<@c.adminTemplate>
<script type="text/javascript" src="/resources/ckeditor/ckeditor.js"></script>
<div class="square" id="single">
    <form id="editor" method="post" accept-charset="UTF-8">
        <@spring.bind "post"/>
        <label for="headerInput">Заголовок</label>
        <@spring.formTextarea "post.header", 'rows=2 id="headerInput" name="postHeaderInput"'/>
        <label for="text">Текст поста</label>
        <@spring.formTextarea "post.text",'rows=30 cols=80 id="textInput" name="postTextInput"'/>
        <@spring.formHiddenInput "post.id", 'value=${post.id}'/>
        <div>
            <label for="isPublished">Опубликовано</label>
            <@spring.formCheckbox "post.published"/>
        </div>
        <input id="block" type="submit" name="saveButton" value="Сохранить">
    </form>
</div>

<div class="square">
    <#if comments?has_content>
        <#list comments as comment>
            <div class="comment" id=${comment.getId()}>
                <h2>${comment.author}</h2>
                <h5>${comment.getPublicationDateAsString()}</h5>
                <p>${comment.text}</p>
                <div class="editorItems">
                    <div class="postEditorItem" style="background-color: #f09b9b" onclick="deleteEntity('comment', ${comment.id})">
                        Удалить
                    </div>
                </div>
                <hr>
            </div>
            </#list>
        <#else>
            <p>Коментариев пока нет</p>
    </#if>
</div>
<script>
    CKEDITOR.replace('text');
</script>
</@c.adminTemplate>