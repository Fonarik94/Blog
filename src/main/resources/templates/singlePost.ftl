<#-- @ftlvariable name="comments" type="java.util.List" -->
<#-- @ftlvariable name="comment" type="com.fonarik94.domain.Comment" -->
<#-- @ftlvariable name="requestedPost" type="com.fonarik94.domain.Post" -->
<#import "/spring.ftl" as spring/>
<#assign xhtmlCompliant = true in spring>
<div class="square">
    <h1>${requestedPost.header}</h1>
    <h5>${requestedPost.getPublicationDateAsString()}</h5>
    <div class="text"><p>${requestedPost.text}</p></div>
</div>

<div class="square">
    <#if comments?has_content>
        <#list comments as comment>
            <div>
                <h2>${comment.author}</h2>
                <h5>${comment.getPublicationDateAsString()}</h5>
                <p>${comment.text}</p>
                <hr>
            </div>
        </#list>
    <#else>
        <p>Коментариев пока нет, будьте первым!</p>
    </#if>
    <form id="editor" action="/post/${requestedPost.id}" method="post" accept-charset="UTF-8">
        <@spring.bind "comment"/>
        <label for="author">Имя</label>
        <@spring.formInput "comment.author", 'id="author" name="author"'/>
        <@spring.showErrors "<br>", 'error'/>
        <br>
        <label for="text">Ваш коментарий</label>
        <@spring.formTextarea "comment.text", 'rows="6" cols="20" id="text" name="text"' />
        <@spring.showErrors "<br>", 'error'/>
        <br>
        <div class="postEditorItems">
            <input type="submit" class="postEditorItem" value="Опубликовать"/>
        </div>
    </form>
</div>

<#--
<div class="square" id="addComment" &lt;#&ndash;style="display: none"&ndash;&gt;>

</div>-->
