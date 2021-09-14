<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html lang="zh-CN">
<%@ include file="/WEB-INF/jsp/include/include-head.jsp" %>
<script type="text/javascript">
    $(function () {
        // 表单回显
        $.ajax({
            // 获取数据
            url: "admin/get/admin/by/id.do",
            type: "post",
            data: {
                adminId: ${param.adminId}
            },
            // 数据填入
            success: function(resp) {
                if (resp.result === "SUCCESS") {
                    window.admin = resp.data;
                    $("#inputLoginAcct").val(admin.loginAcct);
                    $("#inputUserName").val(admin.userName);
                    $("#inputEmail").val(admin.email);
                }
            }
        });

        // 保存修改
        $("#submit").click(function () {
            $.ajax({
                url: "admin/edit/save.do",
                type: "post",
                // async: false, // 关闭异步工作模型，使用同步方式进行工作。此时，所有操作在同一个线程当中
                data: {
                    loginAcct: $("#inputLoginAcct").val(),
                    userName: $("#inputUserName").val(),
                    userPassword: $.trim($("#inputPassword").val()),
                    email: $("#inputEmail").val()
                },
                success: function (result) {
                    console.log(result);
                    layer.msg(result.message);
                    if (result.result === "SUCCESS") {
                        setTimeout(function () {
                            window.location.href = "admin/get/page.do?keyword=" + $("#inputLoginAcct").val();
                        }, 1000);
                    }
                }
            })
        });
    })
</script>
<body>

<%@ include file="/WEB-INF/jsp/include/include-nav.jsp" %>

<div class="container-fluid">
    <div class="row">
        <%@ include file="/WEB-INF/jsp/include/include-sidebar.jsp" %>
        <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
            <ol class="breadcrumb">
                <li><a href="admin/to/main/page.do">首页</a></li>
                <li><a href="admin/get/page.do">数据列表</a></li>
                <li class="active">修改</li>
            </ol>
            <div class="panel panel-default">
                <div class="panel-heading">表单数据
                    <div style="float:right;cursor:pointer;" data-toggle="modal" data-target="#myModal"><i
                            class="glyphicon glyphicon-question-sign"></i></div>
                </div>
                <div class="panel-body">
                    <div class="form-group">
                        <label for="inputLoginAcct">登陆账号</label>
                        <input type="text" class="form-control" id="inputLoginAcct"
                               name="loginAcct" readonly="readonly">
                        <p style="color: red;margin-top: 5px;" id="note"></p>
                    <div class="form-group">
                        <label for="inputUserName">用户名称</label>
                        <input type="text" class="form-control" id="inputUserName" placeholder="请输入要修改的用户名称"
                               name="userName">
                    </div>
                    <div class="form-group">
                        <label for="inputPassword">用户密码</label>
                        <input type="text" class="form-control" id="inputPassword" placeholder="请输入要修改的用户密码"
                               name="userPassword">
                    </div>
                    <div class="form-group">
                        <label for="inputEmail">邮箱地址</label>
                        <input type="email" class="form-control" id="inputEmail" placeholder="请输入要修改的邮箱地址" name="email">
                        <p class="help-block label label-warning">请输入合法的邮箱地址, 格式为： xxxx@xxxx.com</p>
                    </div>
                    <button id="submit" type="button" class="btn btn-success"><i class="glyphicon glyphicon-plus"></i>
                        保存
                    </button>
                    <button type="button" class="btn btn-danger"><i class="glyphicon glyphicon-refresh"></i> 重置</button>
                </div>
            </div>
        </div>
    </div>
</div>
<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span
                        class="sr-only">Close</span></button>
                <h4 class="modal-title" id="myModalLabel">帮助</h4>
            </div>
            <div class="modal-body">
                <div class="bs-callout bs-callout-info">
                    <h4>注意事项</h4>
                    <p>账号仅供查看，不可修改</p>
                </div>
            </div>
            <!--
            <div class="modal-footer">
              <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
              <button type="button" class="btn btn-primary">Save changes</button>
            </div>
            -->
        </div>
    </div>
</div>
</body>
</html>
