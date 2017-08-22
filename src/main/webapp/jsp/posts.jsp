<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<c:forEach items="${publishedPosts}" var="post">
    <div class="post all">
        <a href="read?postId=${post.getPostId()}">
            <h1>${post.getPostHeader()}</h1>
            <div class="postPublicationDate">${post.getPublicationDateAsString()}<br></div>
            <div class="postText"><p>${post.getPostText()}</p></div>
        </a>
    </div>
</c:forEach>