<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="resume" tagdir="/WEB-INF/tags"%>
<form:form action="/edit/skill" method="post" commandName="skillForm">
	<div class="container resume-edit-block">
		<h2 class="text-center">Technical skills in the frameworks and technologies.</h2>
		<table class="table" id="table">
			<tr>
				<th>Category</th>
				<th>Description</th>
			</tr>
			<c:forEach var="skill" items="${skillForm.getItems()}" varStatus="status">
				<resume:edit-block-skill index="${status.index}" skill="${skill}" />
			</c:forEach>
			<tr>
				<td colspan="3" align="center">
					<a href="/add/skill" class="btn btn-default">Add new skill</a>
					<span class="text-muted">Save changes before adding new skill! Unsaved data will be lost!</span>
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