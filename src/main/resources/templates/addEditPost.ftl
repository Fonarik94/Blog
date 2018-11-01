<#--@ftlvariable name="comment" type="com.fonarik94.domain.Comment"-->
<#--@ftlvariable name="post" type="com.fonarik94.domain.Post"-->
<#import "/spring.ftl" as spring/>
<#import "parts/common.ftl" as c>
<@c.commonTemplate>
<script type="text/javascript" src="/resources/ckeditor/ckeditor.js"></script>
<div class="card p-3 m-3 shadow">
    <form id="editor" method="post" accept-charset="UTF-8">
        <@spring.bind "post"/>
        <div class="form-group">
            <label for="headerInput">Заголовок</label>
            <@spring.formInput "post.header", 'class="form-control" name="headerInput"'/>
        </div>
        <div class="form-group">
            <label for="text">Текст поста</label>
            <@spring.formTextarea "post.text",'class="form-control" rows=50 cols=80 name="text"'/>
        </div>
        <@spring.formHiddenInput "post.id", 'value=${post.id}'/>
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        <div class="checkbox">
            <label for="published">
                <@spring.formCheckbox "post.published"/>Опубликовано
            </label>
        </div>
        <div>
            <input type="submit" class="btn btn-primary" value="Сохранить">
        </div>
    </form>
</div>

<div class="card p-3 m-3 shadow">
    <#if post.comments?has_content>
        <#list post.comments as comment>
            <div class="comment" id=${comment.getId()}>
                <h5>${comment.author}</h5>
                <h6 class="card-subtitle text-muted mb-2">
                    ${comment.getPublicationDateAsString()}
                </h6>
                <p>${comment.text}</p>
                    <div class="btn btn-danger" onclick="deleteComment(${comment.id})">
                        Удалить
                    </div>
            </div>
            <#sep ><hr>
        </#list>
    <#else>
            <p>Коментариев пока нет</p>
    </#if>
</div>
<script>
    CKEDITOR.replace('text');
</script>
</@c.commonTemplate>