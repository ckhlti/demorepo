(function($, $document, Coral) {


$(document).on("click",".cq-dialog-submit", function(e) {

    console.log('====== DIALOG READY ======');
var options = {
                val1: 'C#',
                val2: 'PHP'
            };
            var selectOption = $('#category');
            $.each(options, function (val, text) {
                selectOption.append(
                    $('<option></option>').val(val).html(text)
                );
            });

var $formminmax = $(this).closest("form.foundation-form");


    var mySelect = $('#category');
  //  console.log("Select is::"+mySelect);



 var featuretype = $formminmax.find("[name='./type']").val();
     // console.log("Select is::"+featuretype);


var field = $formminmax.find("[data-granite-coral-multifield-name='./features']");

var totalLinkCount = field.children('coral-multifield-item').length;

    var prodData = []; //This will store the multifield data from the dialog.

for (var i = 0; i < totalLinkCount; i++) {

      prodData.push(field.children('coral-multifield-item').find($("[name='./featureName']")[i]).val());


console.log("Multifield is ::"+field.children('coral-multifield-item')[i]);


}
    console.log("Multifield is ::"+prodData);
alert("test");


});
})($, $(document), Coral);