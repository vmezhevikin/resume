<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="resume" tagdir="/WEB-INF/tags"%>
<div class="container">
	<table class="table table-borderless">
		<tr>
			<td>
				<div class="row">
					<div class="col-md-8 col-md-offset-2 col-sm-10 col-sm-offset-1 col-xs-12">
						<div class="panel panel-default" style="padding: 15px;">
							<h4 class="text-center">
								Search results for:
								<strong>${query}</strong>
							</h4>
							<div class="text-muted" style="font-size: 75%;">
								Searched in:
								<ul>
									<li>Objective</li>
									<li>Summary</li>
									<li>Additional Information</li>
									<li>Certificate (description)</li>
									<li>Language (name)</li>
									<li>Course (description)</li>
									<li>Practical experience (company, position)</li>
									<li>Skill (category, description)</li>
								</ul>
								You also may try improving search results using wildcards (? - any character, * - zero or more characters)
							</div>
						</div>
					</div>
				</div>
			</td>
		</tr>
		<tr>
			<td>
				<div id="profileContainer" class="row" data-profile-total="${page.totalPages}" data-profile-number="${page.number}">
					<jsp:include page="fragment/search-more.jsp" />
				</div>
			</td>
		</tr>
		<tr>
			<td>
				<c:if test="${page.number < page.totalPages - 1}">
					<div id="loadMoreContainer" class="text-center">
						<a href="javascript:resume.searchMore('${query}');" class="btn btn-primary">Load more</a>
					</div>
					<div id="loadMoreIndicator" class="text-center" style="display: none;">
						<img src="/static/img/large-loading.gif" alt="loading..." />
					</div>
				</c:if>
			</td>
		</tr>
	</table>
</div>