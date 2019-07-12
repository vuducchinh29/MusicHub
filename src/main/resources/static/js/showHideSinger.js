let toggle = true;
let showsinger = document.getElementById('showSinger');
let hidesinger = document.getElementById('hideSinger');
let listsingercheckbox = document.getElementById('listsingercheckbox');
listsingercheckbox.style.display = 'none';
hidesinger.style.display = 'none';

function showHideSinger() {
  if (toggle) {
    listsingercheckbox.style.display = 'block';
    showsinger.style.display = 'none';
    hidesinger.style.display = 'block';
    toggle = false;
  } else {
    listsingercheckbox.style.display = 'none';
    showsinger.style.display = 'block';
    hidesinger.style.display = 'none';
    toggle = true;
  }
}
