<%@ page import="sda.pl.domain.Product" %>
<%@ page import="java.util.List" %>
<%@ page import="sda.pl.repository.ProductRepository" %>
<%@ page import="java.util.Optional" %>
<%@ page import="sda.pl.repository.CartRepository" %>
<%@ page import="sda.pl.domain.Cart" %>
<%@ page import="sda.pl.repository.OrderRepository" %>
<%@ page import="sda.pl.domain.Order" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %><%--
  Created by IntelliJ IDEA.
  User: PC
  Date: 15.06.2018
  Time: 17:45
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    //TODO dodac user z sesji albo ciastka
    Long USER_ID = 2L;

    List<Order> userOrders = OrderRepository.findAllByUserId(USER_ID);
    pageContext.setAttribute("userOrders", userOrders);
    DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd  HH:mm:ss");
    pageContext.setAttribute("dateFormat", dateFormat);

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
                            <th scope="col">Data</th>
                            <th scope="col">Adres wysyłki</th>
                            <th scope="col">Kwota netto</th>
                            <th scope="col">Kwota brutto</th>
                            <th scope="col">Pozycje zamówienia</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${userOrders}" var="o" varStatus="i">
                            <tr>
                                <th scope="row">${i.index+1}</th>
                                <td>${o.date.format(dateFormat)}</td>
                                <td>${o.cityName}</td>
                                <td>${o.totalPrice.priceNet}</td>
                                <td>${o.totalPrice.priceGross}</td>
                                <td><a href="orderDetails.jsp?orderId=${o.id}">Pozycje zamówienia</a></td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
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

