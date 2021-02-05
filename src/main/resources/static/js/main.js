var modal = document.getElementById("createTask");
var btn = document.getElementById("taskBtn");
var span = document.getElementsByClassName("close")[0];
var edittask = document.getElementById("edittaskform");

btn.onclick = function() {
  modal.style.display = "block";
}

span.onclick = function() {
  modal.style.display = "none";
}

edittask.onclick = function() {
  modal.style.display = "block";
}