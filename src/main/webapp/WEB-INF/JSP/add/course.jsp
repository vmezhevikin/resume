<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="resume" tagdir="/WEB-INF/tags"%>
<form:form action="/add/course" method="post" commandName="courseForm">
	<div class="container resume-edit-block">
		<h2 class="text-center">Courses</h2>
		<table class="table" id="table">
			<tr>
				<td>
					<div class="row">
						<div class="panel panel-default">
							<table class="table">
								<tr>
									<td width="35%">
										<label>Description</label>
										<input name="description" type="text" class="form-control" placeholder="Description" />
										<form:errors path="description" cssClass="alert alert-danger" role="alert" element="div" />
									</td>
									<td width="35%">
										<label>School</label>
										<input name="school" type="text" class="form-control" placeholder="School" />
										<form:errors path="school" cssClass="alert alert-danger" role="alert" element="p" />
									</td>
									<td width="15%">
										<label>Completion month</label>
										<select name="completionMonth" class="form-control">
											<option value="${null}">Not finished</option>
											<c:forEach var="month" begin="1" end="12">
												<option value="${month-1}">${monthName[month-1]}</option>
											</c:forEach>
										</select>
									</td>
									<td width="15%">
										<label>Completion year</label>
										<select name="completionYear" class="form-control">
											<option value="${null}">Not finished</option>
											<c:forEach var="year" begin="${minYear}" end="${maxYear}">
												<option>${year}</option>
											</c:forEach>
										</select>
									</td>
								</tr>
							</table>
						</div>
					</div>
				</td>
			</tr>
			<tr>
				<td align="center">
					<a href="/add/course" class="btn btn-default">Add new course</a>
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
