function submitBtn() {
    if (!confirm("정말 탈퇴하시겠습니까?")) {
        alert("회원탈퇴를 취소합니다");
    } else if(submitBtn.checked){
        alert("정상적으로 탈퇴처리 되었습니다");
    }
}
