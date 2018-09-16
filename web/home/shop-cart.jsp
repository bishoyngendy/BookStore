<%--
  Author: marc
  Date: May 08, 2018
--%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:portalWrapper title="Shop Cart">
    <jsp:attribute name="header">
    <!-- Specific Page Vendor CSS -->
        <link rel="stylesheet" href="../assets/admin/vendor/pnotify/pnotify.custom.css" />
    </jsp:attribute>

    <jsp:attribute name="footer">
		<!-- Specific Page Vendor -->
		<script src="../assets/admin/vendor/pnotify/pnotify.custom.js"></script>
        <script type='text/javascript'>
                    function roundPrices() {
                        $('.float').each(function(i, item) {
                            var num = parseFloat(item.innerHTML);
                            item.innerHTML = num.toFixed(2);
                        });
                    }
                    $(document).ready(function() {
                        roundPrices();
                    })
                </script>
        <script>
            function increase(btn, isbn, price) {
                var url = "cart/add?ISBN=" + isbn;
                $.ajax({
                    url: url,
                    type: 'GET',
                    success: function(data){
                        showSuccess("Book added!", "");
                        $(btn).prev().val( function(i, oldVal) {
                            return ++oldVal;
                        });

                        var id_amount = '#' + isbn + '_amount';
                        var oldAmount = $(id_amount).text();
                        $(id_amount).text(Number(oldAmount) + price);

                        var oldTotal = $('#sub-total').text();
                        $('#sub-total').text(Number(oldTotal) + price);
                        $('#total').text(Number(oldTotal) + price);
                        roundPrices();
                    },
                    error: function(date) {
                        showError("Error adding new book!");
                    }
                });
            }

            function decrease(btn, isbn, price) {
                $(btn).next().val( function(i, oldVal) {
                    if (oldVal != 1) {
                        var url = "cart/remove?ISBN=" + isbn;
                        $.ajax({
                            url: url,
                            type: 'GET',
                            success: function (data) {
                                showSuccess("Book removed!", "");

                                var id_amount = '#' + isbn + '_amount';
                                var oldAmount = $(id_amount).text();
                                $(id_amount).text(Number(oldAmount) - price);

                                var oldTotal = $('#sub-total').text();
                                $('#sub-total').text(Number(oldTotal) - price);
                                $('#total').text(Number(oldTotal) - price);
                                roundPrices();
                            },
                            error: function(date) {
                                showError("Error removing book!");
                            }
                        });
                        return --oldVal;
                    }
                    else return 1;
                });
            }

            function removeOrder(btn, isbn, price) {
                var url = "cart/remove?ISBN=" + isbn + "&ALL=true";
                $.ajax({
                    url: url,
                    type: 'GET',
                    success: function (data) {
                        showSuccess("Book removed!", "");
                        var oldTotal = $('#sub-total').text();
                        var id = '#' + isbn + '_qty';
                        var qty = $(id).val();
                        $('#sub-total').text(Number(oldTotal) - (price * qty));
                        $('#total').text(Number(oldTotal) - (price * qty));
                        if (Number(oldTotal) - (price * qty) == 0) {
                            $("#submitOrder").prop('disabled', true);
                        }

                        $(btn).closest('tr').remove();
                    },
                    error: function(date) {
                        showError("Error removing row!");
                    }
                });
            }

            function placeOrder() {
                var paymentId = $("#payment").val();
                if (paymentId == null) {
                    showError("Please select a payment method");
                    return false;
                }

                var url = "orders";
                $.ajax({
                    url: url,
                    type: 'POST',
                    data: { paymentId: paymentId },
                    success: function (data) {
                        showSuccess("SUCCESS", "Order placed successfully!");
                        setTimeout(function(){
                            window.location.replace("/");
                        }, 2000);
                    },
                    error: function(data) {
                        showError(data.responseJSON.value);
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
                    <div class="col-md-12">
                        <div class="featured-boxes">
                            <div class="row">
                                <div class="col-md-12">
                                    <div class="featured-box featured-box-primary align-left mt-sm">
                                        <div class="box-content">
                                            <form method="post" action="">
                                                <table class="shop_table cart">
                                                    <thead>
                                                    <tr>
                                                        <th class="product-remove">
                                                            &nbsp;
                                                        </th>
                                                        <th class="product-thumbnail">
                                                            &nbsp;
                                                        </th>
                                                        <th class="product-name">
                                                            Product
                                                        </th>
                                                        <th class="product-price">
                                                            Price
                                                        </th>
                                                        <th class="product-quantity">
                                                            Quantity
                                                        </th>
                                                        <th class="product-subtotal">
                                                            Total
                                                        </th>
                                                    </tr>
                                                    </thead>
                                                    <tbody>
                                                    <c:forEach items="${cart.getBooks()}" var="book">
                                                        <tr class="cart_table_item">
                                                            <td class="product-remove">
                                                                <a onclick="removeOrder(this, ${book.ISBN}, ${book.price})" title="Remove this item" class="remove" href="#">
                                                                    <i class="fa fa-times"></i>
                                                                </a>
                                                            </td>
                                                            <td class="product-thumbnail">
                                                                <a href="/book?isbn=${book.ISBN}">
                                                                    <img width="100" height="100" alt="" class="img-responsive" src="img/products/product-3.jpg">
                                                                </a>
                                                            </td>
                                                            <td class="product-name">
                                                                <a href="/book?isbn=${book.ISBN}">${book.getTitle()}</a>
                                                            </td>
                                                            <td class="product-price">
                                                                $<span class="amount float">${book.getPrice()}</span>
                                                            </td>
                                                            <td class="product-quantity">
                                                                <form enctype="multipart/form-data" method="post" class="cart">
                                                                    <div class="quantity">
                                                                        <input type="button" onclick="decrease(this, ${book.ISBN}, ${book.price})" class="minus" value="-">
                                                                        <input type="text" id="${book.ISBN}_qty" readonly class="input-text qty text" title="Qty" value="${book.getQuantity()}" name="quantity" min="1" step="1">
                                                                        <input type="button" onclick="increase(this, ${book.ISBN}, ${book.price})" class="plus" value="+">
                                                                    </div>
                                                                </form>
                                                            </td>
                                                            <td class="product-subtotal">
                                                                $<span id="${book.ISBN}_amount" class="amount float">${book.price * book.getQuantity()}</span>
                                                            </td>
                                                        </tr>
                                                    </c:forEach>
                                                    </tbody>
                                                </table>
                                            </form>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>

                        <div class="featured-boxes">
                            <div class="row">
                                <div class="col-sm-6">
                                    <div class="featured-box featured-box-primary align-left mt-xlg">
                                        <div class="box-content">
                                            <h4 class="heading-primary text-uppercase mb-md">Payment Details</h4>
                                            <form action="/" id="choosePayment" method="post">
                                                <div class="row">
                                                    <div class="form-group">
                                                        <div class="col-md-12">
                                                            <label>Select your payment</label>
                                                            <select id="payment" class="form-control">
                                                                <c:forEach items="${payments}" var="payment">
                                                                    <option value="${payment.paymentId}">${payment.type} - **** **** **** ${payment.cardNumber.toString().substring(8)}</option>
                                                                </c:forEach>
                                                            </select>
                                                        </div>
                                                    </div>
                                                </div>
                                            </form>
                                            <h4 class="heading-primary text-uppercase mb-md">Add new payment</h4>
                                            <form action="/payments/add" id="addNewPayment" method="post">
                                                <div class="row">
                                                    <div class="form-group">
                                                        <div class="col-md-5">
                                                            <label>Type</label>
                                                            <select title="type" name="type" required class="form-control">
                                                                <option value="Visa">Visa</option>
                                                                <option value="Master Card">MasterCard</option>
                                                            </select>
                                                        </div>
                                                        <div class="col-md-7">
                                                            <label>Card Number</label>
                                                            <input type="text" name="cardNumber" title="cardNumber" required pattern="[0-9]{12}" placeholder="your card number" class="form-control">
                                                        </div>
                                                        <div class="col-md-5">
                                                            <label>CVV</label>
                                                            <input type="number" min="100" max="999" step="1" title="cvv" name="cvv" required placeholder="cvv" class="form-control">
                                                        </div>
                                                        <div class="col-md-7">
                                                            <label>Expire Date</label>
                                                            <input type="date" title="date" name="date" required class="form-control">
                                                        </div>
                                                    </div>
                                                </div>
                                                <div class="row">
                                                    <div class="col-md-12">
                                                        <input type="submit" value="Add Payment" class="btn btn-default pull-right mb-xl" data-loading-text="Loading...">
                                                    </div>
                                                </div>
                                            </form>
                                        </div>
                                    </div>
                                </div>
                                <div class="col-sm-6">
                                    <div class="featured-box featured-box-primary align-left mt-xlg">
                                        <div class="box-content">
                                            <h4 class="heading-primary text-uppercase mb-md">Cart Totals</h4>
                                            <table class="cart-totals">
                                                <tbody>
                                                <tr class="cart-subtotal">
                                                    <th>
                                                        <strong>Cart Subtotal</strong>
                                                    </th>
                                                    <td>
                                                        <strong><span class="amount">$<span id="sub-total" class="float">${cart.getTotalOrder()}</span></span></strong>
                                                    </td>
                                                </tr>
                                                <tr class="shipping">
                                                    <th>
                                                        Shipping
                                                    </th>
                                                    <td>
                                                        Free Shipping<input type="hidden" value="free_shipping" id="shipping_method" name="shipping_method">
                                                    </td>
                                                </tr>
                                                <tr class="total">
                                                    <th>
                                                        <strong>Order Total</strong>
                                                    </th>
                                                    <td>
                                                        <strong><span class="amount">$<span id="total" class="float">${cart.getTotalOrder()}</span></span></strong>
                                                    </td>
                                                </tr>
                                                </tbody>
                                            </table>
                                        </div>
                                    </div>
                                </div>
                            </div>

                        </div>

                        <form onclick="return placeOrder()">
                            <div class="row">
                                <div class="col-md-12">
                                    <div class="actions-continue">
                                        <button id="submitOrder" <c:if test="${empty cart || cart.totalOrder == 0}">disabled</c:if> type="submit" class="btn pull-right btn-primary btn-lg">Proceed to Checkout <i class="fa fa-angle-right ml-xs"></i></button>
                                    </div>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>

            </div>

        </div>
    </jsp:body>
</t:portalWrapper>