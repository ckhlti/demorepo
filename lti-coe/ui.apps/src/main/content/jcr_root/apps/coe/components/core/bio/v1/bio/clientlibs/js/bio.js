var bioOffsetCounters = new Object();
$(document).ready(function() {
	$(".coe__bio_wrapper").each( function () { 
	    $(this).addClass("c"+$(this).attr("data-cards-per-row"));
    	var bioDetails = JSON.parse($(this).attr("data-bio-details"));
    	var cardsPerRow = $(this).attr("data-cards-per-row");
    	var totalRows = $(this).attr("data-number-of-rows");
        bioOffsetCounters[$(this).attr("id")] = 0;
    	loadBios(0,bioDetails, cardsPerRow, totalRows, $(this).attr("id"));
    });
}
);


function loadBios(startFrom, bioDetails, cardsPerRow, totalRows, componentId) {
        for (var I=0;I < totalRows * cardsPerRow;I++) {
            if ( bioOffsetCounters[componentId]  >= bioDetails.length) {$("#"+componentId).find(".coe__bio_loadmore").hide();return;}
            generateBioTags(bioDetails[bioOffsetCounters[componentId]], $("#"+componentId).find(".coe__bio_card_wrapper"), $("#"+componentId).attr("data-contact-button"));
            bioOffsetCounters[componentId] += 1;
        }
}

function generateBioTags(bioCardDetail, $parentDiv, contactText)
{
    //console.log("generating for " + bioCardDetail.name);
    var $bioCardDiv = $("<div>", {"class":"coe__bio_card"});
    var $bioCardImageDiv = $("<div>",{"class":"coe__bio_image"});
    var $bioImage = $("<img>", {"src":bioCardDetail.image});


    var $bioContent = $("<div>",{"class":"coe__bio_content"});
    $bioContent.append($("<div>",{"class":"bio_name"}).html(bioCardDetail.name));
    $bioContent.append($("<div>",{"class":"bio_title"}).html(bioCardDetail.title));
    $bioContent.append($("<div>",{"class":"bio_summary"}).html(bioCardDetail.summary));
    $bioContent.append($("<div>",{"class":"bio_email"}).html(bioCardDetail.email));


    var $bioSocial = $("<div>",{"class":"bio_social"});
    $bioSocial.append($("<a>",{"class":"bio_social_fb bio_social_icon","href": bioCardDetail.social_fb}));               
    $bioSocial.append($("<a>",{"class":"bio_social_ln bio_social_icon","href": bioCardDetail.social_ln}));               
    $bioSocial.append($("<a>",{"class":"bio_social_twitter bio_social_icon","href": bioCardDetail.social_twitter}));               

    var $bioContact = $("<div>",{"class":"coe__bio_contact"}).append($("<a>",{"href":bioCardDetail.details}).html(contactText));


    $bioContent.append($bioSocial);
    $bioCardImageDiv.append($bioImage);
    $bioCardDiv.append($bioCardImageDiv);
    $parentDiv.append($bioCardDiv);
    $bioCardDiv.append($bioContent);
    $bioCardDiv.append($bioContact);

}

function loadNextSetBios(bioWrapperId)
{
    var bioDetails = JSON.parse($("#"+bioWrapperId).attr("data-bio-details"));
    var cardsPerRow = $("#"+bioWrapperId).attr("data-cards-per-row");
    var totalRows = $("#"+bioWrapperId).attr("data-number-of-rows");
    loadBios(bioOffsetCounters[bioWrapperId],bioDetails, cardsPerRow, totalRows, bioWrapperId);

}