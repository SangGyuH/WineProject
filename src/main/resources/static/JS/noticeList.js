$(function(){

    $(".nice-select").click(function(){
        var nice_select=$("#nice-select").attr("class");
        if (nice_select = "nice-select open"){
            $("[name='pageRowsList'] > *").click(function(){
                var data_value = $(this).attr("value")
                var frm = $("[name='frmPageRows']")
                $("input[name='pageRows']").attr("value", parseInt(data_value, 10))
                frm.attr("method", "POST")
                frm.attr("action", "pageRows")
                frm.submit();
            });
        }
        var data_value = ""
    });

});