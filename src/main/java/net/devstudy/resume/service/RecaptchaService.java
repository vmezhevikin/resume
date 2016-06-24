package net.devstudy.resume.service;

public interface RecaptchaService {
	
	boolean isResponseValid(String remoteIP, String response);
}