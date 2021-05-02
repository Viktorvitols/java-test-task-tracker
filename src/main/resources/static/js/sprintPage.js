$("select.sprintSelector").change(function() {
    selectedSprint = $(this).children("option:selected").val();
});

function show_sprint() {
    var sprintBoard = document.getElementById("sprintBoard");
    var selectedSprint = $(":selected").val();
    if (selectedSprint != 0) {
        window.location.href = "http://localhost:8090/sprint/" + selectedSprint;
        sprintBoard.style.display = "block";
    } else {
        window.location.href = "http://localhost:8090/sprint/";
        sprintBoard.style.display = "none";
    }
}

function show_sprint_by_date() {
    var sprintBoard = document.getElementById("sprintBoard");
    var selectedSprint = $(":selected").val();
    if (selectedSprint != 0) {
        window.location.href = "http://localhost:8090/sprint/" + selectedSprint + "/by-date";
        sprintBoard.style.display = "block";
    } else {
        window.location.href = "http://localhost:8090/sprint/";
        sprintBoard.style.display = "none";
    }
}