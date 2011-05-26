
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

function getHistory(username, password, upc) {
    // Error callback.
    var error = function() {
        alert('Error');
    };
    var success = function(message) {
        $("input#SunReg").val(message[0][3]);
        $("input#SunSale").val(message[0][4]);
        $("input#monReg").val(message[1][3]);
        $("input#monSale").val(message[1][4]);
        $("input#tueReg").val(message[2][3]);
        $("input#tueSale").val(message[2][4]);
        $("input#wedReg").val(message[3][3]);
        $("input#wedSale").val(message[3][4]);
        $("input#thursReg").val(message[4][3]);
        $("input#thursSale").val(message[4][4]);
        $("input#friReg").val(message[5][3]);
        $("input#friSale").val(message[5][4]);
        $("input#satReg").val(message[6][3]);
        $("input#satSale").val(message[6][4]);
    };

  queryWebservice("getHistory", upc, username, password, success, error);
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
