package net.bzk.auth.aop;

import javax.inject.Inject;

import org.springframework.core.MethodParameter;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebArgumentResolver;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import net.bzk.auth.model.Account;
import net.bzk.auth.service.AccountService;
import net.bzk.infrastructure.ex.BzkRuntimeException;

/**
 * 
 * @author kirin
 *
 */
@Component
public class CurrentUserMethodArgumentResolver implements HandlerMethodArgumentResolver {

	@Inject
	private AccountService userService;

	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		return parameter.getParameterAnnotation(CurrentUser.class) != null
				&& parameter.getParameterType().equals(Account.class);
	}

	@Override
	public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
			NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
		if (this.supportsParameter(parameter)) {
			Object o = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			if (o != null && o instanceof UserDetails) {
				UserDetails oa = (UserDetails) o;
				Account user = fetchFromDb(oa);
				if (user == null) {
					throw new AccessDeniedException("No current user found.");
				}
				return user;
			} else {
				throw new BzkRuntimeException("getPrincipal is :" + o);
			}
		} else {
			return WebArgumentResolver.UNRESOLVED;
		}
	}

	private Account fetchFromDb(Object principal) {
		Object obj = principal;
		// hardcode test user
		if (obj instanceof UserDetails) {
			UserDetails spU = (UserDetails) obj;
			Account u = userService.findByUserName(spU.getUsername()).get();
			return u;
		} else if (obj instanceof Account) {
			return (Account) obj;
		}
		throw new AccessDeniedException("No current user found.");
	}

}