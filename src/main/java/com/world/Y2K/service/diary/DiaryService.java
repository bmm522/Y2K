package com.world.Y2K.service.diary;

import java.util.ArrayList;

import com.world.Y2K.model.vo.Diary;
import com.world.Y2K.model.vo.Reply;

public interface DiaryService {

	int insertDiary(Diary d);

	ArrayList<Diary> selectDiaryList(Long userNo);

	Diary selectDiary(Long bId);

	int insertReply(Reply r);

	ArrayList<Reply> selectReply(Long rboardNo);

	int deleteDiary(Long boardNo);

	int updateDiary(Diary d);

	int deleteReply(Long replyNo);

}
