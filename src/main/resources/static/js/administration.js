function deletePost(id) {
    $.post("/administration/postwriter/delete", "deleteById="+id, function () {
        var post = $("#"+id).parent();
        $(post).hide("slow", function(){post.remove});
    })
}function logout(){
    $.get("/logout", function () {
        
    });
}
$(document).ready(function () {
    
});