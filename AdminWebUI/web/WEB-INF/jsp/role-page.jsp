<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="/WEB-INF/jsp/include/include-head.jsp" %>
<script type="text/javascript" src="ztree/jquery.ztree.all-3.5.min.js"></script>
<script type="text/javascript" src="crowd/my-role.js"></script>
<script type="text/javascript">
    $(function () {
        window.pageNum = 1;
        window.pageSize = 5;
        window.keyword = "";
        generatePage();

        /**
         * 显示添加模态框
         */
        $("#addRole").click(function () {
            $("#modelRoleAdd").modal("show");
        });

        /**
         * 分配权限 模态框确定按钮
         */
        $("#assignBtn").click(function () {
            var authIdArray = [];

            // 获取被选节点将对应id放入数组，准备提交
            let zTreeObj = $.fn.zTree.getZTreeObj("authTreeDemo");
            let checkedNodes = zTreeObj.getCheckedNodes();
            for (let i = 0; i < checkedNodes.length; i++) {
                let checkedNode = checkedNodes[i];
                let authId = checkedNode.id;
                authIdArray.push(authId);
            }
            // 发生ajax请求，提交数据
            $.ajax({
                url: "assign/auth/to/role.do",
                type: "post",
                data: {
                    authIdArray: authIdArray,
                    roleId: window.roleId
                },
                success: function (resp) {
                    if (resp.result === "SUCCESS") {
                        layer.msg(resp.message);
                    }
                }
            });
            $("#assignAuthModel").modal("hide");
        });

        /**
         * 添加新角色 模态框的保存按钮绑定单击函数
         */
        $("#saveRoleBtn").click(function () {
            $.ajax({
                url: "admin/role/save.do",
                type: "post",
                data: {
                    name: $.trim($("#modelRoleAdd [name=name]").val())
                },
                success: function (resp) {
                    if (resp.result === "SUCCESS") {
                        layer.msg(resp.message);
                        setTimeout(function () {
                            $("#modelRoleAdd [name=name]").val("");
                            $('#modelRoleAdd').modal('hide');
                        }, 1500);
                    } else {
                        $("#modelRoleAdd [name=name]").val("");
                        layer.msg(resp.message);
                    }
                }
            })
        });

        /**
         * 编辑角色 模态框的保存按钮绑定单击函数
         */
        $("#editRoleBtn").click(function () {
            $.ajax({
                url: "admin/role/edit.do",
                type: "post",
                data: {
                    id: $("[name=editRoleBtnName]").attr("id"),
                    name: $.trim($("#modelRoleEdit [name=name]").val())
                },
                success: function (resp) {
                    if (resp.result === "SUCCESS") {
                        layer.msg(resp.message);
                        generatePage();
                    } else {
                        layer.msg(resp.message);
                    }
                }
            });
            $('#modelRoleEdit').modal('hide');
        })

    })

    /**
     * 分配权限模态框 弹出按钮
     */
    function modelAssignAuthBtn(roleId) {
        $("#assignAuthModel").modal("show");
        fillAuthTree(roleId);
    }

    /*  jQuery对象的on(var1, var2, var3)函数
        第一个参数是事件类型
        第二个参数是要真正绑定的元素的选择器
        第三个参数是事件的响应函数
        $("#modelRoleEdit").on("click", "pencilBtn", function(){})
     */
    /**
     * 修改按钮单击事件函数
     * @param obj   触发该函数的标签元素
     */
    function editRoleBtn(obj) {
        $("#modelRoleEdit").modal("show");
        $("#modelRoleEdit [name=name]").val($(obj).parent().prev().text());
    }

    /**
     * 删除按钮单击事件函数
     * @param obj   触发该函数的标签元素
     */
    function removeOneRoleBtn(obj) {
        $.ajax({
            url: "admin/role/remove.do",
            type: "post",
            data: {
                list: [$(obj).prev().attr("id")]
            },
            success: function (resp) {
                if (resp.result === "SUCCESS") {
                    layer.msg(resp.message);
                    setTimeout(function () {
                        // 1s后刷新页面
                        generatePage();
                    }, 1000);
                } else {
                    layer.msg(resp.message);
                }
            }
        })
    }

    window.removeWaitList=[];
    /**
     * 多选框批量删除
     * @param obj   多选框标签本身
     */
    function checkBoxRemoveRoles(obj) {
        let roleId = $(obj).parent().next().next().children("[name=editRoleBtnName]").attr("id");
        if ($(obj).is(":checked")) {
            removeWaitList.push(roleId);
        } else {
            removeWaitList.splice($.inArray(roleId, removeWaitList), 1);
        }
    }

    /**
     *  确认批量删除
     */
    function removeRoles() {
        $.ajax({
            url: "admin/role/remove.do",
            type: "post",
            data: {
                list: removeWaitList
            },
            success: function (resp) {
                if (resp.result === "SUCCESS") {
                    removeWaitList = [];
                    layer.msg(resp.message);
                    setTimeout(function () {
                        // 1s后刷新页面
                        generatePage();
                    }, 1000);
                } else {
                    layer.msg(resp.message);
                }
            }
        })
    }
</script>
<body>
<%@ include file="/WEB-INF/jsp/include/include-nav.jsp" %>
<div class="container-fluid">
    <div class="row">
        <%@ include file="/WEB-INF/jsp/include/include-sidebar.jsp" %>
        <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
            <div class="panel panel-default">
                <div class="panel-heading">
                    <h3 class="panel-title"><i class="glyphicon glyphicon-th"></i> 数据列表</h3>
                </div>
                <div class="panel-body">
                    <form class="form-inline" role="form" style="float:left;">
                        <div class="form-group has-feedback">
                            <div class="input-group">
                                <div class="input-group-addon">查询条件</div>
                                <input id="keyword" class="form-control has-success"
                                       type="text" placeholder="请输入查询条件">
                            </div>
                        </div>
                        <button onclick="generatePage()" type="button" class="btn btn-warning"><i
                                class="glyphicon glyphicon-search"></i> 查询
                        </button>
                    </form>
                    <button onclick="removeRoles();" type="button" class="btn btn-danger" style="float:right;margin-left:10px;"><i
                            class=" glyphicon glyphicon-remove"></i> 删除
                    </button>
                    <button id="addRole" type="button" class="btn btn-primary" style="float:right;"><i
                            class="glyphicon glyphicon-plus"></i> 新增
                    </button>
                    <br>
                    <hr style="clear:both;">
                    <div class="table-responsive">
                        <table class="table  table-bordered">
                            <thead>
                            <tr>
                                <th width="30">#</th>
                                <th width="30"><input type="checkbox"></th>
                                <th>名称</th>
                                <th width="100">操作</th>
                            </tr>
                            </thead>
                            <tbody id="rolePageBody">
                            </tbody>
                            <tfoot>
                            <tr>
                                <td colspan="6" align="center">
                                    <div id="Pagination" class="pagination"><!-- 这里显示分页 --></div>
                                </td>
                            </tr>

                            </tfoot>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<%@include file="/WEB-INF/jsp/modal-role-add.jsp" %>
<%@include file="/WEB-INF/jsp/modal-role-edit.jsp" %>
<%@include file="/WEB-INF/jsp/modal-role-assign-auth.jsp"%>
</body>
</html>