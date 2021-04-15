$(".coe-toggler").each(function() {
    var id = $(this).attr("id");
    $("#"+id).find(".toggler-contents").click(function() {toggleDetailsCard(id);});
    $("#"+id).find(".toggler-close-button").click(function() {toggleSummaryCard(id);});
}
)

function toggleDetailsCard(cardId)
{
    console.log("summart clicked");
	var togglerId = $("#"+cardId);
    togglerId.unbind("click");
    togglerId.find(".toggler-contents .toggler-details").show("slow");
    togglerId.find(".toggler-contents .toggler-summary").hide();
    togglerId.find(".toggler-close-button").show();
}

function toggleSummaryCard(cardId)
{
    console.log("Close clicked");
	var togglerId = $("#"+cardId);
    $(togglerId).find(".toggler-contents .toggler-details").hide();
    $(togglerId).find(".toggler-contents .toggler-summary").show();
    togglerId.find(".toggler-close-button").hide();
}