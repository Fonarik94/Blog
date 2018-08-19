<#list comments as comment>
<div class="square">
    <h2>${comment.author}</h2>
    <h5>${comment.getPublicationDateAsString()}</h5>
        <p>${comment.text}</p>
</div>
</#list>