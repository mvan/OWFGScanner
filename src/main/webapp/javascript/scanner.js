$(function() {
    try {
        // TODO: For development purposes only, should be removed when complete.
        clearCache();
    } catch (e) {
        alert("clearCache(): Exception occured: " + e.name + "; " + e.message);
    }
    
    $(".page").initPageManager();
    
    try {
        // Initialize application.
        init();
    } catch (e) {
        alert("init(): Exception occured: " + e.name + "; " + e.message);
    }
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
 * Initializes misc application stuff including:
 * - Binding click events to perform extra actions.
 */
function init() {
  var authSuccess = function(stores) {
    var storeList = document.getElementById('store');

    $(stores).each(function(index, element) {
      var option = new Option(element, element);
      storeList.options[index] = option;
    });

    scanBarcode();

    changePage("#results");
  };

  // Initialize persistent configuration.
  var config = $.fn.Config(function() {
    var username = config.getVar("username", null);
    var password = config.getVar("password", null);

    $("#login-field-username").val(username);
    $("#login-field-password").val(password);

    // Authentication information exists.
    if (username && password) {
      changePage("#loading");

      getStores(username, password, authSuccess);
    }
    // No authentication information.
    else {
      changePage("#login");
    }
  });

  $("#button-login-submit").click(function() {
      var username = $("#login-field-username").val();
      var password = $("#login-field-password").val();
      changePage("#loading");
      getStores(username, password, authSuccess);
  });

  $("#button-config-submit").click(function() {
      var config = $.fn.Config();
      config.setVar("url", $("#config-field-url").val());
  });
  
  $("#results-submit").click(function() {
      var config = $.fn.Config();
      var username = config.getVar("username", "");
      var password = config.getVar("password", "");
      var store = $("#store").val();
      var upc = $("#upc").val();
      
      changePage("#loading");

      setStore(username, password, store, function() {
        changePage("#results");
        getInfo(username, password, upc);
      });
  });

  $("#results").live("page-opened", function() {
      loadMenuItems("#results");

      var config = $.fn.Config();
      config.setVar("username", $("#login-field-username").val());
      config.setVar("password", $("#login-field-password").val());

  });

  $("#forecast").live("page-opened", function() {
      loadMenuItems("#forecast");
  });

  $("#loading").live("page-opened", function() {
      loadMenuItems("#loading");
  });

  $("#config").live("page-opened", function() {
      loadMenuItems("#config");

      var config = $.fn.Config();
      $("#config-field-url").val(config.getVar("url", "https://simdv1.owfg.com:8443/caos/StoreManagement"));
  });
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

function getStores(username, password, success) {
  // Error callback.
  var error = function(message) {
      var storeList = document.getElementById('store');
      alert('Error: ' + message);
      storeList.options.length = 0;
      changePage("#login");
  };

  // Block for desktop browser testing.
  if (typeof webservice === "undefined") {
    success([]);
    //error("");
    return;
  }

  var config = $.fn.Config();
  var address = config.getVar("url", "https://simdv1.owfg.com:8443/caos/StoreManagement");

  //address = "https://simdv1.owfg.com:8443/caos/StoreManagement";
  webservice.client.query(success, error, address, username, password, "getStores", "");
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

function getInfo(username, password, upc) {
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
        $("input#field-forecast").val(forcast);
    };

    // Error callback.
    var error = function() {
        alert('Error');
    };

    var config = $.fn.Config();
    var address = config.getVar("url", "https://simdv1.owfg.com:8443/caos/StoreManagement");

    webservice.client.query(success, error, address, username, password, "getInfo", upc);
}

function setStore(username, password, store, success) {
    // Error callback.
    var error = function() {
        alert('Error');
    };

    var pieces = store.split(" ")
    var storeId = pieces[0];
    var config = $.fn.Config();
    var address = config.getVar("url", "https://simdv1.owfg.com:8443/caos/StoreManagement");

    extraargs = 1; //needs to be a long or something that'll cast to a long

    webservice.client.query(success, error, address, username, password, "setStore", storeId);
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

    clearMenuItems();

    // Menus for results page.
    var items = [];
    if (page === "#results") {
        try {
            items[0] = new blackberry.ui.menu.MenuItem(false, 1, "Scan", scanBarcode);
            items[1] = new blackberry.ui.menu.MenuItem(false, 2, "Logout", function() { changePage("#login"); });
            items[2] = new blackberry.ui.menu.MenuItem(false, 3, "Config", function() { changePage("#config"); });
        }
        catch (e) {
            alert("Exception: new MenuItem(); " + e.name + '; ' + e.message);
        }
    }
    else if (page === "#forecast") {
        try {
            items[0] = new blackberry.ui.menu.MenuItem(false, 1, "Back", function() { changePage("#results"); });
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

function clearMenuItems() {
    // Clear any existing menu items.
    try {
        if (blackberry.ui.menu.getMenuItems().length > 0) {
            blackberry.ui.menu.clearMenuItems();
        }
    }
    catch (e) {
        alert("Exception: clearMenuItems(); " + e.name + '; ' + e.message);
    }
}

