(function($, $document, Coral) {

    $document.on("dialog-ready", function()  {
        // Preload the feature selections 
		var form = document.querySelector('form.cq-dialog');

        // iterate through products
        $(".products").children("coral-multifield-item").each(function(index) {
            var product_index = index;
            // get features
            $(this).find(".product_feature_type").each(function(index) {
                var feature_value = getFeatureValue(product_index, index);
                $(this).children("button").children("coral-button-label").html(feature_value);
                $(this).children("input").val(feature_value);
            });
        });

        function getFeatureValue(p_index, f_index)
        {
            var feature_type = "";
            $.ajax({
                url: form.action + "/products/item"+ p_index +"/featureDetails/item" + f_index + ".json",
                async: false,
                dataType: 'json',
                success: function (data) {
                    feature_type = data.feature_type;
                }
            });
            return feature_type;
        }
    });


    $("body").on("mousedown",".product_feature_type button", function() {
        var aria_control = $(this).attr("aria-controls");

        var selectedValue = $(this).next("input").val();

        var selectElement = $(this).parent().find("._coral-Dropdown-select")
        selectElement.empty();
        // get feature counts
        var total_features = $(".pcc_feature_list").find("input").length - 1 // taking out the template

        var elementHTML = "";
        $("#"+aria_control).html("");
        for (i=0;i<total_features;i++)
        {

            var coralSelectItem = add_feature_type_to_coral_dropdown(i,selectedValue, $(this).attr("id"));
            $("#"+aria_control).append(coralSelectItem);
            var feature_value = $(".pcc_feature_list").find("input[name='./features/item"+i+"/./featureName']").val()
            selectElement.append($("<option>",{value:feature_value,text:feature_value}));

        }


    });

    function add_feature_type_to_coral_dropdown(index, selectedValue, buttonId)
    {

            var feature_value = $(".pcc_feature_list").find("input[name='./features/item"+index+"/./featureName']").val()
            var coral_item_content = $("<coral-selectlist-item-content>",{"class":"_coral-Menu-itemLabel"}).html(feature_value);
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

})($, $(document), Coral);


