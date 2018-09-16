<%@tag description="Admin Wrapper Tag" pageEncoding="UTF-8"%>
<%@attribute name="title" required="true" %>
<%@attribute name="bookExpanded" %>
<%@attribute name="header" fragment="true" %>
<%@attribute name="footer" fragment="true" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html class="fixed">
<head>

    <!-- Basic -->
    <meta charset="UTF-8">

    <title>BookStore - ${title}</title>
    <meta name="keywords" content="HTML5 Admin Template" />
    <meta name="description" content="Porto Admin - Responsive HTML5 Template">
    <meta name="author" content="okler.net">

    <!-- Mobile Metas -->
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />

    <!-- Web Fonts  -->
    <link href="http://fonts.googleapis.com/css?family=Open+Sans:300,400,600,700,800|Shadows+Into+Light" rel="stylesheet" type="text/css">

    <!-- Vendor CSS -->
    <link rel="stylesheet" href="../../assets/admin/vendor/bootstrap/css/bootstrap.css" />
    <link rel="stylesheet" href="../../assets/admin/vendor/font-awesome/css/font-awesome.css" />
    <link rel="stylesheet" href="../../assets/admin/vendor/magnific-popup/magnific-popup.css" />
    <link rel="stylesheet" href="../../assets/admin/vendor/bootstrap-datepicker/css/datepicker3.css" />
    <link rel="stylesheet" href="../../assets/admin/vendor/pnotify/pnotify.custom.css" />

    <!-- Theme CSS -->
    <link rel="stylesheet" href="../../assets/admin/stylesheets/theme.css" />

    <!-- Skin CSS -->
    <link rel="stylesheet" href="../../assets/admin/stylesheets/skins/default.css" />

    <!-- Theme Custom CSS -->
    <link rel="stylesheet" href="../../assets/admin/stylesheets/theme-custom.css">

    <!-- Head Libs -->
    <script src="../../assets/admin/vendor/modernizr/modernizr.js"></script>

    <!-- Specific Page Vendor CSS -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/select2/4.0.5/css/select2.min.css" />
    <link rel="stylesheet" href="../../assets/admin/vendor/jquery-datatables-bs3/assets/css/datatables.css" />
    <jsp:invoke fragment="header"/>

