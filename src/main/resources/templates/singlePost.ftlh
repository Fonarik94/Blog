<#-- @ftlvariable name="captcha_error" type="java.lang.String" -->
<#-- @ftlvariable name="comments" type="java.util.List" -->
<#-- @ftlvariable name="comment" type="com.fonarik94.domain.Comment" -->
<#-- @ftlvariable name="post" type="com.fonarik94.domain.Post" -->
<#import "/spring.ftl" as spring/>
<#import "parts/common.ftl" as c>
<#assign xhtmlCompliant = true in spring>
<@c.commonTemplate>
<div class="col-md-8 offset-md-2 col-xl-6 offset-xl-3 p-0">
    <div class="card mr-0 mt-md-3 shadow">
        <h4 class="card-title mt-3 ml-3 mb-1">${post.header}</h4>
        <small class="card-text text-muted ml-3">
            ${post.getPublicationDateAsString()}
        </small>
        <div class="card-body pt-1"> ${post.text} </div>
    </div>

    <div class="card p-3 mt-3 mb-3 shadow">
        <#if post.comments?has_content>
            <#list post.comments as comment>
                <div class="media">
                    <div class="media-body">
                    <h5 class="mt-0">
                    ${comment.author}
                    </h5>
                    <h6 class="card-subtitle text-muted mb-2">
                        <small>${comment.getPublicationDateAsString()}</small>
                    </h6>
                        ${comment.text}
                    </div>
                </div>
                <#sep>
                    <hr>
            </#list>
        <#else>
            <p>Коментариев пока нет, будьте первым!</p>
        </#if>
        <form class="mt-3" action="/post/${post.id}" method="post" accept-charset="UTF-8">
            <@spring.bind "comment"/>
            <div class="form-group">
                <@spring.formInput "comment.author", 'class="form-control mb-3" id="author" name="author" placeholder="Имя"'/>
                <@spring.showErrors "<br>", 'alert alert-danger mb-3'/>
            </div>
            <div class="form-group">
                <@spring.formTextarea "comment.text", 'class="form-control mb-3" "rows="6" cols="20" placeholder="Ваш коментарий"'/>
                <@spring.showErrors "<br>", 'alert alert-danger mb-3'/>
            </div>
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
            <div class="form-group">
                <div class="g-recaptcha" data-sitekey="6LdbcnYUAAAAABc9JALRpKnT6S9yBPVVhYhlZ4D4"></div>
                <#if captcha_error??>
                    <span class="alert alert-danger mt-3">${captcha_error}</span>
                </#if>
            </div>
            <div>
                <input type="submit" class="btn btn-primary" value="Опубликовать"/>
            </div>
        </form>
    </div>
</div>
<script src='https://www.google.com/recaptcha/api.js'></script>
</@c.commonTemplate>

