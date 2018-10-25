<#-- @ftlvariable name="captcha_error" type="java.lang.String" -->
<#-- @ftlvariable name="comments" type="java.util.List" -->
<#-- @ftlvariable name="comment" type="com.fonarik94.domain.Comment" -->
<#-- @ftlvariable name="post" type="com.fonarik94.domain.Post" -->
<#import "/spring.ftl" as spring/>
<#import "parts/common.ftl" as c>
<#assign xhtmlCompliant = true in spring>
<@c.commonTemplate>
<div class="square">
    <h1>${post.header}</h1>
    <h5>${post.getPublicationDateAsString()}</h5>
    <div class="text"><p>${post.text}</p></div>
</div>

<div class="square">
    <#if post.comments?has_content>
        <#list post.comments as comment>
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
    <form id="editor" action="/post/${post.id}" method="post" accept-charset="UTF-8">
        <@spring.bind "comment"/>
        <label for="author">Имя</label>
        <@spring.formInput "comment.author", 'id="author" name="author"'/>
        <@spring.showErrors "<br>", 'error'/>
        <br>
        <label for="text">Ваш коментарий</label>
        <@spring.formTextarea "comment.text", 'rows="6" cols="20" id="text" name="text"' />
        <@spring.showErrors "<br>", 'error'/>
        <br>
    <#--<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>-->
        <div>
            <div class="g-recaptcha" data-sitekey="6LdbcnYUAAAAABc9JALRpKnT6S9yBPVVhYhlZ4D4"></div>
            <#if captcha_error??>
                <span class="error">${captcha_error}</span>
            </#if>
            <br>
        </div>
        <div class="postEditorItems">
            <input type="submit" class="postEditorItem" value="Опубликовать"/>
        </div>
    </form>
</div>
</@c.commonTemplate>

