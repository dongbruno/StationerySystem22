define(['app'], function(app){
	app.controller('navigationBarCtrl', function($scope, $http){
		$scope.$root.user = {};
		$scope.$root.systeminfo = {};
		$scope.$root.number = $scope.$root.number || 0;
		$scope.$root.isDeadlineShow = false;
		$scope.getSysteminfo = function(){
			$http.get("getSysteminfo", {
				 "Accept": "application/json;charset=utf-8",
				 "Accept-Charset": "charset=utf-8"
			 }).then(function(response){
				 $scope.$root.systeminfo = response.data;
				 var deadTime = new Date($scope.$root.systeminfo.deadline);
				 var nowTime = new Date();
					if (deadTime < nowTime ) {
						$scope.$root.isDeadlineShow = true;
					} else {
							$scope.$root.isDeadlineShow = false;
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