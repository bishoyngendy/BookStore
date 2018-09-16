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

<t:adminWrapper title="Stock Orders">

    <jsp:attribute name="footer">
        <script>
            function receiveOrder(isbn) {
                var url = "/admin/orders/stocks?stockOrderId=" + isbn + "&receive=" + true;
                $.ajax({
                    url: url,
                    type: 'POST',
                    success: function(data) {
                        showSuccess("Order Received successfully!", "");
                        $('#datatable-not-received-ajax').DataTable().ajax.reload();
                        $('#datatable-received-ajax').DataTable().ajax.reload();
                    }
                    });
            }

            function deleteOrder(isbn) {
                var url = "/admin/orders/stocks?stockOrderId=" + isbn + "&delete=" + true;
                $.ajax({
                    url: url,
                    type: 'POST',
                    success: function(data) {
                        showSuccess("Order deleted successfully!", "");
                        $('#datatable-not-received-ajax').DataTable().ajax.reload();
                        $('#datatable-received-ajax').DataTable().ajax.reload();
                    }
                });
            }
        </script>
        <script>
            $(document).ready(function() {
                $('#datatable-not-received-ajax').DataTable( {
                    "processing": true,
                    "serverSide": true,
                    "ordering": false,
                    "ajax": '/admin/orders/stocks?received=false',
                    "columnDefs": [ {
                        "targets": 5,
                        "render": function ( data, type, row ) {
                            return '<button  onclick="receiveOrder(' + row[0] + ');" name="receive" class="btn btn-primary">Receive</button>' +
                                   ' <button onclick="deleteOrder(' + row[0] + ');" name="delete" class="btn btn-default">Delete</button>';
                        }
                    } ]
                } );

                $('#datatable-received-ajax').DataTable( {
                    "processing": true,
                    "serverSide": true,
                    "ordering": false,
                    "ajax": '/admin/orders/stocks?received=true',
                    "columnDefs": [ {
                        "targets": 5,
                        "render": function ( data, type, row ) {
                            return '<button type="submit" onclick="deleteOrder(' + row[0] + ');" name="delete" class="btn btn-default">Delete</button>';
                        }
                    } ]
                } );
            } );
        </script>
    </jsp:attribute>

    <jsp:body>
        <section class="panel">
            <header class="panel-heading">
                <div class="panel-actions">
                    <a href="#" class="fa fa-caret-down"></a>
                    <a href="#" class="fa fa-times"></a>
                </div>

                <h2 class="panel-title">Orders Not Received Yet</h2>
            </header>
            <div class="panel-body">
                <table class="table table-bordered table-striped" id="datatable-not-received-ajax" >
                    <thead>
                    <tr>
                        <th width="15%">Order ID</th>
                        <th width="15%">Book ISBN</th>
                        <th width="20%">Book Name</th>
                        <th width="20%">Publisher Name</th>
                        <th width="10%">Quantity</th>
                        <th width="20%">Actions</th>
                    </tr>
                    </thead>
                </table>
            </div>
            <br>
            <header class="panel-heading">
                <div class="panel-actions">
                    <a href="#" class="fa fa-caret-down"></a>
                    <a href="#" class="fa fa-times"></a>
                </div>

                <h2 class="panel-title">Delivered Orders</h2>
            </header>
            <div class="panel-body">
                <table class="table table-bordered table-striped" id="datatable-received-ajax" >
                    <thead>
                    <tr>
                        <th width="15%">Order ID</th>
                        <th width="15%">Book ISBN</th>
                        <th width="20%">Book Name</th>
                        <th width="20%">Publisher Name</th>
                        <th width="15%">Quantity</th>
                        <th width="15%">Actions</th>
                    </tr>
                    </thead>
                </table>
            </div>
        </section>
    </jsp:body>
</t:adminWrapper>