$(document).ready(function() {
    applyLayoutContainerClass();
 });
 
 $(window).resize(function() {
    applyLayoutContainerClass();
 });
 
 function applyLayoutContainerClass() {
     $('.custom-layout-container-parameters').each(function(){
         var componentID, desktopClass, mobileClass;
         componentID ="#" + $(this).find('.componentclass').attr('value');
         desktopClass =$(this).find('.desktopclass').attr('value');
         mobileClass =$(this).find('.mobileClass').attr('value');
 
         var divObject = $(componentID);
         if (divObject.attr("Data-click-target") >= "")
         {
             divObject.click(function() {window.location.href=divObject.attr("Data-click-target")});
             divObject.css("cursor","pointer");
         }
         
         if($(window).width() <= 896) {
             $(componentID).removeClass(desktopClass).addClass(mobileClass);
         }
         else {
             $(componentID).removeClass(mobileClass).addClass(desktopClass);
         }
 
     });
 }