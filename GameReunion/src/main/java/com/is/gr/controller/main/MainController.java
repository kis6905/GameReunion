package com.is.gr.controller.main;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.is.gr.service.GameService;
import com.is.gr.vo.Game;

/**
 * @author iskwon
 */
@Controller
@RequestMapping(value = "/main/**")
public class MainController {
	
	private static final Logger logger = LoggerFactory.getLogger(MainController.class);
	
	@Autowired
	private GameService gameServiceImpl;
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String getMain(
			HttpServletRequest request,
			Model model) {
		logger.info("-> []");
		
		HttpSession session = request.getSession(false);
		List<Game> boomarkList = gameServiceImpl.getBookmarkList((String)session.getAttribute("MEMBER_ID"));
		model.addAttribute("bookmarkList", boomarkList);
		
		logger.info("<- [boomarkListSize = {}]", boomarkList.size());
		return "main/main";
	}
	
}
