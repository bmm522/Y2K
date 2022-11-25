<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="contextPath" value="${ pageContext.request.contextPath }" scope="application" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
    <link rel="short icon" href="${contextPath}/resources/img/2014.ico"/>
    <link rel="stylesheet" href="${contextPath}/resources/css/reset.css" />
    <link rel="stylesheet" href="${contextPath}/resources/css/style.css" />
    <link rel="stylesheet" href="${contextPath}/resources/css/board.css" />
    <script src="https://code.jquery.com/jquery-3.6.0.js"></script>
    <script src="https://kit.fontawesome.com/7a738a6e1a.js" crossorigin="anonymous"></script>
	<style>body{margin:0; padding:0;}</style>
</head>
<body>
	
    <div class="bg">
      <main>
        <section class="pf-st">
          <div class="pf-side">
            <div class="pf-back">
              <div class="profile-wrap">
                <div class="visitor-counter">
                  <p class="today">999</p>
                  <p class="today-count">1213042</p>
                </div>
                <div class="profile">
                  <p class="todayis">
                    TODAY IS .. <i>♥</i><span> 행복</span>
                  </p>
                  <img class="profile-img" src="${contextPath}/resources/img/profile.jpg" alt="profile"/>
                  <div class="desc-wrap">
                    <p class="text-desc">
                     안녕하세요. <br> 조다롱입니다. <br> 제 미니홈피에 오신것을 환영합니다. </p>
                    <a class="history" href="#">HISTORY</a>
                  </div>
                  <div class="info-wrap">
                    <a class="info-name" href="#">Y2K</a>
                    <p class="text-email">guswhd956@naver.com</p>
                  </div>
                  <div class="profile-dropdown">
                    <div class="dropdown-btn">
                      <div class="dropdown-title">Related SNS Link</div>
                      <div class="triangle-down"></div>
                    </div>
                    <div class="dropdown-content">
                        <a href="https://bit.ly/3IUmQTC" target="_blank">Instagram</a>
                        <a href="https://bit.ly/3IWxs4u" target="_blank">FaceBook</a>
                        <a href="https://bit.ly/3IOwM0W" target="_blank">Blog</a>
                    </div>
                  </div>
                </div>
                <div class="side side1"></div>
                <div class="side side2"></div>
                <div class="side side3"></div>
                <div class="side side4"></div>
              </div>
            </div>
          </div>
        </section>
        <section class="main-section">
          <div class="main-dot">
            <div class="main-paper">
              <div class="main-wrap">
                <div class="title-wrap">
                  <p class="title"><a href="#">나의 미니홈피에 온걸 환영해 :D</a></p>
                  <div class="link-wrap">
                    <a href="https://www.instagram.com/hyunjong_yoo/" target="_blank"><span>일촌맺기</span></a>
                    <a href="https://blog.naver.com/hananharu" target="_blank"><span>팬되기</span></a>
                    <p><a href="#">https://www.cyowrld.com/marketer_JJ</a></p>
                  </div>
                </div>
	<div class="main">
	 <div><hr class="hrB" id="up"></div>
        <br>
        <form id="detailForm">
        <input type="hidden" value="${b.boardNo}" name="boardNo">
		<input type="hidden" value="${page }" name="page">
        <table>
        <thead>
            <tr id="titlebar" style="text-align: center;">
                <td width="70px">${b.category}</td>
                <th width="50px" style="border-right : 1px dashed rgb(11, 82, 0);">제목</th>
                <td>${b.boardTitle}</td>
                <th width="50px" style="border-right : 1px dashed rgb(11, 82, 0);">조회수</th>
                <td width="50px">${b.boardCount}</td>
                <th width="50px" style="border-right : 1px dashed rgb(11, 82, 0);">작성자</th>
                <td width="50px">${b.nickName }</td>
            </tr>
        </thead>
        </table>
        <br>
        <div><hr class="hrB"></div>
        <br><br>

        <div id="boardContent">
        
            <p id="spring">${b.boardContent}</p>
                <br><br>
            <div id="good"><i class="fas fa-hand-holding-heart"></i></div>
             <br><br>
             <span class="sns">
                <span class="facebook">
                <a href="#n" onclick="fn_sendFB('facebook');return false;" id="facebook" target="_self" title="페이스북 새창열림"><img src="../resources/img/icon-facebook.png" width="30px"></a>
                </span>
                <span class="twitter">
                <a href="#n" onclick="fn_sendFB('twitter');return false;" id="twitter" target="_self" title="트위터 새창열림"><img src="../resources/img/icon-twitter.png" width="30px"></a>
                </span>
                <span class="band">
                <a href="#n" onclick="fn_sendFB('band');return false;" id="band" target="_self" title="네이버밴드 새창열림"><img src="../resources/img/icon-line.png" width="30px"></a>
                </span>
             </span>
            <div class="scrollbar">
              <a href="#up"><i class="fa-solid fa-circle-up"></i></a><br><br>
            <!-- <i class="fa-solid fa-circle-down"></i><br><br> -->
            <!-- <a href="#down"><i class="fa-solid fa-circle-down"></i></a><br><br> -->
            <a href="#reply"><i class="far fa-comment-dots"></i></a>
            </div>
        	<br>
        	<%-- <c:if test="${loginUser.id eq b.boardWriter}"> --%>
			<div id="ViewForm">
				<button  type="button" id="updateForm">수정하기</button>				
				<button  type="button" id="deleteForm">삭제하기</button>					
				<button  type="button" id="boardListBack" onclick="location.href='${contextPath}/boardList.bo'">목록으로</button>	
			</div>				
			<%-- </c:if> --%>
			<br>   
        </div>

         <hr>
         
         <div id="replyDiv">
            <table>
                <tr>
                    <td colspan="2">
                        <textarea id="reply" rows="3" cols="50" id="replyContent" style="resize: none;" placeholder='일촌에게 댓글을 작성해보세요!'></textarea>
                    </td>
                    <td>
  					    <div id="replyButton"><button class="button btnBorder btnOrange" onclick="location.href='${contextPath}/insertBoardReply.bo'">등록</button></div>
                    </td>
                </tr>
                <tbody id="replyList">
                <c:forEach items="${list}" var="r">
                            <tr>
                                <td width="80px">${r.nickName}</td>
                                <td>${r.replyContent }</td>
                                <td>${r.r_createDate }</td>
                            </tr>
                </c:forEach>
                </tbody>
            </table>
            
            <div><hr class="hrB" id="down"></div>
        </div>
        </form>
     </div>
			
	</div>
  <div class = "menu align-center expanded text-center SMN_effect-68">
    <a href="mainPage.html">   <div class="menu-item mi-1"  onclick="location.href='${contextPath}'">홈</div></a>
