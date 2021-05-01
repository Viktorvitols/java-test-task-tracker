var createNewSprint = document.getElementById("createNewSprint");
var deleteSprint = document.getElementById("deleteSprint");

$( document ).ready(function() {
if (selectedSprint = 0) {
    alert("hide that shit");
    sprintBoard.style.display = "none";
    }
});

$('#showSprintBtn').click(function(){
    var selectedSprint = $(":selected").val();
    alert("selected sprint: " + selectedSprint);
    if (selectedSprint != 0) {
        window.location.href = "http://localhost:8090/sprint/" + selectedSprint;
    } else {
        window.location.href = "http://localhost:8090/sprint/";
    }
});

$("select.sprintSelector").change(function() {
    selectedSprint = $(this).children("option:selected").val();
});

function show_hide() {
  var sprintBoard = document.getElementById("sprintBoard");
  if (sprintBoard.style.display === "none") {
    sprintBoard.style.display = "block";
  } else {
    sprintBoard.style.display = "none";
  }
}