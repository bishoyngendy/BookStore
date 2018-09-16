<%--
  Author: marc
  Date: May 05, 2018
--%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:adminWrapper title="Add Author" bookExpanded="true">

    <jsp:attribute name="footer">
        <script type='text/javascript'>
            $(document).ready(function() {
                <c:if test="${not empty success}">
                    showSuccess("${success}", "");
                </c:if>
            })
        </script>
    </jsp:attribute>
    <jsp:
body>
    <div class="row">
        <div class="col-md-6">
            <form method="post" id="form" action="add" class="form-horizontal">
                <section class="panel">
                    <header class="panel-heading">
                        <h2 class="panel-title">Add New Author</h2>
                        <p class="panel-subtitle">
                            Add new author to easily connect him with his books.
                        </p>
                    </header>
                    <div class="panel-body">
                        <div class="form-group">
                            <label class="col-sm-3 control-label">Full Name <span class="required">*</span></label>
                            <div class="col-sm-9">
                                <input id="name" type="text" name="name" class="form-control" placeholder="eg.: John Doe" required/>
                                <c:if test="${not empty error}"><label for="name" class="error">${error}</label></c:if>
                            </div>
                        </div>
                    </div>
                    <footer class="panel-footer">
                        <div class="row">
                            <div class="col-sm-9 col-sm-offset-3">
                                <input type="submit" class="btn btn-primary" value="Add" placeholder="eg: John Doe" />
                                <button type="reset" class="btn btn-default">Reset</button>
                            </div>
                        </div>
                    </footer>
                </section>
            </form>
        </div>
    </div>
    </jsp:body>
</t:adminWrapper>
