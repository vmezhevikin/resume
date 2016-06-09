<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<form:form action="/add/education" method="post" commandName="educationForm">
	<div class="container resume-edit-block">
		<div class="text-center">Education</div>
		<table class="table" id="table">
			<tr>
				<td>
					<div class="row">
						<div class="panel panel-default">
							<table class="table table-borderless">
								<tr>
									<td colspan="2">
										<label>Speciality</label>
										<input name="speciality" type="text" class="form-control" placeholder="Speciality" />
										<form:errors path="speciality" cssClass="alert alert-danger" role="alert" element="div" />
									</td>
								</tr>
								<tr>
									<td width="50%">
										<label>Starting year</label>
										<select name="startingYear" class="form-control">
											<c:forEach var="year" begin="${minYear}" end="${maxYear}">
												<option>${year}</option>
											</c:forEach>
										</select>
										<form:errors path="" cssClass="alert alert-danger" element="div" />
									</td>
									<td width="50%">
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
									<td>
										<label>University</label>
										<input name="university" type="text" class="form-control" placeholder="University" />
										<form:errors path="university" cssClass="alert alert-danger" role="alert" element="div" />
									</td>
									<td>
										<label>Department</label>
										<input name="department" type="text" class="form-control" placeholder="Department" />
										<form:errors path="department" cssClass="alert alert-danger" role="alert" element="div" />
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
					<a href="/edit/education" class="btn btn-danger">Cancel</a>
				</td>
			</tr>
		</table>
	</div>
</form:form>