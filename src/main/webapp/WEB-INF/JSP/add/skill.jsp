<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="resume" tagdir="/WEB-INF/tags"%>
<form:form action="/add/skill" method="post" commandName="skillForm">
	<div class="container resume-edit-block">
		<h2 class="text-center">Technical skills in the frameworks and technologies.</h2>
		<table class="table" id="table">
			<tr>
				<th>Category</th>
				<th>Description</th>
			</tr>
			<tr>
				<td width="20%">
					<select name="category" class="form-control">
						<c:forEach var="category" items="${skillCategories}">
							<option value="${category.name}">${category.name}</option>
						</c:forEach>
					</select>
				</td>
				<td width="80%">
					<textarea name="description" class="form-control" rows="3" style="resize: none;" required="required" placeholder="Description"></textarea>
					<form:errors path="description" cssClass="alert alert-danger" role="alert" element="div" />
				</td>
			</tr>
			<tr>
				<td colspan="2" align="center">
					<button type="submit" class="btn btn-primary">Save</button>
					<a href="/edit/skill" class="btn btn-danger">Cancel</a>
				</td>
			</tr>
		</table>
	</div>
</form:form>