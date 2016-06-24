package net.devstudy.resume.validator;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.context.WebApplicationContext;

import net.devstudy.resume.exception.RecaptchaServiceException;
import net.devstudy.resume.form.RecaptchaForm;
import net.devstudy.resume.service.RecaptchaService;

@Component
@Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class RecaptchaFormValidator implements Validator {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(RecaptchaFormValidator.class);

	private final HttpServletRequest httpServletRequest;

	private final RecaptchaService recaptchaService;

	@Autowired
	public RecaptchaFormValidator(HttpServletRequest httpServletRequest, RecaptchaService recaptchaService) {
		this.httpServletRequest = httpServletRequest;
		this.recaptchaService = recaptchaService;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return RecaptchaForm.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		RecaptchaForm form = (RecaptchaForm) target;

		if (form.getRecaptchaResponse() != null) {
			String remoteIP = getRemoteIP(httpServletRequest);
			if (!form.getRecaptchaResponse().isEmpty() && !recaptchaService.isResponseValid(remoteIP, form.getRecaptchaResponse())) {
				LOGGER.info("Recaptcha response invalid, remote address " + remoteIP);
				throw new RecaptchaServiceException("Recaptcha response invalid, remote address " + remoteIP);
			}
		}
	}

	private String getRemoteIP(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}

		return ip;
	}
}