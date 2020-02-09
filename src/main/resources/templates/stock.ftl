<!DOCTYPE html>
<html lang=en>
<head>
    <meta charset=utf-8>
    <meta http-equiv=X-UA-Compatible content="IE=edge">
    <meta name=viewport content="width=device-width,initial-scale=1">
    <!--<link rel=icon href=favicon.ico>-->
    <title>安全库存量</title>
    <link href="/css/bootstrap.min.css" rel="stylesheet">
    <link href="/css/bootstrap-table.min.css" rel="stylesheet">
    <link href="/css/bootstrap-theme.min.css" rel="stylesheet">
    <link href="/css/toastr.css" rel="stylesheet"/>
</head>
<body>


    <div class="table-box">
        <div id="toolbar">
            <div class="form-inline">
                <input type="text" class="form-control" placeholder="请输入物料号..." id="input">
                <input type="text" class="form-control" placeholder="请输入物料名称..." id="inputName">
                <button type="button" class="btn btn-info" id="query">查询</button>
                <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#addModel">新增</button>
                <button type="button" class="btn btn-warning" data-toggle="modal" id="editButton">修改</button>
                <button type="button" class="btn btn-danger" id="delete">删除</button>
            </div>
        </div>
        <table id="table" class="table table-striped"></table>
    </div>



<!--新增-->
<div class="modal fade" id="addModel" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="exampleModalLabel">新增</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <form>
                    <input id="id" type="hidden">
                    <div class="form-group">
                        <label for="recipient-name" class="col-form-label">物料号:</label>
                        <input type="text" class="form-control" id="materielNo">
                    </div>
                    <div class="form-group">
                        <label for="recipient-name" class="col-form-label">物料名称:</label>
                        <input type="text" class="form-control" id="materielName">
                    </div>
                    <div class="form-group">
                        <label for="message-text" class="col-form-label">安全数量:</label>
                        <input type="text" class="form-control" id="stockNum" onkeyup="this.value=this.value.replace(/\D/g,'')">
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-primary" onclick="add()">新增</button>
                <button type="button" class="btn btn-secondary" data-dismiss="modal">关闭</button>
            </div>
        </div>
    </div>
</div>

<!--修改-->
<div class="modal fade" id="editModel" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="exampleModalLabel">修改</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <form>
                    <div class="form-group">
                        <label for="recipient-name" class="col-form-label">物料号:</label>
                        <input type="text" class="form-control" id="materielNo1" readonly>
                    </div>
                    <div class="form-group">
                        <label for="recipient-name" class="col-form-label">物料名称:</label>
                        <input type="text" class="form-control" id="materielName1">
                    </div>
                    <div class="form-group">
                        <label for="message-text" class="col-form-label">安全数量:</label>
                        <input type="text" class="form-control" id="stockNum1" onkeyup="this.value=this.value.replace(/\D/g,'')">
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-primary" onclick="edit()">修改</button>
                <button type="button" class="btn btn-secondary" data-dismiss="modal">关闭</button>
            </div>
        </div>
    </div>
</div>

