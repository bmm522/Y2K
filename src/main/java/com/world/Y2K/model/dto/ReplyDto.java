package com.world.Y2K.model.dto;




import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ReplyDto {
	
	private String content;
	
	private String nickName;
	
	private Long boardNo;
	
	private Long own;
}
