let $rating;        
let $content;
let $point;
let userPoint;
let $totalAmount;
const $wine_id = $('input[name=wine_id]').val();
const $wine_type = $('input[name=wine_type]').val();
const $wine_serialkey = $('input[name=wine_serialkey]').val();
const $wine_name = $('#wine>#info>span:nth-child(1)').html();
const $stockPrice = $('#window>#wine>#stock>div:nth-child(2)>span').html();

$(function(){
    console.log("sjehrmfo?");
    const moneyFormat = function(m){
        // if(m){
            let money = m.toString();
            let result = "";
            let first = money.length % 3;
            for(i = 0; i < first; i++){
                result += money[i];
                (i === first - 1) && (result += ',');
            }
            
            let count = 0;
            for(i = first; i < money.length; i++)  {
                result += money[i];
                count++;
                if((count === 3) && (i !== money.length - 1)){
                    result += ',';
                    count = 0;
                }
            }
            return result;
        // }
    }
    //구매내역 등록
    const buyHistorySave = function(user_uid, wine_id, quantity, paymentKey, totalAmount){
        $.ajax({
            type: "POST",
            url: conPath + "/buy/historySave",
            data: {
                "wine_id" : wine_id,
                "quantity" : quantity,
                "paymentKey" : paymentKey,
                "totalAmount" : totalAmount,
            },
            cache: false,
            success: function(data, status, xhr){
                if(status == "success"){
                    if(data.status !== "OK"){
                        alert(data.status); return 
                    }
                }
            },
        });
        location.reload();
    }
    //포인트 사용으로 인한 포인트 삭감
    const pointCut = function(point){
        $.ajax({
            type: "POST",
            url: conPath + "/point/cut",
            data: {
                "point" : $point,
                "user_uid" : $user_uid,
                "wine_id" : $wine_id,
            },
            cache: false,
            success: function(data, status, xhr){
                if(status == "success"){
                    if(data.status !== "OK"){
                        alert(data.status); return }
                }
            },
        });
    }

    //Wine DB -> 구매로 인한 wine_count 변경
    const buyWine = function(id, quantity){
        $.ajax({
            type: "POST",
            url: conPath + "/wine/buy",
            data: {
                "wine_id" : id,
                "quantity" : quantity,
            },
            cache: false,
            success: function(data, status, xhr){
                if(status == "success"){
                    if(data.status !== "OK"){
                        alert(data.status); return }
                    if($point !== 0) pointCut();
                }
            },
        });
    }
    //결제 진행
    const payment = function(data, number){
        $.ajax({
            type: 'POST',
            cache: false,
            url: 'https://api.tosspayments.com/v1/payments/confirm',
            data: `{
                "amount":"${data.amount}",
                "orderId":"${data.orderId}",
                "paymentKey":"${data.paymentKey}"
            }`,
            processData: false, // FormData로 전송할때 꼭 써줘야할 부분. 안써주면 에러남
            contentType: false, // FormData로 전송할때 꼭 써줘야할 부분. 안써주면 에러남
            beforeSend : function(xhr){ // header 세팅
            xhr.setRequestHeader("Authorization", "Basic dGVzdF9za181bUJaMWdRNFlWWGRQVzcwYXBSVmwyS1BvcU5iOg==");
            xhr.setRequestHeader("Content-Type", "application/json");
            },
            success:function(data) {    //결제 완료
                if(data) alert("결제 되었습니다.");
                (data.status === "DONE")? //정상적으로 결제 완료
                    buyHistorySave($user_uid, $wine_id, number, data.paymentKey, data.totalAmount) :   //구매 내역 DB 등록 + point 등록
                    alert("결제가 비정상적으로 종료되었습니다.");
            },
            error:function(request,status,error){
                console.log("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
                alert('전송이 완료되지 않았습니다.');
            }
        });
    }
    //결제 버튼
    const payBtn = function(number){
        const clientKey = 'test_ck_lpP2YxJ4K870k2p4lXJ3RGZwXLOb';
        const tossPayments = TossPayments(clientKey) // 클라이언트 키로 초기화하기
        let temp_id = Date().split(/[:, ]/);
        let orderID = "";
        $totalAmount || ($totalAmount = number * $stockPrice);
        console.log(`payBtn_$totalAmount = ${$totalAmount}`);
        for(i = 0; i < 7; i++)  orderID += temp_id[i];
        tossPayments
        .requestPayment('카드',{
            // 결제 정보 파라미터
            amount: $totalAmount,
            orderId: orderID+$user_uid,
            orderName: `vvine_${$wine_name.split(" ").join("").replace("-","")}_${$wine_type}${$wine_serialkey}`,    //
            customerName: 'vvine_'+$user_uid,
        })
        .then(function (data) { // 결제 요청 성공
            if(data.paymentKey){    //결제 대기
                try {
                    buyWine($wine_id, number);  //DB
                    payment(data, number);      //결제진행
                } catch (error) {
                    alert(error + ": 결제오류");
                }
            }
        })
        .catch(function (error) {
            if (error.code === 'USER_CANCEL') { // 결제 고객이 결제창을 닫았을 때 에러 처리
                alert('결제가 취소되었습니다.');
            } else if (error.code === 'INVALID_CARD_COMPANY') { // 유효하지 않은 카드 코드에 대한 에러 처리
                alert('유효하지 않은 카드입니다.');
            }
        })

    }
    const resetList = function(wine_type, wine_serialkey){

        $.ajax({
            type: "POST",
            url: conPath + "/review/list",
            data: {
                "wine_serialkey" : wine_serialkey,
                "wine_type" : wine_type,
            },
            cache: false,
            success: function(data, status, xhr){
                if(status == "success"){
                    if(data.status !== "OK"){
                        alert(data.status);
                        return;
                    }
                }
                let reviewList = data.reviews;
                let reviewStr = [];
                for (const review of reviewList) {
                    let out =
                    `<li>
                        <div>
                            <span>${review.user.user_id}</span>
                            <span>${review.wnrv_regdate.replace("T"," ")}</span>`
                    if($user_uid == review.user.user_uid){
                        out += `<div><a class="reviewChange">수정</a><a class="reviewRemove">삭제</a></div>` }
                    out +=
                        `</div>
                        <div class="starScore"><span class="outer-star"><span class="inner-star"></span></span></div>
                        <div>${review.wnrv_content}</div>
                        <input type="hidden" value="${review.wnrv_reviews}" name="userScore" />
                        <input type="hidden" value="${review.wnrv_id}" name="id" />
                    </li>
                    `
                    reviewStr.push(out);
                }
                const formOut = `
                    <fieldset class="rating">
                        <input type="radio" id="star5" name="rating" value="10" /><label class = "full" for="star5"></label>
                        <input type="radio" id="star4half" name="rating" value="9" /><label class="half" for="star4half"></label>
                        <input type="radio" id="star4" name="rating" value="8" /><label class = "full" for="star4"></label>
                        <input type="radio" id="star3half" name="rating" value="7" /><label class="half" for="star3half"></label>
                        <input type="radio" id="star3" name="rating" value="6" /><label class = "full" for="star3"></label>
                        <input type="radio" id="star2half" name="rating" value="5" /><label class="half" for="star2half"></label>
                        <input type="radio" id="star2" name="rating" value="4" /><label class = "full" for="star2"></label>
                        <input type="radio" id="star1half" name="rating" value="3" /><label class="half" for="star1half"></label>
                        <input type="radio" id="star1" name="rating" value="2" /><label class = "full" for="star1"></label>
                        <input type="radio" id="starhalf" name="rating" value="1" /><label class="half" for="starhalf"></label>
                    </fieldset>
                    <div>0</div>`

                $('form[name=reviewForm]>#star').html(formOut);
                $('#coment>input[name=wnrv_content]').val("");
                $('#comentList').html(reviewStr.join("\n"));
                $rating = null;

                for (const el of $('#comentList>li')) {
                    const $userScore = el.getElementsByTagName('input')[0].defaultValue;
                    const $innerStar = $(el).children('div.starScore').children('span').children('span');
                    $innerStar.toggleClass(`score_${$userScore}`);
                }
                $('.reviewChange').on('click',function(){
                    let $id = $(this).parent().parent().siblings('input[name=id]').val();
                    if($(this).html() == "수정"){
                        $content = $(this).parent().parent().siblings('div').next().html();    //바뀌기 전
                        //div:content
                        $(this).parent().parent().siblings('div').next().css('display','none');
                        $(this).parent().parent().siblings('div').parent().append(`<input type="text" name="content" class="contentInput"/>`);
                        //input
                        $(this).parent().parent().siblings('input[name=content]').focus();
                        $(this).parent().parent().siblings('input[name=content]').val($content);
                        $(this).html("완료");
                    }else{
                        $content = $(this).parent().parent().siblings('input[name=content]').val();
                        ($id) && reviewUpdate($id, $content);
                        $content = null;
                    }
                });
                $('.reviewRemove').on('click',function(){
                    let $id = $(this).parent().parent().siblings('input[name=id]').val();
                    confirm('삭제하시겠습니까?') && reviewDelete($id);
                })
                $('.rating > label').mouseover(function(){ $('#star>div').html($(this).prev().val()); });
                $('.rating > label').mouseout(function(){ $rating && $('#star>div').html($rating); });
                $('.rating > label').click(function(){ $rating = $(this).prev().val(); });
            },
        });
        //

    }
    const reviewUpdate = function(id, content){
        $.ajax({
            type: "POST",
            url: conPath + "/review/update",
            data: {
                "wnrv_content" : content,
                "wnrv_id" : id,
            },
            cache: false,
            success: function(data, status, xhr){
                if(status == "success"){
                    if(data.status !== "OK"){ alert(data.status); return; }
                    resetList($wine_type, $wine_serialkey);
                }
            },
        });
    }
    const reviewDelete = function(id){
        $.ajax({
            type: "GET",
            url: conPath + "/review/delete",
            data: {"wnrv_id" : id,},
            cache: false,
            success: function(data, status, xhr){
                if(status == "success"){
                    alert('삭제되었습니다.')
                    resetList($wine_type, $wine_serialkey);
                }
            },
        });
    }
    const totalAmountChange = function(){
        if($('#point>input[type=number]').val() > userPoint){
            $('#point>input[type=number]').val(userPoint)
        }
        const $price = $('#buyPopup>div:nth-child(3)>span>select option:selected').val() * $stockPrice;
        let $point = Number.parseInt($('#point>input[type=number]').val());
        $point || ($point = 0);
        $totalAmount = $price - $point
        $('#total>span>span').html(moneyFormat($totalAmount));
    }
    //
    $('.rating > label').mouseover(function(){ $('#star>div').html($(this).prev().val()); });
    $('.rating > label').mouseout(function(){ $rating && $('#star>div').html($rating); });
    $('.rating > label').click(function(){ $rating = $(this).prev().val(); });

    //review 수정 Btn
    $('.reviewChange').on('click',function(){
        let $id = $(this).parent().parent().siblings('input[name=id]').val();
        if($(this).html() == "수정"){
            $content = $(this).parent().parent().siblings('div').next().html();    //바뀌기 전
            //div:content
            $(this).parent().parent().siblings('div').next().css('display','none');
            $(this).parent().parent().siblings('div').parent().append(`<input type="text" name="content" class="contentInput"/>`);
            //input
            $(this).parent().parent().siblings('input[name=content]').focus();
            $(this).parent().parent().siblings('input[name=content]').val($content);
            $(this).html("완료");
        }else{
            $content = $(this).parent().parent().siblings('input[name=content]').val();
            ($id) && reviewUpdate($id, $content);
            $content = null;
        }
    });
    //review 삭제 Btn
    $('.reviewRemove').on('click',function(){
        let $id = $(this).parent().parent().siblings('input[name=id]').val();
        confirm('삭제하시겠습니까?') && reviewDelete($id);
    })
    //review 등록 Btn
    $('#coment>input[name=reviewBtn]').on('click',function(){
        const $wnrv_content = $(this).siblings('input[type=text]').val();
        if(!$rating || ($rating === 0)){
            alert('별점을 매겨주세요!');
        }else{
            $.ajax({
                type: "POST",
                url: conPath + "/review/insert",
                data: {
                    "wnrv_content" : $wnrv_content,
                    "wnrv_reviews" : $rating,
                    "wine_serialkey" : $wine_serialkey,
                    "wine_type" : $wine_type,
                },
                cache: false,
                success: function(data, status, xhr){
                    if(status == "success"){
                        if(data.status !== "OK"){
                            alert(data.status);
                            return;
                        }
                        resetList($wine_type, $wine_serialkey);
                        $(this).siblings('input[type=text]').val("");
                    }
                },
            });
        }
    });
    $('input[name=buyBtn]').on('click',function(){
        const $wineImg = $('#window>#wine>img').attr('src');
        const $wineName = $('#window>#wine>#info>span:nth-child(1)').html();
        const $wineLocation = $('#window>#wine>#info>span:nth-child(2)').html();
        const $stockTotal = $('#window>#wine>#stock>div:nth-child(1)>#stockTotal').html();


        //화면 검정 불투명
        $('body').prepend('<div id="windowBlack"></div>');
        //팝업창
        $('body').append(`<div id='buyPopup'></div>`);
        let left = ($(window).width() - $('body>div#buyPopup').width()) / 2;
        $('body>div#buyPopup').css('left', `${left}px`);
        $('body>div#buyPopup').append(`<div></div><div></div><div></div>`);

        //  content
        const $imgDiv = $('body>div#buyPopup>div:nth-child(1)');
        const $infoDiv = $('body>div#buyPopup>div:nth-child(2)');
        const $buyDiv = $('body>div#buyPopup>div:nth-child(3)');

        $imgDiv.html(`<img src="${$wineImg}" />`);
        $infoDiv.html(`
            <span>${$wineName}</span>
            <span>${$wineLocation}</span>
            <span><span>${moneyFormat($stockPrice)}</span>원</span>
        `);
        $.ajax({
            type: "POST",
            url: conPath + "/point/totalByUid",
            data: {
                "user_uid" : $user_uid,
            },
            cache: false,
            success: function(data, status, xhr){
                if(status == "success"){
                    if(data.status !== "OK"){
                        alert(data.status); return }
                    userPoint = data.count;
                    //
                    $buyDiv.html(`
                    <span>수량<select></select></span>
                    <span><span></span></span><div id="point"><a>포인트</a><span><a>${userPoint}</a>point</span><input type="number" min="0" max="${userPoint}" step="10"/></div>
                    <div id="total">합계<span><span>${moneyFormat($stockPrice)}</span>원</span></div>
                    <input type="button" name="payBtn" value="결제" />
                    `);
                    //
                    for(i = 1; i <= $stockTotal; i++ ) {$buyDiv.children('span').children('select').append(`<option value="${i}">${i}</option>`);}
                    $buyDiv.children('span').children('span').html(`원`);
                    $buyDiv.children('span').children('span').prepend('<span></span>');

                    $buyDiv.children('span').children('span').children('span').html(moneyFormat($('#buyPopup>div:nth-child(3)>span>select option:selected').val() * $stockPrice));
                    $buyDiv.children('span').children('select').change(function(){
                        const number = $('#buyPopup>div:nth-child(3)>span>select option:selected').val();
                        $('#buyPopup > div:nth-child(3) > span:nth-child(2) > span > span').fadeOut(0);
                        $('#buyPopup > div:nth-child(3) > span:nth-child(2) > span > span').fadeIn(300);

                        $buyDiv.children('span').children('span').children('span').html(moneyFormat( (number * $stockPrice)));
                        totalAmountChange();
                    });

                    $('#point>input[type=number]').on("propertychange change keyup paste input",function(){
                        totalAmountChange();
                    })

                    // 결제 버튼
                    $('input[name=payBtn]').click(function(){
                        const number = $('#buyPopup>div:nth-child(3)>span>select option:selected').val();
                        $point = Number.parseInt($('#point>input[type=number]').val());
                        $point || ($point = 0);
                        console.log(`payBtn_$point0 = ${$point}`);
                        $.ajax({
                            type: "POST",
                            url: conPath + "/wine/stockCheck",
                            data: {
                                "serialkey" : $wine_serialkey,
                                "type" : $wine_type,
                                "quantity" : number,
                            },
                            cache: false,
                            success: function(data, status, xhr){
                                if(status == "success"){
                                    if(data.status == "SHORTAGE"){
                                        alert(data.status + ": 재고 수량이 부족합니다."); return }
                                    else if(data.status !== "OK"){
                                        alert(data.status); return }
                                    try {
                                        console.log(`payBtn_$point1 = ${$point}`);
                                        console.log(`payBtn_number = ${number}`);
                                        payBtn(number); 
                                    } catch (error) { 
                                        alert(error); }
                                }
                            },
                        });

                        $('body>div#windowBlack').remove();
                        $('body>div#buyPopup').remove();
                    });                        
                }
            },
        });
        
        //
        $('body>div#windowBlack').click(function(){
            $('body>div#buyPopup').remove();
            $(this).remove();
        });
        $(window).resize(function(){
            left = ($(window).width() - $('body>div#buyPopup').width()) / 2;
            $('body>div#buyPopup').css('left', `${left}px`);
        });
    });
    
    //review 리스트
    for (const el of $('#comentList>li')) {
        const $userScore = el.getElementsByTagName('input')[0].defaultValue;
        const $innerStar = $(el).children('div.starScore').children('span').children('span');
        $innerStar.toggleClass(`score_${$userScore}`);
    }
    //format
    $('#stock>div:nth-child(2)>span').html(moneyFormat($('#stock>div:nth-child(2)>span').html()));

})