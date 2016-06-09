<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="resume" tagdir="/WEB-INF/tags"%>
<form:form action="/edit/language" method="post" commandName="languageForm">
	<div class="container resume-edit-block">
		<h2 class="text-center">Foreign languages</h2>
		<table class="table" id="table">
			<c:forEach var="language" items="${languageForm.items}" varStatus="status">
				<resume:edit-block-language index="${status.index}" language="${language}" />
			</c:forEach>
			<tr>
				<td align="center">
					<a href="/add/language" class="btn btn-default">Add new language</a>
					<span class="text-muted">Save changes before adding new language! Unsaved data will be lost!</span>
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