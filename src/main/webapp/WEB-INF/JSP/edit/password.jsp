<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<div class="container">
	<div class="row">
		<div class="col-md-6 col-md-offset-3 col-sm-8 col-sm-offset-2 col-xs-12">
			<div class="panel panel-info">
				<div class="panel-heading">
					<div class="panel-title">
						<i class="fa fa-unlock-alt" aria-hidden="true"></i>
						Change password
					</div>
				</div>
				<div class="panel-body">
					<p>Input your new password and confirm it.</p>
					<form:form action="/edit/password" method="post" commandName="changePasswordForm">
						<div class="form-group">
							<label>New password</label>
							<input name="password" type="password" class="form-control" placeholder="New password" />
							<form:errors path="password" cssClass="alert alert-danger" role="alert" element="div" />
						</div>
						<div class="form-group">
							<label>Confirm password</label>
							<input name="confirm" type="password" class="form-control" placeholder="Confirm password" />
							<form:errors path="confirm" cssClass="alert alert-danger" role="alert" element="div" />
							<form:errors path="" cssClass="alert alert-danger" element="div" />
						</div>
						<button type="submit" class="btn btn-primary pull-left">Change</button>
					</form:form>
				</div>
			</div>
		</div>
	</div>
</div>