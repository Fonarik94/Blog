function deleteComment(commentId) {
    if (confirm("Уладилить коментарий " + commentId + "?")) {
        var postid = getUrlParameter('editbyid');
        $.delete("/administration/postwriter/delete/post/" + postid + "/comment/" + commentId, function () {
            let comment = $("#" + commentId);
            $(comment).fadeOut('slow', function () {
                comment.remove()
            })
        })
    }
}

function deletePost(id) {
    if (confirm("Удалить пост " + id + " ?")) {
        $.delete("/administration/postwriter/delete/post/" + id, function () {
                let post = $("#" + id).parent();
                $(post).fadeOut("slow", function () {
                    post.remove()
                })
            }
        )
    }
}

function getUrlParameter(param){
    var currentHref = window.location.search.substring(1);
    var urlParams = currentHref.split('&');
    for(var i = 0; urlParams.length; i++){
        var sep = urlParams[i].split('=');
        if(sep[0] === param){
            return sep[1];
        }
    }
}

function uploadImage() {
    $('#uploadImg').ajaxForm(function (data) {
        data.forEach(function (item, i, arr) {
            $('#text').val($('#text').val() + '\n<img alt="" class="img-fluid" src="/resources/' + item + '"/>');
        })

    });
}
function fileValidation(){
    let input = $('#image-upload');
    let button = $("#fileSubmit");
    if (input[0].files.length >= 1){
        button.prop('disabled', false);
        button.attr('class', 'btn btn-primary');
    }
    if(input[0].files.length === 0){
        messageShow();
    } else {
        button.enable();
        files = input[0].files;
        Array.from(files).forEach(f => {if (f.size/1024 >= 1024){messageShow(f)}})
    }

    function messageShow(file) {
        let emptyErrorMessage = '<div class="alert alert-danger mt-2 error">Файлы не выбраны<div/>';
        if (!file) {
            input.parent().append(emptyErrorMessage);
            button.prop('disabled', true);
            button.attr('class', 'btn btn-secondary');
        } else {
            let text = '<div class="alert alert-danger mt-2 error">Файл  ' + file.name + ' первышает допустимый размер в 1MB - ' + ((file.size / 1024)/1024).toFixed(2) + 'MB <div/>';
            button.prop('disabled', true);
            button.attr('class', 'btn btn-secondary');
            clearFileInput();
            input.parent().append(text);
        }
        let msg = $('.error');
        msg.fadeIn("slow", () => msg.delay(3*1000).fadeOut("slow", () => msg.remove()))
    }
}

function doLivePreview() {
    $("#preview").html($("#text").val());
}

function clearFileInput(){
    $('#image-upload').delay(100).val('');
}

