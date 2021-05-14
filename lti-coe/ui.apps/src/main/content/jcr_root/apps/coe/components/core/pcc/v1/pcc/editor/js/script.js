(function($, $document, Coral) {


$("body").on("mousedown",".product_feature_type button", function() {
    console.log("User selecting a feature");
    var aria_control = $(this).attr("aria-controls");
    console.log("adding dropdowns for - " + aria_control);

    var selectedValue = $(this).next("input").val();
   	console.log("Selected Value = " + selectedValue);

	// get feature counts
    var total_features = $(".pcc_feature_list").find("input").length - 1 // taking out the template

    var elementHTML = "";
    $("#"+aria_control).html("");
    for (i=0;i<total_features;i++)
    {

        console.log("adding feature index - " + i);
        var selectItem = add_feature_type_to_dropdown(i,selectedValue, $(this).attr("id"));
        $("#"+aria_control).append(selectItem);
    }


});
})($, $(document), Coral);



function add_feature_type_to_dropdown(index, selectedValue, buttonId)
{

    	var feature_value = $(".pcc_feature_list").find("input[name='./features/item"+index+"/./featureName']").val()
    	console.log("feature Value = " + feature_value);
        var coral_item_content = $("<coral-selectlist-item-content>",{"class":"_coral-Menu-itemLabel"}).html(feature_value);
    	console.log(coral_item_content.html());
        var coral_item = $("<coral-selectlist-item>",{"value":feature_value,"class":" _coral-Menu-item","tabindex":"0","role":"option"});
    	var span_tag = $("<span>",{"class":"_coral-SelectList-icon","handle":"checkIcon"});
    	if (feature_value != selectedValue)
            span_tag.attr("hidden","");
    	else
        {
            coral_item.attr("selected","");
            coral_item.addClass("is-selected");
        }

    	coral_item.on("click",function() {
            setSelectedValue(buttonId, feature_value);
        });
    	span_tag.html('<svg focusable="false" aria-hidden="true" class="_coral-Icon--svg _coral-Icon _coral-UIIcon-CheckmarkMedium _coral-Menu-checkmark"><use xlink:href="/libs/clientlibs/granite/coralspectrum/resources/spectrum-css-icons.svg#spectrum-css-icon-CheckmarkMedium"></use></svg>');

		coral_item.append(coral_item_content);
		coral_item.append(span_tag);
        return coral_item;
}


function setSelectedValue(buttonId, feature_value)
{
    $("#"+buttonId).next("input").val(feature_value);
    $("#"+buttonId).find("coral-button-label").html(feature_value);
}



