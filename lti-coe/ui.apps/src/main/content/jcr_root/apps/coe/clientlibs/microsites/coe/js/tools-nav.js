$(document).ready(function() {

    $(".tools-left-navigation").each(function() {
        $(this).find(".cmp-navigation__item--level-0").children("a").attr("href","#");
        $(this).find(".cmp-navigation__item--level-0").children("a").addClass("nav_collapsed");
        $(this).find(".cmp-navigation__item--level-0").children(".cmp-navigation__group").hide();
        $(this).find(".cmp-navigation__item--level-0").children("a").click( function() { do_left_nav_open_close($(this));});
    });
});

function do_left_nav_open_close(nav_element) {
    if (nav_element.hasClass("nav_expanded"))
    {
        console.log("collapsing - "+ nav_element.html());
        nav_element.removeClass("nav_expanded").addClass("nav_collapsed");
        nav_element.parent().find(".cmp-navigation__group").hide('fast');
        return;
    }
    if (nav_element.hasClass("nav_collapsed"))
    {
        console.log("expanding - "+ nav_element.html());
        nav_element.removeClass("nav_collapsed").addClass("nav_expanded");
        nav_element.parent().find(".cmp-navigation__group").show('slow');
        return;
    }
}