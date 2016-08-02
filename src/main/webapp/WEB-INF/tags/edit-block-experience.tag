<%@ tag pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="resume" tagdir="/WEB-INF/tags"%>
<%@ attribute name="index" required="true" type="java.lang.Object"%>
<%@ attribute name="experience" required="false" type="net.devstudy.resume.entity.Experience"%>
<%@ attribute name="minYear" required="true" type="java.lang.Object"%>
<%@ attribute name="maxYear" required="true" type="java.lang.Object"%>
<tr id="item-${index}">
	<td>
		<input type="hidden" name="items[${index}].id" value="${experience.id}" />
		<div class="row">
			<div class="panel panel-default">
				<table class="table table-borderless">
					<tr>
						<td colspan="2">
							<label>Position</label>
							<input name="items[${index}].position" type="text" class="form-control" placeholder="Position" value="${experience.position}" />
							<form:errors path="items[${index}].position" cssClass="alert alert-danger" role="alert" element="div" />
						</td>
						<td colspan="2">
							<label>Company</label>
							<input name="items[${index}].company" type="text" class="form-control" placeholder="Company" value="${experience.company}" />
							<form:errors path="items[${index}].company" cssClass="alert alert-danger" role="alert" element="div" />
						</td>
						<td class="text-muted td-close-btn">
							<button type="button" class="close  remove-item-btn" aria-label="Close" id="close-btn-${index}" data-item="${index}">
								<span aria-hidden="true">&times;</span>
							</button>
						</td>
					</tr>
					<tr>
						<td class="experience-td-date">
							<label>Starting month</label>
							<select name="items[${index}].startingMonth" class="form-control">
								<c:forEach var="month" begin="1" end="12">
									<option value="${month}" ${month == experience.startingMonth ? ' selected="selected"' : ''}>${monthName[month-1]}</option>
								</c:forEach>
							</select>
						</td>
						<td class="experience-td-date">
							<label>Starting year</label>
							<select name="items[${index}].startingYear" class="form-control">
								<c:forEach var="year" begin="${minYear}" end="${maxYear}">
									<option ${year == experience.startingYear ? ' selected="selected"' : ''}>${year}</option>
								</c:forEach>
							</select>
						</td>
						<td class="experience-td-date">
							<label>Completion month</label>
							<select name="items[${index}].completionMonth" class="form-control">
								<option value="${null}">Not finished</option>
								<c:forEach var="month" begin="1" end="12">
									<option value="${month}" ${month == experience.completionMonth ? ' selected="selected"' : ''}>${monthName[month-1]}</option>
								</c:forEach>
							</select>
						</td>
						<td class="experience-td-date">
							<label>Completion year</label>
							<select name="items[${index}].completionYear" class="form-control">
								<option value="${null}">Not finished</option>
								<c:forEach var="year" begin="${minYear}" end="${maxYear}">
									<option ${year == experience.completionYear ? ' selected="selected"' : ''}>${year}</option>
								</c:forEach>
							</select>
							<form:errors path="items[${index}]" cssClass="alert alert-danger" element="div"/>
						</td>
						<td></td>
					</tr>
					<tr>
						<td colspan="4">
							<label>Responsibilities</label>
							<textarea name="items[${index}].responsibility" class="form-control" rows="3" style="resize: none;" placeholder="Responsibilities">${experience.responsibility}</textarea>
							<form:errors path="items[${index}].responsibility" cssClass="alert alert-danger" role="alert" element="div" />
						</td>
						<td></td>
					</tr>
					<tr>
						<td colspan="2">
							<label>Demo</label>
							<input name="items[${index}].demo" type="text" class="form-control" placeholder="Demo" value="${experience.demo}" />
							<form:errors path="items[${index}].demo" cssClass="alert alert-danger" role="alert" element="div" />
						</td>
						<td colspan="2">
							<label>Source code</label>
							<input name="items[${index}].code" type="text" class="form-control" placeholder="Source code" value="${experience.code}" />
							<form:errors path="items[${index}].code" cssClass="alert alert-danger" role="alert" element="div" />
						</td>
						<td></td>
					</tr>
				</table>
			</div>
		</div>
	</td>
</tr>