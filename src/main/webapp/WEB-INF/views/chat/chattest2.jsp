<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="contextPath" value="${ pageContext.request.contextPath }" scope="application" />
<!DOCTYPE html>
<html>

<head>
  <title>What's App</title>
  <!-- Required meta tags -->
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
  <!-- Bootstrap CSS -->
  <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css"
    integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">
  <link rel="stylesheet" type="text/css"
    href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.css">
  <link rel="stylesheet" type="text/css" href="${contextPath}/resources/css/chat.css">
</head>

<body>
  <div class="container-fluid">

    <div class="row">

      <div class="col-sm-12">


        <div id="user_chat_data" class="user_chat_data">
          <div class="profile_name">
            &nbsp;&nbsp;&nbsp;&nbsp;<img src="${contextPath}/resources/img/profile.png" class="mr-3 rounded-circle"> &nbsp;&nbsp; 
        <span id="username"></span>
        </div>

          <div class="container-fluid chat_section" id="chat-box">
          </div>

          <div class="type_msg">
            <div class="input_msg_write">
              <input id="chat-outgoing-msg" type="text" class="write_msg" placeholder="Type a message" />
              <button id="chat-send" class="msg_send_btn" type="button"><i class="fa fa-paper-plane"
                  aria-hidden="true"></i></button>
            </div>
          </div>

        </div>
      </div>
    </div>
  </div>




  <script src="${contextPath}/resources/js/chat.js"></script>

  <!-- jQuery first, then Popper.js, then Bootstrap JS -->
  <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js"
    integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo"
    crossorigin="anonymous"></script>
  <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js"
    integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49"
    crossorigin="anonymous"></script>
  <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js"
    integrity="sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy"
    crossorigin="anonymous"></script>
    
    
    <script>
    

  //로그인 시스템 대신 임시방편
  let username = prompt("아이디를 입력하세요");
  let roomNum = prompt("채팅방 번호를 입력하세요");

  document.querySelector('#username').innerHTML = username;


  //SSE연결하기
  const eventSource = new EventSource(`http://localhost:8080/chat/roomNum/${roomNum}`);

  //주소
  // const eventSource = new EventSource("http://localhost:8080/sender/ssar/receiver/cos");

  eventSource.onmessage=(event)=>{
      const data = JSON.parse(event.data); //json객체를 자바스크립트object로 바꿔줌

      if(data.sender ===username){
          //파란박스(내가보낼거)
          initMyMessage(data);
      }else{
          //회색박스
          initYourMessage(data);
      }
      
  }
  //파란박스
  function getSendMsgBox(data){

      let md = data.createdAt.substring(5, 10)
     let tm = data.createdAt.substring(11, 16)
     convertTime = tm + " | " + md


      return `<div class="sent_msg">
      <p>${data.msg}</p>
      <span class="time_date"> ${convertTime}/<b>${data.sender}</b> </span>
    </div>`;
  }

  //회색박스
  function getReceiveMsgBox(data){

      let md = data.createdAt.substring(5, 10)
     let tm = data.createdAt.substring(11, 16)
     convertTime = tm + " | " + md

      return `<div class="received_withd_msg">
      <p>${data.msg}</p>
      <span class="time_date"> ${convertTime}/<b>${data.sender}</b> </span>
    </div>`;
  }

  //최초 초기화될 때 1번방 3건이 있으면 그 3건을 다 가져온다
  // addMessage() 함수 호출 시 DB에 insert되고, 그 데이터가 자동으로 흘러들어온다(SSE)
  //파란박스 초기화하기
  function initMyMessage(data){
      let chatBox = document.querySelector("#chat-box");
      // let msgInput = document.querySelector("#chat-outgoing-msg");
      let sendBox = document.createElement("div");
      sendBox.className = "outgoing_msg";
      // let md = data.createdAt.substring(5,10);
      // let tm = data.createdAt.substring(11,16);
      // convertTime = tm + " | " + md
      sendBox.innerHTML = getSendMsgBox(data);
      chatBox.append(sendBox);
      document.documentElement.scrollTop=document.body.scrollHeight;
      // msgInput.value="";
  }

  //회색박스 초기화하기
  function initYourMessage(data){
      let chatBox = document.querySelector("#chat-box");
      // let msgInput = document.querySelector("#chat-outgoing-msg");
      let receivedBox = document.createElement("div");
      receivedBox.className = "received_msg";
      // let md = data.createdAt.substring(5,10);
      // let tm = data.createdAt.substring(11,16);
      // convertTime = tm + " | " + md
      receivedBox.innerHTML = getReceiveMsgBox(data);
      chatBox.append(receivedBox);
      document.documentElement.scrollTop=document.body.scrollHeight;
      // msgInput.value="";
  }



  //AJAX 채팅 메세지를 전송
  async function addMessage(){
      // let chatBox = document.querySelector("#chat-box");
      let msgInput = document.querySelector("#chat-outgoing-msg");
      // let chatOutgoingBox = document.createElement("div");
      // chatOutgoingBox.className = "outgoing_msg";

      // let date = new Date();
      // let now = date.getHours() +":" +date.getMinutes() + "|" + date.getMonth()+ "/" + date.getDate();

      //비동기함수 fetch
      let chat ={
          sender: username,
          roomNum:roomNum,
          msg:msgInput.value
      };

      //하나씩 해결하다가 내려가다보면 fetch에서 시간이 조금 걸리는데
      //그러면 오래걸리게되면 null로 response해버리고 내려가버림
      //await을 썻을경우 fetch에서 5초가 걸린다면 await에서 block이 걸린다
      //그렇게 되면 다른 모든 것들이 동작하지 않기 때문에
      //비동기 함수 async를 써야한다 status가 200이 뜨면 통신이 잘 됐다는 뜻
      //body안에 데이터를 parse한번 해줘야한다
      /* let response = await*/ fetch("http://localhost:8080/chat",{
          method:"post", //http post메서드(새로운 데이터를 wrtie)
          body:JSON.stringify(chat), //js -> json
          headers:{
              "Content-Type":"application/json; charset=utf-8"
          }
      });
                          //상대방이 응답을 json으로 해줬기 때문에
      // let parseResponse = await response.json();
      
      // chatOutgoingBox.innerHTML = getSendMsgBox(msgInput.value, now);
      // chatBox.append(chatOutgoingBox);
      msgInput.value="";

  }



  //버튼 클릭시 메세지 전송
  document.querySelector("#chat-send").addEventListener("click", ()=>{
      addMessage();
  });
  why

  //엔터를 치면 메세지 전송
  document.querySelector("#chat-outgoing-msg").addEventListener("keydown", (e)=>{
      
      if(e.keyCode===13){
          addMessage();
          }
  });
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    </script>
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
</body>

</html>