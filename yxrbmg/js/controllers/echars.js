/* Setup general page controller */
angular.module('MetronicApp').controller('echars', ['$rootScope', '$scope', 'settings', function($rootScope, $scope, settings) {
    $scope.$on('$viewContentLoaded', function() {
    	// initialize core components
    	App.initAjax();
		// set default layout mode
		// 初始化方法
    	$rootScope.settings.layout.pageContentWhite = true;
        $rootScope.settings.layout.pageBodySolid = false;
        $rootScope.settings.layout.pageSidebarClosed = false;
        $rootScope.settings.echarts;
        $rootScope.settings.echarts_all;
        
        /*
         × 个人签到区间统计，病状图
         */
        var echartsPie;
		var optionPie = {
			    title : {
			        text: '个人签到区间统计',
			        subtext: '',
			        x:'center'
			    },
			    tooltip : {
			        trigger: 'item',
			        formatter: "{a} <br/>{b} : {c} ({d}%)"
			    },
			    legend: {
			        orient : 'vertical',
			        x : 'left',
			        data:['优秀（100%-90%）','良好（89%-80%）','一般（79%-70%）','较差（ <70%）']
			    },
			    toolbox: {
			        show : true,
			        feature : {
			            mark : {show: true},
			            dataView : {show: true, readOnly: false},
			            magicType : {
			                show: true, 
			                type: ['pie', 'funnel'],
			                option: {
			                    funnel: {
			                        x: '25%',
			                        width: '50%',
			                        funnelAlign: 'left',
			                        max: 1548
			                    }
			                }
			            },
			            restore : {show: true},
			            saveAsImage : {show: true}
			        }
			    },
			    calculable : true,
			    series : [
			        {
			            name:'访问来源',
			            type:'pie',
			            radius : '55%',
			            center: ['50%', '60%'],
			            data:[ // 后台返回
			                {value:335, name:'优秀（100%-90%）'},
			                {value:310, name:'良好（89%-80%）'},
			                {value:234, name:'一般（79%-70%）'},
			                {value:50, name:'较差（ <70%）'}
			            ]
			        }
			    ]
			};
			// 实例化个人签到区间统计,饼状图
			echartsPie = echarts.init(document.getElementById('mainPie'));
			echartsPie.setOption(optionPie);
			
			/*
			 ×  签到统计，折线图
			 */
			var echartsLine1;
			var optionLine = {
				    legend: {
				        data:['报名情况']
				    },
				    toolbox: {
				        show : true,
				        feature : {
				            mark : {show: true},
				            dataView : {show: true, readOnly: false},
				            magicType : {show: true, type: ['line', 'bar']},
				            restore : {show: true},
				            saveAsImage : {show: true}
				        }
				    },
				    calculable : true,
				    tooltip : {
				        trigger: 'axis',
				        formatter: "Temperature : <br/>{b}km : {c}°C"
				    },
				    xAxis : [
				        {
				            type : 'value',
				            axisLabel : {
				                formatter: '{value} °C'
				            }
				        }
				    ],
				    yAxis : [
				        {
				            type : 'category',
				            axisLine : {onZero: false},
				            axisLabel : {
				                formatter: '{value} km'
				            },
				            boundaryGap : false,
				            data : ['0', '10', '20', '30', '40', '50', '60', '70', '80']
				        }
				    ],
				    series : [
				        {
				            name:'报名情况',
				            type:'line',
				            smooth:true,
				            itemStyle: {
				                normal: {
				                    lineStyle: {
				                        shadowColor : 'rgba(0,0,0,0.4)'
				                    }
				                }
				            },
				            data:[0, 15,20,30,60]
				        }
				    ]
				};
				
			// 实例化签到统计,饼状图
			echartsLine1 = echarts.init(document.getElementById('mainLine1'));
			echartsLine1.setOption(optionLine);
			
			/*
			 ×  报名情况，折线图
			 */
			var echartsLine2;
			var optionLine2 = option = {
				    title : {
				        text : '时间坐标折线图',
				        subtext : 'dataZoom支持'
				    },
				    tooltip : {
				        trigger: 'item',
				        formatter : function (params) {
				            var date = new Date(params.value[0]);
				            data = date.getFullYear() + '-'
				                   + (date.getMonth() + 1) + '-'
				                   + date.getDate() + ' '
				                   + date.getHours() + ':'
				                   + date.getMinutes();
				            return data + '<br/>'
				                   + params.value[1] + ', ' 
				                   + params.value[2];
				        }
				    },
				    toolbox: {
				        show : true,
				        feature : {
				            mark : {show: true},
				            dataView : {show: true, readOnly: false},
				            restore : {show: true},
				            saveAsImage : {show: true}
				        }
				    },
				    dataZoom: {
				        show: true,
				        start : 0
				    },
				    legend : {
				        data : ['series1']
				    },
				    grid: {
				        y2: 80
				    },
				    xAxis : [
				        {
				            type : 'time',
				            splitNumber:10
				        }
				    ],
				    yAxis : [
				        {
				            type : 'value'
				        }
				    ],
				    series : [
				        {
				            name: 'series1',
				            type: 'line',
				            showAllSymbol: true,
				            symbolSize: function (value){
				                return Math.round(value[2]/10) + 2;
				            },
				            data: (function () {
				                var d = [];
				                var len = 0;
				                var now = new Date();
				                var value;
				                while (len++ < 200) {
				                    d.push([
				                        new Date(2017,10, 1, 0, len * 1000),
				                        (Math.random()*7).toFixed(2) - 0,
				                        (Math.random()*5).toFixed(2) - 0
				                    ]);
				                }
				                return d;
				            })()
				        }
				    ]
				};
				
			// 实例化报名情况,折线图
			echartsLine2 = echarts.init(document.getElementById('mainLine2'));
			echartsLine2.setOption(optionLine2);
	    });
}]);
