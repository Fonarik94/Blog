<#-- @ftlvariable name="captcha_error" type="java.lang.String" -->
<#-- @ftlvariable name="comments" type="java.util.List" -->
<#-- @ftlvariable name="comment" type="com.fonarik94.domain.Comment" -->
<#-- @ftlvariable name="post" type="com.fonarik94.domain.Post" -->
<#import "/spring.ftl" as spring/>
<#import "parts/common.ftl" as c>
<#assign xhtmlCompliant = true in spring>
<@c.commonTemplate>
<div class="card p-3 m-3 shadow">
    <h4 class="card-title">${post.header}</h4>
    <h6 class="card-subtitle text-muted mb-2">
        ${post.getPublicationDateAsString()}
    </h6>
    <div class="card-body">${post.text}</div>
</div>

<div class="card p-3 m-3 shadow">
    <#if post.comments?has_content>
        <#list post.comments as comment>
            <div>
                <h5>${comment.author}</h5>
                <h6 class="card-subtitle text-muted mb-2">
                    ${comment.getPublicationDateAsString()}
                </h6>
                <p>${comment.text}</p>
            </div>
            <#sep><hr>
        </#list>
    <#else>
        <p>Коментариев пока нет, будьте первым!</p>
    </#if>
    <form id="editor" action="/post/${post.id}" method="post" accept-charset="UTF-8">
        <@spring.bind "comment"/>
        <@spring.formInput "comment.author", 'class="form-control mb-3" id="author" name="author" placeholder="Имя"'/>
        <@spring.showErrors "<br>", 'alert alert-danger mb-3'/>
        <@spring.formTextarea "comment.text", 'class="form-control mb-3" "rows="6" cols="20" placeholder="Ваш коментарий" name="text"' />
        <@spring.showErrors "<br>", 'alert alert-danger mb-3'/>
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        <div class="mb-3">
            <div class="g-recaptcha" data-sitekey="6LdbcnYUAAAAABc9JALRpKnT6S9yBPVVhYhlZ4D4"></div>
            <#if captcha_error??>
                <div class="alert alert-danger mt-2">${captcha_error}</div>
            </#if>
        </div>
        <div>
            <input type="submit" class="btn btn-primary" value="Опубликовать"/>
        </div>
    </form>
</div>
</@c.commonTemplate>

