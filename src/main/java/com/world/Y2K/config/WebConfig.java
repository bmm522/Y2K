package com.world.Y2K.config;

import javax.servlet.Filter;

import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.filter.DelegatingFilterProxy;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;


public class WebConfig extends AbstractAnnotationConfigDispatcherServletInitializer{

	@Override
	protected Class<?>[] getRootConfigClasses() {

		return new Class[] {RootConfig.class, SecurityConfig.class};
	}

	@Override
	protected Class<?>[] getServletConfigClasses() {

		return new Class[] {ServletContext.class, BoardContext.class
										, ChatContext.class, DiaryContext.class
										, FriendContext.class, LoginContext.class
										, MainContext.class, MypageContext.class
										, PayContext.class, PhotoContext.class
										, VisitContext.class};
	}

	@Override
	protected String[] getServletMappings() {

		return new String[] {"/"};
	}

	
	@Override
	protected Filter[] getServletFilters() {
		 CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
	     characterEncodingFilter.setEncoding("UTF-8");
	     characterEncodingFilter.setForceEncoding(true);
	    
	    
	    return new Filter[] { characterEncodingFilter };
		
	}
}