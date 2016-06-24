<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:choose>
	<c:when test="${production}">
		<link rel="stylesheet" href="/static/css/style.min.css?v=${cssVersion}">
	</c:when>
	<c:otherwise>
		<link rel="stylesheet" href="/static/css/bootstrap.css">
		<link rel="stylesheet" href="/static/css/bootstrap-theme.css">
		<link rel="stylesheet" href="/static/css/font-awesome.css">
		<link rel="stylesheet" href="/static/css/app.css">
	</c:otherwise>
</c:choose>