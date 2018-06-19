<%@ page import="sda.pl.domain.Product" %>
<%@ page import="java.util.List" %>
<%@ page import="sda.pl.repository.ProductRepository" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %><%--
  Created by IntelliJ IDEA.
  User: PC
  Date: 14.06.2018
  Time: 20:11
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%

    ProductType category = ProductType.valueOf(request.getParameter("category"));
    List<Product> all = ProductRepository.findAllByCategory(category);
    pageContext.setAttribute("allProducts", all);

%>


<!DOCTYPE html>
<html lang="en">

<head>

    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>Shop Homepage - Start Bootstrap Template</title>

    <!-- Bootstrap core CSS -->
    <link href="vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">

    <!-- Custom styles for this template -->
    <link href="css/shop-homepage.css" rel="stylesheet">

</head>

<body>
<%@ include file="header.jsp"%>
<!-- Page Content -->
<div class="container">

    <div class="row">

        <div class="col-lg-3">

            <%@include file="leftMenu.jsp"%>

        </div>
        <!-- /.col-lg-3 -->

        <div class="col-lg-9">

            <div class="row">
                <c:forEach items="${allProducts}" var="product">
                    <div class="col-lg-4 col-md-6 mb-4">
                        <div class="card h-100">
                            <a target="_blank" href="productImage?productId=${product.id}"><img class="card-img-top"
                                src="productImage?productId=${product.id}"  onerror="this.src='http://placehold.it/700x400'" ></a>
                            <div class="card-body">
                                <h4 class="card-title">
                                    <a href="product.jsp?productId=${product.id}">${product.name}</a>
                                </h4>
                                <h5>${product.price.priceGross} ${product.price.priceNet}</h5>
                                <p class="card-text">${product.color}</p>
                            </div>
                            <div class="card-footer">
                                <small class="text-muted">&#9733; &#9733; &#9733; &#9733; &#9734;</small>
                            </div>
                        </div>
                    </div>
                </c:forEach>


            </div>
            <!-- /.row -->

        </div>
        <!-- /.col-lg-9 -->

    </div>
    <!-- /.row -->

</div>
<!-- /.container -->
<%@include file="footer.jsp"%>
<!-- Bootstrap core JavaScript -->
<script src="vendor/jquery/jquery.min.js"></script>
<script src="vendor/bootstrap/js/bootstrap.bundle.min.js"></script>

</body>

</html>

