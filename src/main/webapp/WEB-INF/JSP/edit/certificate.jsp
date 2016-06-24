<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="resume" tagdir="/WEB-INF/tags"%>
<form:form action="/edit/certificate" method="post" commandName="certificateForm">
	<div class="container resume-edit-block">
		<h2 class="text-center">Certificates</h2>
		<table class="table">
			<tr>
				<td>
					<div class="row">
						<c:forEach var="certificate" items="${certificateForm.items}" varStatus="status">
							<resume:edit-block-certificate index="${status.index}" certificate="${certificate}" />
						</c:forEach>
					</div>
				</td>
			</tr>
			<tr>
				<td colspan="3" align="center">
					<a href="/add/certificate" class="btn btn-default">Add certificate</a>
					<span class="text-muted">Save changes before adding new certificate! Unsaved data will be lost!</span>
				</td>
			</tr>
			<tr>
				<td colspan="3" align="center">
					<button type="submit" class="btn btn-primary">Save</button>
				</td>
			</tr>
		</table>
	</div>
</form:form>