<%@ tag language="java" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<div class="panel panel-primary">
	<div class="panel-heading">
		<div class="panel-title">
			<i class="fa fa-university" aria-hidden="true"></i>
			Education
			<sec:authorize access="hasAuthority('USER')">
				<sec:authentication var="currProfilieUid" property="principal.username" />
				<c:if test="${currProfilieUid == profile.uid}">
					<a class="pull-right" href="/edit/education">
						<i class="fa fa-cog" aria-hidden="true"></i>
					</a>
				</c:if>
			</sec:authorize>
		</div>
	</div>
	<div class="panel-body">
		<c:forEach var="education" items="${profile.education}">
			<c:if test="${profile.education.size() > 1}">
				<hr />
			</c:if>
			<h4>${education.speciality}</h4>
			<p>
				<i class="fa fa-calendar" aria-hidden="true"></i>
				${education.startingYear}
				<span> - </span>
				<c:if test="${education.completionYear != null}">${education.completionYear}</c:if>
				<c:if test="${education.completionYear == null}">
					<span class="label label-warning">present</span>
				</c:if>
			</p>
			<p>${education.department}<span>, </span>${education.university}</p>
			<c:if test="${profile.education.size() > 1}">
				<hr />
			</c:if>
		</c:forEach>
	</div>
</div>