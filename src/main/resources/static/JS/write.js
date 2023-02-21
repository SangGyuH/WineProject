$(document).ready(function(){

    $("#myInput").change(function(){

        var input = document.querySelector('#myInput');
        var preview = document.querySelector('.preview');

        const curFiles = input.files;

        for(const file of curFiles){
            $("#preview").append(`
                <div>${file.name}</div>
            `);
        }
    });
});