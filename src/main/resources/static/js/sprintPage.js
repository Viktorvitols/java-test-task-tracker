var createNewSprint = document.getElementById("createNewSprint");
var sprintBoard = document.getElementById("sprintBoard");
var deleteSprint = document.getElementById("deleteSprint");

var selectedSprint = $(":selected").val();
$('#showSprintBtn').click(function(){
    deleteSprint.classList.remove('hidden');
    if (!createNewSprint.classList.contains('hidden')){
        createNewSprint.classList.add('hidden');
        }
    if(selectedSprint != 0) {
        createNewSprint.classList.add('hidden');
        window.location.href = "http://localhost:8090/sprint/" + selectedSprint;
        }
    })

$("select.sprintSelector").change(function() {
    selectedSprint = $(this).children("option:selected").val();
})

$('#newSprintBtn').click(function() {
    window.location.href = "http://localhost:8090/sprint/new";
    createNewSprint.classList.remove('hidden');
    if (!sprintBoard.classList.contains('hidden')) {
        sprintBoard.classList.add('hidden');
        }
    })



$('#deleteSprintBtn').click(function() {
    $('#delDialog').dialog({
        'buttons': {
            'My Button': function(event) {
                // here is the modification of the button
                // opacity set to 25%, all events unbound
                $(event.target).css({opacity: 0.25}).unbind();
            }
        }
    })
})