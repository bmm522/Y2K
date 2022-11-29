<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="contextPath" value="${ pageContext.request.contextPath }" scope="application" />
<!doctype html>
<html lang="en">
<head>
<meta charset="utf-8">
    
<title>구매</title>
    
<link rel="canonical" href="https://getbootstrap.com/docs/5.2/examples/pricing/">
<!-- jQuery -->
<script type="text/javascript" src="https://code.jquery.com/jquery-1.12.4.min.js" ></script>
<!--  iamport.payment.js -->
<script type="text/javascript" src="https://cdn.iamport.kr/js/iamport.payment-1.1.5.js"></script>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-Zenh87qX5JnK2Jl0vWa8Ck2rdkQ2Bzep5IDxbcnCeuOxjzrPF/et3URy9Bv1WTRi" crossorigin="anonymous">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-OERcA2EqjJCMA+/3y+gxIOqMEjwtxJY7qPCqsdltbNJuaOe923+mo//f6V8Qbsw3" crossorigin="anonymous"></script>
<script src="https://kit.fontawesome.com/7a738a6e1a.js" crossorigin="anonymous"></script>
<!-- Custom styles for this template -->
<link rel="stylesheet" href="${contextPath}/resources/css/payment.css" />
<style>

</style>

</head>
  
<body>
<header>
  <div class="collapse bg-dark" id="navbarHeader">
    <div class="container">
      <div class="row">
        <div class="col-sm-8 col-md-7 py-4">
          <h4 class="text-white">Y2K 결제페이지</h4>     
        </div>
        <div class="col-sm-4 offset-md-1 py-4">
          <ul class="list-unstyled">
            <li><a href="${contextPath}/main.lo" class="text-white" target="_blank">메인페이지</a></li>
            <li><a href="${contextPath}/purchaes.pa" class="text-white">구매페이지</a></li>
            <li><a href="${contextPath}/friendList.fr" class="text-white">친구페이지</a></li>
            <li><a href="${contextPath}/friendRequestList.fr" class="text-white">친구 요청 페이지</a></li>
          </ul>
        </div>
      </div>
    </div>
  </div>
  <div class="navbar navbar-dark bg-dark shadow-sm">
    <div class="container">
      <a href="#" class="navbar-brand d-flex align-items-center">
        
        <strong>결제</strong>
      </a>
      <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarHeader" aria-controls="navbarHeader" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
      </button>
    </div>
  </div>
</header>
<div class="main">
    <div class="pricing-header p-3 pb-md-4 mx-auto text-center">
      
      <h4 class="display-4 fw-normal">낑깡 결제</h4>
      <p><strong>카카오 페이 현재 사용 가능</strong></p>    
    </div>
    <div class="row row-cols-1 row-cols-md-3 mb-3 text-center">
      <div class="col">
        <div class="card mb-4 rounded-3 shadow-sm">
          <div class="card-header py-3">
            <h5 class="my-0 fw-normal" style="color:red;">낑깡 100개</h5>
          </div>
          <div class="card-body">
            <h3 class="card-title pricing-card-title">10,000원<small class="text-muted fw-light"></small></h3>
			<img src="${contextPath}/resources/img/payment1.png" style="height: 300px; width: 380px;">
            <button type="button" class="w-100 btn btn-lg btn-outline-primary" onclick="iamportPayment1()">구매하기!</button>
          </div>
        </div>
      </div>
      <div class="col">
        <div class="card mb-4 rounded-3 shadow-sm">
          <div class="card-header py-3">
            <h5 class="my-0 fw-normal" style="color:red;">낑깡 300개</h5>
          </div>
          <div class="card-body">
            <h3 class="card-title pricing-card-title">30,000원<small class="text-muted fw-light"></small></h3>
            <img src="${contextPath}/resources/img/payment3.jpg" style="height: 300px; width: 380px;">
            <button type="button" class="w-100 btn btn-lg btn-outline-primary" id="iamportPayment2">구매하기!</button>
          </div>
        </div>
      </div>
      <div class="col">
        <div class="card mb-4 rounded-3 shadow-sm border-primary">
            <div class="card-header py-3" style="color:red;">
           	 <h5 class="my-0 fw-normal">낑깡 500개</h5>
            </div>
          	<div class="card-body">
	            <h3 class="card-title pricing-card-title">50,000원<small class="text-muted fw-light"></small></h3>
	            <img src="${contextPath}/resources/img/payment2.jpg" style="height: 300px; width: 380px;">
	            <button type="button" class="w-100 btn btn-lg btn-outline-primary" id="iamportPayment3">구매하기!</button>
          	</div>
        </div>
      </div>
    </div>
    <p style="color: red; margin-top: 30px">카카오페이의 최소 충전금액은 10,000원이며 <br/>최대 충전금액은 50,000원 입니다.</p>
    <br><br><br>
</div>
    <footer class="sticky-footer bg-white">
        <footer class="main-footer d-flex p-2 px-3 bg-white border-top">
              <a href="https://github.com/likelion-backendschool/DAMDA_project" rel="nofollow" target="_blank">Y2K</a>
        </footer>
</footer>
</body>
<script>
	function iamportPayment1(){
        var IMP = window.IMP; // 생략가능
        IMP.init('imp78311764'); // 'iamport' 대신 부여받은 "가맹점 식별코드"를 사용
        var msg;
        
        IMP.request_pay({
        	pg : 'kakaopay.TC0ONETIME',
	        pay_method : 'card',
	        merchant_uid : '${ merchant_uid }',
	        name : '낑깡',
	        amount : '10,000',
	        buyer_email : '${ loginUser.email }',
	        buyer_name : '${ loginUser.username }',
	        buyer_tel : '01041045081',
            //m_redirect_url : 'http://www.naver.com'
        }, function(rsp) {
            if ( rsp.success ) {
                //[1] 서버단에서 결제정보 조회를 위해 jQuery ajax로 imp_uid 전달하기
                jQuery.ajax({
                    url: '${contextPath}/paymentRequest.pa', //cross-domain error가 발생하지 않도록 주의해주세요
                    dataType: 'json',
                    data: {
                        imp_uid : rsp.imp_uid,
                        paid_amount : amount,
                        ${loginUser.userNo} : mNo,
                        name : name
                        //기타 필요한 데이터가 있으면 추가 전달
                    }
                }).done(function(data) {
                    //[2] 서버에서 REST API로 결제정보확인 및 서비스루틴이 정상적인 경우
                    if ( everythings_fine ) {
                        msg = '결제가 완료되었습니다.';
                        msg += '\n고유ID : ' + rsp.imp_uid;
                        msg += '\n상점 거래ID : ' + rsp.merchant_uid;
                        msg += '\결제 금액 : ' + rsp.paid_amount;
                        msg += '카드 승인번호 : ' + rsp.apply_num;
                        
                        alert(msg);
                    } else {
                        //[3] 아직 제대로 결제가 되지 않았습니다.
                        //[4] 결제된 금액이 요청한 금액과 달라 결제를 자동취소처리하였습니다.
                    }
                });
                //성공시 이동할 페이지
            } else {
                msg = '결제에 실패하였습니다.';
                msg += '에러내용 : ' + rsp.error_msg;
                //실패시 이동할 페이지
                alert(msg);
            }
        });
    };
</script>
</html>
    