layui.use(['jquery','form','table','layer','laydate'],function(){
    var $=layui.jquery;
    var form=layui.form;
    var table=layui.table;
    var layer=layui.layer;
    var laydate=layui.laydate;
    //��ʼ��ʱ��ѡ����
    laydate.render({
        elem:'#startTime',
        type:'datetime'
    });
    laydate.render({
        elem:'#endTime',
        type:'datetime'
    });

    //���� ����
    var tableIns=table.render({
        elem: '#logInfoTable'
        ,url:'/LogInfo/loadAllLogInfo'
        ,toolbar: '#logInfoToolBar' //����ͷ������������Ϊ������ģ��
        ,title: '�û���½��־���ݱ�'
        ,height:'full-220'
        ,page: true //������ҳ
        ,cols: [ [
            {type: 'checkbox', fixed: 'left'}
            ,{field:'id', title:'ID',align:'center'}
            ,{field:'loginName',title:'Name',align:'center'}
            ,{field:'loginIp',title:'IP',align:'center'}
            ,{field:'loginTime',title:'Time',align:'center'}
            ,{fixed: 'right', toolbar: '#logInfoRowBar',align:'center'}
        ] ]
    });

    //ģ����ѯ
    form.on("submit(doSearch)",function(data){
        tableIns.reload({
            where:data.field,
            page:{
                curr:1
            }
        });
        return false;
    });

    //�������������¼�
    table.on("toolbar(logInfoTable)",function(obj){
        switch(obj.event){
            case 'batchDelete':
                batchDelete();
                break;
        };
    });

    //�����й��������¼�
    table.on("tool(logInfoTable)",function(obj){
        var data = obj.data; //��õ�ǰ������
        switch(obj.event){
            case 'delete':
                deleteInfo(data);
                break;
        };
    });

    //ɾ��
    function deleteInfo(data){
        layer.confirm('��ȷ��Ҫɾ������������?', {icon: 3, title:'��ʾ'}, function(index){
            $.post("/loginfo/deleteLoginfo",{id:data.id},function(res){
                if(res.code==200){
                    tableIns.reload();
                }
                layer.msg(res.msg);
            })
            layer.close(index);
        });
    }

    //����ɾ��
    function  batchDelete(){
        //�õ�ѡ����
        var checkStatus = table.checkStatus('logInfoTable');
        var dataLength=checkStatus.data.length;
        if(dataLength>0){
            layer.confirm('��ȷ��Ҫɾ����Щ������?', {icon: 3, title:'��ʾ'}, function(index){
                var data=checkStatus.data; //��ȡѡ���е�����
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
            layer.msg("��ѡ�в�����")
        }
    }
});