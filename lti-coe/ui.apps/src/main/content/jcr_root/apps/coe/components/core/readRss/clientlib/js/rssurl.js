
$(document).ready(function() 
{  


    $(".RSSFeed").each(function() {
        var rssurl = $(this).data('rssurl');
        var rsslimit = $(this).data('rsslimit');

        var title = $(this).data('rsstitle')
        var pubdate = $(this).data('rsspubdate')
        var description = $(this).data('rssdescription')
        var link = $(this).data('rsslink')

        var urlParts = rssurl.replace('http://','').replace('https://','').split(/[/?#]/);
        var domain = urlParts[0];
        $(this).find(".domain").append($("<span>").text(domain));
        var $rssContent =  $(this).find(".rssContent");

        $.get("/content/coe/servlets/rss.txt.html",{rssurl: rssurl}, function(data) 
        {
            data = data.replace(/<!--(.*?)-->/, "");
            responseJson = JSON.parse(data);
            $.each(responseJson, function(index, item) 		
            {	
                if(index > (rsslimit-1)){
                    return false;
                }

                $rssContent.append($("<br>"));	
                var $div = $("<div>", {id: "feedcard", "class": "card"});
                var $div2 = $("<div>", {id: "feedbody", "class": "card-body"});

                if(title === true){$div2.append($("<h3>").text(item.title))}
                if(pubdate === true){$div2.append($("<small>").addClass("text-muted").text(item.pubDate))}
                if(description === true){$div2.append($("<div>").html(item.description))}
                if(link === true){$div2.append($("<a>",{href: item.link,target:"_blank"}).text("Read more →"))}

                $div.append($div2);
                $rssContent.append($div);
            });
        });
    });
});

