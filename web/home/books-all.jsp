<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:portalWrapper title="Home">

	<jsp:attribute name="header">
		<link href="https://cdnjs.cloudflare.com/ajax/libs/select2/4.0.6-rc.0/css/select2.min.css" rel="stylesheet" />
	</jsp:attribute>

	<jsp:attribute name="footer">
		<!-- Current Page Vendor and Views -->
		<script src="../assets/portal/vendor/nouislider/nouislider.min.js"></script>
		<script src="https://cdnjs.cloudflare.com/ajax/libs/select2/4.0.6-rc.0/js/select2.min.js"></script>

		<script>
			var yearMin = ${searchRange.getYearMin()};
			var yearMax = ${searchRange.getYearMax()};
			var priceMin = ${searchRange.getPriceMin()};
			var priceMax = ${searchRange.getPriceMax()};
            $(".add-to-cart-product").click(function(event) {
                var url = "cart/add?ISBN=" + $(event.target).closest('a').attr('id');
                $.ajax({
                    url: url,
                    type: 'GET',
                    success: function(data){ showSuccess("Book added!", 'Go to your <a href="checkout">Cart</a>.'); },
                    error: function (date) {
                        showError("Error adding book!");
                    }
                });
            });

            $('#publisher').select2({
                ajax: {
                    delay: 250,
                    url: '/Search/Publishers',
                    dataType: 'json',
                    data: function (params) {
                        var query = {
                            name: params.term
                        };
                        // Query parameters will be ?name=[term]
                        return query;
                    },
                    processResults: function (data, page) {
                        return {
                            results: data
                        }
                    },
                    minimumInputLength: 2
                }
            });

            $('#authors').select2({
                ajax: {
                    delay: 250,
                    url: '/Search/Authors',
                    dataType: 'json',
                    data: function (params) {
                        var query = {
                            name: params.term
                        };
                        // Query parameters will be ?name=[term]
                        return query;
                    },
                    processResults: function (data, page) {
                        console.log(data);
                        return {
                            results: data
                        }
                    },
                    minimumInputLength: 2
                }
            });

            var url = "";

            // handle filteration
            function search() {
                var title = $('#title-search').val();
                var isbn = $('#isbn').val();
                var yearMin = $('#year-range-high').val();
                var yearMax = $('#year-range-low').val();
                var publisher = $('#publisher').val();
                var authors = $('#authors').val();
                var priceMin = $('#price-range-low').val();
                var priceMax = $('#price-range-high').val();
                var showInStock = $('#showInStock').is(':checked');
                var category = $('#category').find(":selected").val();

                url = 'books?yearMin=' + yearMin + '&yearMax=' + yearMax
					+ '&priceMin=' + priceMin + '&priceMax=' + priceMax;
                if (title != null && title != '') {
					url += '&title=' + title;
				}

                if (isbn != null && isbn != '') {
                    url += '&isbn=' + isbn;
                }

                if (publisher != null && publisher != '') {
                    url += '&publisher=' + publisher;
                }

                if (authors != null && authors != '') {
                    authors.forEach(addAuthors);
                }

                if (category != "") {
                    url += '&category=' +category;
				}

                if (showInStock == true) {
                    url += '&inStock=0';
                }

                window.location.replace(url);
            }

            function addAuthors(item) {
				url += '&authors=' + item;
			}

            // Filter Price Slider
            if (typeof noUiSlider === 'object') {
                var priceSlider = document.getElementById('price-slider'),
                    priceLow 	= document.getElementById('price-range-low'),
                    priceHigh 	= document.getElementById('price-range-high');

                // Create Slider
                noUiSlider.create(priceSlider, {
                    start: [ ${searchRange.getPriceMin()}, ${searchRange.getPriceMax()} ],
                    connect: true,
                    step: 1,
                    range: {
                        'min': ${searchRange.getPriceMin()},
                        'max': ${searchRange.getPriceMax()}
                    }
                });

                // Update Input values
                priceSlider.noUiSlider.on('update', function( values, handle ) {
                    var value = values[handle];

                    if ( handle ) {
                        priceHigh.value = Math.round(value);
                    } else {
                        priceLow.value = Math.round(value);
                    }
                });

                // when inpout values changei update slider
                priceLow.addEventListener('change', function(){
                    priceSlider.noUiSlider.set([this.value, null]);
                });

                priceHigh.addEventListener('change', function(){
                    priceSlider.noUiSlider.set([null, this.value]);
                });

                // year slider
                var yearSlider = document.getElementById('year-slider'),
                    yearLow 	= document.getElementById('year-range-low'),
                    yearHigh 	= document.getElementById('year-range-high');

                // Create Slider
                noUiSlider.create(yearSlider, {
                    start: [ ${searchRange.getYearMin()}, ${searchRange.getYearMax()} ],
                    connect: true,
                    step: 1,
                    range: {
                        'min':${searchRange.getYearMin()},
                        'max': ${searchRange.getYearMax()}
                    }
                });

                // Update Input values
                yearSlider.noUiSlider.on('update', function( values, handle ) {
                    var value = values[handle];

                    if ( handle ) {
                        yearLow.value = Math.round(value);
                    } else {
                        yearHigh.value = Math.round(value);
                    }
                });

                // when input values change update slider
                yearSlider.addEventListener('change', function(){
                    yearSlider.noUiSlider.set([this.value, null]);
                });

                yearSlider.addEventListener('change', function(){
                    yearSlider.noUiSlider.set([null, this.value]);
                });
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
					<div class="col-md-12">
						<h1 class="mb-none"><strong>Books</strong></h1>
						<p>Showing ${start}–${end} of ${total} results.</p>
					</div>
				</div>

				<div class="row">
					<div class="col-md-9 col-md-push-3">
						<div class="masonry-loader masonry-loader-showing">
							<ul class="products product-thumb-info-list" data-plugin-masonry>
								<c:forEach items="${bookList}" var="book">
									<li class="col-md-4 col-sm-6 col-xs-12 product">
										<c:if test="${book.availableQuantity == 0}">
											<a href="#">
												<span class="onsale" style="
												background-color: #cc0000;
												border-bottom-color: #b30000;">N/A</span>
											</a>
										</c:if>

										<span class="product-thumb-info">
									<a id="${book.isbn}" class="add-to-cart-product">
										<span><i class="fa fa-shopping-cart"></i> Add to Cart</span>
									</a>
									<a href="/book?isbn=${book.isbn}">
										<span class="product-thumb-info-image">
											<span class="product-thumb-info-act">
												<span class="product-thumb-info-act-left"><em>View</em></span>
												<span class="product-thumb-info-act-right"><em><i class="fa fa-plus"></i> Details</em></span>
											</span>
											<img alt="" class="img-responsive" src="../../assets/portal/img/products/product-1.jpg">
										</span>
									</a>
									<span class="product-thumb-info-content">
										<a href="/book?isbn=${book.isbn}">
											<h4>${book.title}</h4>
											<span class="price">
												<ins><span class="amount">$${book.price}</span></ins>
											</span>
										</a>
									</span>
								</span>
									</li>
								</c:forEach>
							</ul>
						</div>

					</div>
					<aside class="col-md-3 col-md-pull-9 sidebar shop-sidebar">
						<div class="panel-group">
							<div class="panel panel-default">
								<div class="panel-heading">
									<h4 class="panel-title">
										<a class="accordion-toggle" data-toggle="collapse" href="#panel-filter-title">
											Metas
										</a>
									</h4>
								</div>
								<div id="panel-filter-title" class="accordion-body form-horizontal form-bordered collapse in">
									<div class="panel-body">
										<div class="form-group">
											<label class="col-sm-3 control-label">Title: </label>
											<div class="col-sm-9">
												<input id="title-search" placeholder="Enter book title" value="${param.title}"
													   name="title-search" class="form-control" type="text">
											</div>
										</div>
										<div class="form-group">
											<label class="col-md-3 control-label">ISBN: </label>
											<div class="col-sm-9">
												<input name="isbn" class="form-control" value="${param.isbn}"
													   id="isbn" placeholder="Enter ISBN" type="text">
											</div>
										</div>
										<div class="filter-price">
											<label class="control-label">Year: </label>
											<div id="year-slider"></div><br/>
											<div class="filter-price-details">
												<span>from</span>
												<input disabled type="text" id="year-range-high" class="form-control price-range-input" placeholder="Min">
												<span>to</span>
												<input disabled type="text" id="year-range-low" class="form-control price-range-input" placeholder="Max">
											</div>
										</div>
										<button onclick="search()" class="btn btn-primary ">FILTER</button>
									</div>
								</div>
							</div>
							<div class="panel panel-default">
								<div class="panel-heading">
									<h4 class="panel-category">
										<a class="accordion-toggle" data-toggle="collapse" href="#panel-filter-category">
											Categories
										</a>
									</h4>
								</div>
								<div id="panel-filter-category" class="accordion-body collapse in">
									<div class="panel-body">
										<select title="category" id="category">
											<option value="" >All Categories</option>
											<option value="Science">Science</option>
											<option value="Science">Art</option>
											<option value="Science">Religion</option>
											<option value="Science">History</option>
											<option value="Science">Geography</option>
										</select>
									</div>
								</div>
							</div>
							<div class="panel panel-default">
								<div class="panel-heading">
									<h4 class="panel-title">
										<a class="accordion-toggle" data-toggle="collapse" href="#panel-filter-publishers-authors">
											Publisher & Authors
										</a>
									</h4>
								</div>
								<div id="panel-filter-publishers-authors" class="accordion-body form-horizontal form-bordered collapse in">
									<div class="panel-body">
										<div class="form-group">
											<label class="col-sm-12">Publisher: </label>
											<div class="col-sm-12">
												<select class="col-sm-9" id="publisher" name="publisher" title="publisher">
												</select>
											</div>
										</div>
										<div class="form-group">
											<label class="col-sm-12">Author(s): </label>
											<div class="col-sm-9">
												<select class="col-sm-12" multiple id="authors" name="authors" title="authors">
												</select>
											</div>
										</div>
										<button onclick="search()" class="btn btn-primary ">FILTER</button>
									</div>
								</div>
							</div>
							<div class="panel panel-default">
								<div class="panel-heading">
									<h4 class="panel-title">
										<a class="accordion-toggle" data-toggle="collapse" href="#panel-filter-price">
											Price
										</a>
									</h4>
								</div>
								<div id="panel-filter-price" class="accordion-body collapse in">
									<div class="panel-body">
										<div class="filter-price">
											<div id="price-slider"></div>
											<div class="filter-price-details">
												<span>from</span>
												<input disabled type="text" id="price-range-low" class="form-control price-range-input" placeholder="Min">
												<span>to</span>
												<input disabled type="text" id="price-range-high" class="form-control price-range-input" placeholder="Max">
											</div>
										</div>
										<div>
											<label for="showInStock">Show in stock only: </label>
											<input type="checkbox" id="showInStock" name="inStock" value="inStock"
											<c:if test="${not empty param.inStock}"> checked </c:if>
											>
										</div>
										<button onclick="search()" class="btn btn-primary">FILTER</button>
									</div>
								</div>
							</div>
						</div>

					</aside>
				</div>

				<div class="row">
					<div class="col-md-12">
						<ul class="pagination pull-right">

							<c:if test="${index != 1}">
								<li><a href="/books?index=${index - 1}${searchURL}"><i class="fa fa-chevron-left"></i></a></li>
							</c:if>
							<c:forEach begin="${pagingStart}" end="${pagingEnd}" varStatus="loop">
								<li <c:if test="${index == loop.index}">class="active"</c:if>>
									<a href="/books?index=${loop.index}${searchURL}">${loop.index}</a>
								</li>
							</c:forEach>
							<c:if test="${index != count}">
								<li><a href="/books?index=${index + 1}${searchURL}"><i class="fa fa-chevron-right"></i></a></li>
							</c:if>
						</ul>
					</div>
				</div>
			</div>
		</div>
	</jsp:body>

</t:portalWrapper>