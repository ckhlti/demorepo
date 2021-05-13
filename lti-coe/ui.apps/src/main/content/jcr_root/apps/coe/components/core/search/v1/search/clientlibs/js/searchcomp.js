function getSearchResult(obj) {
    var searchcomp = $(obj).closest(".coe-searchcomp");
    var apiUrl = $(searchcomp).attr('data-path') + ".model.json";
    var searchterm = $(searchcomp).find(".searchterm").val();
    $(".filterresult").remove();
    $(".resultitems").remove();
    $(".noresult").remove();
    $(".loadmorediv").remove();
    if (apiUrl) {
        $.ajax({
            type: "GET",
            dataType: 'JSON',
            url: apiUrl,
            data: {
                fulltext: searchterm
            },
        }).done(function(data) {
            console.log(data);
            createResults(data);
            createFilters(data);
        }).fail(function() {

            console.log('error in api call');
        });
    }
}




function createFilters(data) {

    var resultsFilters = data.filterList;

    var itemCount = resultsFilters.length;
    var desc = '<div class="filterresult">';
    if (itemCount > 0) {
        desc += '<div><span>Filters:</span>  <span class="clearfilter"><input type="button" onclick="clearFilter(this)" value="Clear Filter" /></span></div>'
    }
    for (var i = 0; i < itemCount; i++) {

        desc += '<div class="category-section" data-category-name="' + resultsFilters[i].filterCategoryName + '"><h4>' + resultsFilters[i].filterCategoryName + '</h4>';
        var listItems = resultsFilters[i].listItem;
        var listItemCount = listItems.length;
        for (var k = 0; k < listItemCount; k++) {
            desc += '<input type="checkbox" onclick="applyFilter(this)" name="' + resultsFilters[i].filterCategoryName.toLowerCase() + '" value="' + listItems[k] + '">' + listItems[k] + '<br>';
        }
        desc += '</div><br>';

    }
    desc += '</div>'
    $("#" + data.componentId + " .resultfilters").append(desc);
}

function createResults(data) {

    var resultList = data.resultList;

    var itemCount = resultList.length;
    var noresultclass = (itemCount == 0) ? "hidden" : "";
    var desc = ''
    for (var i = 0; i < itemCount; i++) {

        desc += '<div id="' + data.componentId + '_result' + (i+1 )+ '" class="resultitems ' + ((data.showLoadMore == "true" && i>= data.onloadItemCount) ? "loadmore hidden" : "") + '" data-tags-general="' + resultList[i].pageTags + '" data-tags-brand="' + resultList[i].brandTags + '" data-tags-color="' + resultList[i].colorTags + '"><a href="' + resultList[i].path + '"><div>' +i+' '+ resultList[i].title + '</div><div>' + resultList[i].description + '</div></a></div>';

    }
    desc += '<div id="' + data.componentId + '_noresult" class="noresult ' + ((itemCount > 0) ? "hidden" : "") + '"> No Result Found:</div>';
    if(data.showLoadMore == "true" && itemCount>data.onloadItemCount){
    desc += '<div class="loadmorediv"><input type="button" onclick="loadMore(this)" value="Load More" /></div>'

    }
    $("#" + data.componentId + " .searchresult").append(desc);
}

