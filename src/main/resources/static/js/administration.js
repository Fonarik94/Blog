function deleteEntity(type, id) {
    $.delete("/administration/postwriter/delete/" + type + "/" + id, function () {
        if (confirm("Уладилить " + type + " " + id + "?")) {
            if (type === 'comment') {
                var comment = $("#"+id);
                $(comment).hide('slow', function () {
                    comment.remove()
                })
            } else if (type === 'post') {
                var post = $("#" + id).parent();
                $(post).hide("slow", function () {
                    post.remove()
                });
            }
        }
    })
}

function deleteComment(id) {

}

function logout() {
    $.get("/logout", function () {

    });
}