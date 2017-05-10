package com.liberty.controller;

import com.liberty.error.NotFoundException;
import org.springframework.social.RateLimitExceededException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionHandlingController {

	@ExceptionHandler(RateLimitExceededException.class)
	public String rateLimitExceeded(RateLimitExceededException e, Model model) {
		model.addAttribute("providerId", e.getProviderId());
		return "error/ratelimit";
	}

	@ExceptionHandler(NotFoundException.class)
	public String pageNotFound(NotFoundException ex, Model model) {
		model.addAttribute("message", ex.getMessage());
		return "error/pagenotfound";
	}
}
