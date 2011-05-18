var pages = [];
var activePage = "undefined";

$(document).ready(function() {
    // TODO: For development purposes only, should be removed when complete.
    clearCache();
    
    initNav();
    
    // Initialize application.
    init();
});

function clearCache() {
    //alert("Clearing cache...");

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
    var database = window.openDatabase("ofilesystem", "1.0", "Ovewaitea Scanner Settings", 1024, null);
    if(!database) {
      alert('DB not created.');
    } else {
      createTable(database);
      readDatabase(database, "url", updateUrlField);
    }
    $("#button-login-submit").click(function() {
        scanBarcode();
    });

    // Initiate barcode scanner when "scan" button pressed.
    $("a#scan").click(function() {
        scanBarcode();
    });
    
    $("#button-config-submit").click(function() {
        updateRow(database, "url", $("#config-field-url").val());
        readDatabase(database, "url", updateUrlField);
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
    loadMenuItems(activePage);
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

function loadMenuItems(page) {
    // Block for desktop browser testing.
    if (typeof blackberry === 'undefined') {
        return;
    }
    
    /*
    if (page === "#results") {
        try {
            var item = new blackberry.ui.menu.MenuItem(false, 1, "Scan", scanBarcode);
            blackberry.ui.menu.addMenuItem(item);
        } catch (e) {
            alert("Exception (addMenus): " + e.name + '; ' + e.message);
        }
    }
    */
}

function customMenuItemClick() {
  alert("user just clicked me");
}

/*********************************************************************
 * Database stuff
 ********************************************************************/
function createTable(db) {
  db.transaction(
    function(transaction) {
      transaction.executeSql('CREATE TABLE IF NOT EXISTS serverLoc(id VARCHAR(10) PRIMARY KEY, val VARCHAR(100))', []);
        
      transaction.executeSql('INSERT INTO serverLoc (id, val) VALUES (?, ?)', ["url", "https://simdv1:8443/caos/StoreManagement?wsdl"]);
    }
  );
}

function updateRow(db, key, newVal) {
  db.transaction(
    function(transaction) {
      transaction.executeSql('UPDATE serverLoc SET val=? WHERE id=?', [newVal, key]);
    }
  );
}

function insertRow(db, key, newVal) {
  db.transaction(
    function(transaction) {
      transaction.executeSql('INSERT INTO serverLoc (id, val) VALUES (?,?)', [key, newVal]);
    }
  );
}

function readDatabase(db, key, successCallbackFunction) {
  var result = [];
  db.transaction(
    function(transaction) {
      transaction.executeSql('SELECT * FROM serverLoc WHERE id=?', [key], function(tx, rs) {
        var row = rs.rows.item(0);
        successCallbackFunction(row);
      });
    }
  );
}

function updateUrlField(row) {
  $("input#login-field-url").val(row.val);
  $("input#config-field-url").val(row.val);
}

