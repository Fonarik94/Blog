<#-- @ftlvariable name="aboutPage" type="com.fonarik94.domain.Post" -->
<#import "parts/common.ftl" as c>
<@c.commonTemplate>
<div class="col-md-6 offset-md-3">
    <div class="card m-3 p-3 shadow">
        <p class="card-text">${aboutPage.text}</p>
    </div>
</div>
</@c.commonTemplate>