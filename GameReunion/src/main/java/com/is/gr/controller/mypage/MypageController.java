package com.is.gr.controller.mypage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author iskwon
 */
@Controller
@RequestMapping(value = "/mypage/**")
public class MypageController {
	
	private static final Logger logger = LoggerFactory.getLogger(MypageController.class);
	
	/**
	 * mypage 화면으로 이동
	 */
	@RequestMapping(value = "/main", method = RequestMethod.GET)
	public String getMain() {
		
		logger.info("-> []");
		
		
		
		logger.info("<- []");
		return "mypage/mypage_main";
	}
	
}