</head>
<body>
<section class="body">

    <!-- start: header -->
    <header class="header">
        <div class="logo-container">
            <a href="../../" class="logo">
                <img src="../../assets/admin/images/logo.png" height="35" alt="Porto Admin" />
            </a>
            <div class="visible-xs toggle-sidebar-left" data-toggle-class="sidebar-left-opened" data-target="html" data-fire-event="sidebar-left-opened">
                <i class="fa fa-bars" aria-label="Toggle sidebar"></i>
            </div>
        </div>

        <!-- start: search & user box -->
        <div class="header-right">
            <span class="separator"></span>

            <div id="userbox" class="userbox">
                <a href="#" data-toggle="dropdown">
                    <figure class="profile-picture">
                        <img src="../../assets/admin/images/!logged-user.jpg" alt="Joseph Doe" class="img-circle" data-lock-picture="../../assets/admin/images/!logged-user.jpg" />
                    </figure>
                    <div class="profile-info">
                        <span class="name">${username}</span>
                        <span class="role">administrator</span>
                    </div>

                    <i class="fa custom-caret"></i>
                </a>

                <div class="dropdown-menu">
                    <ul class="list-unstyled">
                        <li class="divider"></li>
                        <li>
                            <a role="menuitem" tabindex="-1" href="/settings"><i class="fa fa-user"></i> My Profile</a>
                        </li>
                        <li>
                            <a role="menuitem" tabindex="-1" href="/logout"><i class="fa fa-power-off"></i> Logout</a>
                        </li>
                    </ul>
                </div>
            </div>
        </div>
        <!-- end: search & user box -->
    </header>
    <!-- end: header -->

    <div class="inner-wrapper">
        <!-- start: sidebar -->
        <aside id="sidebar-left" class="sidebar-left">

            <div class="sidebar-header">
                <div class="sidebar-title">
                    Navigation
                </div>
                <div class="sidebar-toggle hidden-xs" data-toggle-class="sidebar-left-collapsed" data-target="html" data-fire-event="sidebar-left-toggle">
                    <i class="fa fa-bars" aria-label="Toggle sidebar"></i>
                </div>
            </div>

            <div class="nano">
                <div class="nano-content">
                    <nav id="menu" class="nav-main" role="navigation">
                        <ul class="nav nav-main">
                            <li class="nav-parent <c:if test="${bookExpanded}">nav-active nav-expanded</c:if>">
                                <a>
                                    <i class="fa fa-book" aria-hidden="true"></i>
                                    <span>Books</span>
                                </a>
                                <ul class="nav nav-children">
                                    <li <c:if test="${pageContext.request.requestURI eq '/admin/books.jsp'}">class="nav-active"</c:if>>
                                        <a href="/admin/books">
                                            View Books
                                        </a>
                                    </li>
                                    <li <c:if test="${pageContext.request.requestURI eq '/admin/add-book.jsp'}">class="nav-active"</c:if>>
                                        <a href="/admin/books/add">
                                            Add Book
                                        </a>
                                    </li>
                                    <li <c:if test="${pageContext.request.requestURI eq '/admin/add-author.jsp'}">class="nav-active"</c:if>>
                                        <a href="/admin/authors/add">
                                            Add Author
                                        </a>
                                    </li>
                                    <li <c:if test="${pageContext.request.requestURI eq '/admin/add-publisher.jsp'}">class="nav-active"</c:if>>
                                        <a href="/admin/publishers/add">
                                            Add Publisher
                                        </a>
                                    </li>
                                </ul>
                            </li>
                            <li <c:if test="${pageContext.request.requestURI eq '/admin/stock-orders.jsp'}">class="nav-active"</c:if>>
                                <a href="/admin/orders/stocks">
                                    <i class="fa fa-tasks" aria-hidden="true"></i>
                                    <span>Stocks</span>
                                </a>
                            </li>
                            <li <c:if test="${pageContext.request.requestURI eq '/admin/users.jsp'}">class="nav-active"</c:if>>
                                <a href="/admin/users">
                                    <i class="fa fa-user" aria-hidden="true"></i>
                                    <span>Users</span>
                                </a>
                            </li>
                            <li class="nav-parent">
                                <a>
                                    <i class="fa fa-book" aria-hidden="true"></i>
                                    <span>Reports</span>
                                </a>
                                <ul class="nav nav-children">
                                    <li><a href="/admin/report?reportIndex=0" target="_blank">Total Sales for all Books</a></li>
                                    <li><a href="/admin/report?reportIndex=1" target="_blank">Total Sales for each Book</a></li>
                                    <li><a href="/admin/report?reportIndex=2" target="_blank">Top Customers in Books Count</a></li>
                                    <li><a href="/admin/report?reportIndex=3" target="_blank">Top Customers in Books Profit</a></li>
                                    <li><a href="/admin/report?reportIndex=4" target="_blank">Top Books Profit</a></li>
                                    <li><a href="/admin/report?reportIndex=5" target="_blank">Top Books Sold</a></li>
                                </ul>
                            </li>
                        </ul>
                    </nav>
                </div>
            </div>
        </aside>
        <!-- end: sidebar -->

        <section role="main" class="content-body">
            <header class="page-header">
                <h2>${title}</h2>
            </header>

            <!-- start: page -->
                <jsp:doBody/>
            <!-- end: page -->
        </section>
    </div>

    <aside id="sidebar-right" class="sidebar-right">
        <div class="nano">
            <div class="nano-content">
                <a href="#" class="mobile-close visible-xs">
                    Collapse <i class="fa fa-chevron-right"></i>
                </a>
            </div>
        </div>
    </aside>
</section>

