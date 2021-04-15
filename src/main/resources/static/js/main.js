$(document).ready(function(){
    $("select.statusSelector").change(function(){
        var selectedStatus = $(this).children("option:selected").val();
        tasklist.forEach(function(task) {
            var taskRow = document.getElementById("taskId" + task.id)
            taskRow.classList.remove('hidden');
            }
        );
        tasklist.forEach(function(task) {
        		var taskRow = document.getElementById("taskId" + task.id)
        		if( !(task.status == selectedStatus)){
        			taskRow.classList.add('hidden');
        		}
        });
    });
});

$('#reloadBtn').click(function() {
    location.reload();
});