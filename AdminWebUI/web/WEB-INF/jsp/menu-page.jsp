<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="/WEB-INF/jsp/include/include-head.jsp" %>
<body>
<%@ include file="/WEB-INF/jsp/include/include-nav.jsp" %>
<script type="text/javascript" src="ztree/jquery.ztree.all-3.5.min.js"></script>
<script type="text/javascript" src="crowd/my-menu.js"></script>
<script type="text/javascript">
    $(function () {
        // 生成树
        generateTree();

    })

    // 节点添加按钮呼出模态框
    function addMenuBtn(pId) {
        $("#modelMenuAdd [name=name]").val("");
        $("#modelMenuAdd [name=url]").val("");
        $("#modelMenuAdd").modal("show");
        $("#modelMenuAdd [name=pid]").val(pId);
    }

    function removeMenuBtn(btn) {
        let temp = $(btn).parent().prev().children("span:eq(1)").text();
        $("#modelMenuRemove").modal("show");
        $("#modelMenuRemove [name=id]").val($(btn).attr("id"));
        $("#showName").text(temp);
    }

    // 验证 添加节点模态框的表单
    function checkAdd(form) {
        if ($.trim(form.name.value) === "") {
            layer.msg("节点名称不能为空");
            return false;
        }
        return true;
    }

</script>
<div class="container-fluid">
    <div class="row">
        <%@ include file="/WEB-INF/jsp/include/include-sidebar.jsp" %>
        <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">

            <div class="panel panel-default">
                <div class="panel-heading"><i class="glyphicon glyphicon-th-list"></i> 权限菜单列表
                    <div style="float:right;cursor:pointer;" data-toggle="modal" data-target="#myModal"><i
                            class="glyphicon glyphicon-question-sign"></i></div>
                </div>
                <div class="panel-body">
                    <ul id="treeDemo" class="ztree">
                    </ul>
                </div>
            </div>
        </div>
    </div>
</div>
<%@include file="/WEB-INF/jsp/modal-menu-add.jsp" %>
<%@include file="/WEB-INF/jsp/modal-menu-remove.jsp" %>
</body>
</html>
