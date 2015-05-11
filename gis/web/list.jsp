<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title></title>

    <!-- bootstrap -->
    <link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet">

</head>


<body>

<div class="bs-example" data-example-id="simple-table">
    <table class="table table-striped">
        <caption>统计列表</caption>
        <thead>
        <tr>
            <th>#</th>
            <th>建筑物名称</th>
            <th>修改名</th>
            <th>修改人</th>
            <th>修次数</th>
        </tr>
        </thead>
        <tbody>
        <tr>
            <th scope="row">1</th>
            <td>Mark</td>
            <td>Otto</td>
            <td>Otto</td>
            <td>Otto</td>
            <td>Otto</td>
            <td>@mdo</td>
        </tr>
        </tbody>
    </table>
    <script type="application/javascript" src="${pageContext.request.contextPath}/scripts/jquery.min.js"/>
    <script type="application/javascript" src="${pageContext.request.contextPath}/scripts/bootstrap.min.js"/>

</div>
</body>
</html>
