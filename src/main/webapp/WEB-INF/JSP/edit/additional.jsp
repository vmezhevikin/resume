<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<div class="container" style="background-color: white;">
	<h2 class="text-center">Additional information</h2>
	<form:form action="/edit/additional" method="post" commandName="profile">
		<input type="hidden" name="country" value="stub" />
		<input type="hidden" name="city" value="stub" />
		<input type="hidden" name="birthdayString" value="1990-01-01" />
		<input type="hidden" name="email" value="stub@stub.com" />
		<input type="hidden" name="phone" value="+380501234567" />
		<table class="table">
			<tr>
				<td>
					<h3 class="text-center">A few words about yourself, which will give you an advantage over other candidates.</h3>
				</td>
			</tr>
			<tr>
				<td>
					<textarea name="additionalInfo" class="form-control" rows="6" style="resize: none;" placeholder="Summary">${profile.additionalInfo}</textarea>
					<form:errors path="additionalInfo" cssClass="alert alert-danger" role="alert" element="div" />
				</td>
			</tr>
			<tr>
				<td class="text-muted">
					Provide additional information that is really important to the employer. Such as:
					<br />
					<em>the presence of an open visa to a foreign country, marital status, children, a real experience in the activities which may be your
						subject area in software development possible research experience in research institutes, etc.</em>
					<br />
					You should NOT specify your personal qualities. Almost all candidates are responsible, sociable and decent.
				</td>
			</tr>
			<tr>
				<td align="center">
					<button type="submit" class="btn btn-primary">Save</button>
				</td>
			</tr>
		</table>
	</form:form>
</div>