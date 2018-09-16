<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html class="fixed">
	<head>

		<!-- Basic -->
		<meta charset="UTF-8">

		<meta name="keywords" content="HTML5 Admin Template" />
		<meta name="description" content="Porto Admin - Responsive HTML5 Template">
		<meta name="author" content="okler.net">

		<!-- Mobile Metas -->
		<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />

		<!-- Web Fonts  -->
		<link href="http://fonts.googleapis.com/css?family=Open+Sans:300,400,600,700,800|Shadows+Into+Light" rel="stylesheet" type="text/css">

		<!-- Vendor CSS -->
		<link rel="stylesheet" href="../assets/admin/vendor/bootstrap/css/bootstrap.css" />
		<link rel="stylesheet" href="../assets/admin/vendor/font-awesome/css/font-awesome.css" />
		<link rel="stylesheet" href="../assets/admin/vendor/magnific-popup/magnific-popup.css" />
		<link rel="stylesheet" href="../assets/admin/vendor/bootstrap-datepicker/css/datepicker3.css" />

		<!-- Theme CSS -->
		<link rel="stylesheet" href="../assets/admin/stylesheets/theme.css" />

		<!-- Skin CSS -->
		<link rel="stylesheet" href="../assets/admin/stylesheets/skins/default.css" />

		<!-- Theme Custom CSS -->
		<link rel="stylesheet" href="../assets/admin/stylesheets/theme-custom.css">

		<!-- Head Libs -->
		<script src="../assets/admin/vendor/modernizr/modernizr.js"></script>
		<script src="../assets/admin/vendor/jquery/jquery.js"></script>

		<script type='text/javascript'>
            $(document).ready(function() {
                $('#signup-form').submit(function (e) {
                    if ($("#password").val() != $("#passwordConfirm").val()) {
                        $("#passwordConfirmError").text("Passwords don't match");
                        // alert("enter pass");
                        e.preventDefault(e);
                    } else {
					 	var phonePattern = new RegExp("[0-9]{11,11}");
					 	var phone = $("#phone").val();
                        if (!phonePattern.test(phone) || phone.length != 11) {
                        // if (!$("#phone").val().test('[0-9]{11}')) {
                            // alert("not match");
                            $("#phonePatternError").text("Phone must be 11 digits");
                            e.preventDefault(e);
                        } else {
                            // alert("match");
						    return true;
						}
                    }
                })
            })
		</script>
	</head>
	<body>
		<!-- start: page -->
		<section class="body-sign">
			<div class="center-sign">
				<a href="/" class="logo pull-left">
					<img src="../assets/admin/images/logo.png" height="54" alt="Porto Admin" />
				</a>

				<div class="panel panel-sign">
					<div class="panel-title-sign mt-xl text-right">
						<h2 class="title text-uppercase text-bold m-none"><i class="fa fa-user mr-xs"></i> Sign Up</h2>
					</div>
					<div class="panel-body">
						<form id="signup-form" action="/signup" method="post">
                            <div class="form-group mb-none">
                                <div class="row">
                                    <div class="col-sm-6 mb-lg">
                                        <label>First Name</label>
                                        <input name="firstName" required type="text" value='<%= (request.getAttribute("firstName")) == null ? "" : request.getAttribute("firstName") %>' class="form-control input-lg" />
                                    </div>
                                    <div class="col-sm-6 mb-lg">
                                        <label>Last Name</label>
                                        <input name="lastName" required type="text" value='<%= (request.getAttribute("lastName")) == null ? "" : request.getAttribute("lastName") %>' class="form-control input-lg" />
                                    </div>
                                </div>
                            </div>

							<div class="form-group mb-lg">
								<label>Username</label>
								<input id ="username" name="username" required type="text" value='<%= (request.getAttribute("username")) == null ? "" : request.getAttribute("username") %>' class="form-control input-lg" />
								<c:if test="${not empty usernameError}"><label for="username" class="error">${usernameError}</label></c:if>
							</div>

							<div class="form-group mb-lg">
								<label>E-mail Address</label>
								<input id="email" name="email" required type="email" value='<%= (request.getAttribute("email")) == null ? "" : request.getAttribute("email") %>' class="form-control input-lg" />
								<c:if test="${not empty emailError}"><label for="email" class="error">${emailError}</label></c:if>
							</div>

                            <div class="form-group mb-none">
                                <div class="row">
                                    <div class="col-sm-6 mb-lg">
                                        <label>Password</label>
                                        <input id="password" name="password" required type="password" value='<%= (request.getAttribute("password")) == null ? "" : request.getAttribute("password") %>' class="form-control input-lg" />
                                    </div>
                                    <div class="col-sm-6 mb-lg">
                                        <label>Password Confirmation</label>
                                        <input id="passwordConfirm" name="passwordConfirm" required type="password" value='<%= (request.getAttribute("passwordConfirm")) == null ? "" : request.getAttribute("passwordConfirm") %>' class="form-control input-lg" />
										<label id="passwordConfirmError" class="error"></label>
									</div>
                                </div>
                            </div>

                            <div class="form-group mb-lg">
                                <label>Phone</label>
                                <input id="phone" name="phone" type="text" required placeholder="(123) 1234-1234" value='<%= (request.getAttribute("phone")) == null ? "" : request.getAttribute("phone") %>' class="form-control input-lg" />
								<label id="phonePatternError" class="error"></label>
							</div>

                            <div class="form-group mb-lg">
                                <label>Shipping Address</label>
                                <input name="shippingAddress" required type="text" value='<%= (request.getAttribute("shippingAddress")) == null ? "" : request.getAttribute("shippingAddress") %>' class="form-control input-lg" />
                            </div>

							<div class="row">
								<div class="col-sm-8">

								</div>
								<div class="col-sm-4 text-right">
									<button type="submit" class="btn btn-primary hidden-xs">Sign Up</button>
									<button type="submit" class="btn btn-primary btn-block btn-lg visible-xs mt-lg">Sign Up</button>
								</div>
							</div>

							<span class="mt-lg mb-lg line-thru text-center text-uppercase">
								<span>or</span>
							</span>

							<p class="text-center">Already have an account? <a href="/signin">Sign In!</a>

						</form>
					</div>
				</div>

				<p class="text-center text-muted mt-md mb-md">&copy; Copyright 2014. All Rights Reserved.</p>
			</div>
		</section>
		<!-- end: page -->

		<!-- Vendor -->
		<script src="../assets/admin/vendor/jquery-browser-mobile/jquery.browser.mobile.js"></script>
		<script src="../assets/admin/vendor/bootstrap/js/bootstrap.js"></script>
		<script src="../assets/admin/vendor/nanoscroller/nanoscroller.js"></script>
		<script src="../assets/admin/vendor/bootstrap-datepicker/js/bootstrap-datepicker.js"></script>
		<script src="../assets/admin/vendor/magnific-popup/magnific-popup.js"></script>
		<script src="../assets/admin/vendor/jquery-placeholder/jquery.placeholder.js"></script>
		
		<!-- Theme Base, Components and Settings -->
		<script src="../assets/admin/javascripts/theme.js"></script>
		
		<!-- Theme Custom -->
		<script src="../assets/admin/javascripts/theme.custom.js"></script>
		
		<!-- Theme Initialization Files -->
		<script src="../assets/admin/javascripts/theme.init.js"></script>

	</body>
</html>