<%@ tag pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="resume" tagdir="/WEB-INF/tags"%>
<%@ attribute name="index" required="true" type="java.lang.Object"%>
<%@ attribute name="certificate" required="false" type="net.devstudy.resume.entity.Certificate"%>
<div id="ui-item-${index}" class="col-sm-6 col-md-3">
	<input type="hidden" name="items[${index}].id" value="${certificate.id}" />
	<input type="hidden" name="items[${index}].img" value="${certificate.img}" />
	<input type="hidden" name="items[${index}].imgSmall" value="${certificate.imgSmall}" />
	<input type="hidden" name="items[${index}].description" value="${certificate.description}" />
	<button type="button" class="close" aria-label="Close" onclick="$('#ui-item-${index}').remove();">
		<span aria-hidden="true">&times;</span>
	</button>
	<a href="${certificate.img}" class="thumbnail text-center">
		<img src="${certificate.imgSmall}" alt="certificate">
		<span>${certificate.description}</span>
	</a>
</div>