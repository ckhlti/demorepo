$(document).ready(function() {
    $(".pcc__table_wrapper").each(function() {
        var HeaderRow, bodyContent;
        var productFeatures = new Object();
        var firstTime = true;
        var pccData = JSON.parse($(this).attr("data-json"));
        headerRow = "<tr class='pcc__product_header'><td/>";
		$.each(pccData, function(index, itemData) {
			headerRow += "<td>"+ "<div class='pcc_product_title'>"+itemData.name+"</div><div class='pcc_product_summary'>"+itemData.summary+"</div><div class='pcc_cta_content'><a href='"+itemData.ctaTarget+"'>"+itemData.ctaText+"</div></td>";
            $.each(itemData.features, function(index, featureData) {
                if (firstTime)
                    productFeatures[index]  = "<td>"+index+"</td>";
                productFeatures[index] += "<td>"+featureData+"</td>";
            });
            firstTime = false;
		});
        $(this).append(headerRow);
        console.log(productFeatures["promotionalOffer"]);
        var evenRow = false;
        $.each(productFeatures, function(index, item) {
            bodyContent += "<tr class='" + (evenRow?"grayBackground":"") +"'>"+item+"</tr>";
            evenRow = !evenRow;
        });
        $(this).append(bodyContent);

    });
});