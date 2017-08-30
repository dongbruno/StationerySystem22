define(['app'], function(app){
	app.controller('navigationBarCtrl', function($scope, $http){
		$scope.$root.user = {};
		$scope.$root.systeminfo = {};
		$scope.$root.number = $scope.$root.number || 0;
		$scope.$root.isDeadlineShow = false;
		$scope.$root.startStr = "";
		$scope.$root.endStr = "";
		Date.prototype.format = function(fmt) { 
		     var o = { 
		        "M+" : this.getMonth()+1,                 //月份 
		        "d+" : this.getDate(),                    //日 
		        "h+" : this.getHours(),                   //小时 
		        "m+" : this.getMinutes(),                 //分 
		        "s+" : this.getSeconds(),                 //秒 
		        "q+" : Math.floor((this.getMonth()+3)/3), //季度 
		        "S"  : this.getMilliseconds()             //毫秒 
		    }; 
		    if(/(y+)/.test(fmt)) {
		            fmt=fmt.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length)); 
		    }
		     for(var k in o) {
		        if(new RegExp("("+ k +")").test(fmt)){
		             fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length)));
		         }
		     }
		    return fmt; 
		}
		$scope.getSysteminfo = function(){
			$http.get("getSysteminfo", {
				 "Accept": "application/json;charset=utf-8",
				 "Accept-Charset": "charset=utf-8"
			 }).then(function(response){
				 $scope.$root.systeminfo = response.data;
				 var deadTime = new Date($scope.$root.systeminfo.deadline);
				 var startTime = new Date($scope.$root.systeminfo.startdate);
				 $scope.$root.startStr = startTime.format("yyyy-MM-dd hh:mm:ss");
				 $scope.$root.endStr = deadTime.format("yyyy-MM-dd hh:mm:ss");
				 var nowTime = new Date();
					if (deadTime > nowTime && nowTime > startTime ) {
						$scope.$root.isDeadlineShow = false;
						$scope.$root.isDeadline = false;
					} else {
						$scope.$root.isDeadlineShow = true;
						$scope.$root.isDeadline = true;
					}
			 }, function(err){
				 console.log(err);
			 });
		}
		$scope.getSysteminfo();
		$scope.defaultUser = function(){
			$http.get("getStaffTest?isDefault="+true, {
				 "Accept": "application/json;charset=utf-8",
				 "Accept-Charset": "charset=utf-8"
			 }).then(function(response){
				 $scope.$root.user = response.data;
			 }, function(err){
				 console.log(err);
			 });
		}
		$scope.defaultUser();
		
		$scope.changeUser = function(){
			$http.get("getStaffTest?isDefault="+false, {
				 "Accept": "application/json;charset=utf-8",
				 "Accept-Charset": "charset=utf-8"
			 }).then(function(response){
				 $scope.$root.user = response.data;
			 }, function(err){
				 console.log(err);
			 });
		}
		
//		$http.get("getStaff", {
//			 "Accept": "application/json;charset=utf-8",
//			 "Accept-Charset": "charset=utf-8"
//		 }).then(function(response){
//			 $scope.$root.user = response.data;
//		 }, function(err){
//			 console.log(err);
//		 });
		
		
		
	})
})