<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<header>
	<nav class="navbar navbar-default" role="navigation">
		<div class="container-fluid">
			<div class="navbar-header">
				<button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#header-navbar-collapse" aria-expanded="false">
					<span class="sr-only">Toggle navigation</span>
					<span class="icon-bar"></span>
					<span class="icon-bar"></span>
					<span class="icon-bar"></span>
				</button>
				<a href="/welcome" class="navbar-brand">MyResume</a>
			</div>
			<div class="collapse navbar-collapse" id="header-navbar-collapse">
				<ul class="nav navbar-nav navbar-right">
					<li>
						<form action="/search" method="get" class="navbar-form" role="search">
							<div class="form-group">
								<input name="query" type="text" class="form-control" placeholder="Search">
								<input type="submit" class="btn btn-primary" value="Search" />
							</div>
						</form>
					</li>
					<sec:authorize access="!hasAuthority('USER')">
						<li>
							<a href="/sign-in">Sign in</a>
						</li>
						<li>
							<a href="/sign-up">Sign up</a>
						</li>
					</sec:authorize>
					<sec:authorize access="hasAuthority('USER')">
						<li class="dropdown">
							<a href="#" class="dropdown-toggle" data-toggle="dropdown">
								<sec:authentication property="principal.fullName" />
								<span class="caret"></span>
							</a>
							<ul class="dropdown-menu">
								<li>
									<a href="/my-profile">
										<i class="fa fa-user" aria-hidden="true"></i>
										Profile
									</a>
								</li>
								<li>
									<a href="/edit/general">
										<i class="fa fa-pencil" aria-hidden="true"></i>
										Edit
									</a>
								</li>
								<li>
									<a href="/edit/password">
										<i class="fa fa-unlock-alt" aria-hidden="true"></i>
										Password
									</a>
								</li>
								<li>
									<a href="/remove">
										<i class="fa fa-trash" aria-hidden="true"></i>
										Remove
									</a>
								</li>
								<li role="separator" class="divider"></li>
								<li>
									<form:form action="/sign-out" method="post" id="signoutForm" style="none">
										<a href="#" class="dropdown-menu-aitem" onclick="document.getElementById('signoutForm').submit()">
											<i class="fa fa-sign-out" aria-hidden="true"></i>
											Sign out
										</a>
									</form:form>
								</li>
							</ul>
						</li>
					</sec:authorize>
				</ul>
			</div>
		</div>
	</nav>
</header>