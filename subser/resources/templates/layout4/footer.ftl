<script src="${request.contextPath}/static/global/plugins/jquery.min.js" type="text/javascript"></script>
<script src="${request.contextPath}/static/global/plugins/bootstrap/js/bootstrap.min.js" type="text/javascript"></script>	
<script src="${request.contextPath}/static/global/plugins/bootstrap-hover-dropdown/bootstrap-hover-dropdown.min.js" type="text/javascript"></script>	
<script src="${request.contextPath}/static/global/plugins/jquery-slimscroll/jquery.slimscroll.min.js" type="text/javascript"></script>
<script src="${request.contextPath}/static/global/scripts/metronic.js" type="text/javascript"></script>
<script src="${request.contextPath}/static/layout4/scripts/layout.js" type="text/javascript"></script>
<script>
	jQuery(document).ready(function() {    
		Metronic.init(); // init metronic core componets
		Layout.init(); // init layout
		Demo.init(); // init demo features 
		Index.init(); // init index page
		Tasks.initDashboardWidget(); // init tash dashboard widget  
	});
</script>