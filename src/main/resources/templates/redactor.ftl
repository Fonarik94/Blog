<#list allPosts as post>
    <div class="square all">
        <div id="${post.id}">
            <div class="postHeader">
                <b>
                    ${post.header}
                </b>
                <br>
            </div>
            <div class="publicationDate">
                ${post.getPublicationDateAsString()}
                <br>
            </div>
            <p>
                ${post.text}
            </p>
            <hr>
            <div class="editorItems">
                <div class="postEditorItem"><a href="postwriter/edit?editbyid=${post.id}">Edit</a></div>
                <div class="postEditorItem" onclick="deletePost(${post.id})">Delete</div>
            </div>
        </div>
    </div>
</#list>