function applyFilter(obj) {
     var searchcompId = $(obj).closest(".coe-searchcomp").attr("id");
     var loadmoreItemNumber=$("#" + searchcompId).attr('data-onloadItemCount');
    var filterCategories = [];
    $(".category-section").each(function() {
        var selectedcount = $(this).find("input:checkbox:checked").length;
        if (selectedcount > 0) {
            filterCategories.push($(this).attr("data-category-name").toLowerCase());
        }

    });
    if (filterCategories.length > 0) {
        var filteredDivId = [];
        filterCategories.forEach(function(value, index) {
            if (index == 0) {

                $("input:checkbox[name=" + value + "]:checked").each(function() {
                    var filterValue = $(this).val();
                    $("div .resultitems").each(function() {
                        var dataTag = $(this).attr("data-tags-" + value);
                        var divId = $(this).attr("id");
                        if (dataTag) {
                            var splitTag = dataTag.split("|");
                            splitTag.forEach(function(splitTagVal, index) {
                                if (splitTagVal == filterValue) {
                                    if (filteredDivId.indexOf(divId) === -1) {
                                        filteredDivId.push(divId);
                                    }
                                }
                            });
                        }


                    });
                });
            } else {
                if (filteredDivId.length > 0) {
                    var tempfilteredDivId = filteredDivId.slice();

                    filteredDivId.forEach(function(divID, index) {

                        var dataTag = $(".resultitems#" + divID).attr("data-tags-" + value);
                        var divId = $(".resultitems#" + divID).attr("id");
                        var splitTag = dataTag.split("|");
                        var removeDiv = "true";
                        if (dataTag) {
                            $("input:checkbox[name=" + value + "]:checked").each(function() {
                                var filterValue = $(this).val();

                                splitTag.forEach(function(splitTagVal, index) {
                                    if (splitTagVal == filterValue) {
                                        removeDiv = "false";
                                    }
                                });

                            });
                        }
                        if (removeDiv == "true") {
                            var index = tempfilteredDivId.indexOf(divId);
                            if (index !== -1) {
                                tempfilteredDivId.splice(index, 1);
                            }
                        }
                    });
                    filteredDivId = tempfilteredDivId.slice();
                }


            }

        });
        if (filteredDivId.length > 0) {
            $(".resultitems").addClass("hidden");
            $(".resultitems").removeClass('filtered loadmore');
            $(".noresult").addClass("hidden");
            filteredDivId.forEach(function(divID, index) {
                if(loadmoreItemNumber>0 && index<loadmoreItemNumber){
							$(".resultitems#" + divID).removeClass('hidden');
                    		$(".resultitems#" + divID).addClass('filtered');
                }else{
						$(".resultitems#" + divID).addClass('filtered loadmore');
                }


            });
            var loadmoreitemlength=$("#" + searchcompId+" .resultitems.filtered.loadmore.hidden").length
            if(loadmoreitemlength>0){
						$("#" + searchcompId+" .loadmorediv").removeClass("hidden");
            }else{
                $("#" + searchcompId+" .loadmorediv").addClass("hidden");
            }
        } else {

            $(".resultitems").addClass("hidden");
            $("#" + searchcompId+" .loadmorediv").addClass("hidden"); 
            $(".noresult").removeClass("hidden");
             //onloadLoadmore(obj);

        }

    } else {
        $(".resultitems").removeClass("hidden");
        onloadLoadmore(obj);
    }


}


function clearFilter(obj) {

    var searchcompId = $(obj).closest(".coe-searchcomp").attr("id");
    $("#" + searchcompId).find("input:checkbox:checked").prop('checked', false);
    $(".resultitems").addClass("hidden");
    $(".noresult").addClass("hidden");
    onloadLoadmore(obj);

}

function loadMore(obj) {
	var filterCategories = [];
    var searchcompId = $(obj).closest(".coe-searchcomp").attr("id");
    var loadmoreItemNumber=$("#" + searchcompId).attr('data-onloadItemCount');

    $("#" + searchcompId+" .category-section").each(function() {
        var selectedcount = $(this).find("input:checkbox:checked").length;
        if (selectedcount > 0) {
            filterCategories.push($(this).attr("data-category-name").toLowerCase());
        }

    });
     if (filterCategories.length == 0) {
		var loadmoreitemlength=$("#" + searchcompId+" .resultitems.loadmore.hidden").length
        $("#" + searchcompId+" .resultitems.loadmore.hidden").each(function(index) {
            if(index<loadmoreItemNumber){
                $(this).removeClass("loadmore");
                $(this).removeClass("hidden");
            }


        });
         if(loadmoreitemlength<=loadmoreItemNumber){

				$("#" + searchcompId+" .loadmorediv").addClass("hidden"); 
            }

     }else{
		var loadmoreitemlength=$("#" + searchcompId+" .resultitems.filtered.loadmore.hidden").length
        $("#" + searchcompId+" .resultitems.filtered.loadmore.hidden").each(function(index) {
            if(index<loadmoreItemNumber){
                $(this).removeClass("loadmore");
               	$(this).removeClass("hidden");
            }


        });
         if(loadmoreitemlength<=loadmoreItemNumber){

				$("#" + searchcompId+" .loadmorediv").addClass("hidden"); 
            }


     }


}

function onloadLoadmore(obj){
		var searchcompId = $(obj).closest(".coe-searchcomp").attr("id");
     	var loadmoreItemNumber=$("#" + searchcompId).attr('data-onloadItemCount');

    var loadmoreitemlength=$("#" + searchcompId+" .resultitems").length
        $("#" + searchcompId+" .resultitems").each(function(index) {
            if(index<loadmoreItemNumber){
                $(this).removeClass("loadmore filtered hidden");
                $(this).removeClass("");
            }else{
						$(this).addClass("loadmore hidden");

            }


        });
         if(loadmoreitemlength<=loadmoreItemNumber){

				$("#" + searchcompId+" .loadmorediv").addClass("hidden"); 
         }else{
				$("#" + searchcompId+" .loadmorediv").removeClass("hidden"); 
         }

}