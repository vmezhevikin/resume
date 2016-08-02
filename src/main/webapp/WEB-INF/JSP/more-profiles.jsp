<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:forEach var="profile" items="${profiles}">
	<div class="col-md-8 col-md-offset-2 col-sm-10 col-sm-offset-1 col-xs-12">
		<div class="panel panel-default">
			<div class="panel-body" style="padding: 20px;">
				<div class="media">
					<div class="media-left">
						<a href="${profile.uid}">
							<c:if test="${profile.photoSmall != null}">
								<img class="media-object img-rounded" src="${profile.photoSmall}" alt="${profile.uid}">
							</c:if>
							<c:if test="${profile.photoSmall == null}">
								<img class="media-object img-rounded" src="/static/img/blank-photo-sm.jpg" alt="${profile.uid}">
							</c:if>
						</a>
					</div>
					<div class="media-body">
						<p class="text-primary">
							<span>${profile.fullName} </span>
							<c:if test="${profile.active}">(${profile.getAge()})</c:if>
						</p>
						<c:if test="${profile.active}">
							<h5>${profile.objective}</h5>
							<p>
								<strong>
									<small>${profile.city}, ${profile.country}</small>
								</strong>
							</p>
							<p>
								<small>${profile.summary}</small>
							</p>
						</c:if>
					</div>
				</div>
			</div>
		</div>
	</div>
</c:forEach>