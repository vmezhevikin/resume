<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:choose>
	<c:when test="${production}">
		<script src="/static/js/jquery-min.js?v=${jsVersion}"></script>
		<script src="/static/js/script.min.js?v=${jsVersion}"></script>
	</c:when>
	<c:otherwise>
		<script src="/static/js/jquery.js"></script>
		<script src="/static/js/bootstrap.js"></script>
		<script src="/static/js/app.js"></script>
	</c:otherwise>
</c:choose>