package com.world.Y2K.service.photo;

import java.util.HashMap;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.world.Y2K.dao.photo.PhotoDAO;
import com.world.Y2K.model.vo.Photo;
import com.world.Y2K.service.PhotoStore;
import com.world.Y2K.service.login.auth.UserDetailsImpl;

@Service("psService")
public class PhotoImageStoreImpl extends PhotoStore implements PhotoImageStore{

	
	@Autowired
	private SqlSessionTemplate sqlSession;
	
	@Autowired
	private PhotoDAO pDAO;

	private String uploadFolder ="C:/upload/";


	@Override
	public void insertImage(Photo p, MultipartFile file, UserDetailsImpl userDetails) {
		//System.out.println("service" +p.getPhotoName());
	//	PhotoEntity photoEntity = insertEntity(p.getPhotoName(), p.getPhotoPath(), p.getRenameName());
		UUID uuid = UUID.randomUUID();
		
		HashMap<String, Object> fileInfo = insertImage(file, uuid, uploadFolder);
		
		Long userNo = userDetails.getMember().getUserNo();
		p.setPhotoName((String)fileInfo.get("originFileName"));
		p.setRenameName((String)fileInfo.get("renameFileName"));
		p.setPhotoPath(uploadFolder);
		p.setUserNo(userNo);
		
		//System.out.println(p);
		
		pDAO.insertImage(sqlSession, p);
		
	}



	@Override
	public void updateAll(Photo p, MultipartFile file, HttpServletRequest request) {

		UUID uuid = UUID.randomUUID();
		
		HashMap<String, Object> fileInfo = insertImage(file, uuid, uploadFolder);
	
		p.setPhotoName((String)fileInfo.get("originFileName"));
		p.setRenameName((String)fileInfo.get("renameFileName"));
		p.setPhotoPath(uploadFolder);
		
		p.setPhotoComent(p.getPhotoComent());


		pDAO.updateAll(sqlSession, p);
		
	}


}
