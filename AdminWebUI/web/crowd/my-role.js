// 生成权限树
function fillAuthTree(roleId) {
    window.roleId = roleId;
    $.ajax({
        url: "assign/get/all/auth.do",
        type: "post",
        success: function (resp) {
            if (resp.result === "SUCCESS") {
                var zNodes = resp.data;
                var setting = {
                    data: {
                        simpleData: {
                            enable: true,    // ztree自动组装树形结构
                            pIdKey: "categoryId"
                        },
                        key: {
                            name: "title"
                        }
                    },
                    check: {
                        enable: true
                    }
                };
                // 初始化生成树
                $.fn.zTree.init($("#authTreeDemo"), setting, zNodes);
                // 展开所有节点
                let zTreeObj = $.fn.zTree.getZTreeObj("authTreeDemo");
                zTreeObj.expandAll(true);

                //自动勾选已分配节点
                $.ajax({
                    url: "assign/get/assigned/auth/id/by/roleId.do",
                    type: "post",
                    data: {
                        roleId: roleId
                    },
                    success: function (resp) {
                        if (resp.result === "SUCCESS") {
                            let authIdList = resp.data;
                            for (let i = 0; i < authIdList.length; i++) {
                                let authId = authIdList[i];
                                let treeNode = zTreeObj.getNodeByParam("id", authId);

                                // 表示勾选
                                let checked = true;
                                // 表示不联动其他勾选框
                                let checkTypeFlag = false;
                                zTreeObj.checkNode(treeNode, checked, checkTypeFlag);
                            }
                        }
                    }
                });

            }
        }
    });
}

// 执行分页，生成页面效果，任何时候调用这个函数都会重新加载页面
function generatePage() {
    let keyword = $("#keyword").val();
    // 获取分页数据
    let pageInfo = getPageInfoRemote(keyword);

    // 填充表格
    fillTableBody(pageInfo);
}

// 远程访问服务器端程序获取pageInfo数据
function getPageInfoRemote(keyword) {
    let resultEntity;
    $.ajax({
        url: "admin/role/get/page/info.do",
        type: "post",
        async: false,
        data: {
            pageNum: pageNum,
            pageSize: pageSize,
            keyword: keyword
        },
        dataType: "json",
        success: function (resp) {
            resultEntity = resp;
        }
    });
    var result = resultEntity.result;

    // 判断结果是否成功
    if (result === "FAILED") {
        layer.msg(result.message);
        return null;
    }

    return resultEntity.data;
}

// 填充表格
function fillTableBody(pageInfo) {
    // 清楚tbody中旧的内容
    $("#rolePageBody").empty();
    $("#Pagination").empty();

    // 判断pageInfo是否有效
    if (pageInfo == null || pageInfo.list == null || pageInfo.list.length === 0) {
        $("#rolePageBody").append("<tr><td colspan='4' align='center'>抱歉！没有查询到想要的数据!</td></tr>");
        return null;
    }

    for (let i = 0; i < pageInfo.list.length; i++) {
        let role = pageInfo.list[i];
        let roleId = role.id;
        let roleName = role.name;
        let checkbox = "<td><input type='checkbox' onclick='checkBoxRemoveRoles(this);'></td>";
        if (removeWaitList.length !== 0 && removeWaitList.indexOf(roleId + "") !== -1) {
            checkbox = "<td><input type='checkbox' checked='checked' onclick='checkBoxRemoveRoles(this);'></td>"
        }
        let tr =
            "<tr>" +
                "<td>" + (i + 1) + "</td>" +
                checkbox +
                "<td>" + roleName + "</td>" +
                "<td>" +
                    "<button id='" + roleId + "' type='button' class='btn btn-success btn-xs' onclick='modelAssignAuthBtn(this.id);'><i class=' glyphicon glyphicon-check'></i></button> " +
                    "<button id='" + roleId + "' name='editRoleBtnName' type='button' class='btn btn-primary btn-xs' onclick='editRoleBtn(this);'><i class=' glyphicon glyphicon-pencil'></i></button> " +
                    "<button type='button' class='btn btn-danger btn-xs' onclick='removeOneRoleBtn(this);'><i class=' glyphicon glyphicon-remove'></i></button> " +
                "</td>" +
            "</tr>";
        $("#rolePageBody").append(tr);
    }
    generateNavigator(pageInfo);
}

// 生成分页页码导航条
function generateNavigator(pageInfo) {
    var totalRecord = pageInfo.total;
    $("#Pagination").pagination(totalRecord, {
        num_edge_entries: 3, //边缘页数
        num_display_entries: 5, //主体页数
        callback: paginationCallBack,
        items_per_page: pageInfo.pageSize, //每页显示1项
        current_page: pageInfo.pageNum -1, // Pagination内部使用pageIndex来管理页码，pageIndex从0开始
        prev_text: "上一页",
        next_text: "下一页"
    });
}

// 翻页时的回调函数
function paginationCallBack(pageIndex, jQuery) {
    window.pageNum = pageIndex + 1;
    generatePage();

    // 由于每一个页码的按钮都是超链接，所以在这个函数的最后取消超链接的默认行为
    return false;
}