package net.devstudy.resume.controller;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.FacebookClient.AccessToken;
import com.restfb.Parameter;
import com.restfb.Version;
import com.restfb.scope.ExtendedPermissions;
import com.restfb.scope.ScopeBuilder;
import com.restfb.scope.UserDataPermissions;
import com.restfb.types.User;

import net.devstudy.resume.entity.Profile;
import net.devstudy.resume.service.SocialService;
import net.devstudy.resume.util.SecurityUtil;

@Controller
public class FacebookController {
	
	@Value("${social.facebook.idClient}")
	private String facebookIdClient;

	@Value("${social.facebook.secret}")
	private String facebookSecret;

	private String redirectUrl;

	@Value("${app.host}")
	public void setRedirectUrl(String appHost) {
		this.redirectUrl = appHost + "/fromFb";
	}

	@Autowired
	private SocialService<User> facebookService;

	@RequestMapping(value = "/fbLogin", method = RequestMethod.GET)
	public String getFbLogin() {
		return "redirect:" + getAuthorizeUrl();
	}

	@RequestMapping(value = "/fromFb", method = RequestMethod.GET)
	public String getFromFb(@RequestParam(value = "code", required = false) String code) {
		if (StringUtils.isBlank(code)) {
			return "redirect:/sign-in";
		}

		FacebookClient facebookClient = new DefaultFacebookClient(Version.VERSION_2_6);
		AccessToken accessToken = facebookClient.obtainUserAccessToken(facebookIdClient, facebookSecret, redirectUrl,
				code);
		facebookClient = new DefaultFacebookClient(accessToken.getAccessToken(), Version.VERSION_2_6);
		User user = facebookClient.fetchObject("me", User.class, Parameter.with("fields",
				"name,email,first_name,last_name,birthday,hometown,relationship_status,education,work,languages,picture"));

		Profile profile = facebookService.findProfileViaSocailNetwork(user);
		if (profile != null) {
			SecurityUtil.authentificate(profile);
			return "redirect:/my-profile";
		}

		profile = facebookService.createNewProfileViaSocailNetwork(user);
		if (profile != null) {
			SecurityUtil.authentificate(profile);
			return "redirect:/sign-up/success-via-facebook";
		}

		return "redirect:/sign-in";
	}

	private String getAuthorizeUrl() {
		ScopeBuilder scopeBuilder = new ScopeBuilder();
		scopeBuilder.addPermission(ExtendedPermissions.EMAIL);
		scopeBuilder.addPermission(UserDataPermissions.USER_BIRTHDAY);
		scopeBuilder.addPermission(UserDataPermissions.USER_EDUCATION_HISTORY);
		scopeBuilder.addPermission(UserDataPermissions.USER_HOMETOWN);
		scopeBuilder.addPermission(UserDataPermissions.USER_RELATIONSHIPS);
		scopeBuilder.addPermission(UserDataPermissions.USER_WORK_HISTORY);
		FacebookClient client = new DefaultFacebookClient(Version.VERSION_2_6);
		return client.getLoginDialogUrl(facebookIdClient, redirectUrl, scopeBuilder);
	}
}