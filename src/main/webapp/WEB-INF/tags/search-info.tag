<%@ tag pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="resume" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ attribute name="query" required="true" type="java.lang.Object"%>
<c:if test="${query != null}">
	<tr>
		<td>
			<div class="row">
				<div class="col-md-8 col-md-offset-2 col-sm-10 col-sm-offset-1 col-xs-12">
					<div class="panel panel-default panel-padding">
						<h4 class="text-center">
							Search results for:
							<strong>${query}</strong>
						</h4>
						<div class="resume-search-text">
							Searched in:
							<ul>
								<li>Objective</li>
								<li>Summary</li>
								<li>Additional Information</li>
								<li>Certificate (description)</li>
								<li>Language (name)</li>
								<li>Course (description)</li>
								<li>Practical experience (company, position)</li>
								<li>Skill (category, description)</li>
							</ul>
							You also may try improving search results using wildcards (? - any character, * - zero or more characters)
						</div>
					</div>
				</div>
			</div>
		</td>
	</tr>
</c:if>