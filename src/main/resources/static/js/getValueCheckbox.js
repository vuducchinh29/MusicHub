$(document).ready(function () {
  $('.singer-name').click(function () {
    var val = [];
    $("input[type = 'checkbox']:checked").each(function () {
      val.push($(this).val());
    });
    console.log(val);
    $('#singer').val(val);
    //document.getElementById('singer').innerHTML = val;
  });
});

$(document).ready(function () {
  $('.playlist-name').click(function () {
    var val = [];
    $("input[type = 'checkbox']:checked").each(function () {
      val.push($(this).val());
    });
    console.log(val);
    $('#songIDs').val(val);
  });
});
