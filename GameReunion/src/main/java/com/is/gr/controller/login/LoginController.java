package com.is.gr.controller.login;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.is.gr.commons.Codes;
import com.is.gr.dao.ComCodeMapper;
import com.is.gr.vo.ComCode;

@Controller
public class LoginController {

	private static final Logger logger = LoggerFactory.getLogger(LoginController.class);
	
	@Autowired
	private ComCodeMapper comCodeMapper;
	
	@RequestMapping(value = { "/", "/login" }, method = RequestMethod.GET)
	public String getLogin() {
		
		logger.info("-> []");
		
		logger.info("<- []");
		return "login";
	}
	
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String getLogout(HttpServletRequest request) {
		
		logger.info("-> []");
		
		HttpSession session = request.getSession(false);
		if (session != null)
			session.invalidate();
		
		logger.info("<- []");
		return "login";
	}
	
	@RequestMapping(value = "/loginfail", method = RequestMethod.GET)
	public String getLoginFail(
			@RequestParam(value = "cause", required = true) Integer cause,
			ModelMap modelMap) {
		
		logger.info("-> [cause = {}]", cause);
		
		ComCode comCode = new ComCode(Codes.INFO_CODE_GROUP, Codes.VALID_PASSWORD_CNT_CODE);
		Integer validPasswordFailCnt = Integer.parseInt(comCodeMapper.getComCode(comCode).getCodeValue());
		modelMap.put("cause", cause);
		modelMap.put("validPasswordFailCnt", validPasswordFailCnt);
		
		logger.info("<- [cause = {}], [validPasswordFailCnt = {}]", cause, validPasswordFailCnt);
		return "login_fail";
	}
	
	@RequestMapping(value = "/sessiontimeout", method = RequestMethod.GET)
	public String getSessionTimeout() {
		
		logger.info("-> []");
		
		logger.info("<- []");
		return "error/session_expired";
	}
	
}
