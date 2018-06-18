<%@ page import="java.util.Optional" %>
<%@ page import="sda.pl.repository.CartRepository" %>
<%@ page import="sda.pl.domain.Cart" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %><%--
  Created by IntelliJ IDEA.
  User: PC
  Date: 15.06.2018
  Time: 17:45
  To change this template use File | Settings | File Templates.
--%>
<%
    //TODO dodac user z sesji albo ciastka
    Long USER_ID = 2L;
    Optional<Cart> cartByUserId = CartRepository.findCartByUserId(USER_ID);
    if (cartByUserId.isPresent()) {
        pageContext.setAttribute("cart", cartByUserId.get());
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
                            <th scope="col">Usuń</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${cart.cartDetailSet}" var="cd" varStatus="i">
                            <tr>
                                <th scope="row">${i.index+1}</th>
                                <td><a href="product.jsp?productId=${cd.product.id}" >${cd.product.name}</a></td>
                                <td>${cd.amount}</td>
                                <td>${cd.price.priceNet}</td>
                                <td>${cd.price.priceNet*cd.amount}</td>
                                <td>${cd.price.priceGross}</td>
                                <td>${cd.price.priceGross*cd.amount}</td>
                                <td><a href="removeProductFromCart?productId=${cd.product.id}">Usuń</a></td>
                                <%--<td><a href="/removeProductFromCart">Usuń</a></td>--%>
                            </tr>
                        </c:forEach>
                        </tbody>
                        <tfoot>
                        <tr>
                            <td colspan="4"></td>
                            <td><c:set var="total" value="${0}"/>
                                <c:forEach var="article" items="${cart.cartDetailSet}">
                                    <c:set var="total" value="${total + (article.product.price.priceNet*article.amount)}" />
                                </c:forEach>
                                ${total}zł
                            </td>
                            <td colspan="1"></td>
                            <td><c:set var="total" value="${0}"/>
                                <c:forEach var="article" items="${cart.cartDetailSet}">
                                    <c:set var="total" value="${total + (article.product.price.priceGross*article.amount)}" />
                                </c:forEach>
                                ${total}zł
                            </td>
                        </tr>
                        </tfoot>
                    </table>
                    <a href="/createOrder" class="btn-success btn">Kup i zapłać</a>
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

