<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="resume" tagdir="/WEB-INF/tags"%>
<resume:edit-navtab section="General" />
<form:form action="/edit/general?${_csrf.parameterName}=${_csrf.token}" method="post" commandName="form" enctype="multipart/form-data">
	<div class="container resume-edit-block">
		<h2 class="text-center">${form.fullName}</h2>
		<input type="hidden" name="fullName" value="${form.fullName}" />
		<table class="table">
			<tr>
				<td class="general-td-items">
					<strong>Photo *</strong>
				</td>
				<td class="general-td-fileds">
					<c:if test="${form.photo != null}">
						<img src="${form.photo}" class="img-responsive" width="50%" alt="Photo">
						<input type="hidden" name="photo" value="${form.photo}" />
					</c:if>
					<c:if test="${form.photo == null}">
						<img src="/static/img/blank-photo.jpg" class="img-responsive" width="50%" alt="Photo">
					</c:if>
					<br />
					<div class="input-group">
						<span class="form-control" id="fileName">Choose image file</span>
						<span class="input-group-btn">
							<input class="input-file" id="fileInput" name="file" type="file" value="${form.file}" />
							<span class="btn btn-warning" id="fileButton">
								<i class="fa fa-folder-open" aria-hidden="true"></i>
								Browse...
							</span>
						</span>
					</div>
					<form:errors path="" cssClass="alert alert-danger" role="alert" element="div" />
				</td>
				<td class="text-muted general-td-notes">
					1. Photo can tell a lot about a candidate: from his aesthetic qualities to his relation to the search of serious work.
					<br />
					2. Photo as in a passport or in a suit is not obligatory, most importantly is adequate and neat appearance.
					<br />
					3. As examples you can look at photos of the demonstration resumes.
					<br />
					4. Photo size should be no less than 400x400.
					<br />
					5. File format must be jpg.
				</td>
			</tr>
			<tr>
				<td>
					<strong>Birthday *</strong>
				</td>
				<td>
					<input name="birthdayString" type="text" class="form-control" placeholder="Birthday" value="${form.birthdayString}" />
					<form:errors path="birthdayString" cssClass="alert alert-danger" role="alert" element="div" />
				</td>
				<td class="text-muted">Date format: yyyy-mm-dd.</td>
			</tr>
			<tr>
				<td>
					<strong>City *</strong>
				</td>
				<td>
					<input name="city" type="text" class="form-control" placeholder="City" value="${form.city}" />
					<form:errors path="city" cssClass="alert alert-danger" role="alert" element="div" />
				</td>
				<td class="text-muted"></td>
			</tr>
			<tr>
				<td>
					<strong>Country *</strong>
				</td>
				<td>
					<input name="country" type="text" class="form-control" placeholder="Country" value="${form.country}" />
					<form:errors path="country" cssClass="alert alert-danger" role="alert" element="div" />
				</td>
				<td class="text-muted"></td>
			</tr>
			<tr>
				<td>
					<strong>Email *</strong>
				</td>
				<td>
					<input name="email" type="text" class="form-control" placeholder="Email" value="${form.email}" />
					<form:errors path="email" cssClass="alert alert-danger" role="alert" element="div" />
				</td>
				<td class="text-muted">
					1. It is desirable that email contains your name and surname as in your passport. If the specified name is already taken, contraction is
					possible.
					<br />
					2. It is not recommended to use creative email, such as TheBestDeveloper@gmail.com, lakomka@gmail.com, etc.
					<br />
					3. It is not recommended to use the domain name of the employer where you are currently working.
					<br />
					4. It is recommended for programmers to use domain @gmail.com.
				</td>
			</tr>
			<tr>
				<td>
					<strong>Phone *</strong>
				</td>
				<td>
					<input name="phone" type="text" class="form-control" placeholder="Phone" value="${form.phone}" />
					<form:errors path="phone" cssClass="alert alert-danger" role="alert" element="div" />
				</td>
				<td class="text-muted">Phone number should be working, and from which you will answer calls from unknown to you numbers. Phone number must be
					provided in the E.164 format, for example: +380501234567.</td>
			</tr>
			<tr>
				<td>
					<strong>Objective *</strong>
				</td>
				<td>
					<input name="objective" type="text" class="form-control" placeholder="Objective" value="${form.objective}" />
					<form:errors path="objective" cssClass="alert alert-danger" role="alert" element="div" />
				</td>
				<td class="text-muted">In this section you need to specify the desired position, short and clear as much as possible.</td>
			</tr>
			<tr>
				<td>
					<strong>Summary *</strong>
				</td>
				<td>
					<textarea name="summary" class="form-control" rows="6" placeholder="Summary">${form.summary}</textarea>
					<form:errors path="summary" cssClass="alert alert-danger" role="alert" element="div" />
				</td>
				<td class="text-muted">
					1. In this section you should describe your experience (practical and theoretical) in the direction in which you are looking for a job.
					<br />
					2. If you work as a photographer and want to change the area of your activity, you shouldn't describe your achievements in photography.
					<br />
					3. If in desired positions you do not have the achievements, it is a reason to pass any courses in this area, or to start studying the
					foundations of a future profession.
				</td>
			</tr>
			<tr>
				<td colspan="3" class="align-center">
					<button type="submit" class="btn btn-primary">Edit</button>
				</td>
			</tr>
		</table>
	</div>
</form:form>
<resume:modal-message message="${message}" />