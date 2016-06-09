package net.devstudy.resume.service.impl;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.annotation.JsonProperty;

import net.devstudy.resume.exception.RecaptchaServiceException;
import net.devstudy.resume.service.RecaptchaService;

@Service
public class RecaptchaServiceImpl implements RecaptchaService
{
	private static final Logger LOGGER = LoggerFactory.getLogger(RecaptchaServiceImpl.class);
	
	private static class RecaptchaResponse
	{
		@JsonProperty("success")
		private boolean success;

		@JsonProperty("error-codes")
		private Collection<String> errorCodes;
	}

	@Value("${recaptcha.url}")
	private String recaptchaUrl;

	@Value("${recaptcha.secret.key}")
	private String recaptchaSecretKey;

	private final RestTemplate restTemplate;

	@Autowired
	public RecaptchaServiceImpl(RestTemplate restTemplate)
	{
		this.restTemplate = restTemplate;
	}

	@Override
	public boolean isResponseValid(String remoteIP, String response)
	{
		RecaptchaResponse recaptchaResponse;
		try
		{
			recaptchaResponse = restTemplate.postForEntity(recaptchaUrl, createBody(recaptchaSecretKey, remoteIP, response), RecaptchaResponse.class).getBody();
		}
		catch (RestClientException e)
		{
			LOGGER.error("Recaptcha API not available");
			throw new RecaptchaServiceException("Recaptcha API not available ", e);
		}
		return recaptchaResponse.success;
	}

	private MultiValueMap<String, String> createBody(String secret, String remoteIP, String response)
	{
		MultiValueMap<String, String> form = new LinkedMultiValueMap<>();
		form.add("secret", secret);
		form.add("remoteip", remoteIP);
		form.add("response", response);
		return form;
	}
}
