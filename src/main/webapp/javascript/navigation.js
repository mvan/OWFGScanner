var activePage = "";
var pageHistory = [];

(function($) {
  /**
   * Initialize navigation between pages.
   * - Scans html document for page divs and indexes them.
   * - Makes the 1st page div visible.
   * - Finds all elements with class="nav" and a data-dest attributes and bind's
   *   their click events.
   */
  $.fn.initPageManager = function() {
    var pages = [];

    // Identify all pages.
    return this.each(function(i, e) {
      var element = $(e);
      pages[i] = "#" + element.attr("id");

      if (i == 0) {
        activePage = pages[i];
        $(activePage).trigger("page-opened");
        element.show();
      }

      // Bind page change events
      $(".nav", this).each(function(i, e) {
          var element = $(e);
          element.click(function() {
              var dest = element.attr("data-dest");
              changePage(dest);
              return false;
          });
      });
    });
  }

})(jQuery);

function changePage(id, record) {
  var ignore = record || false;
  $(activePage).hide();
  activePage = id;

  if (!ignore && pageHistory[pageHistory.length - 1] !== id) {
    pageHistory.push(id);
  }

  $(activePage).trigger("page-opened");
  $(activePage).show();
}

function backPage() {
  if (pageHistory.length == 1) {
    return false;
  }

  // Pop current page off.
  pageHistory.pop();

  // Ok to pop last page off as changePage() will push it back on.
  var last = pageHistory.pop();
  changePage(last);

  return true;
}

function clearHistory() {
  pageHistory = [];
  pageHistory.push(activePage);
}
