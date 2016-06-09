<%@ tag language="java" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<div class="panel panel-primary">
	<div class="panel-heading">
		<div class="panel-title">
			<i class="fa fa-briefcase" aria-hidden="true"></i>
			Objective
			<sec:authorize access="hasAuthority('USER')"><sec:authentication var="currProfilieUid" property="principal.username" />
				<c:if test="${currProfilieUid == profile.uid}">
				<a class="pull-right" href="/edit/general">
					<i class="fa fa-cog" aria-hidden="true"></i>
				</a></c:if>
			</sec:authorize>
		</div>
	</div>
	<div class="panel-body">
		<h4>${profile.objective}</h4>
		<strong>Summary:</strong>
		<br />
		${profile.summary}.
	</div>
</div>