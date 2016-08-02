<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="resume" tagdir="/WEB-INF/tags"%>
<resume:edit-navtab section="Hobby" />
<form:form action="/edit/hobby" method="post" commandName="form">
	<div class="container resume-edit-block">
		<h2 class="text-center">Hobbies</h2>
		<hr />
		<h4 class="text-center text-muted">Some employers pay attention to the hobbies of the candidate.</h4>
		<p class="text-center text-muted">Select no more than ${form.maxSize} items.</p>
		<hr />
		<input type="hidden" name="maxSize" value="${form.maxSize}" />
		<input type="hidden" name="currSize" value="${form.currSize}" id="current-hobby-count" />
		<table class="table table-borderless">
			<tr>
				<td>
					<form:errors path="" cssClass="alert alert-danger" role="alert" element="div" />
				</td>
			</tr>
			<tr>
				<td>
					<c:forEach var="hobby" items="${form.items}" varStatus="status">
						<resume:edit-block-hobby hobby="${hobby}" index="${status.index}" />
					</c:forEach>
				</td>
			</tr>
			<tr>
				<td class="align-center">
					<button type="submit" class="btn btn-primary">Edit</button>
				</td>
			</tr>
		</table>
	</div>
</form:form>
<resume:modal-message message="${message}" />