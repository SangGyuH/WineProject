function checkAgree(){
    let agree = document.getElementsByName("agree");
    for(e of agree){
        if(!e.checked){
            alert(`${e.value} 항목에 동의하셔야 합니다`);
            return false;
        }
    }
}