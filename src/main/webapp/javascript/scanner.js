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
    //alert("Clearing cache...");

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
    var database = window.openDatabase("ofilesystem", "1.0", "Ovewaitea Scanner Settings", 1024, null);
    if(!database) {
      alert('DB not created.');
    } else {
      createTable(database);
      readDatabase(database, "url", parseReturnData);
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
        readDatabase(database, "url", parseReturnData);
    });
    
    $("#form-results").submit(function() {
        alert("results");
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

/**
 * Manages menu items.
 * - Clears the menu item list.
 * - Builds new menu item list for current page.
 */
function loadMenuItems(page) {
    // Block for desktop browser testing.
    if (typeof blackberry === 'undefined') {
        return;
    }

    // Clear any existing menu items.
    try {
        if (blackberry.ui.menu.getMenuItems().length > 0) {
            blackberry.ui.menu.clearMenuItems();
        }
    }
    catch (e) {
        alert("Exception: clearMenuItems(); " + e.name + '; ' + e.message);
    }

    // Menus for results page.
    var items = [];
    if (page === "#results") {
        try {
            items[0] = new blackberry.ui.menu.MenuItem(false, 1, "Scan", scanBarcode);
        }
        catch (e) {
            alert("Exception: new MenuItem(); " + e.name + '; ' + e.message);
        }
    }
    
    // Add items to the menu.
    try {
        $(items).each(function(index, item) {
            blackberry.ui.menu.addMenuItem(item);
        });
    }
    catch (e) {
        alert("Exception: addMenuItem(); " + e.name + '; ' + e.message);
    }
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
      transaction.executeSql('CREATE TABLE IF NOT EXISTS serverLoc(id VARCHAR(10) PRIMARY KEY, val VARCHAR(100))', [],
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
      transaction.executeSql('UPDATE serverLoc SET val=? WHERE id=?', [newVal, key],
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

function readDatabase(db, key, successCallbackFunction) {
  db.transaction(
    function(transaction) {
      transaction.executeSql('SELECT * FROM serverLoc WHERE id=?', [key], successCallbackFunction);
    }
  );
}

function parseReturnData(transaction, result) {
  $("input#login-field-url").val(renderToDo(result.rows.item(0)));
}

function renderToDo(row) {
  return row.val;
}

