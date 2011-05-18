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
    
    $("#results-submit").click(function() {
        getInfo();
        //insertURL(db, "http://tomnightingale.com");
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
            getStores();
        }, 
        // Error.
        function(error) {
            alert('Error: ' + error);
        });
}
/*
function  (address, user, pass, arg) {
    webservice.client.query(
        // Success.
        function(message) {alert('Scanned: ' + message);}, 
        // Error.
        function(error) {alert('Error: ' + error);},
        address, user, pass, arg
    );
}
*/

function getStores() {
    var success = function(stores) {
        var option;
        var storeList = document.getElementById('store');
        $(stores).each(function(index, element) {
            option = new Option(element, element);
            storeList.options[index] = option;
        });
    };
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
    var success = function() {
        alert('Success');
    };
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
    var success = function() {
        alert('Success');
    };
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

