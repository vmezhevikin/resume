<%@ tag pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="resume" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ attribute name="message" required="true" type="java.lang.String"%>
<div class="modal fade" id="modal-message" tabindex="-1" role="dialog" aria-labelledby="modal-message-title" data-message="${message}">
	<div class="modal-dialog modal-sm" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<h3 class="modal-title">Update</h3>
			</div>
			<div class="modal-body">
				<p class="text-primary">${message}</p>
			</div>
		</div>
	</div>
</div>