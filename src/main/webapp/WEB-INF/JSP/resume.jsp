<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="resume" tagdir="/WEB-INF/tags"%>
<body>
	<div class="container">
		<div class="row">
			<div class="col-lg-4 col-md-4 col-sm-12 col-xs-12">
				<resume:resume-general />
				<div class="hidden-sm hidden-xs">
					<c:if test="${profile.language.size() != 0}">
						<resume:resume-language />
					</c:if>
					<c:if test="${profile.hobby.size() != 0}">
						<resume:resume-hobby />
					</c:if>
					<c:if test="${profile.additionalInfo != null}">
						<resume:resume-additional />
					</c:if>
				</div>
			</div>
			<div class="col-lg-8 col-md-8 col-sm-12 col-xs-12">
				<c:if test="${profile.objective != null}">
					<resume:resume-objective />
				</c:if>
				<c:if test="${profile.skill.size() != 0}">
					<resume:resume-skill />
				</c:if>
				<c:if test="${profile.experience.size() != 0}">
					<resume:resume-experience />
				</c:if>
				<c:if test="${profile.certificate.size() != 0}">
					<resume:resume-certificate />
				</c:if>
				<c:if test="${profile.course.size() != 0}">
					<resume:resume-course />
				</c:if>
				<c:if test="${profile.education.size() != 0}">
					<resume:resume-education />
				</c:if>
			</div>
			<div class="col-sm-12 col-xs-12 visible-sm visible-xs">
				<c:if test="${profile.language.size() != 0}">
					<resume:resume-language />
				</c:if>
				<c:if test="${profile.hobby.size() != 0}">
					<resume:resume-hobby />
				</c:if>
				<c:if test="${profile.additionalInfo != null}">
					<resume:resume-additional />
				</c:if>
			</div>
		</div>
	</div>
</body>