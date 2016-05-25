<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<nav class="container">
	<ul class="nav nav-tabs nav-justified">
		<li role="presentation" ${section == "General" ? ' class="active"' : ''}>
			<a href="/edit/general">General</a>
		</li>
		<li role="presentation" ${section == "Contact" ? ' class="active"' : ''}>
			<a href="/edit/contact">Contact</a>
		</li>
		<li role="presentation" ${section == "Skill" ? ' class="active"' : ''}>
			<a href="/edit/skill">Skill</a>
		</li>
		<li role="presentation" ${section == "Experience" ? ' class="active"' : ''}>
			<a href="/edit/experience">Experience</a>
		</li>
		<li role="presentation" ${section == "Certificate" ? ' class="active"' : ''}>
			<a href="/edit/certificate">Certificate</a>
		</li>
		<li role="presentation" ${section == "Course" ? ' class="active"' : ''}>
			<a href="/edit/course">Course</a>
		</li>
		<li role="presentation" ${section == "Education" ? ' class="active"' : ''}>
			<a href="/edit/education">Education</a>
		</li>
		<li role="presentation" ${section == "Language" ? ' class="active"' : ''}>
			<a href="/edit/language">Language</a>
		</li>
		<li role="presentation" ${section == "Hobby" ? ' class="active"' : ''}>
			<a href="/edit/hobby">Hobby</a>
		</li>
		<li role="presentation" ${section == "Additional" ? ' class="active"' : ''}>
			<a href="/edit/additional">Additional</a>
		</li>
	</ul>
</nav>