<!--              <a href="photo.html" class="menu-item mi-2" onclick="openPopup()">사진첩</a>-->
              <a href="photo.html" class="menu-item mi-2" >사진첩</a>
              <a href="diary.html" class="menu-item mi-3">다이어리</a>
      </div>
              <a class="menu-item mi-5 menu-checked" onclick="location.href='${contextPath}/boardList.bo'">게시판</a>
              <div class = "menu align-center expanded text-center SMN_effect-68">
                <a class="menu-item mi-4" onclick="location.href='${contextPath}/visit.vi'">방명록</a>
              </div>
              <!-- <a href="#" class="menu-item mi-5">마케팅</a> -->
<!--              <div class="menu-item mi-6">게시판</div>-->
<!--              <div class="menu-item mi-7">방명록</div>-->
            </div>
          </div>
        </section>
      </main> 
   </div>
     <script>
     	//수정 및 삭제
     		
     	window.onload = () => {
			
			const upd = document.getElementById('updateForm');
			const form = document.getElementById('detailForm');
			
			if(upd != null) {
				upd.addEventListener('click', ()=> {
					form.action = '${contextPath}/updateForm.bo';
					form.submit();
				});
			}
			
			document.getElementById('deleteForm').addEventListener('click', ()=> {
				 if (confirm("게시글을 삭제하시겠습니까?") == true){ 
						form.action = '${contextPath}/deleteForm.bo';
						form.submit();
					 }else{
					   console.log("취소되었습니다");
					 }

			});
     	}
     	
     	
     
     	
     
            //share function

            function shareFacebook() {
              var sendUrl = "https://";    
              window.open("http://www.facebook.com/sharer/sharer.php?u=" + sendUrl, "Y2K World", "height=480px, width=600px");
            }
            
            function shareTwitter() {
              var sendText = "Y2K World의 게시글을 공유합니다 :)" 
              var sendUrl = "http://www.google.com"
              window.open("https://twitter.com/intent/tweet?text=" + sendText + "&url=" + sendUrl, "Y2K World", "height=480px, width=600px");
            }

            function shareBand() {
              var sendText = "Y2K World의 게시글을 공유합니다 :)" 
              var sendUrl = "http://www.google.com";  
              window.open("http://www.band.us/plugin/share?body=" + sendText + "&url=" + sendUrl, "Y2K World", "height=450px, width=350px");  
            }


            //button click action

            $(document).ready(function() {
 
            $("#facebook").click(function() {
                shareFacebook();
            });

            $("#twitter").click(function() {
                shareTwitter();
            });

            $("#band").click(function(){
                shareBand();
            });

          });
          
      </script>
</body>
</html>