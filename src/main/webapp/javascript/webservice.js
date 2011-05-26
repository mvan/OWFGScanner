
function queryWebservice(query, args, username, password, success, error) {
  // Block for desktop browser testing.
  if (typeof webservice === "undefined") {
    success([]);
    //error("");
    return;
  }

  //success();
  //return;

  var config = $.fn.Config();
  var address = config.getVar("url", "https://simdv1.owfg.com:8443/caos/StoreManagement");

  webservice.client.query(success, error, address, username, password, query, args);
}

function getStores(username, password, success, error) {
  queryWebservice("getStores", "", username, password, success, error);
}

function getBanners(username, password, success, error) {
    queryWebservice("getBanners", "", username, password, success, error);
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

    queryWebservice("getInfo", upc, username, password, success, error);
}

function setStore(username, password, store, success, error) {
    var pieces = store.split(" ")
    var storeId = pieces[0];

    queryWebservice("setStore", storeId, username, password, success, error);
}
