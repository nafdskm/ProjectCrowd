<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div id="modelMenuAdd" class="modal fade" tabindex="-1" role="dialog">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span>
                </button>
                <h4 class="modal-title">添加节点</h4>
            </div>
            <form action="admin/menu/save/node.do" method="post" class="form-signin" role="form" onsubmit="return checkAdd(this);">
                <div class="modal-body">
                    <div class="form-group has-success has-feedback">
                        <input type="hidden" name="pid" value="">
                        <input type="text" name="name" class="form-control" placeholder="请输入新节点的名称"><br/>
                        <input type="text" name="url" class="form-control" placeholder="请输入新节点的url地址">
                    </div>
                </div>
                <div class="modal-footer">
                    <button id="saveMenuBtn" type="submit" class="btn btn-primary">保存</button>
                </div>
            </form>
        </div><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
</div>
<!-- /.modal -->
