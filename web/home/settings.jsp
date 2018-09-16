<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:portalWrapper title="settings">
	<jsp:attribute name="header">
    	<!-- Specific Page Vendor CSS -->
        <link rel="stylesheet" href="../assets/admin/vendor/pnotify/pnotify.custom.css" />
    </jsp:attribute>
	<jsp:attribute name="footer">

		<!-- Specific Page Vendor -->
		<script src="../assets/admin/vendor/pnotify/pnotify.custom.js"></script>

		<script type='text/javascript'>
			$(document).ready(function() {

				<c:if test="${not empty success}">
					showSuccess("${success}", "");
				</c:if>

				function isEmpty(str) {
					return (!str || 0 === str.length);
				}

				$('#settings-form').submit(function (e) {
					$("#oldError").text("");
					$("#newPasswordError").text("");
					$("#newPasswordConfirmError").text("");
					var phonePattern = new RegExp("[0-9]{11,11}");
					var phone = $("#phone").val();
					if (!phonePattern.test(phone) || phone.length != 11) {
						$("#phonePatternError").text("Phone must be 11 digits");
						e.preventDefault(e);
					} else {
						var old = $("#old_password").val();
						var new_password = $("#new_password").val();
						var new_password_confirm = $("#new_password_confirm").val();
						if ((isEmpty(old) && isEmpty(new_password) && isEmpty(new_password_confirm)) ||
							(!isEmpty(old) && !isEmpty(new_password) && !isEmpty(new_password_confirm)
									&& new_password === new_password_confirm)) {
							return true;
						} else {
							if (isEmpty(old)) {
								$("#oldError").text("You should provide your old password!");
							} else if (isEmpty(new_password)) {
								$("#newPasswordError").text("You should provide your new password!");
							} else if (isEmpty(new_password_confirm)) {
								$("#newPasswordConfirmError").text("You should confirm your new password!");
							} else if (new_password !== new_password_confirm) {
								$("#newPasswordError").text("Passwords don't match!");
								$("#newPasswordConfirmError").text("Passwords don't match!");
							}
							e.preventDefault(e);
						}
					}
				})
  			  })
		</script>
	</jsp:attribute>
	<jsp:body >
		<div role="main" class="main">
			<section class="page-header">
				<div class="container">
					<div class="row">
						<div class="col-md-12">
							<ul class="breadcrumb">
								<li><a href="#">Home</a></li>
								<li class="active">Settings</li>
							</ul>
						</div>
					</div>
					<div class="row">
						<div class="col-md-12">
							<h1>Change Account Settings</h1>
						</div>
					</div>
				</div>
			</section>
			<div class="container">
				<div class="row">
					<div class="col-lg-12">
						<section class="panel panel-admin">
							<div class="panel-body">
								<form id="settings-form" action="/settings" method="post" class="form-horizontal form-bordered">
									<div class="form-group">
										<label class="col-md-3 control-label" for="firstName">First Name</label>
										<div class="col-md-6">
											<input type="text" required value="${firstName}" class="form-control" name="firstName" id="firstName">
										</div>
									</div>

									<div class="form-group">
										<label class="col-md-3 control-label" for="lastName">Last Name</label>
										<div class="col-md-6">
											<input type="text" required value="${lastName}" class="form-control" name="lastName" id="lastName">
										</div>
									</div>

									<div class="form-group">
										<label class="col-md-3 control-label" for="email">Email Address</label>
										<div class="col-md-6">
											<input type="email" required value="${email}" class="form-control" name="email" id="email">
											<c:if test="${not empty emailError}"><label for="email" class="error">${emailError}</label></c:if>
										</div>
									</div>

									<div class="form-group">
										<label class="col-md-3 control-label" for="username">Username</label>
										<div class="col-md-6">
											<input type="text" required value="${username}" class="form-control" name="username" id="username">
											<c:if test="${not empty usernameError}"><label for="username" class="error">${usernameError}</label></c:if>
										</div>
									</div>

									<div class="form-group">
										<label class="col-md-3 control-label" for="shipping_address">Shipping Address</label>
										<div class="col-md-6">
											<input type="text" required value="${shipping_address}" class="form-control" name="shipping_address" id="shipping_address">
										</div>
									</div>

									<div class="form-group">
										<label class="col-md-3 control-label" for="phone">Phone</label>
										<div class="col-md-6">
											<input type="text" required value="${phone}" class="form-control" name="phone" id="phone">
											<label id="phonePatternError" for="phone" class="error"></label>
										</div>
									</div>

									<div class="form-group">
										<label class="col-md-3 control-label" for="old_password">Old Password</label>
										<div class="col-md-6">
											<input type="password" class="form-control" name="old_password" <c:if test="${not empty oldPassword}">value="${oldPassword}" </c:if> id="old_password">
											<label id="oldError" for="old_password" class="error">${oldError}</label>
										</div>
									</div>

									<div class="form-group">
										<label class="col-md-3 control-label" for="new_password">New Password</label>
										<div class="col-md-6">
											<input type="password" class="form-control" name="new_password" id="new_password">
											<label id="newPasswordError" for="new_password" class="error"></label>
										</div>
									</div>

									<div class="form-group">
										<label class="col-md-3 control-label" for="new_password_confirm">Confirm New Password</label>
										<div class="col-md-6">
											<input type="password" class="form-control" id="new_password_confirm">
											<label id="newPasswordConfirmError" for="new_password_confirm" class="error"></label>
										</div>
									</div>

									<%--<footer class="panel-footer">--%>
										<div class="row">
											<div class="col-sm-9 col-sm-offset-3">
												<input type="submit" class="btn btn-primary-scale-2" value="Done Editting"/>
												<%--<button type="reset" class="btn btn-default">Reset</button>--%>
											</div>
										</div>
									<%--</footer>--%>
								</form>
							</div>
						</section>
					</div>
				</div>
			</div>
		</div>
	</jsp:body>
</t:portalWrapper>
