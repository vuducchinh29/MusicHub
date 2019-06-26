$(document).ready(function () {
  $("#uploadSongButton").click(function (event) {
    event.preventDefault();
    uploadSong();
  });
});

function uploadSong() {
  let form = $('#songUploadForm')[0];
  let data = new FormData(form);
  $("#uploadSongButton").prop("disabled", true);
  $.ajax({
    type: "POST",
    enctype: 'multipart/form-data',
    url: "http://localhost:8080/uploadSong",
    data: data,
    processData: false,
    contentType: false,
    cache: false,
    timeout: 1000000,
    success: function (data) {
      linkSong.value = data;
    },
    error: function () {
    }
  });
}
