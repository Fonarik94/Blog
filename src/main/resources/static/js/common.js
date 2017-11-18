

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

function showPosts(){
    $.getJSON(document.location.href+".json", function(posts){
        if(posts.length > 1){
            $.each(posts, function(index, el) { drawPost(el, 'all')});
        } else{
            drawPost(posts, 'single');
        }
    });

    function drawPost(post, amount){
        var template = '<div class="post' + amount + '">\
            <h1>${post.header}</h1>\
            <h5>${post.publicationDateTime}</h5>\
            <div class="text">${post.text}</div>\
        </div>';
        $.template('postTemplate', template);
        $.tmpl('postTemplate', post).append('#content');
    }
}


$(document).ready(function(){
    stickyTop();
    // showPosts();
});