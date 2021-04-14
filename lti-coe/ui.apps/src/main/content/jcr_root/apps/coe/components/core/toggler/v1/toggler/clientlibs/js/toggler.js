$(".coe-toggler").each(function() {
    //console.log($(this).html());
    var id = $(this).attr("id");
    console.log(id);
    $("#"+id).find(".down-arrow").click(function() {toggleDetailsCard(id);});
    $("#"+id).find(".up-arrow").click(function() {toggleSummaryCard(id);});
}
)

function toggleDetailsCard(cardId)
{
	var togglerId = $("#"+cardId);;
    togglerId.find(".toggler-contents .toggler-details").show("slow");
    togglerId.find(".toggler-contents .toggler-summary").hide();
    togglerId.find(".toggler-cta .down-arrow").hide();
    togglerId.find(".toggler-cta .up-arrow").show();
}

function toggleSummaryCard(cardId)
{
	var togglerId = $("#"+cardId);
    $(togglerId).find(".toggler-contents .toggler-details").hide();
    $(togglerId).find(".toggler-contents .toggler-summary").show();
    $(togglerId).find(".toggler-cta .down-arrow").show();
    $(togglerId).find(".toggler-cta .up-arrow").hide();
}