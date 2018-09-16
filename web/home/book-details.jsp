<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:portalWrapper title="${title}">
	<jsp:attribute name="header">
    <!-- Specific Page Vendor CSS -->
        <link rel="stylesheet" href="../assets/admin/vendor/pnotify/pnotify.custom.css" />
    </jsp:attribute>

	<jsp:attribute name="footer">
		<!-- Specific Page Vendor -->
		<script src="../assets/admin/vendor/pnotify/pnotify.custom.js"></script>

		<script>
            function increase() {
				$('#book-quantity').val( function(i, oldVal) {
                    return ++oldVal;
                });
			}

			function decrease() {
                $('#book-quantity').val( function(i, oldVal) {
                    if (oldVal != 1)
	                    return --oldVal;
                    else return 1;
                });
            }

            function addToCart() {
				var ISBN = getUrlVars()["isbn"];
				var quantity = $('#book-quantity').val();
                var url = "cart/add?ISBN=" + ISBN + '&quantity=' + quantity;
				var message = quantity == 1 ?
					"1 book added to your cart." : quantity + " book added!";

                $.ajax({
                    url: url,
                    type: 'GET',
                    success: function(data){ showSuccess("Book Added!", message); },
                    error: function(data) {
                        showError("Error adding book!");
                    }
                });

				return false;
			}

		</script>
	</jsp:attribute>
	<jsp:body>
		<div role="main" class="main shop">
			<div class="container">
				<div class="row">
					<div class="col-md-12">
						<hr class="tall">
					</div>
				</div>
				<div class="row">
					<div class="col-md-6">
						<div class="owl-carousel owl-theme owl-loaded owl-drag owl-carousel-init" data-plugin-options="{'items': 1}">
							<div class="owl-stage-outer"><div class="owl-stage" style="transform: translate3d(-1206px, 0px, 0px); transition: 0s; width: 4221px;"><div class="owl-item cloned" style="width: 603px;"><div>
								<div class="thumbnail">
									<img alt="" class="img-responsive img-rounded" src="../assets/portal/img/products/product-7-2.jpg">
								</div>
							</div></div><div class="owl-item cloned" style="width: 603px;"><div>
								<div class="thumbnail">
									<img alt="" class="img-responsive img-rounded" src="../assets/portal/img/products/product-7-3.jpg">
								</div>
							</div></div><div class="owl-item active" style="width: 603px;"><div>
								<div class="thumbnail">
									<img alt="" class="img-responsive img-rounded" src="../assets/portal/img/products/product-7.jpg">
								</div>
							</div></div><div class="owl-item" style="width: 603px;"><div>
								<div class="thumbnail">
									<img alt="" class="img-responsive img-rounded" src="../assets/portal/img/products/product-7-2.jpg">
								</div>
							</div></div><div class="owl-item" style="width: 603px;"><div>
								<div class="thumbnail">
									<img alt="" class="img-responsive img-rounded" src="../assets/portal/img/products/product-7-3.jpg">
								</div>
							</div></div><div class="owl-item cloned" style="width: 603px;"><div>
								<div class="thumbnail">
									<img alt="" class="img-responsive img-rounded" src="../assets/portal/img/products/product-7.jpg">
								</div>
							</div></div><div class="owl-item cloned" style="width: 603px;"><div>
								<div class="thumbnail">
									<img alt="" class="img-responsive img-rounded" src="../assets/portal/img/products/product-7-2.jpg">
								</div>
							</div></div></div></div><div class="owl-nav disabled"><div class="owl-prev"></div><div class="owl-next"></div></div><div class="owl-dots"><div class="owl-dot active"><span></span></div><div class="owl-dot"><span></span></div><div class="owl-dot"><span></span></div></div></div>
					</div>
					<div class="col-md-6">
						<div class="summary entry-summary">
							<h1 class="mb-none"><strong>${title}</strong></h1>
							<div class="review_num">
								<span class="count" itemprop="ratingCount">2</span> reviews
							</div>
							<div title="Rated 5.00 out of 5" class="star-rating">
								<span style="width:100%"><strong class="rating">5.00</strong> out of 5</span>
							</div>
							<p class="price">
								<span class="amount">$${price}</span>
							</p>

							<%--<p class="taller">Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed tempus nibh sed elimttis adipiscing. Fusce in hendrerit purus. Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed tempus nibh sed elimttis adipiscing. Fusce in hendrerit purus. </p>--%>
							<%--<hr>--%>

							<h5 class="heading-primary">Authors</h5>
							<c:forEach items="${authors}" var="author">
								<a href="#"><span class="label label-dark">${author.name}</span></a>
							</c:forEach>

							<hr>
							<h5 class="heading-primary">Published by: ${publisher.name}</h5>
							<hr>

							<form enctype="multipart/form-data" method="post" class="cart">
								<div class="quantity">
									<input type="button" onclick="decrease()" class="minus" value="-">
									<input type="number" id="book-quantity" class="input-text qty text" title="Qty" value="1" name="quantity" min="1" step="1">
									<input type="button" onclick="increase()" class="plus" value="+">
								</div>
								<button onclick="return addToCart()" class="btn btn-primary btn-icon">Add to cart</button>
							</form>
							<div class="product_meta">
								<span class="posted_in">Category: ${category}.</span>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</jsp:body>
</t:portalWrapper>
