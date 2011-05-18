var pages = [];
var activePage = "undefined";

$(document).ready(function() {
    // TODO: For development purposes only, should be removed when complete.
    //clearCache();
    
    initNav();
    
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

function initNav() {
    // Identify all pages.
    $("div.page").each(function(i, e) {
        var element = $(e);
        pages[i] = "#" + element.attr("id");

        if (i == 0) {
            activePage = pages[i];
            element.show();
        }
    });

    // Bind page change events
    $(".nav").each(function(i, e) {
        var element = $(e);
        element.click(function() {
            changePage(element.attr("data-dest"));
            return false;
        });
    });
}

function init() {
    var db = window.openDatabase("ofilesystem", "1.0", "Overwaitea File Storage", 1024);
    if(!db) {
      alert('DB not created.');
    } else {
      alert('db created');
      createTable(db);
    }
    $("#button-login-submit").click(function() {
        scanBarcode();
    });

    // Initiate barcode scanner when "scan" button pressed.
    $("a#scan").click(function() {
        scanBarcode();
    });
    
    $("#button-config-submit").click(function() {
        updateRow(db, "url", $("#urlConfig-field-newUrl").val());
    });
    
    $("#form-results").submit(function() {
        alert("results");
        return false;
    });
}

function changePage(id) {
    $(activePage).hide();
    $(id).show();
    activePage = id;
}

function scanBarcode() {
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

/*********************************************************************
 * Database stuff
 ********************************************************************/
function createTable(db) {
  db.transaction(
    function(transaction) {
      transaction.executeSql('CREATE TABLE IF NOT EXISTS serverLoc(id VARCHAR(10) PRIMARY KEY, val VARCHAR(100))', [],
        function() {
          //console.log("Create YAY");
        },
        function(transaction, error) {
          //console.log('Create FAIL ' + error.message);
        });
        
      transaction.executeSql('INSERT INTO serverLoc (id, val) VALUES (?, ?)', ["url", "https://simdv1:8443/caos/StoreManagement?wsdl"]);
    }
  );
}

function updateRow(db, key, newVal) {
  db.transaction(
    function(transaction) {
      transaction.executeSql('UPDATE serverLoc SET val=? WHERE id =?', [newVal, key],
        function() {
          //console.log("Update YAY");
        },
        function(transaction, error) {
          //console.log('Update FAIL ' + error.message);
        });
    }
  );
}

function insertRow(db, key, newVal) {
  db.transaction(
    function(transaction) {
      transaction.executeSql('INSERT INTO serverLoc (id, val) VALUES (?,?)', [key, newVal],
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
      transaction.executeSql('SELECT * FROM serverLoc', [], null, null);
    }
  );
}

