layui.use(['jquery','form','table','layer','laydate'],function(){
    var $=layui.jquery;
    var form=layui.form;
    var table=layui.table;
    var layer=layui.layer;
    var laydate=layui.laydate;
    //初始化时间选择器
    laydate.render({
        elem:'#startTime',
        type:'datetime'
    });
    laydate.render({
        elem:'#endTime',
        type:'datetime'
    });

    //加载 数据
    var tableIns=table.render({
        elem: '#logInfoTable'
        ,url:'/LogInfo/loadAllLogInfo'
        ,toolbar: '#logInfoToolBar' //开启头部工具栏，并为其绑定左侧模板
        ,title: '用户登陆日志数据表'
        ,height:'full-220'
        ,page: true //开启分页
        ,cols: [ [
            {type: 'checkbox', fixed: 'left'}
            ,{field:'id', title:'ID',align:'center'}
            ,{field:'loginName',title:'Name',align:'center'}
            ,{field:'loginIp',title:'IP',align:'center'}
            ,{field:'loginTime',title:'Time',align:'center'}
            ,{fixed: 'right', toolbar: '#logInfoRowBar',align:'center'}
        ] ]
    });

    //模糊查询
    form.on("submit(doSearch)",function(data){
        tableIns.reload({
            where:data.field,
            page:{
                curr:1
            }
        });
        return false;
    });

    //监听工具条的事件
    table.on("toolbar(logInfoTable)",function(obj){
        switch(obj.event){
            case 'batchDelete':
                batchDelete();
                break;
        };
    });

    //监听行工具条的事件
    table.on("tool(logInfoTable)",function(obj){
        var data = obj.data; //获得当前行数据
        switch(obj.event){
            case 'delete':
                deleteInfo(data);
                break;
        };
    });

    //删除
    function deleteInfo(data){
        layer.confirm('你确定要删除这条数据吗?', {icon: 3, title:'提示'}, function(index){
            $.post("/loginfo/deleteLoginfo",{id:data.id},function(res){
                if(res.code==200){
                    tableIns.reload();
                }
                layer.msg(res.msg);
            })
            layer.close(index);
        });
    }

    //批量删除
    function  batchDelete(){
        //得到选中行
        var checkStatus = table.checkStatus('logInfoTable');
        var dataLength=checkStatus.data.length;
        if(dataLength>0){
            layer.confirm('你确定要删除这些数据吗?', {icon: 3, title:'提示'}, function(index){
                var data=checkStatus.data; //获取选中行的数据
                var ids="";
                $.each(data,function(index,item){
                    if(index==0){
                        ids+="ids="+item.id;
                    }else{
                        ids+="&ids="+item.id;
                    }
                })
                $.post("/loginfo/batchDeleteLoginfo",ids,function(res){
                    if(res.code==200){
                        tableIns.reload();
                    }
                    layer.msg(res.msg);
                })
                layer.close(index);
            });

        }else{
            layer.msg("请选中操作行")
        }
    }
});