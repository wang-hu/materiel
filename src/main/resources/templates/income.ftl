<!DOCTYPE html>
<html lang=en>
<head>
    <meta charset=utf-8>
    <meta http-equiv=X-UA-Compatible content="IE=edge">
    <meta name=viewport content="width=device-width,initial-scale=1">
    <!--<link rel=icon href=favicon.ico>-->
    <title>来料管理</title>
    <link href="/css/bootstrap.min.css" rel="stylesheet">
    <link href="/css/bootstrap-table.min.css" rel="stylesheet">
    <link href="/css/bootstrap-theme.min.css" rel="stylesheet">
    <link href="/css/toastr.css" rel="stylesheet"/>
    <link href="/css/bootstrap-datetimepicker.min.css" rel="stylesheet"/>
    <link href="/css/select2.min.css" rel="stylesheet"/>
</head>
<body>


    <div class="table-box">
        <div id="toolbar">
            <div class="form-inline">
                <input type="text" class="form-control" placeholder="请输入物料号..." id="materielNo">
                开始时间:
                <div class='input-group date'>
                    <input type="text" id="startTime" class="form-control"/>
                    <span class="input-group-addon">
                        <span class="glyphicon glyphicon-calendar"></span>
                    </span>
                </div>
                结束时间:
                <div class='input-group date'>
                    <input type="text" id="endTime" class="form-control"/>
                    <span class="input-group-addon">
                        <span class="glyphicon glyphicon-calendar"></span>
                    </span>
                </div>
                <button type="button" class="btn btn-info" id="query">查询</button>
                <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#addModel">新增</button>
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
                        <label for="recipient-name" class="col-form-label">物料号:</label><br>
                        <select id="selectMateriel" class="form-control" style="width: 550px;" >
                            <option></option>
                        </select>
                    </div>
                    <div class="form-group">
                        <label for="message-text" class="col-form-label">来料数量:</label>
                        <input type="text" class="form-control" style="width: 550px;" id="incomeNum1" onkeyup="this.value=this.value.replace(/\D/g,'')">
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
</body>
<script src="/js/jquery-1.11.3.js"></script>
<script src="/js/common.js"></script>
<script src="/js/toastr.js"></script>
<script src="/js/bootstrap.min.js"></script>
<script src="/js/select2.full.min.js"></script>
<script src="/locale/zh-CN.js"></script>
<script src="/js/bootstrap-table.min.js"></script>
<script src="/js/bootstrap-table-zh-CN.min.js"></script>
<script src="/js/bootstrap-datetimepicker.min.js"></script>
<script src="/locale/bootstrap-datetimepicker.zh-CN.js"></script>

<script>
    $.fn.modal.Constructor.prototype.enforceFocus = function () {};

    $(function () {
        var oTable = new TableInit();
        oTable.Init();
    });

    $("#selectMateriel").select2({
        ajax: {
            url: '/stock/listMaterielNoAndName',
            language: 'zh-CN',
            placeholder: '请输入物料号或物料名称搜索...',
            allowClear: true,
            data: function (params) {
                return {
                    materielInfo: params.term
                };
            },
            dataType: 'json',
            processResults: function (data) {
                return {
                    results: data
                };
            },
            width:'100%',
            delay: 500,
            minimumInputLength:1,
            maximumInputLength:5
        }
    });

    var picker1 = $('#startTime').datetimepicker({
        format: 'yyyy-mm-dd hh:00:00',
        language:  'zh-CN',
        autoclose:true,
        todayBtn:true,
        minView:1
    });

    var picker2 = $('#endTime').datetimepicker({
        format: 'yyyy-mm-dd hh:00:00',
        language:  'zh-CN',
        autoclose:true,
        todayBtn:true,
        minView:1
    });

    picker1.on('changeDate', function (e) {
        $('#endTime').datetimepicker('setStartDate',e.date);
    });

    picker2.on('changeDate', function (e) {
        $('#startTime').datetimepicker('setEndDate',e.date);
    });

    $('#addModel').on('hide.bs.modal', function () {
        $("#selectMateriel").val("").trigger("change");
        $("#incomeNum1").val("");
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

            $.post("/income/remove",{"ids":ids},function (data) {
                if (data.success) {
                    toastr.success(data.responseMsg);
                    $("#table").bootstrapTable('refresh');
                } else {
                    toastr.error(data.responseMsg);
                }
            },"json")
        });

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
        var materielNo = $("#selectMateriel").val();
        if (materielNo == null || materielNo.trim() == '') {
            toastr.warning("物料号不能为空！");
            return;
        }
        
        var incomeNum = $("#incomeNum1").val();
        if (incomeNum == null || incomeNum.trim() == '') {
            toastr.warning("来料数量不能为空！");
            return;
        }

        $.post("/income/add",{"materielNo":materielNo,"incomeNum":incomeNum},function (data) {
            if (data.success) {
                toastr.success(data.responseMsg);
                $('#addModel').modal('hide');
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
                url: '/income/listIncomeMateriel',     //请求后台的URL（*）
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
                pageList: [10, 25, 50, 100],    //可供选择的每页的行数（*）
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
                    title: '物料号'
                }, {
                    field: 'materielName',
                    title: '物料名称'
                }, {
                    field: 'incomeNum',
                    title: '来料数量'
                }, {
                    field: 'incomeDate',
                    title: '来料时间'
                }]
            });
        };

        //得到查询的参数
        oTableInit.queryParams = function (params) {
            var temp = {   //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
                limit: params.limit,   //页面大小
                offset: params.offset,
                materielNo:$("#materielNo").val(),
                startTime:$("#startTime").val(),
                endTime:$("#endTime").val()
            };
            return temp;
        };
        return oTableInit;
    };


</script>
</html>