
let wineList = null;
let pageNum = 1, onePageTotal = 12, pageArrNum = 0;
let pages = [];
let $wine_type = "";


const movePage = function(number){
    if(number < 0) return;
    else if(number === pages.length) return;
    pageArrNum = number;
    let str = "";
    
    str += `<span onclick="movePage(${pageArrNum - 1})"><</span><div>`
        pages[pageArrNum].forEach(number => {
            if(number === pageNum)
            str += `<li onclick="setList(${number},${onePageTotal})" style="font-size: 20px; font-weight: bold;">${number}</li>`;
            else
            str += `<li onclick="setList(${number},${onePageTotal})">${number}</li>`;
        });
    str += `</div>`;
    str += `<span onclick="movePage(${pageArrNum + 1})">></span>`;
    
    document.getElementById("pageList").innerHTML = str;
    window.scroll(top);
}

const setList = function(currPage, onePageTotal){
    pageNum = currPage;
    let str = "";
    document.getElementById("resultList").innerHTML = "";
    for(count = (pageNum - 1) * onePageTotal; count < (pageNum * onePageTotal); count++){
        if(count >= wineList.length) break;
        let $wine = wineList[count];
        let $wine_location = $wine.location.split("\n·\n");                
        str += `
        <li>
            <span><img src="${$wine.image}"></span><span>${$wine.wine}</span><span><a>${$wine_location[0]}</a><a>${$wine_location[1]}</a></span>
            <input type="hidden" name="wine_serialkey" value="${$wine.id}" />
            <div><span><a></a></span><span></span><a>상세보기</a></div>
        </li>
            `;
    }
    document.getElementById("resultList").innerHTML += `<div><span>${wineList.length}</span>개의 검색 결과가 있습니다.</div>`
    document.getElementById("resultList").innerHTML += str;
    movePage(pageArrNum);

    $('#resultList>li').hover(function(){
        let $wine_serialkey = $(this).children('input[name=wine_serialkey]').val();
        let $this = $(this);
        const wine_data = {
            "wine_serialkey" : $wine_serialkey,
            "wine_type" : $wine_type,
        }
        //
        $.ajax({
            type: "POST",
            url: conPath + "/review/showScore",
            data: wine_data,
            cache: false,
            success: function(data, status, xhr){
                if(status == "success"){
                    if(data.status !== "OK"){
                        alert(data.status);
                        return;
                    }
                    let score = data.reviewScore;
                    let message = "";
                    if(score == 0) message = '등록된 후기가 없습니다.<span style="display: block;font-size: 12px;margin-top: 5px;opacity: 80%;">첫번째 후기를 남겨주세요!</span>'
                    else if(score < 5)  message = "나쁘지 않아요!"
                    else if(score < 8)  message = "GOOD"
                    else if(score < 10)  message = "추천합니다!"
                    else if(score == 10)  message = "강력 추천!"

                    $this.children('div').children('span').children('a').html('<i class="fa-solid fa-star"></i>' + score.toFixed(1));
                    $this.children('div').children('span:nth-child(2)').html(message);
                }
            },
        });
    });
    $('#resultList>li>div>a').on('click', function(){
        let $wine_serialkey = $(this).parent().siblings('input[name=wine_serialkey]').val();
        let $wine_img = $(this).parent().siblings('span:nth-child(1)').children('img').attr("src");
        let $wine_name = $(this).parent().siblings('span:nth-child(2)').html();
        let $wine_location = $(this).parent().siblings('span:nth-child(3)').children('a:nth-child(1)').html()
                        + " " + $(this).parent().siblings('span:nth-child(3)').children('a:nth-child(2)').html();

        const wineform = $("<form>", {
            method: "post",
            action: "/wine/detail",
        }).appendTo("body");
        wineform.append(`<input type="hidden" name="serialkey" value="${$wine_serialkey}">`);
        wineform.append(`<input type="hidden" name="type" value="${$wine_type}">`);
        wineform.append(`<input type="hidden" name="img" value="${$wine_img}">`);
        wineform.append(`<input type="hidden" name="name" value="${$wine_name}">`);
        wineform.append(`<input type="hidden" name="location" value="${$wine_location}">`);
        wineform.submit();
    });
}

