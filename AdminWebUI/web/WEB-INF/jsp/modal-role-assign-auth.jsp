<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div id="assignAuthModel" class="modal fade" tabindex="-1" role="dialog">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span>
                </button>
                <h4 class="modal-title">权限分配</h4>
            </div>
            <form class="form-signin" role="form">
                <ul id="authTreeDemo" class="ztree"></ul>
                <div class="modal-footer">
                    <button id="assignBtn" type="button" class="btn btn-primary">确定</button>
                </div>
            </form>
        </div><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
</div>
<!-- /.modal -->
