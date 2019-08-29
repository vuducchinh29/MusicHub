$(document).ready(function () {
  $("#uploadAvatarButton").click(function (event) {
    event.preventDefault();
    uploadAvatar();
  });
});

function uploadAvatar() {
  let form = $('#avatarUploadForm')[0];
  let data = new FormData(form);
  $("#uploadAvatarButton").prop("disabled", true);
  $.ajax({
    type: "POST",
    enctype: 'multipart/form-data',
    url: "/files/upload-image",
    data: data,
    processData: false,
    contentType: false,
    cache: false,
    timeout: 1000000,
    success: function (data) {
      linkAvatar.value = data;
    },
    error: function () {
      linkAvatar.value = "Fail to upload. Please refresh page and try again."
    }
  });
}
