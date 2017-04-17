<%@ page import="com.fonarik94.dao.PostDao" %>
<%@ page import="org.apache.logging.log4j.LogManager" %>
<%@ page import="org.apache.logging.log4j.Logger" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<% final Logger logger = LogManager.getLogger("posts.jsp");%>
<%logger.debug(">>Post page"); %>
<%PostDao post = PostDao.getInstance();
request.setAttribute("post", post );%>

<c:forEach items="${post.getList()}" var="post">
    <%logger.debug(">>Post printed"); %>
    <div class="post"><p>
        ${post.getpostText()}
    </p></div>
</c:forEach>