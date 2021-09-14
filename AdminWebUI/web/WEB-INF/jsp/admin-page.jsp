<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="/WEB-INF/jsp/include/include-head.jsp" %>
<script type="text/javascript">
    $(function () {
        initPagination();
    })

    function removeAdmin(obj) {
        let id = $(obj).val();
        layer.confirm('确认删除?', {icon: 3, title: '提示'}, function (index) {
            $.ajax({
                url: "admin/remove/" + id + ".do",
                data: {
                    keyword:"${param.keyword}",
                    pageNum:"${requestScope.pageInfo.pageNum}"
                },
                success: function (resp) {
                    layer.msg(resp.message);
                    setTimeout(function () {
                        window.location.href=resp.data;
                    }, 1000);
                }
            })
            layer.close(index);
        });
    }

    function initPagination() {
        var totalRecord = ${requestScope.pageInfo.total};
        $("#Pagination").pagination(totalRecord, {
            num_edge_entries: 3, //边缘页数
            num_display_entries: 5, //主体页数
            callback: pageSelectCallback,
            items_per_page:${requestScope.pageInfo.pageSize}, //每页显示1项
            current_page: ${requestScope.pageInfo.pageNum} -1, // Pagination内部使用pageIndex来管理页码，pageIndex从0开始
            prev_text: "上一页",
            next_text: "下一页"
        });
    }

    // 回调函数的含义：声明以后不是自己调用，而是交给系统或框架进行调用（被作为参数传递的函数）
    // 用户点击页码时通过这个函数进行跳转
    function pageSelectCallback(pageIndex, jQuery) {
        var pageNum = pageIndex + 1;
        var toUrl = "admin/get/page.do?pageNum=" + pageNum;
        var keyword = "".concat(${param.keyword});
        if (keyword !== "") {
            toUrl += "&keyword=" + keyword;
        }
        window.location.href = toUrl;

        // 由于每一个页码的按钮都是超链接，所以在这个函数的最后取消超链接的默认行为
        return false;
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
                    <form action="admin/get/page.do" method="post" class="form-inline" role="form" style="float:left;">
                        <div class="form-group has-feedback">
                            <div class="input-group">
                                <div class="input-group-addon">查询条件</div>
                                <input name="keyword" class="form-control has-success" type="text"
                                       placeholder="请输入查询条件"/>
                            </div>
                        </div>
                        <button type="submit" class="btn btn-warning"><i class="glyphicon glyphicon-search"></i> 查询
                        </button>
                    </form>
                    <button type="button" class="btn btn-danger" style="float:right;margin-left:10px;"><i
                            class=" glyphicon glyphicon-remove"></i> 删除
                    </button>
                    <button type="button" class="btn btn-primary" style="float:right;"
                            onclick="window.location.href='admin/add/page.do'"><i class="glyphicon glyphicon-plus"></i> 新增
                    </button>
                    <br>
                    <hr style="clear:both;">
                    <div class="table-responsive">
                        <table class="table  table-bordered">
                            <thead>
                            <tr>
                                <th width="30">#</th>
                                <th width="30"><input type="checkbox"></th>
                                <th>账号</th>
                                <th>名称</th>
                                <th>邮箱地址</th>
                                <th width="100">操作</th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:if test="${empty requestScope.pageInfo.list}">
                                <tr>
                                    <td colspan="6" align="center">抱歉！未查询到你要的数据！</td>
                                </tr>
                            </c:if>
                            <c:if test="${!empty requestScope.pageInfo.list}">
                                <c:forEach items="${requestScope.pageInfo.list}" var="admin" varStatus="myStatus">
                                    <tr>
                                        <td>${myStatus.count}</td>
                                        <td><input type="checkbox"></td>
                                        <td>${admin.loginAcct}</td>
                                        <td>${admin.userName}</td>
                                        <td>${admin.email}</td>
                                        <td>
                                            <a href="assign/to/assign/role/page.do?adminId=${admin.id}&pageNum=${requestScope.pageInfo.pageNum}&keyword=${param.keyword}" type="button" class="btn btn-success btn-xs"><i
                                                    class=" glyphicon glyphicon-check"></i></a>
                                            <a href="admin/edit/page.do?adminId=${admin.id}" class="btn btn-primary btn-xs"><i
                                                    class=" glyphicon glyphicon-pencil"></i></a>
                                            <button value="${admin.id}" type="button"
                                                    class="btn btn-danger btn-xs" onclick="removeAdmin(this)"><i
                                                    class=" glyphicon glyphicon-remove"></i></button>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </c:if>

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
</body>
</html>
