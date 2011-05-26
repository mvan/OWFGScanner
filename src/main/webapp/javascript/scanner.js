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
    // Block for desktop browser testing.
    if (typeof blackberry == 'undefined' || typeof blackberry.widgetcache === 'undefined') {
        return;
    }

    blackberry.widgetcache.clearAll();
}

/**
 * Initializes application event handling:
 */
function init() {
  // Action to take when login authorization is successful.
  var authSuccess = function(stores) {
    var storeList = document.getElementById('store');

    $(stores).each(function(index, element) {
      var option = new Option(element, element);
      storeList.options[index] = option;
    });
    
    var store = $.fn.Config().getVar("store", "");
    $("#store").val(store);

    scanBarcode();

    changePage("#results");
  };

  // Action to take when login authorization fails.
  var authError = function(message) {
      alert('authError: ' + message);
      changePage("#login");
  };

  // Bind action to take when the back button is pressed.
  bindBackEvent(function() {
      // If there are no pages in the nav history, prompt user about exiting
      // application.
      if (!backPage()) {
          try {
              var message = "Exit application?";
              var ret = blackberry.ui.dialog.standardAsk(blackberry.ui.dialog.D_YES_NO, message, blackberry.ui.dialog.C_NO, false);
          }
          catch (e) {
              alert("standardAsk(): (dialog) Exception occured: " + e.name + "; " + e.message);
          }

          if (ret == blackberry.ui.dialog.C_YES) {
              exit();
          }
      }
  });

  // Initialize persistent configuration.
  var config = $.fn.Config(
      function() {
          var username = config.getVar("username", null);
          var password = config.getVar("password", null);

          $("#login-field-username").val(username);
          $("#login-field-password").val(password);

          // Authentication information exists.
          if (username && password) {
              changePage("#loading", true);
              getStores(username, password, authSuccess, authError);
          }
          // No authentication information.
          else {
            changePage("#login");
          }
      }, 
      // Error setting up config.
      function() {
          alert("No external storage detected.\nPlease insert SD card and try again.");
          exit();
      }
  );

  $("#button-login-submit").click(function() {
      changePage("#loading", true);
      var username = $("#login-field-username").val();
      var password = $("#login-field-password").val();

      getStores(username, password, authSuccess, authError);
  });

  $("#button-config-submit").click(function() {
      var config = $.fn.Config();
      config.setVar("url", $("#config-field-url").val());
      config.setVar("develop", $("#config-field-develop").is(":checked"));
  });

  $("#button-config-reset").click(function() {
      var config = $.fn.Config();
      config.reset();
      exit();
  });
  
  $("#results-submit").click(function() {
      var config = $.fn.Config();
      var username = config.getVar("username", "");
      var password = config.getVar("password", "");
      var store = $("#store").val();
      var upc = $("#upc").val();
      
      changePage("#loading", true);

      config.setVar("store", store);

      setStore(username, password, store,
        // Success.
        function() {
          changePage("#results");
          getInfo(username, password, upc);
        },
        // Error.
        function() {
          alert("Error: could not set the store.");
        }
      );
  });

  $("#results").live("page-opened", function() {
      loadMenuItems("#results");
      clearHistory();

      var config = $.fn.Config();
      config.setVar("username", $("#login-field-username").val());
      config.setVar("password", $("#login-field-password").val());
  });

  $("#login").live("page-opened", function() {
      loadMenuItems("#login");
      clearHistory();
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
      var checked = (config.getVar("develop", false) === "false") ? false : true;
      if (checked) {
        $("#config-field-develop").attr("checked", "checked");
      }
      else {
        $("#config-field-develop").removeAttr("checked");
      }
  });
}

function bindBackEvent(action) {
    // Block for desktop browser testing.
    if (typeof blackberry === 'undefined') {
        return;
    }

    try {
        blackberry.system.event.onHardwareKey(blackberry.system.event.KEY_BACK, action);
    } catch (e) {
        alert("onHardwareKey(): Exception occured: " + e.name + "; " + e.message);
    }
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

    clearMenuItems();

    // Menus for results page.
    var items = [];
    items[0] = new blackberry.ui.menu.MenuItem(false, 1, "Back", function() { backPage(); });

    if (page === "#results") {
        try {
            items[1] = new blackberry.ui.menu.MenuItem(false, 2, "Scan", scanBarcode);
            items[2] = new blackberry.ui.menu.MenuItem(false, 3, "Logout", function() { changePage("#login"); });
            items[3] = new blackberry.ui.menu.MenuItem(false, 4, "Config", function() { changePage("#config"); });
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

function exit() {
    if (typeof blackberry === "undefined") {
        return;
    }
    try {
        clearMenuItems();
        blackberry.app.exit();
    }
    catch (e) {
        alert("exit(): Exception occured: " + e.name + "; " + e.message);
    }
}
