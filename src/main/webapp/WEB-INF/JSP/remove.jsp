<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<div class="container">
	<div class="row">
		<div class="col-md-8 col-md-offset-2 col-sm-10 col-sm-offset-1 col-xs-12">
			<div class="panel panel-warning">
				<div class="panel-heading">
					<div class="panel-title">
						<i class="fa fa-exclamation-triangle" aria-hidden="true"></i>
						Remove account. Operation can not be undone.
					</div>
				</div>
				<div class="panel-body">
					<p>
						Account deleting
						<strong>CAN NOT BE UNDONE. </strong>
						Please confirm removing account
						<a href="/${profile.uid}">${profile.uid}</a>
					</p>
					<form:form action="/remove" method="post">
						<button type="submit" class="btn btn-danger">Confirm deleting</button>
					</form:form>
				</div>
			</div>
		</div>
	</div>
</div>