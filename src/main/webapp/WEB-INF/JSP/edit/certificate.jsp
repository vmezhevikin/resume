<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="resume" tagdir="/WEB-INF/tags"%>
<resume:edit-navtab section="Certificate" />
<form:form action="/edit/certificate" method="post" commandName="form">
	<div class="container resume-edit-block">
		<h2 class="text-center">Certificates</h2>
		<hr/>
		<table class="table table-borderless">
			<tr>
				<td>
					<div class="row">
						<c:forEach var="certificate" items="${form.items}" varStatus="status">
							<resume:edit-block-certificate index="${status.index}" certificate="${certificate}" />
						</c:forEach>
					</div>
				</td>
			</tr>
			<tr>
				<td colspan="3" class="align-center">
					<hr/>
					<a href="/add/certificate" type="button" class="btn btn-success">Add</a>
					<button type="submit" class="btn btn-primary">Edit</button>
				</td>
			</tr>
		</table>
	</div>
</form:form>
<resume:modal-certificate />
<resume:modal-message message="${message}" />