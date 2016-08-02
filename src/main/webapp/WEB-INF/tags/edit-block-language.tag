<%@ tag pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="resume" tagdir="/WEB-INF/tags"%>
<%@ attribute name="index" required="true" type="java.lang.Object"%>
<%@ attribute name="language" required="false" type="net.devstudy.resume.entity.Language"%>
<tr id="item-${index}">
	<td>
		<input type="hidden" name="items[${index}].id" value="${language.id}" />
		<div class="row">
			<div class="panel panel-default">
				<table class="table">
					<tr>
						<td class="language-td-type">
							<label>Type</label>
							<select name="items[${index}].type" class="form-control">
								<option ${"All" == language.type ? ' selected="selected"' : ''}>All</option>
								<option ${"Writing" == language.type ? ' selected="selected"' : ''}>Writing</option>
								<option ${"Speaking" == language.type ? ' selected="selected"' : ''}>Speaking</option>
							</select>
						</td>
						<td class="language-td-name">
							<label>Language</label>
							<input name="items[${index}].name" type="text" class="form-control" placeholder="Language" value="${language.name}" />
							<form:errors path="items[${index}].name" cssClass="alert alert-danger" role="alert" element="div" />
						</td>
						<td class="language-td-level">
							<label>Level</label>
							<select name="items[${index}].level" class="form-control">
								<option ${"Beginner" == language.level ? ' selected="selected"' : ''}>Beginner</option>
								<option ${"Elementary" == language.level ? ' selected="selected"' : ''}>Elementary</option>
								<option ${"Pre-Intermediate" == language.level ? ' selected="selected"' : ''}>Pre-Intermediate</option>
								<option ${"Intermediate" == language.level ? ' selected="selected"' : ''}>Intermediate</option>
								<option ${"Upper-Intermediate" == language.level ? ' selected="selected"' : ''}>Upper-Intermediate</option>
								<option ${"Advanced" == language.level ? ' selected="selected"' : ''}>Advanced</option>
								<option ${"Proficiency" == language.level ? ' selected="selected"' : ''}>Proficiency</option>
							</select>
						</td>
						<td class="text-muted td-close-btn">
							<button type="button" class="close remove-item-btn" aria-label="Close" id="close-btn-${index}" data-item="${index}">
								<span aria-hidden="true">&times;</span>
							</button>
						</td>
					</tr>
				</table>
			</div>
		</div>
	</td>
</tr>