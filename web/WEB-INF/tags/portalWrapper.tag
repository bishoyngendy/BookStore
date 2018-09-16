<%@tag description="Portal Wrapper Tag" pageEncoding="UTF-8"%>
<%@attribute name="header" fragment="true" %>
<%@attribute name="footer" fragment="true" %>
<%@attribute name="title" required="true" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<!DOCTYPE html>
<html>
<head>

    <!-- Basic -->
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">

    <title>BookStore - ${title}</title>

    <meta name="keywords" content="HTML5 Template" />
    <meta name="description" content="Porto - Responsive HTML5 Template">
    <meta name="author" content="okler.net">

    <!-- Favicon -->
    <link rel="shortcut icon" href="../../assets/portal/img/favicon.ico" type="image/x-icon" />
    <link rel="apple-touch-icon" href="../../assets/portal/img/apple-touch-icon.png">

    <!-- Mobile Metas -->
    <meta name="viewport" content="width=device-width, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no">

    <!-- Web Fonts  -->
    <link href="https://fonts.googleapis.com/css?family=Open+Sans:300,400,600,700,800%7CShadows+Into+Light" rel="stylesheet" type="text/css">

    <!-- Vendor CSS -->
    <link rel="stylesheet" href="../../assets/portal/vendor/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="../../assets/portal/vendor/font-awesome/css/font-awesome.min.css">
    <link rel="stylesheet" href="../../assets/portal/vendor/animate/animate.min.css">
    <link rel="stylesheet" href="../../assets/portal/vendor/simple-line-icons/css/simple-line-icons.min.css">
    <link rel="stylesheet" href="../../assets/portal/vendor/owl.carousel/assets/owl.carousel.min.css">
    <link rel="stylesheet" href="../../assets/portal/vendor/owl.carousel/assets/owl.theme.default.min.css">
    <link rel="stylesheet" href="../../assets/portal/vendor/magnific-popup/magnific-popup.min.css">
    <link rel="stylesheet" href="../../assets/admin/vendor/pnotify/pnotify.custom.css" />

    <jsp:invoke fragment="header"/>

    <!-- Theme CSS -->
    <link rel="stylesheet" href="../../assets/portal/css/theme.css">
    <link rel="stylesheet" href="../../assets/portal/css/theme-elements.css">
    <link rel="stylesheet" href="../../assets/portal/css/theme-blog.css">
    <link rel="stylesheet" href="../../assets/portal/css/theme-shop.css">

    <!-- Skin CSS -->
    <link rel="stylesheet" href="../../assets/portal/css/skins/default.css">

    <!-- Theme Custom CSS -->
    <link rel="stylesheet" href="../../assets/portal/css/custom.css">

    <!-- Head Libs -->
    <script src="../../assets/portal/vendor/modernizr/modernizr.min.js"></script>

</head>
<body>

