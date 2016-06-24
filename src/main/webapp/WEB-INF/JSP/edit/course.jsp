<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="resume" tagdir="/WEB-INF/tags"%>
<form:form action="/edit/course" method="post" commandName="courseForm">
	<div class="container resume-edit-block">
		<h2 class="text-center">Courses</h2>
		<table class="table" id="table">
			<c:forEach var="course" items="${courseForm.items}" varStatus="status">
				<resume:edit-block-course index="${status.index}" course="${course}" minYear="${minYear}" maxYear="${maxYear}" />
			</c:forEach>
			<tr>
				<td align="center">
					<a href="/add/course" class="btn btn-default">Add course</a>
					<span class="text-muted">Save changes before adding new course! Unsaved data will be lost!</span>
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
