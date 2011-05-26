(function($) {

  function exception(e, info) {
    alert("Exception: " + e + "; " + e.name);
  }

  /**
   * TODO: Document the hell out of this.
   */
  $.fn.Config = function(initCallback, errorCallback) {
    var instance = (function() {
      /************************************************************************
      * Private members
      ************************************************************************/
      var DB_NAME      = "owfg_config";
      var DB_VERSION   = "1.0";
      var DB_DISPLAY   = "Overwaitea Scanner Configuration Settings";
      var DB_SIZE      = 1024;

      var config = {};

      // Initialize the database.
      var db = (function() {
        // Get databse; if doesn't exist, create it.
        try {
          var database = window.openDatabase(DB_NAME, DB_VERSION, DB_DISPLAY, DB_SIZE);
          if (typeof database === 'undefined') {
            errorCallback();
          }
        }
        catch (e) {
          exception(e, "Could not get database instance.");
        }
      
        // Create config table to store key -> value pairs.
        try {
          database.transaction(function(transaction) {
            var query = 'CREATE TABLE IF NOT EXISTS config_map (key VARCHAR(10) PRIMARY KEY, val VARCHAR(100))';
            var args = [];
            transaction.executeSql(query, args);
          });
        }
        catch (e) {
          exception(e, "Could not create table.");
        }
        
        return database;
      })();

      updateCache(initCallback);

      /**
       *
       */
      function update(key, newVal) {
        try {
          db.transaction(function(transaction) {
            var args = [newVal, key];
            var query = 'UPDATE config_map SET val=? WHERE key=?';
            transaction.executeSql(query, args);
          });
        }
        catch (e) {
          exception(e, "Could not update variable.");
        }
      }

      /**
       *
       */
      function insert(key, newVal) {
        try {
          db.transaction(function(transaction) {
            var query = 'INSERT INTO config_map (key, val) VALUES (?,?)';
            var args = [key, newVal];
            transaction.executeSql(query, args);
          });
        }
        catch (e) {
          exception(e, "Could not insert variable.");
        }
      }

      /**
       *
       */
      function updateCache(callback) {
        try {
          db.transaction(function(transaction) {
            var query = 'SELECT * FROM config_map';
            var args = [];
            transaction.executeSql(query, args, function(tx, result) {
              for (var i = 0; i < result.rows.length; i++) {
                var row = result.rows.item(i);
                config[row.key] = row.val;
              }
              if (callback) callback();
            });
          });
        }
        catch (e) {
          exception(e, "Could not update config cache.");
        }
      }
      
      /************************************************************************
      * Public members
      ************************************************************************/
      var publicInterface = { 
        /**
         *
         */
        setVar : function(key, val) {
          if (config[key]) {
            update(key, val);
          }
          else {
            insert(key, val);
          }
          config[key] = val;
        },
        
        /**
         *
         */
        getVar : function(key, defVal) {
          return (config[key]) ? config[key] : defVal;
        }
      };
    
      return publicInterface;
    })();

    // Redefine the constructor for subsequent calls.
    $.fn.Config = function(initCallback) {
        return instance;
    };

    return $.fn.Config();
  }
})(jQuery);
