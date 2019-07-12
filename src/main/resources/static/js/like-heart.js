let heart = document.getElementById('heart');
let hearto = document.getElementById('heart-o');
let toggle = true;

heart.style.display = 'none';
function like() {
  if (toggle) {
    heart.style.display = 'inline-block';
    hearto.style.display = 'none';
    toggle = false;
  } else {
    hearto.style.display = 'inline-block';
    heart.style.display = 'none';
    toggle = true;
  }
}
