var pages = [];
var activePage = "undefined";

$(document).ready(function() {
    // TODO: For development purposes only, should be removed when complete.
    clearCache();
    
    // Initialize navigation.
    initNav();
    
    // Initialize application.
    init();
});


/**
 * Helper function to clear cache while in development.
 * Protected for safe usage in desktop browser.
 */
function clearCache() {
    alert("Clearing cache...");

    // Block for desktop browser testing.
    if (typeof blackberry === 'undefined') {
        return;
    }

    blackberry.widgetcache.clearAll();
}

/**
 * Initialize navigation between pages.
 * - Scans html document for page divs and indexes them.
 * - Makes the 1st page div visible.
 * - Finds all elements with class="nav" and a data-dest attributes and bind's
 *   their click events.
 */
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

/**
 * Initializes misc application stuff including:
 * - Binding click events to perform extra actions.
 */
function init() {
    
    $("#button-login-submit").click(function() {
        scanBarcode();
    });

    // Initiate barcode scanner when "scan" button pressed.
    $("a#scan").click(function() {
        scanBarcode();
    });
    
    $("#button-config-submit").click(function() {
        //insertURL(db, $("#urlConfig-field-newUrl").val());
    });
    
    $("#form-results").submit(function() {
        alert("results");
        //insertURL(db, "http://tomnightingale.com");
        return false;
    });
}

/**
 * Navigation helper.
 * - Hides active page. 
 * - Shows and activates destination page
 */
function changePage(id) {
    $(activePage).hide();
    $(id).show();
    activePage = id;
    loadMenuItems(activePage);
}

/**
 * Starts barcode scanner.
 * - On sucess, updates the #upc field.
 * - On fail (timeout), displays error message.
 *
 * TODO: Should probably take callback functions as arguments.
 */
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
/*function createTable(db) {
  db.transaction(
    function(transaction) {
      transaction.executeSql('DROP TABLE serverLoc', [],
        function() {
          console.log("Drop YAY");
        },
        function(transaction, error) {
          console.log('Drop FAIL ' + error.message);
        });
          
      transaction.executeSql('CREATE TABLE serverLoc(url VARCHAR(100))', [],
        function() {
          //console.log("Create YAY");
        },
        function(transaction, error) {
          console.log('Create FAIL ' + error.message);
        });
        
      transaction.executeSql('INSERT INTO serverLoc (url) VALUES (?)', ["https://simdv1:8443/caos/StoreManagement?wsdl"],
        function() {
          console.log("Insert YAY");
        },
        function(transaction, error) {
          console.log('Insert FAIL ' + error.message);
        });
    }
  );
}*/

/*function insertURL(db, newUrl) {
  db.transaction(
    function(transaction) {
      transaction.executeSql('UPDATE serverLoc SET url=?', [newUrl],
        function() {
          //console.log("Insert YAY");
        },
        function(transaction, error) {
          //console.log('Insert FAIL ' + error.message);
        });
    }
  );
}
*/
/*
function readURL(db) {
  db.transaction(
    function(transaction) {
      transaction.executeSql('SELECT * FROM serverLoc', null, null);
    }
  );
}*/
