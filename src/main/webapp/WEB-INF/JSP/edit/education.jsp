<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="resume" tagdir="/WEB-INF/tags"%>
<resume:edit-navtab section="Education" />
<form:form action="/edit/education" method="post" commandName="form">
	<div class="container resume-edit-block">
		<h2 class="text-center">Education</h2>
		<hr/>
		<c:set var="itemsSize" value="${form.items.size()}" />
		<table class="table table-borderless" id="edit-table" data-init-size="${itemsSize}">
			<c:forEach var="education" items="${form.items}" varStatus="status">
				<resume:edit-block-education index="${status.index}" education="${education}" minYear="${minYear}" maxYear="${maxYear}" />
			</c:forEach>
			<resume:edit-block-education index="${itemsSize}" minYear="${minYear}" maxYear="${maxYear}" />
			<tr id="add-item-row">
				<td class="align-center">
					<hr/>
					<button id="add-item" type="button" class="btn btn-success">Add</button>
					<button type="submit" class="btn btn-primary">Edit</button>
				</td>
			</tr>
		</table>
	</div>
</form:form>
<resume:modal-message message="${message}" />