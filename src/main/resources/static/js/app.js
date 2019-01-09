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

function backend_submitMatchRow(parent) {

//alert($(parent));

    console.log("goals1: " + $(parent).children("input[name='goals1']").val());
    console.log("goals2: " + $(parent).children("input[name='goals2']").val());
    console.log("status: " + $(parent).children("select[name='status']").val());

    var form = $(parent).children("form");
    form.submit();
}