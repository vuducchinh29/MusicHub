//Scroll to comment area
$("#comment-btn").click(function() {
  $('html, body').animate({
    scrollTop: $("#comment").offset().top
  }, 1000);
});
