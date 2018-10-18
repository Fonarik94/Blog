<#import "parts/common.ftl" as c>
<@c.adminTemplate>
<div class="square" style="width: auto">
    <form id="editor" name="wol" method="post">
        <h3> Wake-on-lan </h3>
        <div>
            <label for="mac">MAC</label>
            <input id="mac" type="text" name="mac" value="BC-AE-C5-74-0E-BB">
        </div>
        <br>
        <button id="block" type="submit" autofocus>Разбудить</button>
    </form>
</div>
</@c.adminTemplate>