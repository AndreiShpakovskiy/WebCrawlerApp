<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="<c:url value="/res/css/home.css"/>" rel="stylesheet" type="text/css"/>
    <title>Web Crawler</title>

    <script>
        function runCrawler() {
            let response = document.getElementById('response');
            let p = document.createElement('p');
            p.style.wordWrap = 'break-word';
            p.style.fontWeight = 'bold';
            p.appendChild(document.createTextNode("Crawler is running. Please, wait. It takes up to several minutes."));
            response.appendChild(p);
        }
    </script>
</head>

<body>
<div class="outer">
    <div class="middle">
        <div class="inner">
            <form:form method="POST" action="crawl">
                <h2>WEB CRAWLER</h2>
                <hr>

                <br>
                <input id="root-page" name="root-page" placeholder="http://google.com" required type="text">
                <input id="terms" name="terms" placeholder="First Term, Second Term, etc." required type="text">
                <input id="max-pages" name="max-pages" placeholder="Max pages to visit (Default: 10000)" type="text">
                <input id="max-depth" name="max-depth" placeholder="Max crawling depth (Default: 8)" type="text">

                <button type="submit" class="submit-button" onclick="runCrawler()">START CRAWLING</button>
            </form:form>

            <p id="response"></p>
        </div>
    </div>
</div>
</body>
</html>