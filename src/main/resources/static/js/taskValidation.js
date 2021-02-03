$().ready(function () {

    $('#taskform').validate({
        rules: {
            project: {
                required: true,
                maxlength: 5
            },
            summary: {
                required: true,
                minlength: 5
            },
            description: {
                maxlength: 4000
             }
        }
    });
});