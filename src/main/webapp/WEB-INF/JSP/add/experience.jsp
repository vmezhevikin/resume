<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<form:form action="/add/experience" method="post" commandName="experienceForm">
	<div class="container resume-edit-block">
		<div class="text-center">Practical experience</div>
		<table class="table" id="table">
			<tr>
				<td>
					<div class="row">
						<div class="panel panel-default">
							<table class="table table-borderless">
								<tr>
									<td colspan="2">
										<label>Position</label>
										<input name="position" type="text" class="form-control" placeholder="Position" />
										<form:errors path="position" cssClass="alert alert-danger" role="alert" element="div" />
									</td>
									<td colspan="2">
										<label>Company</label>
										<input name="company" type="text" class="form-control" placeholder="Company" />
										<form:errors path="company" cssClass="alert alert-danger" role="alert" element="div" />
									</td>
								</tr>
								<tr>
									<td width="25%">
										<label>Starting month</label>
										<select name="startingMonth" class="form-control">
											<c:forEach var="month" begin="1" end="12">
												<option value="${month}">${monthName[month-1]}</option>
											</c:forEach>
										</select>
									</td>
									<td width="25%">
										<label>Starting year</label>
										<select name="startingYear" class="form-control">
											<c:forEach var="year" begin="${minYear}" end="${maxYear}">
												<option>${year}</option>
											</c:forEach>
										</select>
									</td>
									<td width="25%">
										<label>Completion month</label>
										<select name="completionMonth" class="form-control">
											<option value="${null}">Not finished</option>
											<c:forEach var="month" begin="1" end="12">
												<option value="${month}">${monthName[month-1]}</option>
											</c:forEach>
										</select>
									</td>
									<td width="25%">
										<label>Completion year</label>
										<select name="completionYear" class="form-control">
											<option value="${null}">Not finished</option>
											<c:forEach var="year" begin="${minYear}" end="${maxYear}">
												<option>${year}</option>
											</c:forEach>
										</select>
										<form:errors path="" cssClass="alert alert-danger" element="div" />
									</td>
								</tr>
								<tr>
									<td colspan="4">
										<label>Responsibilities</label>
										<textarea name="responsibility" class="form-control" rows="3" style="resize: none;" placeholder="Responsibilities"></textarea>
										<form:errors path="responsibility" cssClass="alert alert-danger" role="alert" element="div" />
									</td>
								</tr>
								<tr>
									<td colspan="2">
										<label>Demo</label>
										<input name="demo" type="text" class="form-control" placeholder="Demo" />
										<form:errors path="demo" cssClass="alert alert-danger" role="alert" element="div" />
									</td>
									<td colspan="2">
										<label>Source code</label>
										<input name="code" type="text" class="form-control" placeholder="Source code" />
										<form:errors path="code" cssClass="alert alert-danger" role="alert" element="div" />
									</td>
								</tr>
							</table>
						</div>
					</div>
				</td>
			</tr>
			<tr>
				<td align="center">
					<button type="submit" class="btn btn-primary">Save</button>
					<a href="/edit/language" class="btn btn-danger">Cancel</a>
				</td>
			</tr>
		</table>
	</div>
</form:form>