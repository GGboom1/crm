$(function () {
    $.ajax({
        method: "post",
        url: ctx+"/data_dic/queryConstitute",
        success:function (data) {
            dataMap = data;
            // 基于准备好的dom，初始化echarts实例
            var myChart = echarts.init(document.getElementById('main'));

            // 指定图表的配置项和数据
            var option = {
                title: {
                    text: '客户构成分析图'
                },
                tooltip: {},
                legend: {
                    data:['人数']
                },
                xAxis: {
                    data: dataMap.dataDicValue
                },
                yAxis: {},
                series: [{
                    name: '人数',
                    type: 'bar',
                    data: dataMap.total
                }]
            };

            // 使用刚指定的配置项和数据显示图表。
            myChart.setOption(option);
        }
    });

});