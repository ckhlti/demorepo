$(".coe-accordionCard").each(function() {
    //console.log($(this).html());
    var id = $(this).attr("id");
    console.log(id);
    $("#"+id).find(".down-arrow").click(function() {accordionCardDetailsCard(id);});
    $("#"+id).find(".up-arrow").click(function() {accordionCardSummaryCard(id);});
}
)

function accordionCardDetailsCard(cardId)
{
	var accordionCardId = $("#"+cardId);;
    accordionCardId.find(".accordionCard-contents .accordionCard-details").show("slow");
    accordionCardId.find(".accordionCard-contents .accordionCard-summary").hide();
    accordionCardId.find(".accordionCard-cta .down-arrow").hide();
    accordionCardId.find(".accordionCard-cta .up-arrow").show();
}

function accordionCardSummaryCard(cardId)
{
	var accordionCardId = $("#"+cardId);
    $(accordionCardId).find(".accordionCard-contents .accordionCard-details").hide();
    $(accordionCardId).find(".accordionCard-contents .accordionCard-summary").show();
    $(accordionCardId).find(".accordionCard-cta .down-arrow").show();
    $(accordionCardId).find(".accordionCard-cta .up-arrow").hide();
}