<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<form id="postEditorForm" name="editorForm" method="post">
    Заголовок<br>
    <textarea rows=2 id="headerInput" type="text" name="postHeaderInput"></textarea>
    Текст поста<br>
    <textarea rows=30 cols=80 id="textInput"type="text" name="postTextInput"></textarea><br>
    <div>Опубликовано<input type="checkbox" name="isPublished"></div>
    <input id="block" type="submit" name="saveButton" value="Сохранить">
</form>