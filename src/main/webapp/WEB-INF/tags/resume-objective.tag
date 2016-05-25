<%@ tag language="java" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<div class="panel panel-primary">
	<div class="panel-heading">
		<h3 class="panel-title">
			<i class="fa fa-briefcase" aria-hidden="true"></i>
			Objective
			<sec:authorize access="hasAuthority('USER')">
				<a class="pull-right" href="/edit/general">
					<i class="fa fa-cog" aria-hidden="true"></i>
				</a>
			</sec:authorize>
		</h3>
	</div>
	<div class="panel-body">
		<h4>${profile.objective}</h4>
		<strong>Summary:</strong>
		<br />
		${profile.summary}.
	</div>
</div>