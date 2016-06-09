<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<div class="container">
	<div class="row">
		<div class="col-md-6 col-md-offset-3 col-sm-8 col-sm-offset-2 col-xs-12">
			<div class="panel panel-info">
				<div class="panel-heading">
					<div class="panel-title">
						<i class="fa fa-user-plus" aria-hidden="true"></i>
						New profile
					</div>
				</div>
				<div class="panel-body">
					<p>
						Warning!
						<br />
						Your name can not be changed after registration. Provide your real name.
					</p>
					<form:form action="/sign-up" method="post" commandName="signUpForm">
						<div class="form-group">
							<label>First name</label>
							<input name="firstName" type="text" class="form-control" placeholder="Example: Richard" />
							<form:errors path="firstName" cssClass="alert alert-danger" role="alert" element="div" />
						</div>
						<div class="form-group">
							<label>Last name</label>
							<input name="lastName" type="text" class="form-control" placeholder="Example: Hendricks" />
							<form:errors path="lastName" cssClass="alert alert-danger" role="alert" element="div" />
						</div>
						<div class="form-group">
							<label>Password</label>
							<input name="password" type="password" class="form-control" placeholder="Password" />
							<form:errors path="password" cssClass="alert alert-danger" role="alert" element="div" />
						</div>
						<div class="form-group">
							<label>Confirm password</label>
							<input name="confirm" type="password" class="form-control" placeholder="Confirm password" />
							<form:errors path="confirm" cssClass="alert alert-danger" role="alert" element="div" />
							<form:errors path="" cssClass="alert alert-danger" element="div" />
						</div>
						<div class="form-group"><div id="g-recaptcha"></div></div>
						<input name="recaptchaResponse" type="hidden" id="recaptchaResponse" />
						<script src="https://www.google.com/recaptcha/api.js?onload=onloadCallback&render=explicit&hl=en" async defer></script>
						<form:errors path="recaptchaResponse" cssClass="alert alert-danger" role="alert" element="div" />
						<div class="form-group">
							<button type="submit" class="btn btn-primary">Sign up</button>
						</div>
					</form:form>
				</div>
			</div>
		</div>
	</div>
</div>