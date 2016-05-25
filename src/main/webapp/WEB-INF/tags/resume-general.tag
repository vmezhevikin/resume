<%@ tag language="java" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="panel panel-primary">
	<div class="panel-body">
		<c:if test="${profile.photo != null}">
			<img src="${profile.photo}" class="img-rounded img-responsive" width="400px" alt="Photo">
		</c:if>
		<c:if test="${profile.photo == null}">
			<img src="/static/img/blank-photo.jpg" class="img-rounded img-responsive" width="400px" alt="Photo">
		</c:if>
	</div>
	<h3 class="text-center">${profile.fullName}</h3>
	<c:if test="${profile.active == true}">
		<p class="text-center">
			<strong>${profile.city}, ${profile.country}</strong>
		</p>
		<p class="text-center">
			<strong>Age: </strong>
			${profile.age}
			<strong> Birthday: </strong>
			${profile.birthdayString}
		</p>
		<table class="table">
			<tr>
				<td width="5%">
					<i class="fa fa-phone" aria-hidden="true"></i>
				</td>
				<td width="95%">
					<a href="tel:${profile.phone}">${profile.phone}</a>
				</td>
			</tr>
			<tr>
				<td>
					<i class="fa fa-envelope" aria-hidden="true"></i>
				</td>
				<td>
					<a href="mailto:${profile.email}">${profile.email}</a>
				</td>
			</tr>
			<c:if test="${profile.contact.skype != null}">
				<tr>
					<td>
						<i class="fa fa-skype" aria-hidden="true"></i>
					</td>
					<td>${profile.contact.skype}</td>
				</tr>
			</c:if>
			<c:if test="${profile.contact.vkontakte != null}">
				<tr>
					<td>
						<i class="fa fa-vk" aria-hidden="true"></i>
					</td>
					<td>
						<a href="${profile.contact.vkontakte}">${profile.contact.vkontakte}</a>
					</td>
				</tr>
			</c:if>
			<c:if test="${profile.contact.facebook != null}">
				<tr>
					<td>
						<i class="fa fa-facebook" aria-hidden="true"></i>
					</td>
					<td>
						<a href="${profile.contact.facebook}">${profile.contact.facebook}</a>
					</td>
				</tr>
			</c:if>
			<c:if test="${profile.contact.linkedin != null}">
				<tr>
					<td>
						<i class="fa fa-linkedin" aria-hidden="true"></i>
					</td>
					<td>
						<a href="${profile.contact.linkedin}">${profile.contact.linkedin}</a>
					</td>
				</tr>
			</c:if>
			<c:if test="${profile.contact.github != null}">
				<tr>
					<td>
						<i class="fa fa-github" aria-hidden="true"></i>
					</td>
					<td>
						<a href="${profile.contact.github}">${profile.contact.github}</a>
					</td>
				</tr>
			</c:if>
			<c:if test="${profile.contact.stackoverflow != null}">
				<tr>
					<td>
						<i class="fa fa-stack-overflow" aria-hidden="true"></i>
					</td>
					<td>
						<a href="${profile.contact.stackoverflow}">${profile.contact.stackoverflow}</a>
					</td>
				</tr>
			</c:if>
		</table>
	</c:if>
</div>
