$(function () {
   $.ajax({
       method: "post",
       url: ctx+"/customer_serve/queryServe",
       success: function (data) {
           // 基于准备好的dom，初始化echarts实例
           var myChart = echarts.init(document.getElementById('main'));

           var option = {
               tooltip: {
                   trigger: 'item',
                   formatter: "{a} <br/>{b}: {c} ({d}%)"
               },
               legend: {
                   orient: 'vertical',
                   x: 'left',
                   data:data.title
               },
               series: [
                   {
                       name:'访问来源',
                       type:'pie',
                       radius: ['50%', '70%'],
                       avoidLabelOverlap: false,
                       label: {
                           normal: {
                               show: false,
                               position: 'center'
                           },
                           emphasis: {
                               show: true,
                               textStyle: {
                                   fontSize: '30',
                                   fontWeight: 'bold'
                               }
                           }
                       },
                       labelLine: {
                           normal: {
                               show: false
                           }
                       },
                       data:data.map
                   }
               ]
           };
           // 使用刚指定的配置项和数据显示图表。
           myChart.setOption(option);
       }
   })
});