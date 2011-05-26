(function($) {

  function exception(e, info) {
    alert("Exception: " + e + "; " + e.name);
  }

  /**
   * Configuration storage class (singleton). 
   *
   * Provides a Key -> Value persistant storage.
   * Can be invoked anywhere by calling the constructor found in the jQuery 
   * object: var config = $.fn.Config();
   *
   * Data is actively managed in the config object which is populated during
   * instantiation from the database. 
   * Whenever the object is updated the equivalent rows in the db are also 
   * updated.
   *
   * Author: Tom Nightingale.
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
          // No SD error handling for OS 5 (gears).
          if (typeof database === 'undefined') {
            errorCallback();
          }
        }
        catch (e) {
          // No SD error handling for OS 6 (webSQL).
          errorCallback();
        }
      
        // Create config table to store key -> value pairs.
        createTable(database);

        return database;
      })();

      // Populate the config object with values from the database.
      updateCache(initCallback);

      /**
       * Updates a value for a key in the db.
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
       * Inserts a new key-value into the db.
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
       * Retrieves all stored config variables and popultates the config object.
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

      /**
       *
       */
      function dropTable() {
        try {
          db.transaction(function(transaction) {
            var query = 'DROP TABLE config_map';
            var args = [];
            transaction.executeSql(query, args);
          });
        }
        catch (e) {
          exception(e, "Could not update config cache.");
        }
      }

      /**
       *
       */
      function createTable(database) {
        db = database || db;
        // Create config table to store key -> value pairs.
        try {
          db.transaction(function(transaction) {
            var query = 'CREATE TABLE IF NOT EXISTS config_map (key VARCHAR(10) PRIMARY KEY, val VARCHAR(100))';
            var args = [];
            transaction.executeSql(query, args);
          });
        }
        catch (e) {
          exception(e, "Could not create table.");
        }
      }
      
      /************************************************************************
      * Public members
      ************************************************************************/
      var publicInterface = { 
        /**
         * Sets a variable with a value.
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
         * Retrieves a value of a variable.
         *
         * @param String key    - The variable name.
         * @param Object defVal - A default value if no matching key
         *                        exists.
         */
        getVar : function(key, defVal) {
          return (config[key]) ? config[key] : defVal;
        },
        
        /**
         * Drops the config table and creates a new one.
         */
        reset : function() {
          dropTable();
          createTable();
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
