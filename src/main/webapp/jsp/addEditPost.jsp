<jsp:useBean id="text" scope="request" type="java.lang.String"/>
<jsp:useBean id="mode" scope="request" type="java.lang.String"/>
<jsp:useBean id="postHeader" scope="request" type="java.lang.String"/>

<%@page contentType="text/html;charset=UTF-8" %>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/nicEdit.js"></script>
<script type="text/javascript">
    bkLib.onDomLoaded(function() { new nicEditor({fullPanel : true, iconsPath : '/resources/images/nicEditorIcons.gif'}).panelInstance('textInput'); });
</script>
<form id="postEditorForm" method="post" accept-charset="UTF-8">
    Заголовок<br>
    <textarea rows=2 id="headerInput" name="postHeaderInput">${postHeader}</textarea>
    <input type="hidden" name="Page" value=${mode}>
    Текст поста<br>
    <textarea rows=30 cols=80 id="textInput" name="postTextInput">${text}</textarea>
    <br>
    <div>Опубликовано<input type="checkbox" name="isPublished"></div>
    <input id="block" type="submit" name="saveButton" value="Сохранить">
</form>