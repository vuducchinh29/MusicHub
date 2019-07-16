function likePlaylist(id) {
  $.ajax({
    type: "GET",
    enctype: 'multipart/form-data',
    url: "/playlists/like/" + id,
    success: function (data) {
      if (data === 'no login') {
        alert("Please login to like this playlist");
      } else if (data === "not found") {
        alert("Playlist not found")
      } else {
        document.getElementById("count-like").innerText = data;
      }
    },
    error: function () {
      alert("Error! Please try again.")
    }
  });
}

function likeSong(id) {
  $.ajax({
    type: "GET",
    enctype: 'multipart/form-data',
    url: "/songs/like/" + id,
    success: function (data) {
      if (data === 'no login') {
        alert("Please login to like this playlist");
      } else if (data === "not found") {
        alert("Playlist not found")
      } else {
        document.getElementById("count-like").innerText = data;
      }
    },
    error: function () {
      alert("Error! Please try again.")
    }
  });
}
