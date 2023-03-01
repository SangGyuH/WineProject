document.getElementById("submitBtn").addEventListener("click", function(event){
  event.preventDefault();
  result = window.confirm('정말 회원탈퇴를 하시겠습니까?');
  if( result === false ) {
    return false;
  }

  let password = document.getElementById("password").value;
  let re_password = document.getElementById("re_password").value;
  if(password === '' ||  re_password === ''){
      alert('패스워드를 확인하세요');
    return false;
  }

  if(password === re_password) {
    let myform = document.getElementById("myform");
    myform.submit();
  }else {
    alert('패스워드를 확인하세요');
    return false;
  }
});