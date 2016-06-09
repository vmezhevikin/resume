<%@ tag language="java" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<div class="panel panel-primary">
	<div class="panel-heading">
		<div class="panel-title">
			<i class="fa fa-graduation-cap" aria-hidden="true"></i>
			Courses
			<sec:authorize access="hasAuthority('USER')">
				<sec:authentication var="currProfilieUid" property="principal.username" />
				<c:if test="${currProfilieUid == profile.uid}">
					<a class="pull-right" href="/edit/course">
						<i class="fa fa-cog" aria-hidden="true"></i>
					</a>
				</c:if>
			</sec:authorize>
		</div>
	</div>
	<div class="panel-body">
		<c:forEach var="course" items="${profile.course}">
			<c:if test="${profile.course.size() > 1}">
				<hr />
			</c:if>
			<h4>${course.description}<span> at </span>${course.school}</h4>
			<p>
				<i class="fa fa-calendar" aria-hidden="true"></i>
				<strong> Finished: </strong>
				<c:if test="${course.completionDate != null}">${course.completionDateString}</c:if>
				<c:if test="${course.completionDate == null}">
					<span class="label label-warning">present</span>
				</c:if>
			</p>
			<c:if test="${profile.course.size() > 1}">
				<hr />
			</c:if>
		</c:forEach>
	</div>
</div>