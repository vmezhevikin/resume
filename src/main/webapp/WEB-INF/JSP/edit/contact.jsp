<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<form:form action="/edit/contact" method="post" commandName="contact">
	<div class="container resume-edit-block">
		<h2 class="text-center">Additional contacts: skype, social networks, etc.</h2>
		<table class="table">
			<tr>
				<td width="10%">
					<strong>Skype</strong>
				</td>
				<td width="50%">
					<input name="skype" type="text" class="form-control" placeholder="Skype" value="${contactForm.skype}"/>
					<form:errors path="skype" cssClass="alert alert-danger" role="alert" element="div"/>
				</td>
				<td class="text-muted" width="40%">
					1. It is advisable that your skype name contains your name and surname as in your passport. If the specified name is already taken, contraction
					is possible.
					<br />
					2. It is not recommended to use creative skype, such as TheBestDeveloper, lakomka, etc.
					<br />
					3. If you do not have adequate skype name, it means that it's time to create it.
				</td>
			</tr>
			<tr>
				<td>
					<strong>Vkontakte</strong>
				</td>
				<td>
					<input name="vkontakte" type="text" class="form-control" placeholder="Vkontakte" value="${contactForm.vkontakte}"/>
					<form:errors path="vkontakte" cssClass="alert alert-danger" role="alert" element="div"/>
				</td>
				<td rowspan="5" class="text-muted">
					1. To gather full information about the candidate HR-manager may look at his profile on social networks. To help find your profile faster and
					easier, you can provide references to it in additional contacts.
					<br />
					2. Providing the profile, make sure that there is no contrary information to your resume, because the discrepancy can be seen with the naked eye.
					<br />
					3. Look at your profile from the side and make sure that there are no indecent photos of your tumultuous student life.
					<br />
					4. If you are a member of suspicious groups that might compromise you as an adequate person, it's time to leave these communities.
					<br />
					5. In the settings of modern social networks is possible to create named links instead id1234578, so it makes sense to use advantage of these
					possibilities.
				</td>
			</tr>
			<tr>
				<td>
					<strong>Facebook</strong>
				</td>
				<td>
					<input name="facebook" type="text" class="form-control" placeholder="Facebook" value="${contactForm.facebook}"/>
					<form:errors path="facebook" cssClass="alert alert-danger" role="alert" element="div"/>
				</td>
			</tr>
			<tr>
				<td>
					<strong>Linkedin</strong>
				</td>
				<td>
					<input name="linkedin" type="text" class="form-control" placeholder="Linkedin" value="${contactForm.linkedin}"/>
					<form:errors path="linkedin" cssClass="alert alert-danger" role="alert" element="div"/>
				</td>
			</tr>
			<tr>
				<td>
					<strong>Github</strong>
				</td>
				<td>
					<input name="github" type="text" class="form-control" placeholder="Github" value="${contactForm.github}"/>
					<form:errors path="github" cssClass="alert alert-danger" role="alert" element="div"/>
				</td>
			</tr>
			<tr>
				<td>
					<strong>Stackoverflow</strong>
				</td>
				<td>
					<input name="stackoverflow" type="text" class="form-control" placeholder="Stackoverflow" value="${contactForm.stackoverflow}"/>
					<form:errors path="stackoverflow" cssClass="alert alert-danger" role="alert" element="div"/>
				</td>
			</tr>
			<tr>
				<td colspan="3" align="center">
					<button type="submit" class="btn btn-primary">Save</button>
				</td>
			</tr>
		</table>
	</div>
</form:form>