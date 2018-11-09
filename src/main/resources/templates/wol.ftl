<#import "parts/common.ftl" as c>
<@c.commonTemplate>
<div class="col-md-4 offset-md-4">
    <div class="card p-3 m-3 shadow">
        <form id="editor" name="wol" method="post">
            <h2> Wake-on-lan </h2>
            <div>
                <label for="mac">MAC</label>
                <input class="form-control mb-3" type="text" name="mac" placeholder="MAC" value="BC-AE-C5-74-0E-BB">
            </div>
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
            <button class="btn btn-primary" type="submit" autofocus>Разбудить</button>
        </form>
    </div>
</div>
</@c.commonTemplate>