<div class="body">
    <header id="header" data-plugin-options="{'stickyEnabled': true, 'stickyEnableOnBoxed': true, 'stickyEnableOnMobile': true, 'stickyStartAt': 57, 'stickySetTop': '-57px', 'stickyChangeLogo': true}">
        <div class="header-body">
            <div class="header-container container">
                <div class="header-row">
                    <div class="header-column">
                        <div class="header-logo">
                            <a href="books">
                                <img alt="Porto" width="111" height="54" data-sticky-width="82" data-sticky-height="40" data-sticky-top="33" src="../../assets/portal/img/logo.png">
                            </a>
                        </div>
                    </div>
                    <div class="header-column">
                        <div class="header-row">
                            <nav class="header-nav-top">
                                <ul class="nav nav-pills">
                                    <li class="hidden-xs">
                                        <a href="/"><i class="fa fa-angle-right"></i>Home</a>
                                    </li>
                                    <li class="hidden-xs">
                                        <a href="/logout"><i class="fa fa-angle-right"></i>Logout</a>
                                    </li>
                                    <li>
                                        <span class="ws-nowrap"><i class="fa fa-phone"></i> (123) 456-789</span>
                                    </li>
                                </ul>
                            </nav>
                        </div>
                        <div class="header-row">
                            <div class="header-nav">
                                <button class="btn header-btn-collapse-nav" data-toggle="collapse" data-target=".header-nav-main">
                                    <i class="fa fa-bars"></i>
                                </button>
                                <div class="header-nav-main header-nav-main-effect-1 header-nav-main-sub-effect-1 collapse">
                                    <nav>
                                        <ul class="nav nav-pills" id="mainNav">
                                            <li <c:if test="${empty param.category && pageContext.request.requestURI eq '/home/books-all.jsp'}">class="active"</c:if>>
                                                <a href="/">
                                                    Home
                                                </a>
                                            </li>
                                            <li <c:if test="${param.category == 'science'}">class="active"</c:if>>
                                                <a href="/books?category=science">
                                                    Science
                                                </a>
                                            </li>
                                            <li <c:if test="${param.category == 'art'}">class="active"</c:if>>
                                                <a href="/books?category=art">
                                                    Art
                                                </a>
                                            </li>
                                            <li <c:if test="${param.category == 'religion'}">class="active"</c:if>>
                                                <a href="/books?category=religion">
                                                    Religion
                                                </a>
                                            </li>
                                            <li <c:if test="${param.category == 'history'}">class="active"</c:if>>
                                                <a href="/books?category=history">
                                                    History
                                                </a>
                                            </li>
                                            <li  <c:if test="${param.category == 'geography'}">class="active"</c:if>>
                                                <a href="/books?category=geography">
                                                    Geography
                                                </a>
                                            </li>
                                            <li <c:if test="${pageContext.request.requestURI eq '/home/settings.jsp'}">class="active"</c:if>>
                                                <a href="/settings">
                                                    Settings
                                                </a>
                                            </li>
                                            <li <c:if test="${pageContext.request.requestURI eq '/home/shop-cart.jsp'}">class="active"</c:if>>
                                                <a href="/checkout">
                                                    <i class="fa fa-user"></i> Go to your cart
                                                </a>
                                            </li>
                                        </ul>
                                    </nav>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </header>

    <!-- start: page -->
    <jsp:doBody/>
    <!-- end: page -->

    <footer id="footer">
        <div class="container">
            <div class="row">
                <div class="footer-ribbon">
                    <span>Get in Touch</span>
                </div>
                <div class="col-md-3">
                    <div class="newsletter">
                        <h4>Newsletter</h4>
                        <p>Keep up on our always evolving product features and technology. Enter your e-mail and subscribe to our newsletter.</p>

                        <div class="alert alert-success hidden" id="newsletterSuccess">
                            <strong>Success!</strong> You've been added to our email list.
                        </div>

                        <div class="alert alert-danger hidden" id="newsletterError"></div>

                        <form id="newsletterForm" action="php/newsletter-subscribe.php" method="POST">
                            <div class="input-group">
                                <input class="form-control" placeholder="Email Address" name="newsletterEmail" id="newsletterEmail" type="text">
                                <span class="input-group-btn">
											<button class="btn btn-default" type="submit">Go!</button>
										</span>
                            </div>
                        </form>
                    </div>
                </div>
                <div class="col-md-3">
                    <h4>Latest Tweets</h4>
                    <div id="tweet" class="twitter" data-plugin-tweets data-plugin-options="{'username': '', 'count': 2}">
                        <p>Please wait...</p>
                    </div>
                </div>
                <div class="col-md-4">
                    <div class="contact-details">
                        <h4>Contact Us</h4>
                        <ul class="contact">
                            <li><p><i class="fa fa-map-marker"></i> <strong>Address:</strong> 1234 Street Name, City Name, United States</p></li>
                            <li><p><i class="fa fa-phone"></i> <strong>Phone:</strong> (123) 456-789</p></li>
                            <li><p><i class="fa fa-envelope"></i> <strong>Email:</strong> <a href="mailto:mail@example.com">mail@example.com</a></p></li>
                        </ul>
                    </div>
                </div>
                <div class="col-md-2">
                    <h4>Follow Us</h4>
                    <ul class="social-icons">
                        <li class="social-icons-facebook"><a href="http://www.facebook.com/" target="_blank" title="Facebook"><i class="fa fa-facebook"></i></a></li>
                        <li class="social-icons-twitter"><a href="http://www.twitter.com/" target="_blank" title="Twitter"><i class="fa fa-twitter"></i></a></li>
                        <li class="social-icons-linkedin"><a href="http://www.linkedin.com/" target="_blank" title="Linkedin"><i class="fa fa-linkedin"></i></a></li>
                    </ul>
                </div>
            </div>
        </div>
        <div class="footer-copyright">
            <div class="container">
                <div class="row">
                    <div class="col-md-1">
                        <a href="index.html" class="logo">
                            <img alt="Porto Website Template" class="img-responsive" src="../../assets/portal/img/logo-footer.png">
                        </a>
                    </div>
                    <div class="col-md-7">
                        <p>© Copyright 2017. All Rights Reserved.</p>
                    </div>
                    <div class="col-md-4">
                        <nav id="sub-menu">
                            <ul>
                                <li><a href="page-faq.html">FAQ's</a></li>
                                <li><a href="sitemap.html">Sitemap</a></li>
                                <li><a href="contact-us.html">Contact</a></li>
                            </ul>
                        </nav>
                    </div>
                </div>
            </div>
        </div>
    </footer>
