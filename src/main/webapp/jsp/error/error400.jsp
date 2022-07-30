

<fmt:message key="reference.back_to_main" var="back_to_main"/>

<html>
<header>
    <jsp:include page="../header/header.jsp"/>
</header>
<head>
    <meta charset="UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="${path}/bootstrap-5.1.3-dist/css/bootstrap.min.css" rel="stylesheet">
    <title>Error 400</title>
</head>
<body>
<div class="container">
    <table class="table text-secondary border-secondary">
        <thead>
        <tr>
            <th scope="col">Error 400</th>
        </tr>
        </thead>
        <tbody>
        <tr>
            <td>Request from ${pageContext.errorData.requestURI} is failed</br>
                Servlet name: ${pageContext.errorData.servletName}</br>
                Status code: ${pageContext.errorData.statusCode}</br>
                Message: Command not present
            </td>
        </tr>
        </tbody>
    </table>
    <a class="link-secondary text-decoration-none"
       href="${path}/controller?command=go_to_main_page">${back_to_main}</a>
</div>
<footer>
    <jsp:include page="../footer/footer.jsp"/>
</footer>
</body>
</html>
