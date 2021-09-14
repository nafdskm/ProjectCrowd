<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>$Title$</title>
    <%--http://localhost:8080/AdminWebUI_war_exploded/--%>
    <%--要参照base标签的路径不能以 '\' 开头--%>
    <base href="http://${pageContext.request.serverName}:${pageContext.request.serverPort}${pageContext.request.contextPath}/"/>
    <script type="text/javascript" src="jquery-2.1.1.min.js"></script>
    <script src="layer/layer.js"></script>
    <script type="text/javascript">
        $(function () {
            $("#btn1").click(function () {
                let ary = [3,5,12];
                let aryJson = JSON.stringify(ary);
                $.ajax({
                    url: "test/array.do",
                    type: "post",
                    contentType: "application/json;charset=utf-8",
                    data: aryJson,
                    dataType: "text",
                    success: function (resp) {
                        console.log(resp);
                    }
                })
            })

            $("#btn2").click(function () {
                layer.msg("测试layer弹窗");
            })

        })
    </script>
</head>
<body>
<button id="btn1">发送数组[5,8,12]</button>
<br>
<br>
<button id="btn2">点我弹窗</button>
</body>
</html>
