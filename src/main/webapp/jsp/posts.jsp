<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.fonarik94.dao.PostDao" %>
<%@ page import="com.fonarik94.dao.PostDaoImpl" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%
    PostDao postDao = PostDaoImpl.getInstance();
    request.setAttribute("post", postDao);
%>

<%--<c:forEach items="${post.getListOfPosts(0, 100)}" var="post">--%>
<c:forEach items="${post.getListOfAllPosts(true)}" var="post">
    <div class="post">
        <div class="postHeader"><b>${post.getPostHeader()}</b><br></div>
        <div class="postPublicationDate">${post.getPublicationDateAsString()}<br></div>
        <div class="postText"><p>${post.getPostText()}</p></div>
    </div>
</c:forEach>