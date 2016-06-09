<%@ tag language="java" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<div class="panel panel-primary">
	<div class="panel-heading">
		<div class="panel-title">
			<i class="fa fa-heart" aria-hidden="true"></i>
			Hobby
			<sec:authorize access="hasAuthority('USER')">
				<sec:authentication var="currProfilieUid" property="principal.username" />
				<c:if test="${currProfilieUid == profile.uid}">
					<a class="pull-right" href="/edit/hobby">
						<i class="fa fa-cog" aria-hidden="true"></i>
					</a>
				</c:if>
			</sec:authorize>${principal.id}
		</div>
	</div>
	<div class="panel-body">
		<table class="table table-bordered table-hobby">
			<c:forEach var="hobby" items="${hobbies}">
				<c:if test="${profile.hasHobby(hobby.name)}">
					<tr>
						<td width="25%" class="text-center">${hobby.icon}</td>
						<td width="75%" class="text-center">${hobby.name}</td>
					</tr>
				</c:if>
			</c:forEach>
		</table>
	</div>
</div>