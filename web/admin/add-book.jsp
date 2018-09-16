<%--
  Author: marc
  Date: May 05, 2018
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:adminWrapper title="Add Book" bookExpanded="true">
    <jsp:attribute name="header">
    <!-- Specific Page Vendor CSS -->
        <link rel="stylesheet" href="../assets/admin/vendor/jquery-ui/css/ui-lightness/jquery-ui-1.10.4.custom.css" />
        <%--<link rel="stylesheet" href="../../assets/admin/vendor/select2/select2.css" />--%>
        <link href="https://cdnjs.cloudflare.com/ajax/libs/select2/4.0.6-rc.0/css/select2.min.css" rel="stylesheet" />
        <link rel="stylesheet" href="../assets/admin/vendor/bootstrap-multiselect/bootstrap-multiselect.css" />
        <link rel="stylesheet" href="../assets/admin/vendor/bootstrap-tagsinput/bootstrap-tagsinput.css" />
        <link rel="stylesheet" href="../assets/admin/vendor/bootstrap-colorpicker/css/bootstrap-colorpicker.css" />
        <link rel="stylesheet" href="../assets/admin/vendor/bootstrap-timepicker/css/bootstrap-timepicker.css" />
        <link rel="stylesheet" href="../assets/admin/vendor/dropzone/css/basic.css" />
        <link rel="stylesheet" href="../assets/admin/vendor/dropzone/css/dropzone.css" />
        <link rel="stylesheet" href="../assets/admin/vendor/bootstrap-markdown/css/bootstrap-markdown.min.css" />
        <link rel="stylesheet" href="../assets/admin/vendor/summernote/summernote.css" />
        <link rel="stylesheet" href="../assets/admin/vendor/summernote/summernote-bs3.css" />
        <link rel="stylesheet" href="../assets/admin/vendor/codemirror/lib/codemirror.css" />
        <link rel="stylesheet" href="../assets/admin/vendor/codemirror/theme/monokai.css" />
    </jsp:attribute>

    <jsp:attribute name="footer">
        <script type='text/javascript'>
            $(document).ready(function() {
                <c:if test="${not empty success}">
                    showSuccess("${success}", "");
                </c:if>
            })
        </script>
        <script>
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
                      console.log(data);
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
        </script>
    </jsp:attribute>

    <jsp:body>
        <div class="row">
        <div class="col-md-6">
            <form method="post" id="form" action="add" class="form-horizontal">
                <section class="panel">
                    <header class="panel-heading">
                        <h2 class="panel-title">Add New Book</h2>
                        <p class="panel-subtitle">
                            Add new book to the learning treasure.
                        </p>
                    </header>
                    <div class="panel-body">
                        <div class="form-group">

                            <label class="col-sm-3 control-label">ISBN <span class="required">*</span></label>
                            <div class="col-sm-9">
                                <input id="ISBN" value="${param.ISBN}" type="number" min="1000000000" max="9999999999" name="ISBN" class="form-control" placeholder="eg.: 978-3-16-148410-0" required/>
                                <c:if test="${not empty error_isbn}"><label for="ISBN" class="error">${error_isbn}</label></c:if>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-3 control-label">Title <span class="required">*</span></label>
                            <div class="col-sm-9">
                                <input id="title" value="${param.title}" type="text" name="title" class="form-control" placeholder="eg.: The Secret" required/>
                                <c:if test="${not empty error_title}"><label for="title" class="error">${error_title}</label></c:if>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-3 control-label">Publisher <span class="required">*</span></label>
                            <div class="col-sm-9">
                                <select class="col-sm-9" id="publisher" name="publisher" title="publisher">
                                </select>
                                <c:if test="${not empty error_publisher}"><label for="publisher" class="error">${error_publisher}</label></c:if>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-3 control-label">Authors <span class="required">*</span></label>
                            <div class="col-sm-9">
                                <select class="col-sm-9" multiple id="authors" name="authors" title="authors"></select>
                                <c:if test="${not empty error_author}"><label for="ISBN" class="error">${error_author}</label></c:if>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-3 control-label">Publication Year<span class="required">*</span></label>
                            <div class="col-sm-9">
                                <input id="publication_year" required value="${param.publication_year}" type="number" min="0" max="9999" name="publication_year" class="form-control" placeholder="eg.: 2018"/>
                                <c:if test="${not empty error_publication_year}"><label for="publication_year" class="error">${error_publication_year}</label></c:if>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-3 control-label">Price <span class="required">*</span></label>
                            <div class="col-sm-9">
                                <div class="input-group">
													<span class="input-group-addon">
														<i class="fa fa-dollar"></i>
													</span>
                                    <input name="price" value="${param.price}" type="number" id="price" class="form-control" placeholder="eg.: 50" required min="0" max="1000000">
                                    <c:if test="${not empty error_price}"><label for="price" class="error">${error_price}</label></c:if>
                                </div>
                            </div>
                            <div class="col-sm-9">

                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-3 control-label">Threshold <span class="required">*</span></label>
                            <div class="col-sm-9">
                                <input id="threshold" value="${param.threshold}" type="number" name="threshold" class="form-control" placeholder="eg.: 100" min="0" required/>
                                <c:if test="${not empty error_threshold}"><label for="threshold" class="error">${error_threshold}</label></c:if>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-3 control-label">Category <span class="required">*</span></label>
                            <div class="col-sm-9">
                                <select title="category" name="category" required class="form-control mb-md">
                                    <option value="Science">Science</option>
                                    <option value="Art">Art</option>
                                    <option value="Religion">Religion</option>
                                    <option value="History">History</option>
                                    <option value="Geography">Geography</option>
                                </select>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-3 control-label">Stock Order Quantity <span class="required">*</span></label>
                            <div class="col-sm-9">
                                <input id="stock_order_quamntity" value="${param.stock_order_quamntity}" type="number" name="stock_order_quamntity" class="form-control" placeholder="eg.: 100" min="1" required/>
                                <c:if test="${not empty error_stock_order_quantity}"><label for="stock_order_quamntity" class="error">${error_stock_order_quantity}</label></c:if>
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

