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
        return;
    }

    blackberry.widgetcache.clearAll();
}

function init() {
    
    var sPath = window.location.href;
    var sPage = sPath.substring(sPath.lastIndexOf('/') + 1);

    var db = window.openDatabase("ofilestorage", "1.0", "Ovewaitea Scanner Settings", 1024, null);
    if (!db) {
        alert("Failed to connect to database.");
    } else {
      createTable(db);
    }
    
    $("#form-login").submit(function() {
    });
    
    $("#button-config").click(function() {
        $.mobile.changePage($("#urlConfig"));
        return false;
    });
    
    $("#form-urlConfig").submit(function() {
        $.mobile.changePage($("#login"));
        insertURL(db, $("#urlConfig-field-newUrl").val());
        return false;
    });
    
    $("#form-results").submit(function() {
        alert("results");
        //insertURL(db, "http://tomnightingale.com");
    });

    // Perform actions when the Login page is first created.
    $('#login').live('pagecreate', function(event) {
    });
    
    // Perform actions when the results page is first created.
    //$('#results').live('pagecreate', function(event){
    if (sPage == "#results") {
    alert(sPage);
        
        // Load scanner right away.
        scanBarcode();

        // Initiate barcode scanner when "scan" button pressed.
        $("a#scan").click(function() {
            scanBarcode();
        });
    }
    //});
}

function scanBarcode() {
    alert("scanBarcode");
    // Block for desktop browser testing.
    if (typeof barcode === 'undefined') {
        return;
    }

    barcode.scanner.scan(
        // Success.
        function(message) {
            $("input#upc").val(message);
        }, 
        // Error.
        function(error) {
            alert('Error: ' + error);
        });
}

function moveTo() {
    var id = 'login-field-password';
    blackberry.focus.setFocus(id);
}

/*********************************************************************
 * Database stuff
 ********************************************************************/
function createTable(db) {
  db.transaction(
    function(transaction) {
      transaction.executeSql('DROP TABLE serverLoc', [],
        function() {
          //console.log("Drop YAY");
        },
        function(transaction, error) {
          //console.log('Drop FAIL ' + error.message);
        });
          
      transaction.executeSql('CREATE TABLE serverLoc(id VARCHAR(10) PRIMARY KEY, val VARCHAR(100))', [],
        function() {
          //console.log("Create YAY");
        },
        function(transaction, error) {
          //console.log('Create FAIL ' + error.message);
        });
        
      transaction.executeSql('INSERT INTO serverLoc (id, val) VALUES (?, ?)', ["url", "https://simdv1:8443/caos/StoreManagement?wsdl"],
        function() {
          //console.log("Insert YAY");
        },
        function(transaction, error) {
          //console.log('Insert FAIL ' + error.message);
        });
    }
  );
}

function updateRow(db, key, newVal) {
  db.transaction(
    function(transaction) {
      transaction.executeSql('UPDATE serverLoc SET val=? WHERE id =?', [newVal, key],
        function() {
          //console.log("Insert YAY");
        },
        function(transaction, error) {
          //console.log('Insert FAIL ' + error.message);
        });
    }
  );
}

function readURL(db) {
  db.transaction(
    function(transaction) {
      transaction.executeSql('SELECT * FROM serverLoc', null, null);
    }
  );
}