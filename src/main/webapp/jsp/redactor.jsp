<%@ page import="com.fonarik94.dao.PostDao" %>
<%@ page import="com.fonarik94.dao.PostDaoImpl" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
    PostDao postDao = PostDaoImpl.getInstance();
    request.setAttribute("post", postDao);
%>
<c:forEach items="${post.getListOfAllPosts(false)}" var="post">
    <div class="post all">
        <div class="postHeader"><b>${post.getPostHeader()}</b><br></div>
        <div class="postPublicationDate">${post.getPublicationDateAsString()}<br></div>
        <div class="postText"><p>${post.getPostText()}</p></div>
        <hr>
        <div class="editorItems">
            <div class="postEditorItem"><a href="postWriter/edit?editById=${post.getPostId()}">Edit</a></div>
            <div class="postEditorItem">
                <form id="deletePostById"  method="post">
                    <input type="hidden" name="Page" value="Delete">
                    <input type="hidden" name="DeleteById" value="${post.getPostId()}">
                    <input type="submit" value="Delete">
                </form>
            </div>
        </div>
    </div>
</c:forEach>