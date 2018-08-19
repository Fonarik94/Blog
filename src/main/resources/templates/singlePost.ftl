<div class="square">
    <h1>${requestedPost.header}</h1>
    <h5>${requestedPost.getPublicationDateAsString()}</h5>
    <div class="text"><p>${requestedPost.text}</p></div>
</div>
<hr>

<div class="square">
    <form id="editor" action="/post/${requestedPost.id}/addcomment" method="post" accept-charset="UTF-8">
        <label for="author">Имя</label>
        <input type="text" id="author" name="author"><br>
        <label for="text">Ваш коментарий</label>
        <textarea rows="6" cols="20" id="text" name="text"></textarea> <br>
        <div class="postEditorItems">
            <input type="submit" class="postEditorItem" value="Опубликовать"/>
        </div>
    </form>
</div>

<#include "comment.ftl">
