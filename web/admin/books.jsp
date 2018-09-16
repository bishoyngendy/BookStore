<%--
  Created by IntelliJ IDEA.
  User: programajor
  Date: 5/8/18
  Time: 9:10 PM
  To change this template use File | Settings | File Templates.
--%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:adminWrapper title="Books" bookExpanded="true">

    <jsp:attribute name="header">
        <link rel="stylesheet" href="../assets/admin/vendor/magnific-popup/magnific-popup.css" />
    </jsp:attribute>

    <jsp:attribute name="footer">
        <script src="../assets/admin/vendor/magnific-popup/magnific-popup.js"></script>
        <script>
            $(document).ready(function() {
                $('#datatable-books-ajax').DataTable( {
                    "processing": true,
                    "serverSide": true,
                    "ordering": false,
                    "ajax": '/admin/books',
                    "columnDefs": [ {
                        "targets": 7,
                        "render": function ( data, type, row ) {
                            var block = '<div class="center"> <a href="/admin/books/edit?isbn=' + row[0] +'" class="btn btn-default">Edit</a>  ' +
                                '<button href="#modalForm" onclick="passISBN(' + row[0] + ')" class="modal-place-stock btn btn-primary">Place Order</button>' +
                                '</div>';
                            return block;
                        }
                    } ],
                    "columns": [
                        { "width": "10%" },
                        { "width": "20%" },
                        { "width": "15%" },
                        { "width": "5%" },
                        { "width": "5%" },
                        { "width": "5%" },
                        { "width": "5%" },
                        { "width": "35%" }
                    ],
                    "drawCallback": function( settings ) {
                        $(document).on('click', '.modal-dismiss', function (e) {
                            e.preventDefault();
                            $.magnificPopup.close();
                        });

                        $(document).on('click', '.modal-confirm', function (e) {
                            placeOrder();
                            e.preventDefault();
                        });

                        $('.modal-place-stock').magnificPopup({
                            type: 'inline',
                            preloader: false,
                            focus: '#name',
                            modal: true,

                            // When elemened is focused, some mobile browsers in some cases zoom in
                            // It looks not nice, so we disable it:
                            callbacks: {
                                beforeOpen: function() {
                                    if($(window).width() < 700) {
                                        this.st.focus = false;
                                    } else {
                                        this.st.focus = '#name';
                                    }
                                }
                            }
                        });
                    }
                } );
            } );

            function passISBN(isbn) {
                $('#isbn').val(isbn);
            }

            function placeOrder() {
                var isbn = $('#isbn').val();
                var quantity = $('#quantity').val();
                var url = '/admin/orders/stocks?isbn=' + isbn + '&quantity=' + quantity;
                if (quantity > 0) {
                    $.ajax({
                        url: url,
                        type: 'POST',
                        success: function(data) {
                            showSuccess("Order added successfully!", "");
                            $.magnificPopup.close();
                        }
                    });
                } else {
                    showError("Invalid Quantity");
                }
            }
        </script>
    </jsp:attribute>

    <jsp:body>
        <div id="modalForm" class="modal-block modal-block-primary mfp-hide">
            <section class="panel">
                <header class="panel-heading">
                    <h2 class="panel-title">Registration Form</h2>
                </header>
                <div class="panel-body">
                    <form id="demo-form" class="form-horizontal mb-lg" novalidate="novalidate">
                        <div class="form-group mt-lg">
                            <label class="col-sm-3 control-label">Order Quantity</label>
                            <div class="col-sm-9">
                                <input type="number" min="1" step="1" id="quantity" name="Quantity" class="form-control" placeholder="Type your the needed quantity..." required/>
                            </div>
                        </div>
                        <input type="text" hidden id="isbn" name="name" required/>
                    </form>
                </div>
                <footer class="panel-footer">
                    <div class="row">
                        <div class="col-md-12 text-right">
                            <button class="btn btn-primary modal-confirm">Place Order</button>
                            <button class="btn btn-default modal-dismiss">Cancel</button>
                        </div>
                    </div>
                </footer>
            </section>
        </div>

        <section class="panel">
            <header class="panel-heading">
                <div class="panel-actions">
                    <a href="#" class="fa fa-caret-down"></a>
                    <a href="#" class="fa fa-times"></a>
                </div>

                <h2 class="panel-title">All Books</h2>
            </header>
            <div class="panel-body">
                <table class="table table-bordered table-striped" id="datatable-books-ajax" >
                    <thead>
                    <tr>
                        <th width="15%">ISBN</th>
                        <th width="20%">Title</th>
                        <th width="20%">Publisher Name</th>
                        <th width="5%">Price</th>
                        <th width="10%">Available</th>
                        <th width="10%">Threshold</th>
                        <th width="15%">Order Quantity</th>
                        <th width="35%">Actions</th>
                    </tr>
                    </thead>
                </table>
            </div>
        </section>
    </jsp:body>
</t:adminWrapper>