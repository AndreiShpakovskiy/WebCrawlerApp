<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<!DOCTYPE html>
<html>

<head>
    <title>Result</title>
    <link href="<c:url value="/res/css/result.css"/>" rel="stylesheet" type="text/css"/>
    <link href="<c:url value="/res/css/home.css"/>" rel="stylesheet" type="text/css"/>
    <link href="<c:url value="/res/css/all-rooms.css"/>" rel="stylesheet" type="text/css"/>
</head>

<body>
<article>
    <h2>RESULTS</h2>
    <hr>
    <table>
        <tr>
            <th>Page Address</th>
            <c:forEach var="term" items="${terms}">
                <th>${term}</th>
            </c:forEach>
            <th>Count</th>
        </tr>

        <c:forEach var="statItem" items="${top10}">
            <tr>

                <td><a href="${statItem.pageAddress}">${statItem.pageAddress}</a></td>

                <c:forEach var="subitem" items="${statItem.occurrencesStat}">
                    <td>${subitem}</td>
                </c:forEach>
            </tr>
        </c:forEach>
    </table>

    <br>
    <form:form method="GET" action="download/${downloadPath}">
        <div style="text-align: center;"><button type="submit" class="submit-button">DOWNLOAD RESULTS</button></div>
    </form:form>
</article>
</body>
</html>