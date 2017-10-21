<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%--<jsp:useBean id="postDao" type="com.fonarik94.dao.PostDao"/>--%>

<c:forEach items="${postDao.getAllPosts()}" var="post">
    <div class="post all">
        <div id="${post.getId()}">
        <div class="postHeader"><b>${post.getHeader()}</b><br></div>
        <div class="postPublicationDate">${post.getPublicationDateAsString()}<br></div>
        <div class="text"><p>${post.getText()}</p></div>
        <hr>
        <div class="editorItems">
            <div class="postEditorItem"><a href="postwriter/edit?editbyid=${post.getId()}">Edit</a></div>
            <div class="postEditorItem">
             <button onclick="ajaxPost(${post.getId()})">Delete</button>
                </div>
            </div>
        </div>
    </div>
</c:forEach>