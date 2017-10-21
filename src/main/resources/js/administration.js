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
