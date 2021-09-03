<%-- <%@page import="com.cos.dto.SecretVO"%>
<%@page import="com.cos.dao.SecretDAO"%> --%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<% 
	request.setCharacterEncoding("UTF-8");  //한글깨지면 주석제거
	//request.setCharacterEncoding("EUC-KR");  //해당시스템의 인코딩타입이 EUC-KR일경우에
/* 	String inputYn = request.getParameter("inputYn"); 
	String roadFullAddr = request.getParameter("roadAddrPart1"); 
	String roadAddrPart1 = request.getParameter("roadAddrPart2"); 
	String roadAddrPart2 = request.getParameter("roadFullAddr");
	String addrDetail = request.getParameter("addrDetail"); 
	String zipNo = request.getParameter("zipNo"); 
	SecretDAO dao = new SecretDAO();
	SecretVO secret = dao.getSecret("juso"); */
	String confmKey = "devU01TX0FVVEgyMDIxMDcwMTIyMjYxMDExMTM0ODg=";
	
	String inputYn = request.getParameter("inputYn");
	String roadFullAddr = request.getParameter("roadFullAddr");
	String roadAddrPart1 = request.getParameter("roadAddrPart1");
	String roadAddrPart2 = request.getParameter("roadAddrPart2");
	String engAddr = request.getParameter("engAddr");
	String jibunAddr = request.getParameter("jibunAddr");
 	String zipNo = request.getParameter("zipNo");
	String addrDetail = request.getParameter("addrDetail");
%>
</head>
<script>

function init(){
	var url = location.href;
	var confmKey = "<%=confmKey%>";
	var resultType = "2"; // 도로명주소 검색결과 화면 출력내용, 1 : 도로명, 2 : 도로명+지번, 3 : 도로명+상세건물명, 4 : 도로명+지번+상세건물명
	var inputYn= "<%=inputYn%>";
	if(inputYn != "Y"){
		document.form.confmKey.value = confmKey;
		document.form.returnUrl.value = url;
		document.form.resultType.value = resultType;
		document.form.action="http://www.juso.go.kr/addrlink/addrLinkUrl.do"; //인터넷망
		//document.form.action="http://www.juso.go.kr/addrlink/addrMobileLinkUrl.do"; //모바일 웹인 경우, 인터넷망
		document.form.submit();
	}else{
<%-- 		opener.jusoCallBack("<%=roadFullAddr%>"); --%>
		opener.jusoCallBack("<%=roadFullAddr%>",
				"<%=roadAddrPart1%>","<%=addrDetail%>",
				"<%=roadAddrPart2%>", "<%=engAddr%>", 
				"<%=jibunAddr%>", "<%=zipNo%>" ); 
		window.close();
		}
}
</script>
<body onload="init();">
	<form id="form" name="form" method="post">
		<input type="hidden" id="confmKey" name="confmKey" value=""/>
		<input type="hidden" id="returnUrl" name="returnUrl" value=""/>
		<input type="hidden" id="resultType" name="resultType" value=""/>
		<!-- 해당시스템의 인코딩타입이 EUC-KR일경우에만 추가 START-->
		<!-- 
		<input type="hidden" id="encodingType" name="encodingType" value="EUC-KR"/>
		 -->
		<!-- 해당시스템의 인코딩타입이 EUC-KR일경우에만 추가 END-->
	</form>
</body>
</html>