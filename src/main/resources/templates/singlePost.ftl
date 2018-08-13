<div class="square">
        <h1>${requestedPost.header}</h1>
        <div class="postPublicationDate">${requestedPost.getPublicationDateAsString()}</div>
        <div class="text"><p>${requestedPost.text}</p></div>
    </div>
<div class="square">
    <form id="editor" method="post" accept-charset="UTF-8">
        <label for="textInput">Ваш коментарий</label> <br>
        <textarea rows="6" cols="20" id="textInput" name="commentTextInput"></textarea> <br>
        <div class="editorItems">
            <div class="postEditorItem" onclick="postComment(${requestedPost.id})">Опубликовать</div>
        </div>
    </form>
</div>
<#--<#include "comment.ftl">-->
