<%@ tag pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="resume" tagdir="/WEB-INF/tags"%>
<%@ attribute name="index" required="true" type="java.lang.Object"%>
<%@ attribute name="hobby" required="false" type="net.devstudy.resume.entity.Hobby"%>
<c:if test="${hobby.checked == 'checked'}">
	<c:set var="cssStyle" value="btn-success" />
</c:if>
<c:if test="${hobby.checked == 'unchecked'}">
	<c:set var="cssStyle" value="btn-default" />
</c:if>
<div class="col-xs-12 col-sm-6 col-md-4 col-lg-3 text-center hobby-block">
	<div class="btn ${cssStyle} hobby-btn" id="hobby-div-${index}" data-hobby-ind="${index}">
		<input type="hidden" name="items[${index}].id" value="${hobby.id}" />
		<input type="hidden" name="items[${index}].checked" value="${hobby.checked}" id="hobby-input-${index}" />
		<input type="hidden" name="items[${index}].icon" value="${hobby.icon}" />
		<input type="hidden" name="items[${index}].description" value="${hobby.description}" />
		<span class="hobby-td-icon">${hobby.icon}</span>
		<span class="hobby-td-name">${hobby.description}</span>
	</div>
</div>