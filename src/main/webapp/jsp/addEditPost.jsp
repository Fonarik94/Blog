<jsp:useBean id="text" scope="request" type="java.lang.String"/>
<jsp:useBean id="mode" scope="request" type="java.lang.String"/>
<jsp:useBean id="postHeader" scope="request" type="java.lang.String"/>

<%@page contentType="text/html;charset=UTF-8" %>
<script src="${pageContext.request.contextPath}/resources/js/jquery/2.2.4/jquery.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/ckeditor/ckeditor.js"></script>
<form id="postEditorForm" method="post" accept-charset="UTF-8">
    <label for="headerInput">Заголовок</label>
    <textarea rows=2 id="headerInput" name="postHeaderInput">${postHeader}</textarea>
    <input type="hidden" name="Page" value=${mode}>
    <label for="textInput">Текст поста</label>
    <textarea rows=30 cols=80 id="textInput" name="postTextInput">${text}</textarea>
    <div>Опубликовано<input type="checkbox" name="isPublished"></div>
    <input id="block" type="submit" name="saveButton" value="Сохранить">
</form>
<script>
    CKEDITOR.replace('textInput');
</script>