</div>

<!-- Vendor -->
<script src="../../assets/portal/vendor/jquery/jquery.min.js"></script>
<script src="../../assets/portal/vendor/jquery.appear/jquery.appear.min.js"></script>
<script src="../../assets/portal/vendor/jquery.easing/jquery.easing.min.js"></script>
<script src="../../assets/portal/vendor/jquery-cookie/jquery-cookie.min.js"></script>
<script src="../../assets/portal/vendor/bootstrap/js/bootstrap.min.js"></script>
<script src="../../assets/portal/vendor/common/common.min.js"></script>
<script src="../../assets/portal/vendor/jquery.validation/jquery.validation.min.js"></script>
<script src="../../assets/portal/vendor/jquery.easy-pie-chart/jquery.easy-pie-chart.min.js"></script>
<script src="../../assets/portal/vendor/jquery.gmap/jquery.gmap.min.js"></script>
<script src="../../assets/portal/vendor/jquery.lazyload/jquery.lazyload.min.js"></script>
<script src="../../assets/portal/vendor/isotope/jquery.isotope.min.js"></script>
<script src="../../assets/portal/vendor/owl.carousel/owl.carousel.min.js"></script>
<script src="../../assets/portal/vendor/magnific-popup/jquery.magnific-popup.min.js"></script>
<script src="../../assets/portal/vendor/vide/vide.min.js"></script>
<script src="../../assets/admin/vendor/pnotify/pnotify.custom.js"></script>

<!-- Theme Base, Components and Settings -->
<script src="../../assets/portal/js/theme.js"></script>

<!-- Theme Custom -->
<script src="../../assets/portal/js/custom.js"></script>

<!-- Theme Initialization Files -->
<script src="../../assets/portal/js/theme.init.js"></script>

<script>
    function getUrlVars()
    {
        var vars = [], hash;
        var hashes = window.location.href.slice(window.location.href.indexOf('?') + 1).split('&');
        for(var i = 0; i < hashes.length; i++)
        {
            hash = hashes[i].split('=');
            vars.push(hash[0]);
            vars[hash[0]] = hash[1];
        }
        return vars;
    }

    function showSuccess(title, body) {
        new PNotify({
            title: title,
            text: body,
            type: 'success',
            shadow: true,
            hide: true,
            delay: 5000,
            sticker: false
        });
    }

    function showError(body) {
        new PNotify({
            title: 'Error!',
            text: body,
            type: 'error',
            shadow: true,
            hide: true,
            delay: 5000,
            sticker: false
        });
    }
</script>

<jsp:invoke fragment="footer"/>

</body>
</html>
