$(document).ready(function() {
    $(document).find(".coe__socialfeed_fb").click(function() {share_fb($(this));});
    $(document).find(".coe__socialfeed_twitter").click(function() {share_twitter($(this));});
    $(document).find(".coe__socialfeed_ln").click(function() {share_linkedin($(this));});
});

    function share_fb(fb_tag) {
        window.open('https://www.facebook.com/sharer/sharer.php?u='+fb_tag.attr("data_url")+'&quote='+fb_tag.attr("data-title"),'facebook-share-dialog',"width=626, height=436")
        event.preventDefault();
    }
    
    function share_twitter(twitter_tag) {
        window.open('http://twitter.com/share?url='+twitter_tag.attr("data-url") + '&title='+twitter_tag.attr("data-title"),'_blank', 'width=550,height=420')
        event.preventDefault();
    }
    
    function share_linkedin(linkedin_tag) {
        window.open('https://www.linkedin.com/shareArticle?mini=true&url='+linkedin_tag.attr("data-url")+"&title="+linkedin_tag.attr("data-title")+"&summary="+linkedin_tag.attr("data-summary"),'_blank','left=20,top=20,width=500,height=500,toolbar=1,resizable=0')

        event.preventDefault();
    }

