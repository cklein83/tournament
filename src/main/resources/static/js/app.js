$(document).ready(function () {

    //Toggle menupoints for corresponding viewport
    $(".dropdown").click(function () {
        var currentWindowsSize = $(window).width();
        if (currentWindowsSize < 767) {
            $(".dropdown-content").slideUp();
            $(".dropdown").removeClass("border-shadow-upper-lower");
            $(this).find(".dropdown-content").slideDown();
            $(this).addClass("border-shadow-upper-lower");
        }
    });

});