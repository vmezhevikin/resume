<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="resume" tagdir="/WEB-INF/tags"%>
<form:form action="/edit/education" method="post" commandName="educationForm">
	<div class="container" style="background-color: white;">
		<h2 class="text-center">Education</h2>
		<table class="table" id="table">
			<c:forEach var="education" items="${educationForm.items}" varStatus="status">
				<resume:edit-block-education index="${status.index}" education="${education}"  minYear="${minYear}" maxYear="${maxYear}" />
			</c:forEach>
			<tr>
				<td align="center">
					<a href="/add/education" class="btn btn-default">Add new education</a>
					<span class="text-muted">Save changes before adding new education! Unsaved data will be lost!</span>
				</td>
			</tr>
			<tr>
				<td align="center">
					<button type="submit" class="btn btn-primary">Save</button>
				</td>
			</tr>
		</table>
	</div>
</form:form>