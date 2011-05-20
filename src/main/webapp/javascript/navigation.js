$(function() {
  var activePage = "";

  initNav();


  /**
   * Initialize navigation between pages.
   * - Scans html document for page divs and indexes them.
   * - Makes the 1st page div visible.
   * - Finds all elements with class="nav" and a data-dest attributes and bind's
   *   their click events.
   */
  function initNav() {
    var pages = [];

    // Identify all pages.
    $("div.page").each(function(i, e) {
      var element = $(e);
      pages[i] = "#" + element.attr("id");

      if (i == 0) {
        activePage = pages[i];
        $(activePage).trigger("page-opened");
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

  function changePage(id) {
    $(activePage).hide();
    activePage = id;
    $(activePage).trigger("page-opened");
    $(activePage).show();
  }
});
