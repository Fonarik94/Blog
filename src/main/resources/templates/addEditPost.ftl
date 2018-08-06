<script type="text/javascript" src="/resources/ckeditor/ckeditor.js"></script>
<div class="square" id="single">
    <form id="editor" method="post" accept-charset="UTF-8">
        <label for="headerInput">Заголовок</label>
        <textarea rows=2 id="headerInput" name="postHeaderInput">${postHeader}</textarea>
        <label for="textInput">Текст поста</label>
        <textarea rows=30 cols=80 id="textInput" name="postTextInput">${text}</textarea>
        <div>
            <input type="checkbox" id="isPublished" name="isPublished">
            <label for="isPublished">Опубликовано</label>
        </div>
        <input id="block" type="submit" name="saveButton" value="Сохранить">
    </form>
</div>
<script>
    CKEDITOR.replace('textInput');
</script>