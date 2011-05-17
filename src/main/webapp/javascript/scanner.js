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
    var db = window.openDatabase("ofilestorage", "1.0", "Ovewaitea Scanner Settings", 1024, null);
    if (!db) {
        alert("Failed to connect to database.");
    }
    createTable(db);
    
    $("#form-login").submit(function() {
        $.mobile.changePage($("#results"));
        insertURL(db, "http://tomnightingale.com");
        return false;
    });

    // Perform actions when the Login page is first created.
    $('#login').live('pagecreate', function(event) {
        console.log("Login page created.");
    });
    
    // Perform actions when the results page is first created.
    $('#results').live('pagecreate', function(event){
        console.log("Results page created.");
        
        // Load scanner right away.
        scanBarcode();

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

function moveTo() {
    var id = 'login-field-password';
    console.log("Move to: " + id);
    blackberry.focus.setFocus(id);
}

/*********************************************************************
 * Database stuff
 ********************************************************************/
function createTable(db) {
  db.transaction(
    function(transaction) {
      transaction.executeSql(
        'CREATE TABLE serverLoc(url VARCHAR(5) PRIMARY KEY, urlValue VARCHAR(30))', [],
        function() {
          console.log("Create YAY");
        },
        function(transaction, error) {
          console.log('Create FAIL ' + error.message);
        }
      );
      
    }
  );
}

function insertURL(db, newUrl) {
  db.transaction(
    function(transaction) {
      transaction.executeSql('INSERT INTO serverLoc (urlValue) VALUES (?,?) WHERE ...', ["main",newUrl],
        function() {
          console.log("Insert YAY");
        },
        function(transaction, error) {
          console.log('Insert FAIL ' + error.message);
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