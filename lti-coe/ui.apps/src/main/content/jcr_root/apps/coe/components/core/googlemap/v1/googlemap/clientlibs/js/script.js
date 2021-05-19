function initMap() {
	var mapProp= {
		center:new google.maps.LatLng(33.7678358,-84.4906438),
		zoom:5,
	};
	var map = new google.maps.Map(document.getElementById("googleMap"),mapProp);

	document.getElementById("submit").addEventListener("click", () => {
    	geocodeAddress(geocoder, map);
  	});


}



function geocodeAddress(geocoder, resultsMap) {
    const address = document.getElementById("address").value;
    geocoder.geocode({ address: address }, (results, status) => {
      if (status === "OK") {
        resultsMap.setCenter(results[0].geometry.location);
        new google.maps.Marker({
          map: resultsMap,
          position: results[0].geometry.location,
        });
      } else {
        alert("Geocode was not successful for the following reason: " + status);
      }
    });
  }