<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="resume" tagdir="/WEB-INF/tags"%>
<div class="container">
	<table class="table table-borderless">
		<resume:search-info query="${query}" />
		<tr>
			<td>
				<div id="profileContainer" class="row" data-profile-total="${page.totalPages}" data-profile-number="${page.number}" data-search-query="${query}">
					<jsp:include page="more-profiles.jsp" />
				</div>
			</td>
		</tr>
		<tr>
			<td>
				<div id="loadMoreContainer" class="text-center">
					<button id="loadMoreBtn" class="btn btn-primary">Load more</button>
				</div>
				<div id="loadMoreIndicator" class="text-center">
					<img src="/static/img/large-loading.gif" alt="loading..." />
				</div>
			</td>
		</tr>
	</table>
</div>