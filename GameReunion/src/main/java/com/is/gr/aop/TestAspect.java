package com.is.gr.aop;

import java.util.Arrays;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.is.gr.commons.Utility;
import com.is.gr.vo.FindBoard;
import com.is.gr.vo.FreeBoard;
import com.is.gr.vo.FreeBoardComment;

@Aspect
public class TestAspect {
	
	private static final Logger logger = LoggerFactory.getLogger(TestAspect.class);
	
	private static final String INSERT_FREE_BOARD 			= "insertFreeBoard";
	private static final String INSERT_FIND_BOARD 			= "insertFindBoard";
	private static final String INSERT_FREE_BOARD_COMMENT	= "insertFreeBoardComment";
	
	@Before("execution(* com.is.gr.service.*.insert*(..))")
	public void insertBoardBefore(JoinPoint joinPoint) {
		
		Object[] params = joinPoint.getArgs();
		String methodName = joinPoint.getSignature().getName();
		
		logger.info("~~ [methodName = {}], [params = {}]", methodName, Arrays.toString(params));
		
		if (methodName == null)
			return;
		
		if (params != null && params.length > 0) {
			// 게시판, 댓글의 비속어 필터링
			// TODO XSS 공격 방어로 특수문자 변환 작업 여기서 해줄 것!
			switch (methodName) {
			case INSERT_FREE_BOARD:
				FreeBoard freeBoard = (FreeBoard)params[0];
				freeBoard.setContents(Utility.swearwordFilter(freeBoard.getContents()));
				break;
			case INSERT_FIND_BOARD:
				FindBoard findBoard = (FindBoard)params[0];
				findBoard.setContents(Utility.swearwordFilter(findBoard.getContents()));
				break;
			case INSERT_FREE_BOARD_COMMENT:
				FreeBoardComment freeBoardComment = (FreeBoardComment)params[0];
				freeBoardComment.setContents(Utility.swearwordFilter(freeBoardComment.getContents()));
				break;
			default:
				break;
			}
		}
	}
	
	@AfterReturning(pointcut = "execution(* com.is.gr.service.*.insert*(..))", returning= "result")
	public void afterReturning(
			JoinPoint joinPoint,
			Object result) {
		
		Object[] params = joinPoint.getArgs();
		String methodName = joinPoint.getSignature().getName();
		
		logger.info("~~ [methodName = {}], [params = {}]", methodName, Arrays.toString(params));
		
	}
	
}
