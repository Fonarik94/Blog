<%@ page import="com.fonarik94.dao.Post" %>
<%@ page import="com.fonarik94.dao.PostDao" %>
<%@ page import="com.fonarik94.dao.PostDaoImpl" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@page contentType="text/html;charset=UTF-8" %>
<%
    Post post = (Post)request.getAttribute("editMode");
    request.setAttribute("postHeader", "");
    request.setAttribute("text", "");
    request.setAttribute("published", "unchecked");
    request.setAttribute("mode", "Add");
    if(post!=null) {
        request.setAttribute("postHeader", post.getPostHeader());
        request.setAttribute("text", post.getPostText());
        request.setAttribute("published", post.isPublished()?"checked":"unchecked");
        request.setAttribute("mode", "Edit");
    }
%>
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