<%--
  Created by IntelliJ IDEA.
  User: PC
  Date: 14.06.2018
  Time: 20:21
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    Long userId = 1L;
    String phrase = request.getParameter("phrase");
    if (phrase == null) {
        phrase = "";
    }
    pageContext.setAttribute("phrase", phrase);
    pageContext.setAttribute("userId", userId);
%>}
<html>
<head>
    <title>header</title>
</head>
<body>
<nav class="navbar navbar-expand-lg navbar-dark bg-dark fixed-top">
    <div class="container">
        <a class="navbar-brand" href="index.jsp">Online-Shop-Name</a>
        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarResponsive"
                aria-controls="navbarResponsive" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div>
            <form method="get" action="productSearch.jsp">
                <input type="text" name="phrase" value="${phrase}">
                <button type="submit">Szukaj</button>
            </form>
        </div>
        <div class="collapse navbar-collapse" id="navbarResponsive">
            <ul class="navbar-nav ml-auto">
                <li class="nav-item active">
                    <a class="nav-link" href="index.jsp">Home
                        <span class="sr-only">(current)</span>
                    </a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="cart.jsp">Koszyk</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="order.jsp">Zamówienia</a>
                </li>
                <c:if test="${userId eq 1}">
                    <li class="nav-item">
                        <a class="nav-link" href="productAdminPage.jsp">Panel admina</a>
                    </li>
                </c:if>
            </ul>
        </div>
    </div>
</nav>
</body>
</html>
