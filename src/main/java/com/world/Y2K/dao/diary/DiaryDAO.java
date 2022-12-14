package com.world.Y2K.dao.diary;

import java.util.ArrayList;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import com.world.Y2K.model.vo.Diary;
import com.world.Y2K.model.vo.Reply;

@Repository
public class DiaryDAO {

	public int insertDiary(SqlSessionTemplate sqlSession, Diary d) {
		return sqlSession.insert("diaryMapper.insertDiary", d);
	}

	public ArrayList<Diary> selectDiaryList(SqlSessionTemplate sqlSession, Long own) {
		return (ArrayList)sqlSession.selectList("diaryMapper.selectDiaryList", own);
	}

	public Diary selectDiary(SqlSessionTemplate sqlSession, Long bId) {
		return sqlSession.selectOne("diaryMapper.selectDiary", bId);
	}

	public int insertReply(SqlSessionTemplate sqlSession, Reply r) {
		return sqlSession.insert("diaryMapper.insertReply", r);
	}

	public ArrayList<Reply> selectReply(SqlSessionTemplate sqlSession, Long rboardNo) {
		return (ArrayList)sqlSession.selectList("diaryMapper.selectReply", rboardNo);
	}

	public int deleteDiary(SqlSessionTemplate sqlSession, Long boardNo) {
		return sqlSession.update("diaryMapper.deleteDiary", boardNo);
	}

	public int updateDiary(SqlSessionTemplate sqlSession, Diary d) {
		return sqlSession.update("diaryMapper.updateDiary", d);
	}

	public int deleteReply(SqlSessionTemplate sqlSession, Long replyNo) {
		return sqlSession.update("diaryMapper.deleteReply", replyNo);
	}

}
