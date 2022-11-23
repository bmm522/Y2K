// 댓글쓰기
 function addComment() {

 	let commentInput = document.getElementById("#storyCommentInput-1");
 	let commentList = document.getElementById("#storyCommentList-1");

	console.log(commentInput);
	console.log(commentList);
	
 	let data = {
			 content: commentInput.val()
	 	}

	console.log(data);
	console.log(JSON.stringify(data));

	 	if (data.content === "") {
			alert("댓글을 작성해주세요!");
			return;
		}

		$.ajax({
			type: "post",
			url: "/ai/comment",
			data: JSON.stringify(data),
			contentType:"application/json; charset=utf-8",
			dataType: "json" 
		}).done(res=>{
					console.log("성공", res);
					
						let comment = res.data;
	 			let content = `
	 		
	 			  <div class="sl__item__contents__comment" id="storyCommentItem-2""> 
	 			    <p>
						<b>조현재 :</b> 제발 되어라..
					</p>
						<button type="button">
							<i class="fas fa-times" id="change"></i>
						</button>
					</div>
		 	`;
		 	commentList.prepend(content);
		 	
		 	}).fail(error=>{
		
		
		});
			commentInput.val("");
 }

// 댓글 삭제
function deleteComment() {

}

// 댓글쓰기
function addComment() {

	let commentInput = $("#storyCommentInput-1");
	let commentList = $("#storyCommentList-1");

	let data = {
		content: commentInput.val()
	}

	if (data.content === "") {
		alert("댓글을 작성해주세요!");
		return;
	}

	let content = `
			  <div class="sl__item__contents__comment" id="storyCommentItem-2""> 
			    <p>
			      <b>GilDong :</b>
			      댓글 샘플입니다.
			    </p>
			    <button><i class="fas fa-times"></i></button>
			  </div>
	`;
	commentList.prepend(content);
	commentInput.val("");
}

// (6) 사용자 정보(회원정보, 로그아웃, 닫기) 모달
function popup(obj) {
	$(obj).css("display", "flex");
}

function modalInfo() {
	$(".modal-info").css("display", "none");
}


window.onload=()=>{
			const upd = document.getElementById('updateForm');
			const form = document.getElementById('detailForm');
			if(upd != null){
				upd.addEventListener('click', ()=>{
					
					form.action = 'edit.ph';
					form.submit();
					
					window.close();
				});
			}
			
			const deleteModal = document.getElementById('deleteModal');
			
			if(deleteModal != null){
				document.getElementById('deleteModal').addEventListener('click', ()=>{
					console.log(document.getElementById('deleteModal'));
					
					form.action = 'delete.ph';

					form.submit();
				 	
				 	window.close();
				});
				
			
			}




}


