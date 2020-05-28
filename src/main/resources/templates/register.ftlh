<#import "parts/common.ftl" as c>
<#import "/spring.ftl" as spring/>
<@c.commonTemplate>
    <div class="form-group">
        <form action="/register" method="post" accept-charset="UTF-8">
            <@spring.bind "userDto"/>
            <div class="form-group">
                <@spring.formInput "userDto.username", 'class="form-control mb-3" placeholder="Имя"'/>
                <@spring.showErrors "<br>", 'alert alert-danger mb-3'/>
            </div>
            <div class="form-group">
                <@spring.formInput "userDto.email", 'class="form-control mb-3" placeholder="Email"'/>
                <@spring.showErrors "<br>", 'alert alert-danger mb-3'/>
            </div>
            <div class="form-group">
                <@spring.formPasswordInput "userDto.password", 'class="form-control mb-3" placeholder="Пароль"'/>
                <@spring.showErrors "<br>", 'alert alert-danger mb-3'/>
            </div>
            <div class="form-group">
                <@spring.formPasswordInput "userDto.confirmPassword", 'class="form-control mb-3" placeholder="Повторите пароль"'/>
                <@spring.showErrors "<br>", 'alert alert-danger mb-3'/>
            </div>
            <#if passwordConfirmError??>
                <div class="alert alert-danger mb-3">${passwordConfirmError}</div>
            </#if>
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
            <input type="submit" class="btn btn-primary" value="Отправить"/>
        </form>
    </div>
</@c.commonTemplate>