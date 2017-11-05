<#list allPosts as post>
    <div class="post all">
        <div id="${post.id}">
        <div class="postHeader"><b>${post.header}</b><br></div>
        <div class="postPublicationDate">${post.getPublicationDateAsString()}<br></div>
        <div class="text"><p>${post.text}</p></div>
        <hr>
        <div class="editorItems">
            <div class="postEditorItem"><a href="postwriter/edit?editbyid=${post.id}">Edit</a></div>
            <div class="postEditorItem">
             <button onclick="ajaxPost(${post.id})">Delete</button>
                </div>
            </div>
        </div>
    </div>
</#list>