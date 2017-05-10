package com.liberty.controller;

import org.springframework.http.HttpStatus;
import org.springframework.social.RateLimitExceededException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.NoHandlerFoundException;

@ControllerAdvice
public class ExceptionHandlingController {

	@ExceptionHandler(RateLimitExceededException.class)
	public String rateLimitExceeded(RateLimitExceededException e, Model model) {
		model.addAttribute("providerId", e.getProviderId());
		return "error/ratelimit";
	}

	/*@ExceptionHandler(NoHandlerFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public String pageNotFound(NoHandlerFoundException ex, Model model) {
		model.addAttribute("url", ex.getRequestURL());
		return "error/pagenotfound";
	}*/
}
