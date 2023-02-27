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


    // [삭제] 버튼 누르면 삭제될 첨부파일 id 담기
    $("[data-fileid-del]").click(function(){
    //        alert($(this).attr('data-fileid-del'));
        let write_file_id = $(this).attr('data-fileid-del');
        deleteFiles(write_file_id);
        $(this).parent().remove()
    });




});

function deleteFiles(write_file_id){
   // 삭제할 file 의 id 값(들)을 #delFiles 에 담아 submit 한다
   $("#delFiles").append(`<input type='hidden' name='delfile' value='${write_file_id}'>`);
}
