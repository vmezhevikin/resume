<%@ tag language="java" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="panel panel-primary">
	<div class="panel-heading">
		<h3 class="panel-title">
			<i class="fa fa-code" aria-hidden="true"></i>
			Skills
			<sec:authorize access="hasAuthority('USER')">
				<a class="pull-right" href="/edit/skill">
					<i class="fa fa-cog" aria-hidden="true"></i>
				</a>
			</sec:authorize>
		</h3>
	</div>
	<div class="panel-body">
		<table class="table table-bordered table-striped">
			<c:forEach var="skill" items="${profile.skill}">
				<tr>
					<td class="text-center" width="20%">${skill.category}</td>
					<td>${skill.description}</td>
				</tr>
			</c:forEach>
		</table>
	</div>
</div>