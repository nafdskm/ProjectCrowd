<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html lang="zh-CN">
<%@ include file="/WEB-INF/jsp/include/include-head.jsp" %>
<script type="text/javascript">
    $(function () {
        // 账号重复验证
        $("#exampleInputPassword1").blur(function () {
            let adminAcct = $("#exampleInputPassword1").val();
            if ("" !== "".concat(adminAcct)) {
                $.ajax({
                    url: "admin/add/checkAdminAcct.do",
                    type: "post",
                    data: {
                        adminAcct: adminAcct
                    },
                    success: function (resp) {
                        if (resp.result === "FAILED") {
                            $("#note").text(resp.message);
                        } else {
                            $("#note").text("");
                        }
                    }
                })
            } else {
                $("#note").text("");
            }
        })

        // 添加新用户
        $("#submit").click(function () {
            $.ajax({
                url: "admin/add/save.do",
                type: "post",
                // async: false, // 关闭异步工作模型，使用同步方式进行工作。此时，所有操作在同一个线程当中
                data: {
                    loginAcct: $("#inputLoginAcct").val(),
                    userName: $("#inputUserName").val(),
                    userPassword: $("#inputPassword").val(),
                    email: $("#inputEmail").val()
                },
                success: function (result) {
                    layer.msg(result.data);
                    if (result.result === "SUCCESS") {
                        setTimeout(function () {
                            window.location.href = "admin/get/page.do?keyword=" + $("#inputLoginAcct").val();
                        }, 1000);
                    }
                }
            })
        })
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
                <li class="active">新增</li>
            </ol>
            <div class="panel panel-default">
                <div class="panel-heading">表单数据
                    <div style="float:right;cursor:pointer;" data-toggle="modal" data-target="#myModal"><i
                            class="glyphicon glyphicon-question-sign"></i></div>
                </div>
                <div class="panel-body">
                    <div class="form-group">
                        <label for="inputLoginAcct">登陆账号</label>
                        <input type="text" class="form-control" id="inputLoginAcct" placeholder="请输入登陆账号"
                               name="loginAcct">
                        <p style="color: red;margin-top: 5px;" id="note"></p>
                    </div>
                    <div class="form-group">
                        <label for="inputUserName">用户名称</label>
                        <input type="text" class="form-control" id="inputUserName" placeholder="请输入用户名称"
                               name="userName">
                    </div>
                    <div class="form-group">
                        <label for="inputPassword">用户密码</label>
                        <input type="text" class="form-control" id="inputPassword" placeholder="请输入用户密码"
                               name="userPassword">
                    </div>
                    <div class="form-group">
                        <label for="inputEmail">邮箱地址</label>
                        <input type="email" class="form-control" id="inputEmail" placeholder="请输入邮箱地址" name="email">
                        <p class="help-block label label-warning">请输入合法的邮箱地址, 格式为： xxxx@xxxx.com</p>
                    </div>
                    <button id="submit" type="button" class="btn btn-success"><i class="glyphicon glyphicon-plus"></i>
                        新增
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
                    <h4>测试标题1</h4>
                    <p>测试内容1，测试内容1，测试内容1，测试内容1，测试内容1，测试内容1</p>
                </div>
                <div class="bs-callout bs-callout-info">
                    <h4>测试标题2</h4>
                    <p>测试内容2，测试内容2，测试内容2，测试内容2，测试内容2，测试内容2</p>
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
