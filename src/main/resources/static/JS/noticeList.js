$(function(){


    $("[name='pageRows']").change(function(){
        var frm = $("[name='frmPageRows']")
        frm.attr("method", "POST")
        frm.attr("action", "pageRows")
        frm.submit();
    });


//    $(".pageRows").click(function(){
//        console.log("nice-select 클릭됨");
//    });
//
//    $(".pageRowsItem").click(function(){
//
//        location.href="/pageRows(page="+page+", pageRows" + pageRows + ")";
//    });
});