</body>
<script src="/js/jquery-1.11.3.js"></script>
<script src="/js/common.js"></script>
<script src="/js/toastr.js"></script>
<script src="/js/bootstrap.min.js"></script>
<script src="/js/bootstrap-table.min.js"></script>
<script src="/js/bootstrap-table-zh-CN.min.js"></script>
<script>

    $(function () {
        var oTable = new TableInit();
        oTable.Init();
    });

    $('#addModel').on('hide.bs.modal', function () {
        $("#materielNo").val("");
        $("#materielName").val("");
        $("#stockNum").val("");
    });

    $("#delete").click(function () {
        var rows = $("#table").bootstrapTable('getSelections');

        if (rows.length == 0){
            toastr.warning("请选择至少一行修改！");
            return;
        }

        var ids = [];
        $.each(rows,function(index,row){
            ids.push(row.id);
        });

        Ewin.confirm({ message: "确认要删除选择的数据吗？" }).on(function (e) {
            if (!e) {
                return;
            }

            $.post("/stock/remove",{"ids":ids},function (data) {
                if (data.success) {
                    toastr.success(data.responseMsg);
                    $("#table").bootstrapTable('refresh');
                } else {
                    toastr.error(data.responseMsg);
                }
            },"json")
        });

    });

    $('#editButton').click(function () {
        var rows = $("#table").bootstrapTable('getSelections');

        if (rows.length != 1){
            toastr.warning("请选择一行修改！");
            $('#editModel').modal('hide')
            return;
        }
        $("#id").val(rows[0].id);
        $("#materielNo1").val(rows[0].materielNo);
        $("#materielName1").val(rows[0].materielName);
        $("#stockNum1").val(rows[0].stockNum);
        $('#editModel').modal({backdrop:false})
    });
    
    $("#query").click(function () {
        query();
    });

    function query() {
        var oTable = new TableInit();
        oTable.Init();
        $("#table").bootstrapTable('refresh');
    }

    function add() {
        var materielNo = $("#materielNo").val();
        if (materielNo == null || materielNo.trim() == '') {
            toastr.warning("物料号不能为空！");
            return;
        }

        var stockNum = $("#stockNum").val();
        if (stockNum == null || stockNum.trim() == '') {
            toastr.warning("安全数量不能为空！");
            return;
        }

        $.post("/stock/add",{"materielNo":materielNo, "materielName":$("#materielName").val(), "stockNum":stockNum},function (data) {
            if (data.success) {
                toastr.success(data.responseMsg);
                $('#addModel').modal('hide');
                $("#materielNo").val("");
                $("#materielName").val("");
                $("#stockNum").val("");
                $("#table").bootstrapTable('refresh');
            } else {
                toastr.error(data.responseMsg);
            }
        },"json")
    }

    function edit() {
        var stockNum = $("#stockNum1").val();
        if (stockNum == null || stockNum.trim() == '') {
            toastr.warning("安全数量不能为空！");
            return;
        }

        var materielName = $("#materielName1").val();

        $.post("/stock/edit",{"id": $("#id").val(),"materielName":materielName,"stockNum":stockNum},function (data) {
            if (data.success) {
                toastr.success(data.responseMsg);
                $('#editModel').modal('hide');
                $("#materielNo1").val("");
                $("#stockNum1").val("");
                $("#table").bootstrapTable('refresh');
            } else {
                toastr.error(data.responseMsg);
            }
        },"json")
    }

    var TableInit = function () {
        var oTableInit = new Object();
        $('#table').bootstrapTable('destroy');
        //初始化Table
        oTableInit.Init = function () {
            $('#table').bootstrapTable({
                url: '/stock/listMaterielStock',     //请求后台的URL（*）
                method: 'get',           //请求方式（*）
                toolbar: '#toolbar',        //工具按钮用哪个容器
                dataType: "json",
                responseHandler:function (res) {
                    var resData = {
                        "rows": res.rows,
                        "total": res.page.count//总个数
                    }
                    return resData;
                },
                striped: true,           //是否显示行间隔色
                cache: false,            //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
                pagination: true,          //是否显示分页（*）
                sortable: false,           //是否启用排序
                sortOrder: "asc",          //排序方式
                queryParams: oTableInit.queryParams,//
                //传递参数（*）
                sidePagination: "server",      //分页方式：client客户端分页，server服务端分页（*）
                pageNumber: 1,            //初始化加载第一页，默认第一页
                pageSize: 10,            //每页的记录行数（*）
                pageList: [10, 20, 50, 100],    //可供选择的每页的行数（*）
                search: false,            //是否显示表格搜索，此搜索是客户端搜索，不会进服务端，所以，个人感觉意义不大
                strictSearch: true,
                showColumns: true,         //是否显示所有的列
                showRefresh: false,         //是否显示刷新按钮
                minimumCountColumns: 2,       //最少允许的列数
                clickToSelect: true,        //是否启用点击选中行
                uniqueId: "id",           //每一行的唯一标识，一般为主键列
                showToggle: false,          //是否显示详细视图和列表视图的切换按钮
                cardView: false,          //是否显示详细视图
                detailView: false,          //是否显示父子表
                showExport: true, //是否显示导出按钮
                buttonsAlign: "right", //按钮位置
                Icons: 'glyphicon-export icon-share',
                exportDataType: 'basic',
                showExport: true,  //是否显示导出按钮
                buttonsAlign:"right",  //按钮位置
                exportTypes:['excel'],  //导出文件类型
                Icons:'glyphicon-export',
                columns: [{
                    checkbox: true
                }, {
                    field: 'id',
                    title: '序号',
                    formatter: function (value, row, index) {
                        return index+1;
                    }
                }, {
                    field: 'materielNo',
                    title: '物料号',
                    align:'center'
                }, {
                    field: 'materielName',
                    title: '物料名称',
                    align:'center'
                }, {
                    field: 'stockNum',
                    title: '安全数量',
                    align:'center'
                }, {
                    field: 'total',
                    title: '库存总量',
                    formatter: function (value, row, index) {
                        var stockNum = row.stockNum;
                        if (value <= stockNum) {
                            return '<span class="glyphicon glyphicon-exclamation-sign" style="color:#c12e2a;">  '+ value +'</span>';
                        } else {
                            return '<span class="glyphicon glyphicon-ok-circle" style="color:green;">  '+value+'</span>';
                        }
                    },
                    align:'center'
                }]
            });
        };

        //得到查询的参数
        oTableInit.queryParams = function (params) {
            var temp = {   //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
                limit: params.limit,   //页面大小
                offset: params.offset,
                materielNo:$("#input").val(),
                materielName:$("#inputName").val()
            };
            return temp;
        };
        return oTableInit;
    };
</script>
</html>