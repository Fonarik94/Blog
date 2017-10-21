<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<c:forEach items="${publishedPosts}" var="post">
    <div class="post all">
        <a href="post/${post.getId()}">
            <h1>${post.getHeader()}</h1>
            <div class="postPublicationDate">${post.getPublicationDateAsString()}<br></div>
            <div class="text"><p>${post.getText()}</p></div>
        </a>
    </div>
</c:forEach>