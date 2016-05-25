<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<form:form action="/edit/hobby" method="post" commandName="hobbyForm">
	<input type="hidden" name="maxSize" value="${hobbyForm.maxSize}" />
	<div class="container" style="background-color: white;">
		<h2 class="text-center">Hobbies.</h2>
		<div class="form-group">
			<table class="table">
				<tr>
					<td class="text-muted text-center">
						<h4 class="text-center">Some employers pay attention to the hobbies of the candidate.</h4>
						Select no more than ${hobbyForm.maxSize} items.
					</td>
				</tr>
				<tr>
					<td>
						<div class="row">
							<c:forEach var="hobby" items="${hobbies}" varStatus="status">
								<div class="col-md-3 col-sm-6 col-xs-12" align="center" style="margin-bottom: 15px;">
									<table>
										<tr>
											<td width="5%">
												<form:checkbox path="checkedItems" value="${hobby.name}" />
											</td>
											<td width="15%" align="center">${hobby.icon}</td>
											<td width="75%">${hobby.name}</td>
										</tr>
									</table>
								</div>
							</c:forEach>
						</div>
						<form:errors path="" cssClass="alert alert-danger" role="alert" element="div" />
					</td>
				</tr>
				<tr>
					<td align="center">
						<button type="submit" class="btn btn-primary">Save</button>
					</td>
				</tr>
			</table>
		</div>
	</div>
</form:form>