<%@ page import="java.util.Optional" %>
<%@ page import="sda.pl.repository.OrderRepository" %>
<%@ page import="sda.pl.domain.*" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %><%--
  Created by IntelliJ IDEA.
  User: PC
  Date: 17.06.2018
  Time: 13:42
  To change this template use File | Settings | File Templates.
--%>
<%
    Long orderId = Long.valueOf(request.getParameter("orderId"));
    Optional<Order> orderByOrderId = OrderRepository.findOrderById(orderId);

    if (orderByOrderId.isPresent()) {
            pageContext.setAttribute("order", orderByOrderId.get());
    } else {
        //TODO dodac przekierowanie, error 404
    }

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
<%@ include file="header.jsp" %>
<!-- Page Content -->
<div class="container">

    <div class="row">

        <div class="col-lg-3">

            <%@include file="leftMenu.jsp" %>

        </div>
        <!-- /.col-lg-3 -->

        <div class="col-lg-9">

            <div class="row">
                <div class="col-lg-12 col-md-12 mb-12">
                    <table class="table table-striped">
                        <thead>
                        <tr>
                            <th scope="col">#</th>
                            <th scope="col">Nazwa produktu</th>
                            <th scope="col">Ilość</th>
                            <th scope="col">Cena netto (1szt.)</th>
                            <th scope="col">Cena netto</th>
                            <th scope="col">Cena brutto (1szt.)</th>
                            <th scope="col">Cena brutto</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${order.orderDetailSet}" var="od" varStatus="i">
                            <tr>
                                <th scope="row">${i.index+1}</th>
                                <td><a href="product.jsp?productId=${od.product.id}">${od.product.name}</a></td>
                                <td>${od.amount}</td>
                                <td>${od.price.priceNet}</td>
                                <td>${od.price.priceNet*od.amount}</td>
                                <td>${od.price.priceGross}</td>
                                <td>${od.price.priceGross*od.amount}</td>
                            </tr>
                        </c:forEach>
                        </tbody>
                        <tfoot>
                        <tr>
                            <td colspan="4"></td>
                            <td><c:set var="total" value="${0}"/>
                                <c:forEach var="article" items="${order.orderDetailSet}">
                                    <c:set var="total" value="${total + (article.price.priceNet*article.amount)}" />
                                </c:forEach>
                                ${total}zł
                            </td>
                            <td colspan="1"></td>
                            <td><c:set var="total" value="${0}"/>
                                <c:forEach var="article" items="${order.orderDetailSet}">
                                    <c:set var="total" value="${total + (article.price.priceGross*article.amount)}" />
                                </c:forEach>
                                ${total}zł
                            </td>
                        </tr>
                        </tfoot>
                    </table>
                    <a href="order.jsp">Powrót</a>
                </div>

            </div>
            <!-- /.row -->

        </div>
        <!-- /.col-lg-9 -->


    </div>
    <!-- /.row -->

</div>
<!-- /.container -->
<%@include file="footer.jsp" %>
<!-- Bootstrap core JavaScript -->
<script src="vendor/jquery/jquery.min.js"></script>
<script src="vendor/bootstrap/js/bootstrap.bundle.min.js"></script>

</body>

</html>

