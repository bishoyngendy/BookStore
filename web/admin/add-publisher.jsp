<%--
  Author: marc
  Date: May 05, 2018
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:adminWrapper title="Add Publisher" bookExpanded="true">
    <jsp:attribute name="footer">
        <script type='text/javascript'>
            $(document).ready(function() {
                <c:if test="${not empty success}">
                showSuccess("${success}", "");
                </c:if>
            })
        </script>
    </jsp:attribute>
    <jsp:body>
    <div class="row">
        <div class="col-md-6">
            <form method="post" id="form" action="add" class="form-horizontal">
                <section class="panel">
                    <header class="panel-heading">
                        <h2 class="panel-title">Add New Publisher</h2>
                        <p class="panel-subtitle">
                            Add new publisher to easily connect him with his books.
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
                        <div class="form-group">
                            <label class="col-sm-3 control-label">Address <span class="required">*</span></label>
                            <div class="col-sm-9">
                                <input id="address" type="text" name="address" class="form-control" placeholder="eg.: 123, ABC ST, 12345" required/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-3 control-label">Phone</label>
                            <div class="col-sm-9 control-label">
                                <div class="input-group">
															<span class="input-group-addon">
																<i class="fa fa-phone"></i>
															</span>
                                    <input id="phone" name="phone" data-plugin-masked-input="" data-input-mask="(999) 9999-9999" placeholder="(123) 1234-1234" class="form-control" style="background-image: url(&quot;data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAABAAAAAQCAYAAAAf8/9hAAABHklEQVQ4EaVTO26DQBD1ohQWaS2lg9JybZ+AK7hNwx2oIoVf4UPQ0Lj1FdKktevIpel8AKNUkDcWMxpgSaIEaTVv3sx7uztiTdu2s/98DywOw3Dued4Who/M2aIx5lZV1aEsy0+qiwHELyi+Ytl0PQ69SxAxkWIA4RMRTdNsKE59juMcuZd6xIAFeZ6fGCdJ8kY4y7KAuTRNGd7jyEBXsdOPE3a0QGPsniOnnYMO67LgSQN9T41F2QGrQRRFCwyzoIF2qyBuKKbcOgPXdVeY9rMWgNsjf9ccYesJhk3f5dYT1HX9gR0LLQR30TnjkUEcx2uIuS4RnI+aj6sJR0AM8AaumPaM/rRehyWhXqbFAA9kh3/8/NvHxAYGAsZ/il8IalkCLBfNVAAAAABJRU5ErkJggg==&quot;); background-repeat: no-repeat; background-attachment: scroll; background-size: 16px 18px; background-position: 98% 50%; cursor: auto;">
                                </div>
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