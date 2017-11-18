<script type="text/javascript" src="/resources/ckeditor/ckeditor.js"></script>
<form id="postEditorForm" method="post" accept-charset="UTF-8">
    <label for="headerInput">Заголовок</label>
    <textarea rows=2 id="headerInput" name="postHeaderInput">${postHeader}</textarea>
    <label for="textInput">Текст поста</label>
    <textarea rows=30 cols=80 id="textInput" name="postTextInput">${text}</textarea>
    <div>Опубликовано<input type="checkbox" name="isPublished"></div>
    <input id="block" type="submit" name="saveButton" value="Сохранить">
</form>
<script>
    CKEDITOR.replace('textInput');
</script>