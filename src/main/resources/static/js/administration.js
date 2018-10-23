function deleteComment(commentId) {
    if (confirm("Уладилить коментарий " + commentId + "?")) {
        $.delete("/administration/postwriter/delete/comment/" + commentId, function () {
            var comment = $("#" + commentId);
            $(comment).hide('slow', function () {
                comment.remove()
            })
        })
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


    function deleteComment(id) {

    }

    function logout() {
        $.get("/logout", function () {
        });
    }
}