<!-- Vendor -->
<script src="../../assets/admin/vendor/jquery/jquery.js"></script>
<script src="../../assets/admin/vendor/jquery-browser-mobile/jquery.browser.mobile.js"></script>
<script src="../../assets/admin/vendor/bootstrap/js/bootstrap.js"></script>
<script src="../../assets/admin/vendor/nanoscroller/nanoscroller.js"></script>
<script src="../../assets/admin/vendor/bootstrap-datepicker/js/bootstrap-datepicker.js"></script>
<script src="../../assets/admin/vendor/magnific-popup/magnific-popup.js"></script>
<script src="../../assets/admin/vendor/jquery-placeholder/jquery.placeholder.js"></script>

<!-- Specific Page Vendor -->
<script src="../../assets/admin/vendor/jquery-validation/jquery.validate.js"></script>


<script src="../../assets/admin/vendor/jquery-ui/js/jquery-ui-1.10.4.custom.js"></script>
<script src="../../assets/admin/vendor/jquery-ui-touch-punch/jquery.ui.touch-punch.js"></script>
<%--<script src="../../assets/admin/vendor/select2/select2.js"></script>--%>
<script src="https://cdnjs.cloudflare.com/ajax/libs/select2/4.0.6-rc.0/js/select2.min.js"></script>
<script src="../../assets/admin/vendor/bootstrap-multiselect/bootstrap-multiselect.js"></script>
<script src="../../assets/admin/vendor/jquery-maskedinput/jquery.maskedinput.js"></script>
<script src="../../assets/admin/vendor/bootstrap-tagsinput/bootstrap-tagsinput.js"></script>
<script src="../../assets/admin/vendor/bootstrap-colorpicker/js/bootstrap-colorpicker.js"></script>
<script src="../../assets/admin/vendor/bootstrap-timepicker/js/bootstrap-timepicker.js"></script>
<script src="../../assets/admin/vendor/fuelux/js/spinner.js"></script>
<script src="../../assets/admin/vendor/dropzone/dropzone.js"></script>
<script src="../../assets/admin/vendor/bootstrap-markdown/js/markdown.js"></script>
<script src="../../assets/admin/vendor/bootstrap-markdown/js/to-markdown.js"></script>
<script src="../../assets/admin/vendor/bootstrap-markdown/js/bootstrap-markdown.js"></script>
<script src="../../assets/admin/vendor/codemirror/lib/codemirror.js"></script>
<script src="../../assets/admin/vendor/codemirror/addon/selection/active-line.js"></script>
<script src="../../assets/admin/vendor/codemirror/addon/edit/matchbrackets.js"></script>
<script src="../../assets/admin/vendor/codemirror/mode/javascript/javascript.js"></script>
<script src="../../assets/admin/vendor/codemirror/mode/xml/xml.js"></script>
<script src="../../assets/admin/vendor/codemirror/mode/htmlmixed/htmlmixed.js"></script>
<script src="../../assets/admin/vendor/codemirror/mode/css/css.js"></script>
<script src="../../assets/admin/vendor/summernote/summernote.js"></script>
<script src="../../assets/admin/vendor/bootstrap-maxlength/bootstrap-maxlength.js"></script>
<script src="../../assets/admin/vendor/ios7-switch/ios7-switch.js"></script>
<script src="../../assets/admin/vendor/pnotify/pnotify.custom.js"></script>

<!-- Specific Page Vendor -->
<%--<script src="../../assets/admin/vendor/select2/select2.js"></script>--%>
<script src="../../assets/admin/vendor/jquery-datatables/media/js/jquery.dataTables.js"></script>
<script src="../../assets/admin/vendor/jquery-datatables-bs3/assets/js/datatables.js"></script>

<!-- Theme Base, Components and Settings -->
<script src="../../assets/admin/javascripts/theme.js"></script>

<!-- Theme Custom -->
<script src="../../assets/admin/javascripts/theme.custom.js"></script>

<!-- Theme Initialization Files -->
<script src="../../assets/admin/javascripts/theme.init.js"></script>

<!-- Examples -->
<script src="../../assets/admin/javascripts/forms/examples.validation.js"></script>
<script src="../../assets/admin/javascripts/tables/examples.datatables.ajax.js"></script>

<script>
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
<%----%>
<%--<script src="../../assets/admin/javascripts/forms/examples.advanced.form.js"> </script>--%>

</body>
</html>