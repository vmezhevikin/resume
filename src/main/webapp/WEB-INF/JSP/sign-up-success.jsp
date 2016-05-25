<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<div class="container">
	<div class="row">
		<div class="col-md-8 col-md-offset-2 col-sm-10 col-sm-offset-1 col-xs-12">
			<div class="panel panel-info">
				<div class="panel-heading">
					<h3 class="panel-title">
						<i class="fa fa-thumbs-o-up" aria-hidden="true"></i>
						Sign up succeeded
					</h3>
				</div>
				<div class="panel-body">
					<p>
						After completing the registration, your account will be available here:
						<a href="http://net.devstudy.resume/${profile.uid}">http://net.devstudy.resume/${profile.uid}</a>
					</p>
					<p>
						Your UID:
						<strong>${profile.uid}</strong>
						. Use this UID to log in.
					</p>
					<a href="/my-profile" class="btn btn-primary">Complete</a>
				</div>
			</div>
		</div>
	</div>
</div>