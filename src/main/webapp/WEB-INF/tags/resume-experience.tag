<%@ tag language="java" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<div class="panel panel-primary">
	<div class="panel-heading">
		<div class="panel-title">
			<i class="fa fa-briefcase" aria-hidden="true"></i>
			Experience
			<sec:authorize access="hasAuthority('USER')">
				<sec:authentication var="currProfilieUid" property="principal.username" />
				<c:if test="${currProfilieUid == profile.uid}">
					<a class="pull-right" href="/edit/experience">
						<i class="fa fa-cog" aria-hidden="true"></i>
					</a>
				</c:if>
			</sec:authorize>
		</div>
	</div>
	<div class="panel-body">
		<c:forEach var="experience" items="${profile.experience}">
			<c:if test="${profile.experience.size() > 1}">
				<hr />
			</c:if>
			<h4>${experience.position}<span> at </span>${experience.company}</h4>
			<p>
				<i class="fa fa-calendar" aria-hidden="true"></i>
				${experience.startingDateString}
				<span> - </span>
				<c:if test="${experience.completionDate != null}">${experience.completionDateString}</c:if>
				<c:if test="${experience.completionDate == null}">
					<span class="label label-warning">present</span>
				</c:if>
			</p>
			<p>
				<strong>Responsibilities included: </strong>
				${experience.responsibility}
			</p>
			<c:if test="${experience.demo != null}">
				<p>
					<strong>Demo: </strong>
					<a href="${experience.demo}">${experience.demo}</a>
				</p>
			</c:if>
			<c:if test="${experience.code != null}">
				<p>
					<strong>Source code: </strong>
					<a href="${experience.code}">${experience.code}</a>
				</p>
			</c:if>
			<c:if test="${profile.experience.size() > 1}">
				<hr />
			</c:if>
		</c:forEach>
	</div>
</div>