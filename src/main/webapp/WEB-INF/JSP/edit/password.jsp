<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<div class="container">
	<div class="row">
		<div class="col-md-6 col-md-offset-3 col-sm-8 col-sm-offset-2 col-xs-12">
			<div class="panel panel-info">
				<div class="panel-heading">
					<h3 class="panel-title">
						<i class="fa fa-unlock-alt" aria-hidden="true"></i>
						Change password
					</h3>
				</div>
				<div class="panel-body">
					<p>Input your new password and confirm it.</p>
					<form>
						<div class="form-group">
							<label for="password">New password</label>
							<input type="password" class="form-control" id="password" placeholder="New password">
						</div>
						<div class="form-group">
							<label for="confirmPassword">Confirm password</label>
							<input type="password" class="form-control" id="confirmPassword" placeholder="Confirm password">
						</div>
						<button type="submit" class="btn btn-primary pull-left">Change</button>
					</form>
				</div>
			</div>
		</div>
	</div>
</div>