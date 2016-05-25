<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="resume" tagdir="/WEB-INF/tags"%>
<form:form action="/edit/certificate/upload" method="post" commandName="certificateUploadForm" enctype="multipart/form-data">
	<div class="container" style="background-color: white;">
		<h2 class="text-center">Certificates</h2>
		<hr />
		<div class="container">
			<div class="form-group">
				<label>Choose certificate file</label>
				<input name="file" type="file"/>
			</div>
			<div class="form-group">
				<label>Description</label>
				<input name="description" type="text" class="form-control" placeholder="Description"/>
			</div>
			<button type="submit" class="btn btn-primary pull-left">Add certificate</button>
		</div>
	</div>
</form:form>