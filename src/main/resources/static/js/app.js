$(document).ready(function () {
    // Responsible 4 different slide intervals
    $('#myCarousel').on('slid.bs.carousel', function () {

        var duration = $(this).find('.active').attr('data-interval');

        $('#myCarousel').carousel("pause")
        setTimeout(function () {
            $('#myCarousel').carousel("next")
        }, duration)
    })
});