$(document).ready(function() {
    // TODO: For development purposes only, should be removed when complete.
    clearCache();
    
    // Initialize application.
    init();
});


function clearCache() {
    alert("Clearing cache...");

    // Block for desktop browser testing.
    if (typeof blackberry === 'undefined') {
        console.log("clearCache(): No blackberry object detected.");
        return;
    }

    blackberry.widgetcache.clearAll();
}

function init() {
    // Perform actions when the results page is first created.
    $('#results').live('pagecreate', function(event){
        // Load scanner right away.
        scanBarcode();
        getStores();
        // Initiate barcode scanner when "scan" button pressed.
        $("a#scan").click(function() {
            scanBarcode();
        });
    });
}

function scanBarcode() {
    // Block for desktop browser testing.
    if (typeof barcode === 'undefined') {
        console.log("scanBarcode(): No barcode object detected.");
        return;
    }

    barcode.scanner.scan(
        // Success.
        function(message) {
            console.log('Scanned: ' + message);
            $("input#upc").val(message);
        }, 
        // Error.
        function(error) {
            alert('Error: ' + error);
        });
}

function getStores() {
	var success = function() {
		alert('Success');
	};
	var error = function() {
		alert('Error');
	};
	
	address = "https://warrenv.dlinkddns.com/StoreManagement-ws";
	user = "test"; //tget from div#login-username
	password = "test"; //get from div#login-password
	extraargs = "";
	webservice.client.query(success, error, address, user, password, extraargs);
}
