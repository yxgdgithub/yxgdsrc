angular.module('MetronicApp').controller('dashboard', function($rootScope, $scope, $http, $timeout,settings) {
    $scope.$on('$viewContentLoaded', function() {   
        // initialize core components
        App.initAjax();
    });

    // set sidebar closed and body solid layout mode
    $rootScope.settings.layout.pageContentWhite = true;
    $rootScope.settings.layout.pageBodySolid = false;
    $rootScope.settings.layout.pageSidebarClosed = false;
    $rootScope.settings.echarts;
    //$rootScope.settings.echarts_all;
	$rootScope.settings.knob;
	$rootScope.settings.throttle;
    $rootScope.settings.classycountdown;
	$rootScope.settings.classycount; 
    /*
	 * 个人签到区间统计，仪表盘
	 */
	 var echartsGauge;
	 var optionGauge = {
			    tooltip : {
			        formatter: "{a} <br/>{b} : {c}%"
			    },
			    toolbox: {
			        show : false,
			        feature : {
			            mark : {show: true},
			            restore : {show: true},
			            saveAsImage : {show: true}
			        }
			    },
			    series : [
			        {
			            name:'业务指标',
			            type:'gauge',
			            splitNumber: 10,       // 分割段数，默认为5
			            axisLine: {            // 坐标轴线
			                lineStyle: {       // 属性lineStyle控制线条样式
			                    color: [[0.2, '#228b22'],[0.8, '#48b'],[1, '#ff4500']], 
			                    width: 2
			                }
			            },
			            axisTick: {            // 坐标轴小标记
			                splitNumber: 10,   // 每份split细分多少段
			                length :11,        // 属性length控制线长
			                lineStyle: {       // 属性lineStyle控制线条样式
			                    color: 'auto'
			                }
			            },
			            axisLabel: {           // 坐标轴文本标签，详见axis.axisLabel
			                textStyle: {       // 其余属性默认使用全局文本样式，详见TEXTSTYLE
			                    color: 'auto'
			                }
			            },
			            splitLine: {           // 分隔线
			                show: true,        // 默认显示，属性show控制显示与否
			                length :11,         // 属性length控制线长
			                lineStyle: {       // 属性lineStyle（详见lineStyle）控制线条样式
			                    color: 'auto'
			                }
			            },
			            pointer : {
			                width : 5
			            },
			            title : {
			                show : true,
			                offsetCenter: [0, '-40%'],       // x, y，单位px
			                textStyle: {       // 其余属性默认使用全局文本样式，详见TEXTSTYLE
			                    fontWeight: 'bolder'
			                }
			            },
			            detail : {
			                formatter:'{value}%',
			                textStyle: {       // 其余属性默认使用全局文本样式，详见TEXTSTYLE
			                    color: 'auto',
			                    fontWeight: 'bolder'
			                }
			            },
			            data:[{value: 50, name: '签到率'}]
			        }
			    ]
			};
			// 实例化个人签到区间统计,仪表盘
			echartsGauge = echarts.init(document.getElementById('gauge1'));
			echartsGauge.setOption(optionGauge);
			
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
			
			function nameShow(){
				var userNm = localStorage.getItem("username") || "";
				if(userNm){
					$("#userNmShow").text(userNm);
				}
			}
			
			
			(function pageInit(){
				nameShow();
			})()
});