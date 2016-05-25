<%@ tag language="java" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<div class="panel panel-primary">
	<div class="panel-heading">
		<h3 class="panel-title">
			<i class="fa fa-info-circle" aria-hidden="true"></i>
			Additional info
			<sec:authorize access="hasAuthority('USER')">
				<a class="pull-right" href="/edit/additional">
					<i class="fa fa-cog" aria-hidden="true"></i>
				</a>
			</sec:authorize>
		</h3>
	</div>
	<div class="panel-body">${profile.additionalInfo}</div>
</div>