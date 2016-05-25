<%@ tag pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="resume" tagdir="/WEB-INF/tags"%>
<%@ attribute name="index" required="true" type="java.lang.Object"%>
<%@ attribute name="education" required="false" type="net.devstudy.resume.entity.Education"%>
<%@ attribute name="minYear" required="true" type="java.lang.Object"%>
<%@ attribute name="maxYear" required="true" type="java.lang.Object"%>
<tr>
	<td>
		<div class="row">
			<div class="panel panel-default">
				<input type="hidden" name="items[${index}].id" value="${education.id}" />
				<table class="table table-borderless">
					<tr>
						<td colspan="2">
							<label>Speciality</label>
							<input name="items[${index}].speciality" type="text" class="form-control" placeholder="Speciality" value="${education.speciality}">
							<form:errors path="items[${index}].speciality" cssClass="alert alert-danger" role="alert" element="div" />
						</td>
						<td class="text-muted">
							<button type="button" class="close" aria-label="Close" onclick="deleteRow(this);">
								<span aria-hidden="true">&times;</span>
							</button>
						</td>
					</tr>
					<tr>
						<td width="48%">
							<label>Starting year</label>
							<select name="items[${index}].startingYear" class="form-control">
								<c:forEach var="year" begin="${minYear}" end="${maxYear}">
									<option ${year == education.startingYear ? ' selected="selected"' : ''}>${year}</option>
								</c:forEach>
							</select>
							<form:errors path="" cssClass="alert alert-danger" element="div"/>
						</td>
						<td width="48%">
							<label>Completion year</label>
							<select name="items[${index}].completionYear" class="form-control">
								<option value="${null}">Not finished</option>
								<c:forEach var="year" begin="${minYear}" end="${maxYear}">
									<option ${year == education.completionYear ? ' selected="selected"' : ''}>${year}</option>
								</c:forEach>
							</select>
							<form:errors path="items[${index}]" cssClass="alert alert-danger" element="div"/>
						</td>
						<td width="4%"></td>
					</tr>
					<tr>
						<td>
							<label>University</label>
							<input name="items[${index}].university" type="text" class="form-control" placeholder="University" value="${education.university}" />
							<form:errors path="items[${index}].university" cssClass="alert alert-danger" role="alert" element="div" />
						</td>
						<td>
							<label>Department</label>
							<input name="items[${index}].department" type="text" class="form-control" placeholder="Department" value="${education.department}" />
							<form:errors path="items[${index}].department" cssClass="alert alert-danger" role="alert" element="div" />
						</td>
						<td></td>
					</tr>
				</table>
			</div>
		</div>
	</td>
</tr>