$(function(){
    const connectAPI = function(word, serchType){
        $.ajax({
            type: "GET",
            url: "https://api.sampleapis.com/wines/" + $wine_type,
            data: {},
            success: function( result ) {
                wineList = [];
                switch (serchType) {
                    case "name":
                        /*TODO : 유효성 검사 - 빈칸, 대문자->소문자 */
                        result.forEach(el => {
                            el.wine.includes(word) && wineList.push(el);
                        });
                        break;
                    case "location":
                        // word = word.toLowerCase().replace(word[0], word[0].toUpperCase());
                        word = $('#search>div>select[name=selectLocation] option:selected').html();
                        result.forEach(el => {
                            el.location.includes(word) && wineList.push(el);
                        });
                    break;
                }

                if(wineList.length <= 0){
                    alert('검색된 결과가 없습니다.'); return;
                }

                pageNum = 1, pageArrNum = 0;
                let totalPage = (wineList.length / onePageTotal) - parseInt(wineList.length / onePageTotal) > 0?
                    parseInt(wineList.length / onePageTotal) + 1 : parseInt(wineList.length / onePageTotal);
                let p = 1, x = 0;
                pages = [];

                while (p <= totalPage) {
                    pages[x] = new Array;
                    for(y = 0; y < 5; y++)  
                        (p > totalPage ) || (pages[x][y] = p++);                
                    x++;
                }
                setList(1, onePageTotal);
            }
        });
    }
    const locationChange = function(){
        $wine_type = $('#wineTypeBtn>input[name="wine_type"]:checked').val();
        let locationArr = {};
        $.ajax({
            type: "GET",
            url: "https://api.sampleapis.com/wines/" + $wine_type,
            data: {},
            success: function( result ) {
                result.forEach(el => {
                    let lo = el.location.split("\n·\n")[0];
                    (lo == "") && (lo = "obscurity");
                    locationArr[lo] = (locationArr[lo])? locationArr[lo] + 1 : 1;
                });
                $('select[name=selectLocation]').html("");
                Object.keys(locationArr).forEach(l => {
                    $('select[name=selectLocation]').append(`<option value="${l}">${l}</option>`);
                });
            }
        });
    }
    $('#wineTypeBtn>input[name="wine_type"]').change(function(){
        locationChange();
    });
    $('#search > div:nth-child(2) > select:nth-child(1)').change(function(){
        if($(this).val() == 'name'){
            $('input[name=searchContent]').css("display", "inline-block");
            $('select[name=selectLocation]').css("display", "none");                
        }else if($(this).val() == 'location'){                
            $('input[name=searchContent]').css("display", "none");
            $('select[name=selectLocation]').css("display", "inline-block");
        }
    });

    $('#searchBtn').on("click", function(value){
        $wine_type = $('#wineTypeBtn>input[name="wine_type"]:checked').val();
        let $serchType = $('#search>div>select[name=searchType] option:selected').val();
        let $searchContent =  $('#search>div>input[name=searchContent]').val();
        // ajax : API
        connectAPI($searchContent, $serchType);

        switch ($wine_type) {
            case "reds": $wine_type = "red"; break;
            case "whites": $wine_type = "white"; break;
        }
    });
    $('#search>div>input[name=searchContent]').focus(function(){
        $(document).keypress(function(e) {
            if (e.keyCode == 13){ 
                $wine_type = $('#wineTypeBtn>input[name="wine_type"]:checked').val();
                let $serchType = $('#search>div>select[name=searchType] option:selected').val();
                let $searchContent =  $('#search>div>input[name=searchContent]').val();
                // ajax : API
                connectAPI($searchContent, $serchType);

                switch ($wine_type) {
                    case "reds": $wine_type = "red"; break;
                    case "whites": $wine_type = "white"; break;
                }
            }
        });
    });

    $(".chkbox").click(function(){
        $("input:checkbox[class='chkbox']").prop("checked", false);
        $(this).prop("checked", true);
        var chkid = $(this).attr("id");
        $("input:radio[name='wine_type']").prop("checked", false);

        switch(chkid){
            case "primary-radio-red" : $("#wineTypeBtn > input:radio[value='reds']").prop("checked" , true); break;
            case "primary-radio-white" : $("#wineTypeBtn > input:radio[value='whites']").prop("checked" , true); break;
            case "primary-radio-sparkling" : $("#wineTypeBtn > input:radio[value='sparkling']").prop("checked" , true); break;
            case "primary-radio-rose" : $("#wineTypeBtn > input:radio[value='rose']").prop("checked" , true); break;
            case "primary-radio-dessert" : $("#wineTypeBtn > input:radio[value='dessert']").prop("checked" , true); break;
            case "primary-radio-port" : $("#wineTypeBtn > input:radio[value='port']").prop("checked" , true); break;
        }
    });


    $(document).keypress(function(e) {
            if (e.keyCode == 13){ e.preventDefault(); }
    });

    locationChange();




});