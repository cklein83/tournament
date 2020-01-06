$(document).ready(function () {
    // Responsible 4 different slide intervals
/*    $('#myCarousel').on('slid.bs.carousel', function () {

        *//*
        var duration = $(this).find('.active').attr('data-interval');

        $('#myCarousel').carousel("pause")
        setTimeout(function () {
            $('#myCarousel').carousel("next")
        }, duration)
        *//*

        $('#myCarousel').carousel();
    })*/

    $('#myCarousel').carousel({
        keyboard: false,
        pause: false
    });
});

function backend_submitMatchRow(parent) {

//alert($(parent));

    console.log("goals1: " + $(parent).children("input[name='goals1']").val());
    console.log("goals2: " + $(parent).children("input[name='goals2']").val());
    console.log("status: " + $(parent).children("select[name='status']").val());

    var form = $(parent).children("form");
    form.submit();
}

function backend_incGoal(elem) {
    var i = $(elem).val();
    $(elem).val(++i);
}

function backend_decGoal(elem) {
  var i = $(elem).val();
  if (i > 0) {
      $(elem).val(--i);
  }
}

function backend_ajaxSubmit(form) {
    $.post("/backend/matches", $(form).serialize())
}

function backend_highlightByStatus(elem) {
    $(elem).parents("tr").removeClass();
    if ($(elem).val() == "started") {
        $(elem).parents("tr").addClass("bg-info");
    } else if ($(elem).val() == "finished") {
        $(elem).parents("tr").addClass("bg-warning");
    }
}