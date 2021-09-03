/**
 *  개인 / 기업 마이페이지로 이동
 */
console.log('login 성공  - js/mypage.js')
checkUserType();

function moveToMyPage(){
	var $mypage = $('#my-page');
	var $section = $('#page-top > section.base');
	console.log("$mypage :"+ $mypage);
	console.log("$section :"+ $section);
	$mypage.click(function(e){
	$(document).off();
		var href = $(this).attr('href');
	     $section.load(href, function(responseTxt, statusTxt, xhr){ // 에러창 띄우기용 콜백 펑션 + href+'xxx'
	         if(statusTxt == "error")
	           alert("Error: " + xhr.status + ": " + xhr.statusText);
	     		});
		return false;
	});
}

function checkUserType(){
	var url = "/Add_it/acc";
	$.ajax({
		url: url,
		success: function (responseObj) {
			var $mypage = $('#my-page');
			if(responseObj.userType==1) { // 개인 회원
				console.log('개인 회원');
				$mypage.attr('href', './indi/indi_side.html'); // ./indi/indi_side.html
				moveToMyPage();
			} else if(responseObj.userType==2) { // 기업 회원
				console.log('기업 회원');
				$mypage.attr('href', './com/mypage_first.html');
				moveToMyPage();
			} else { // 개인 회원
				console.log('회원 유형 없음');
			}
			console.log("$mypage.attr('href') :"+$mypage.attr('href'));
		}	
	});
	
	
}