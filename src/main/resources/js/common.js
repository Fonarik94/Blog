

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

function ajaxGet( url) {
    var request = new XMLHttpRequest();

    request.onreadystatechange = function () {
        if(request.readyState == 4){

        }
    };

    request.open('GET', url);
    request.send();
}

function ajaxPost(id) {
    var postRequest = new XMLHttpRequest();
    postRequest.onreadystatechange = function () {
        if(postRequest.readyState == 4){
            var post = document.getElementById(id);
            post = post.parentNode;
            $(post).hide("slow", function(){post.remove});
        }
    };
    postRequest.open('POST', "/administration/postwriter/ajaxdelete");
    postRequest.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
    postRequest.send('deleteById='+id);

}


function onDeletePost(id) {


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
    /*    function drawPost(post, amount){
            var postDiv = $('<div/>',{'class': 'post'});
            var header = $('<h1/>').append(post.header);
            var dateTime = $('<h5/>').append(post.publicationDateTime);
            var text = $('<div/>', {class: "text"}).append(post.text);
            postDiv.addClass(amount);
            postDiv.append(header).append(dateTime).append(text);
            postDiv.click(function(){location.href ='/post/'+post.id});
            $('#content').append(postDiv);

        }*/
}


$(document).ready(function(){
    stickyTop();
    // showPosts();
});