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

<t:adminWrapper title="Users">

    <jsp:attribute name="footer">
        <script>
            $(document).ready(function() {
                $('#datatable-users-ajax').DataTable( {
                    "processing": true,
                    "serverSide": true,
                    "searching": false,
                    "ordering": false,
                    "ajax": '/admin/users',
                    "columnDefs": [ {
                        "targets": 5,
                        "render": function ( data, type, row ) {
                            var txt = "";
                            <c:forEach items="${roles}" var="role">
                            var equalRole = ${role.roleId} == row[6];
                            if (equalRole) {
                                txt += '<option selected value="${role.roleId}">${role.roleName}</option>';
                            } else {
                                txt += '<option value="${role.roleId}">${role.roleName}</option>';
                            }
                            </c:forEach>
                            return '<select onchange="changeUserRole(' + row[0] + ', this)" title="role" name="role" required class="form-control mb-md">'+ txt + '</select>';
                        }
                    } ]
                } );
            } );

            function changeUserRole(userId, selectObject) {
                var roleId = (selectObject.value || selectObject.options[selectObject.selectedIndex].value);
                var url = "/admin/users?userId=" + userId + "&roleId=" + roleId;
                $.ajax({
                    url: url,
                    type: 'POST',
                    success: function(data) {
                        showSuccess("Role changed successfully!", "");
                    }
                });
            }
        </script>
    </jsp:attribute>

    <jsp:body>
        <section class="panel">

            <header class="panel-heading">
                <div class="panel-actions">
                    <a href="#" class="fa fa-caret-down"></a>
                    <a href="#" class="fa fa-times"></a>
                </div>

                <h2 class="panel-title">All Users </h2>
            </header>
            <div class="panel-body">
                <table class="table table-bordered table-striped" id="datatable-users-ajax" >
                    <thead>
                    <tr>
                        <th width="15%">ID</th>
                        <th width="20%">Full Name</th>
                        <th width="15%">Username</th>
                        <th width="15%">Phone</th>
                        <th width="20%">Shipping Address</th>
                        <th width="15%">Role</th>
                    </tr>
                    </thead>
                </table>
            </div>
        </section>
    </jsp:body>
</t:adminWrapper>