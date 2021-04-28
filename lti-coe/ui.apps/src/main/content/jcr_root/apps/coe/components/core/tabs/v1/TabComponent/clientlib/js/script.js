$(document).ready(function() {
    console.log("inside javascript file");

    $('a[data-toggle="tab"]').on('shown.bs.tab', function (e) {
    history.pushState({}, '', e.target.hash);
    });

    $(window).on('hashchange', function() {
        $('.active').removeClass('active');
        var hash = window.location.hash;
        if(hash.length > 0) {
            hash = hash.substring(1);
            $('li a[href$='+hash+']').addClass('active').tab('show');
            $('#'+hash).addClass('active show').tab('show');
        }
    });
});
