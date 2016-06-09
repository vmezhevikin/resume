<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<form:form action="/restore" method="post">
	<div class="container">
		<div class="row">
			<div class="col-md-6 col-md-offset-3 col-sm-8 col-sm-offset-2 col-xs-12">
				<div class="panel panel-info">
					<div class="panel-heading">
						<div class="panel-title">
							<i class="fa fa-unlock" aria-hidden="true"></i>
							Access recovery
						</div>
					</div>
					<div class="panel-body">
						<div class="form-group">
							<label>Input your UID or Email or Phone number</label>
							<input name="anyUniqueId" type="text" class="form-control" placeholder="UID or Email or Phone number" />
						</div>
						<button type="submit" class="btn btn-primary">Restore</button>
					</div>
				</div>
			</div>
		</div>
	</div>
</form:form>