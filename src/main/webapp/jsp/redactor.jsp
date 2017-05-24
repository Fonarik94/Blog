<%@ page import="com.fonarik94.dao.PostDao" %>
<%@ page import="org.apache.logging.log4j.LogManager" %>
<%@ page import="org.apache.logging.log4j.Logger" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<% final Logger logger = LogManager.getLogger("posts.jsp");%>
<%logger.debug(">>Post page"); %>
<%PostDao post = PostDao.getInstance();
    request.setAttribute("post", post );%>
<a class="item" href="/administration/postWriter/addPost"><b>ДОБАВИТЬ</b></a>
<c:forEach items="${post.getList()}" var="post">
    <%logger.debug(">>Post printed"); %>
    <div class="post">
        <div class="postHeader"><b>${post.getPostHeader()}</b><br></div>
        <div class="postPublicationDate">${post.getPublicationDate()}<br></div>
        <div class="postText"><p>${post.getPostText()}</p></div>
        <hr>
        <div class="editorItems">
            <div class="postEditorItem"><a href="">Edit</a></div>
            <div class="postEditorItem"><a href="">Delete</a></div>
        </div>
    </div>
</c:forEach>