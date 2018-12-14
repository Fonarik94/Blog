<#assign known = Session.SPRING_SECURITY_CONTEXT?? />
<#if known>
    <#assign
    user = Session.SPRING_SECURITY_CONTEXT.authentication.principal
    username = user.getUsername()
    admin = user.getAuthorities()?seq_contains("ADMIN")??
    />
<#else>
<#assign admin = false/>
</#if>