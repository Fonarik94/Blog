<#list allPosts as post>
    <div class="square all">
        <div id="${post.id}">
            <div class="postHeader">
                <b>
                    ${post.header}
                </b>
                <br>
            </div>
            <div class="postPublicationDate">
                ${post.getPublicationDateAsString()}
                <br>
            </div>
            <p>
                ${post.text}
            </p>
            <hr style="width: 750px">
            <div class="editorItems">
                <div class="postEditorItem"><a href="postwriter/edit?editbyid=${post.id}">Edit</a></div>
                <div class="postEditorItem" onclick="deletePost(${post.id})">Delete</div>
            </div>
        </div>
    </div>
</#list>