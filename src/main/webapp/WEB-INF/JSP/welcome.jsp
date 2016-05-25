<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="resume" tagdir="/WEB-INF/tags"%>
<div class="container">
	<table class="table table-borderless">
		<tr>
			<td>
				<div id="profileContainer" class="row" data-profile-total="${page.totalPages}" data-profile-number="${page.number}">
					<jsp:include page="fragment/welcome-more.jsp" />
				</div>
			</td>
		</tr>
		<tr>
			<td>
				<c:if test="${page.number < page.totalPages - 1}">
					<div id="loadMoreContainer" class="text-center">
						<a href="javascript:resume.welcomeMore();" class="btn btn-primary">Load more</a>
					</div>
					<div id="loadMoreIndicator" class="text-center" style="display: none;">
						<img src="/static/img/large-loading.gif" alt="loading..." />
					</div>
				</c:if>
			</td>
		</tr>
	</table>
</div>