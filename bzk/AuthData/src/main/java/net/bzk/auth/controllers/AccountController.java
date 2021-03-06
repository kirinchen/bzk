package net.bzk.auth.controllers;

import javax.inject.Inject;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import net.bzk.auth.aop.CurrentUser;
import net.bzk.auth.model.Account;
import net.bzk.auth.service.AccountService;

@CrossOrigin(maxAge = 3600, methods = { RequestMethod.GET, RequestMethod.PUT, RequestMethod.POST, RequestMethod.PATCH,
		RequestMethod.OPTIONS, RequestMethod.HEAD }, allowedHeaders = "*", origins = "*")
@Validated
@Controller
@RequestMapping(value = "/user/")
public class AccountController {

	@Inject
	private AccountService service;


	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	@RequestMapping(value = "me", method = RequestMethod.GET)
	public Account getCurrentUser(@CurrentUser Account userPrincipal) {
		return userPrincipal;
	}

}
