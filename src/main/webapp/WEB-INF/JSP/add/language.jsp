<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<form:form action="/add/language" method="post" commandName="languageForm">
	<div class="container" style="background-color: white;">
		<h2 class="text-center">Foreign languages</h2>
		<table class="table" id="table">
			<tr>
				<td>
					<div class="row">
						<div class="panel panel-default">
							<table class="table">
								<tr>
									<td width="32%">
										<label>Type</label>
										<select name="type" class="form-control">
											<option>All</option>
											<option>Writing</option>
											<option>Speaking</option>
										</select>
									</td>
									<td width="36%">
										<label>Language</label>
										<input name="name" type="text" class="form-control" placeholder="Language" />
										<form:errors path="name" cssClass="alert alert-danger" role="alert" element="div" />
									</td>
									<td width="32%">
										<label>Type</label>
										<select name="level" class="form-control">
											<option>Beginner</option>
											<option>Elementary</option>
											<option>Pre-Intermediate</option>
											<option>Intermediate</option>
											<option>Upper-Intermediate</option>
											<option>Advanced</option>
											<option>Proficiency</option>
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
					<button type="submit" class="btn btn-primary">Save</button>
					<a href="/edit/language" class="btn btn-danger">Cancel</a>
				</td>
			</tr>
		</table>
	</div>
</form:form>