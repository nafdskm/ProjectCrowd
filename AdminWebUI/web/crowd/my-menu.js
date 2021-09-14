// 生成树
function generateTree() {
    $.ajax({
        url: "admin/menu/get/whole/tree.do",
        type: "post",
        success: function (resp) {
            if (resp.result === "SUCCESS") {
                var setting = {
                    view: {
                        addDiyDom: myAddDiyDom,
                        addHoverDom: myAddHoverDom,
                        removeHoverDom: myRemoveHoverDom
                    },
                    data: {
                        key: {
                            url: "doNotJump"    // 让url去寻找一个不存在的属性，从而实现单击不跳转
                        }
                    }
                };
                var zNodes = resp.data;
                $.fn.zTree.init($("#treeDemo"), setting, zNodes);
            }
        }
    });
}

// 删除浮动按钮组
function myRemoveHoverDom(treeId, treeNode) {
    let btnGroupId = "#" + treeNode.tId + "_btnGroup";
    $(btnGroupId).remove();
}

// 添加浮动按钮组
function myAddHoverDom(treeId, treeNode) {
    let btnGroupId = treeNode.tId + "_btnGroup";
    let anchorId = "#" + treeNode.tId + "_a";
    let nodeName = treeNode.name;
    // 防止重复添加按钮组
    if ($("#" + btnGroupId).length > 0) {
        return;
    }

    var addBtn = "<a id='" + treeNode.id + "' class='btn btn-info dropdown-toggle btn-xs' style='margin-left:10px;padding-top:0;' title='添加子节点' onclick='addMenuBtn(this.id)'>&nbsp;&nbsp;<i class='fa fa-fw fa-plus rbg '></i></a>";
    var removeBtn = "<a id='" + treeNode.id + "' class='btn btn-info dropdown-toggle btn-xs' style='margin-left:10px;padding-top:0;' title='删除节点' onclick='removeMenuBtn(this)'>&nbsp;&nbsp;<i class='fa fa-fw fa-times rbg '></i></a>";
    var editBtn = "<a id='" + treeNode.id + "' class='btn btn-info dropdown-toggle btn-xs' style='margin-left:10px;padding-top:0;' title='修改节点'>&nbsp;&nbsp;<i class='fa fa-fw fa-edit rbg '></i></a>";

    $(anchorId).after("<span id='" + btnGroupId + "'></span>");
    // 根据节点级别确定按钮组内容
    if (treeNode.level === 0) {
        $("#" + btnGroupId).html(addBtn);
    }
    if (treeNode.level === 1) {
        let btnHtml = addBtn + " " + editBtn;
        if (!(treeNode.children.length > 0)) {
            btnHtml += " " + removeBtn;
        }
        $("#" + btnGroupId).html(btnHtml);
    }
    if (treeNode.level === 2) {
        $("#" + btnGroupId).html(editBtn + " " + removeBtn);
    }
}

// 修改图标
function myAddDiyDom(treeId, treeNode) {
    /*
        zTree生成id规则
        例子：treeDemo_7_ico
        解析：ul标签id_当前节点的序号_功能
     */
    let icoId = "#" + treeNode.tId + "_ico";

    // 修改class属性来修改图标
    $(icoId).attr("class", treeNode.icon);
}