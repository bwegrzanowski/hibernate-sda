<%@ page import="sda.pl.domain.ProductType" %><%--
  Created by IntelliJ IDEA.
  User: PC
  Date: 14.06.2018
  Time: 20:24
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    ProductType[] values = ProductType.values();

    pageContext.setAttribute("categories", values);

%>

<html>
<head>
    <title>left menu</title>
</head>
<body>
<h1 class="my-4">Shop Name</h1>
<div class="list-group">

    <c:forEach var="category" items="${categories}">
    <a href="productByCategory.jsp?category=${category}" class="list-group-item">${category}</a>
    </c:forEach>
</div>
</body>
</html>
