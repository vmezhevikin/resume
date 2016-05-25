<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="resume" tagdir="/WEB-INF/tags"%>
<form:form action="/edit/experience" method="post" commandName="experienceForm">
	<div class="container" style="background-color: white;">
		<h2 class="text-center">Practical experience</h2>
		<table class="table" id="table">
			<c:forEach var="experience" items="${experienceForm.items}" varStatus="status">
				<resume:edit-block-experience index="${status.index}" experience="${experience}" minYear="${minYear}" maxYear="${maxYear}"/>
			</c:forEach>
			<tr>
				<td align="center">
					<a href="/add/experience" class="btn btn-default">Add new experience</a>
					<span class="text-muted">Save changes before adding new experience! Unsaved data will be lost!</span>
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