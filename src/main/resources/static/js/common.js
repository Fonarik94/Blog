

function stickyTop(){
    $(window).scroll(function(){
        if($(this).scrollTop() >= parseInt($('#topbar').css("height"))){
            $('#menubar').css({
                top: '0',
                position: 'fixed'
            })
            $('#content').css({
                "padding-top": $('#menubar').css("height")
            });
        }
        else{
            $('#menubar').css({
                top:'auto',
                position:'sticky'
            });
            $('#content').css({
                "padding-top": 0
            });
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
    stickyTop();
    // showPosts();
});