package com.world.Y2K.controller.photo;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.world.Y2K.exception.PhotoException;
import com.world.Y2K.model.dto.Member;
import com.world.Y2K.model.vo.Photo;
import com.world.Y2K.model.vo.Reply;
import com.world.Y2K.service.login.auth.UserDetailsImpl;
import com.world.Y2K.service.photo.PhotoImageStore;
import com.world.Y2K.service.photo.PhotoService;

import lombok.RequiredArgsConstructor;



@RequiredArgsConstructor
@Controller
public class PhotoController {
	
	@Autowired
	private PhotoImageStore photoImageStore; 
	
	@Autowired
	private PhotoService pService;
	
	@RequestMapping("/photo.ph")
	public String photo(Model model, Authentication authentication, HttpSession session,
			@RequestParam("userNo")Long userNo) {
		
		UserDetailsImpl userDetails = (UserDetailsImpl)authentication.getPrincipal();
		//System.out.println("user : " + userDetails.getMember());
		/* Member member = userDetails.getMember(); */
		//Long userNo = (Long) session.getAttribute("loginUser");
//		Long userNo = Long.parseLong((String) request.getAttribute("userNo"));
//		request.setAttribute("userNo", userNo);
		List<Photo> images = pService.photoList(userNo);
		model.addAttribute("images", images);
		model.addAttribute("dto", userDetails);
		model.addAttribute("userNo", userNo);
		return "photo/photo";
	}
	
	@RequestMapping("/show.ph")
	public ModelAndView selectImg(
			HttpSession session, ModelAndView mv,
			
			@RequestParam("boardNo") Long boardNo,
			Authentication authentication
			) {
		
		UserDetailsImpl userDetails = (UserDetailsImpl)authentication.getPrincipal();
		
		Member member = userDetails.getMember();
		
		Photo p = pService.selectImg(boardNo);
		//p.setUserNo(userDetails.getMember().getUserNo());
	
		ArrayList<Reply> list =pService.selectReply(boardNo);
		
		mv.addObject("photo", p);
		mv.addObject("member", member);
		mv.addObject("list", list);
		mv.setViewName("photo/show");

		return mv;
	}
	
	@RequestMapping("/upload.ph")
	public String upload(@RequestParam("userNo") Long userNo, Model model) {
		model.addAttribute("userNo", userNo);
		return "photo/upload";
	}
	
	@RequestMapping("/image")
	public String imageUpload(@ModelAttribute Photo p,
			@RequestParam(value="file", required=false) MultipartFile file,
			@RequestParam("userNo") Long userNo,
			HttpServletRequest request, Model model,Authentication authentication) {
		
		UserDetailsImpl userDetails = (UserDetailsImpl)authentication.getPrincipal();
		
		if(file.isEmpty()) {
			throw new PhotoException("�떎�뙣");
		}
		
		
		photoImageStore.insertImage(p, file, userDetails);
		model.addAttribute("userNo", userNo);
		
//		return "photo/photo.ph?userNo=" + userNo;
		return "redirect:/photo.ph";
	}
	
	
	@RequestMapping("/delete.ph")
	public String deleteImage(
			@RequestParam("boardNo") Long boardNo,
			Model model,
			@RequestParam("userNo")Long userNo
			) {
		
		//System.out.println("result");
		
		pService.deletetImg(boardNo);
		model.addAttribute("userNo", userNo);
		
			return "redirect:/photo.ph";
			
		}
	
	
	
	
	@RequestMapping("/edit.ph")
	public String editFrom(
			@RequestParam("boardNo") Long boardNo,
			@RequestParam("userNo") Long userNo,
			Model model
			) {

		Photo photo = pService.selectImg(boardNo);
		
		model.addAttribute("photo", photo);
		model.addAttribute("userNo", userNo);
		return "/photo/edit";
	}
	
	@RequestMapping("/update")
	public String updateImage(@ModelAttribute Photo p,
			@RequestParam("photoComent") String photoComent,
			@RequestParam("file") MultipartFile file,
			HttpServletRequest request,
			Model model,
			@RequestParam("renameName") String renameName,
			@RequestParam("photoName") String photoName,
			@RequestParam("boardNo") Long boardNo,
			@RequestParam("userNo") Long userNo
			
			) {
			model.addAttribute("userNo", userNo);
			if(file.getOriginalFilename().equals("") && p.getRenameName()==renameName ) {
				
				p.setPhotoComent(photoComent);
				p.setBoardNo(boardNo);
				pService.updateComent(p);
				
			}else {
			
				photoImageStore.updateAll(p, file, request);
			
			}

		
			return "redirect:/photo.ph";
	}

}