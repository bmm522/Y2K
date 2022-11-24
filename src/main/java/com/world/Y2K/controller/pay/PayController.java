package com.world.Y2K.controller.pay;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.world.Y2K.exception.PaymentException;
import com.world.Y2K.model.vo.PayPageInfo;
import com.world.Y2K.model.vo.Product;
import com.world.Y2K.model.vo.ProductPhoto;
import com.world.Y2K.pagination.PayPagination;
import com.world.Y2K.service.login.auth.UserDetailsImpl;
import com.world.Y2K.service.payment.PaymentService;

@Controller
public class PayController {
	
	@Autowired
	private PaymentService pService;
	
	@RequestMapping("purchaes.pa")
	public String selectPayList(@RequestParam(value="page", required=false) Integer page, Model model, Authentication authentication) throws PaymentException {
		
		UserDetailsImpl userdetails = (UserDetailsImpl)authentication.getPrincipal();
		String userRole = userdetails.getMember().getRole();
		
		int payCurrentPage = 1;
		
		if(page != null) {
			payCurrentPage = page;
		}
		
		int payListCount = pService.getPayListCount();
		PayPageInfo pi = PayPagination.getPageInfo(payCurrentPage, payListCount, 6);
		ArrayList<Product> pList = pService.selectProductList(pi);
		ArrayList<ProductPhoto> photoList = pService.selectPhotoList();
		
		if(pList != null) {
			model.addAttribute("pi", pi);
			model.addAttribute("pList", pList);
			model.addAttribute("photoList", photoList);
			model.addAttribute("userRole", userRole);
			return "pay/purchaes";
		} else {
			throw new PaymentException("구매 게시글 조회 실패");
		}

	}
	
	@GetMapping("writePurchaes.pa")
	public String writer(Authentication authentication, Model model) {
		UserDetailsImpl userdetails = (UserDetailsImpl)authentication.getPrincipal();
		Long userNo = userdetails.getMember().getUserNo();
		
		model.addAttribute("mNo", userNo);
		
		return "pay/writePurchaes";	
	}
	
	// 파일 저장소를 만드는 메소드(실제 파일이 들어갈 폴더)
	public String[] addFile(MultipartFile file, HttpServletRequest request) {
		
		String uploadPath = "C:\\Users\\박유진\\Desktop\\uploadFolder/"; 
		
		UUID uuid = UUID.randomUUID();
		
		String originFileName = file.getOriginalFilename();
		String renameFileName = uuid + "_" + originFileName.substring(originFileName.lastIndexOf("."));
		
		File fileFolder = new File(uploadPath + renameFileName);
		
		try {
			file.transferTo(fileFolder);
		} catch (IOException e) {
			System.out.println("파일 전송 에러 : " + e.getMessage());
		}
		
		String[] returnArr = new String[3];
		returnArr[0] = uploadPath;
		returnArr[1] = renameFileName;
		returnArr[2] = originFileName;
		
		System.out.println("파일저장소 나옴?");
		return returnArr;
	}
	
	@RequestMapping("insertPurchaes.pa")
	public String insertPurchaes(Authentication authentication, @ModelAttribute Product p, @RequestParam("file") MultipartFile file, HttpServletRequest request) throws PaymentException {
		
		UserDetailsImpl userdetails = (UserDetailsImpl)authentication.getPrincipal();
		String mRole = userdetails.getMember().getRole();
		Long mNo = userdetails.getMember().getUserNo();
			
		if(mRole.equals("ROLE_ADMIN")) {
			MultipartFile upload  = file;
			
			ProductPhoto pp = new ProductPhoto();
			
			if(!upload.getOriginalFilename().equals("")) {
				String[] returnArr = addFile(upload, request);
				
				pp.setProductPhotoName(returnArr[2]);
				pp.setProductReNameName(returnArr[1]);
				pp.setProductPhotoPath(returnArr[0]);
				
			}
			p.setUserNo(mNo);
			
			int result1 = pService.insertBoard(p);
			int result2 = pService.insertPurchaes(pp);		

			System.out.println("나오냐");
			if(result2 > 0 && result1 > 0) {
				return "redirect:purchaes.pa";
			} else {
				throw new PaymentException("구매 게시글 작성이 실패하였습니다.");
			}
		} else {
			throw new PaymentException("관리자만 게시글을 작성할 수 있습니다.");
		}
		
		
	}
	
	@RequestMapping("selectPurchaes.pa")
	public String detailPurchaes(Authentication authentication, HttpServletRequest request, @RequestParam(value="page", required=false) Integer page, Model model, @RequestParam(value="productNo", required=false) Long pNo) throws PaymentException {
		
		UserDetailsImpl userdetails = (UserDetailsImpl)authentication.getPrincipal();
		String userRole = userdetails.getMember().getRole();
		
		System.out.println(pNo);
		
		Product p = pService.detailPurchaes(pNo);
		ProductPhoto photo = pService.selectPhoto(pNo);

		if(p != null && photo != null) {
			model.addAttribute("p", p);
			model.addAttribute("photo", photo);
			model.addAttribute("userRole", userRole);
			return "pay/detailPurchaes";
		} else {
			throw new PaymentException("구매게시글 상세 조회 실패");
		}
	}	
	
	@RequestMapping("deletePurchaes.pa")
	public String deletePurchaes(HttpSession session, @RequestParam("productNo") Long pNo) throws PaymentException {
		// 관리자만 삭제할 수 있게 로직 짜기
		
		System.out.println(pNo);
		
		int result1 = pService.deletePurchaes(pNo);
		int result2 = pService.deleteProductPhoto(pNo);
		
		System.out.println(result1);
		System.out.println(result2);
		
		if(result1 > 0 && result2 > 0) {
			return "redirect:purchaes.pa"; // submit으로 값 전달 후 팝업창 닫고 부모 게시판 갱신 찾기
		} else {
			throw new PaymentException("구매게시글 삭제 실패");
		}
	}
	
	@RequestMapping("orderPurchaes.pa")
	public String deletePurchaes(Authentication authentication, @RequestParam("productNo") Long pNo, @RequestParam("price") Long price, @ModelAttribute Product p) throws PaymentException {
		UserDetailsImpl userdetails = (UserDetailsImpl)authentication.getPrincipal();
		System.out.println(userdetails.getMember());
		Long mNo = userdetails.getMember().getUserNo();
		
		System.out.println(mNo);
		
		HashMap<String, Long> map = new HashMap<String, Long>();
		map.put("pNo", pNo);
		map.put("price", price);
		map.put("mNo", mNo);
			
		int result = pService.orderPurchaes(map);
		
		int conutOrange = pService.getOrangeCount(map);
		System.out.println(conutOrange);
		
		if(conutOrange < price) {
			throw new PaymentException("낑깡이 부족합니다.");
		} else {
			if(result > 0) {
				return "redirect:purchaes.pa"; // submit으로 값 전달 후 팝업창 닫고 부모 게시판 갱신 찾기
			} else {
				throw new PaymentException("상품 구매 실패");
			}
		}	
	}
	
	@GetMapping("payment.pa")
	public String test2() {
		return "pay/payment";
	}
	
}
