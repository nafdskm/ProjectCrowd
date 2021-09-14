<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div id="modelMenuRemove" class="modal fade" tabindex="-1" role="dialog">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span>
                </button>
                <h4 class="modal-title">删除节点</h4>
            </div>
            <form action="admin/menu/remove/node.do" method="post" class="form-signin" role="form">
                <div class="modal-body">
                    <div class="form-group has-success has-feedback">
                        <div style="text-align: center;" id="showName"></div>
                        <input name="id" type="hidden">
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="submit" class="btn btn-danger">确定</button>
                </div>
            </form>
        </div><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
</div>
<!-- /.modal -->
