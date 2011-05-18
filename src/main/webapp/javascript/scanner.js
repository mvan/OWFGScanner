var pages = [];
var activePage = "undefined";

$(document).ready(function() {
    // TODO: For development purposes only, should be removed when complete.
    clearCache();
    
    // Initialize navigation.
    initNav();
    
    // Initialize database.
    initDB();
    
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
 * Initialize the database for persistent storage.
 * - Loads database if one exists, otherwise:
 * - Creates a database if none exists.
 */
function initDB() {
    var database = window.openDatabase("ofilesystem", "1.0", "Ovewaitea Scanner Settings", 1024, null);

    // If unable to load the database, quite likely no SDCard is present.
    // TODO: Application should probably terminate if this fails.
    if (!database) {
        alert('Unable to create database: Application requires SDCard for external storage.');
    } 
    else {
      createTable(database);
      readDatabase(database, "url", updateUrlField);
    }
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
        updateRow(database, "url", $("#config-field-url").val());
        readDatabase(database, "url", updateUrlField);
    });
    
    $("#results-submit").click(function() {
        getInfo();
        //insertURL(db, "http://tomnightingale.com");
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
            getStores();
        }, 
        // Error.
        function(error) {
            alert('Error: ' + error);
        });
}

function getStores() {
    // Success callback.
    var success = function(stores) {
        var option;
        var storeList = document.getElementById('store');
        $(stores).each(function(index, element) {
            option = new Option(element, element);
            storeList.options[index] = option;
        });
    };

    // Error callback.
    var error = function() {
        var storeList = document.getElementById('store');
        alert('Error');
        storeList.options.length = 0;
    };

    address = 'https://warrenv.dlinkddns.com/StoreManagement-ws';
    user = 'test'; //tget from div#login-username
    password = 'test'; //get from div#login-password
    fnname = 'getStores';
    extraargs = '';

    webservice.client.query(success, error, address, user, password, fnname, extraargs);
}

function getBanners() {
    // Success callback.
    var success = function() {
        alert('Success');
    };

    // Error callback.
    var error = function() {
        alert('Error');
    };

    address = 'https://warrenv.dlinkddns.com/StoreManagement-ws';
    user = 'test'; //tget from div#login-username
    password = 'test'; //get from div#login-password
    fnname = 'getBanners';
    extraargs = '';

    webservice.client.query(success, error, address, user, password, fnname, extraargs);
}

function getInfo() {
    // Success callback.
    var success = function(boh,
                           forcast,
                           inTransit,
                           itemDesc,
                           min,
                           onOrder,
                           pack,
                           promotion,
                           regularPrice,
                           source,
                           storeId,
                           upc) {
        $("h3#product-name").html(itemDesc);
        $("input#boh").val(boh);
        $("input#intransit").val(inTransit);
        $("input#minimum").val(min);
        $("input#onorder").val(onOrder);
        $("input#pack").val(pack);
        $("input#regprice").val(regularPrice);
        $("input#source").val(source);
        $("input#forecast").val(forcast);
    };

    // Error callback.
    var error = function() {
        alert('Error');
    };

    address = 'https://warrenv.dlinkddns.com/StoreManagement-ws';
    user = 'test'; //tget from div#login-username
    password = 'test'; //get from div#login-password
    fnname = 'getInfo';
    extraargs = '';

    webservice.client.query(success, error, address, user, password, fnname, extraargs);
}
function setStore() {
    // Success callback.
    var success = function() {
        alert('Success');
    };

    // Error callback.
    var error = function() {
        alert('Error');
    };

    address = 'https://warrenv.dlinkddns.com/StoreManagement-ws';
    user = 'test'; //tget from div#login-username
    password = 'test'; //get from div#login-password
    fnname = 'setStore';
    extraargs = 1; //needs to be a long or something that'll cast to a long

    webservice.client.query(success, error, address, user, password, fnname, extraargs);
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

