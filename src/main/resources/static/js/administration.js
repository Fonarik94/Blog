function deleteComment(commentId) {
    if (confirm("Уладилить коментарий " + commentId + "?")) {
        $.delete("/administration/postwriter/delete/comment/" + commentId, function () {
            var comment = $("#" + commentId);
            $(comment).hide('slow', function () {
                comment.remove()
            })
        })
    }
}

function deletePost(id) {
    if (confirm("Удалить пост " + id + " ?")) {
        $.delete("/administration/postwriter/delete/post/" + id, function () {
                var post = $("#" + id).parent();
                $(post).hide("slow", function () {
                    post.remove()
                })
            }
        )
    }
}

function uploadImage(){
    $('#uploadImg').ajaxForm(function (data) {
        data.forEach(function (item, i, arr) {
            console.log(item);
            $('#text').val($('#text').val()+'\n<img class="img-fluid" src="/resources/' + item + '">');
        })

    })
}