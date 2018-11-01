//JQuery "PUT" and "DELETE" shortcut
jQuery.each( [ "put", "delete" ], function( i, method ) {
    jQuery[ method ] = function( url, data, callback, type ) {
        if ( jQuery.isFunction( data ) ) {
            type = type || callback;
            callback = data;
            data = undefined;
        }

        return jQuery.ajax({
            url: url,
            type: method,
            dataType: type,
            data: data,
            success: callback
        });
    };
});

function ajaxCsrf(name, token) {
    jQuery.ajaxSetup({
        headers:{
            [name] : token
        }
    });
}

function chooseLoginMethod(method){
    switch (method){
        case 1:
            $('#authblock').css("display","block");
            $('#registerblock').css("display","none");
            history.pushState(null, null, "/login");
            break;
        case 2:
            $('#authblock').css("display","none");
            $('#registerblock').css("display","block");
            history.pushState(null, null, "/register");
            break;

    }
}

function postComment(postId){
    $.post("/post/"+postId+"/addcomment", "text="+$('#textInput').val() + "&author="+$('#author').val())
}


$(document).ready(function(){
});