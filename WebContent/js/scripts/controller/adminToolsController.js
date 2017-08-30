define(['app'], function(app){
	app.controller('adminToolsCtrl', function($scope, $http){
		$scope.setStartDate = function(){
			var r = confirm("如果订购活动已经在进行中了，请慎重修改开始时间以避免生成报表出错。如果你要发起订购活动，请选择大于现在的时间作为开始时间。确定要设所选时间为开始时间吗？");
		    if (r == true) {
		    	$http.get("setStartDate?startDate="+startDateForm.date.value).then(function(response){
					alert(response.data["result"]);
					$scope.$root.startStr = startDateForm.date.value;
				}, function(err){
					alert(err);
				})
		    } 
		}
		$scope.setDeadline = function(){
			var r = confirm("如果订购活动已经在进行中了，请慎重修改结束时间以避免生成报表出错。如果你要发起订购活动，请选择大于现在的时间作为截止时间。确定要设所选时间为结束时间吗？");
		    if (r == true) {
			$http.get("setDeadline?deadline="+deadlineForm.date.value).then(function(response){
				alert(response.data["result"]);
				$scope.$root.endStr = deadlineForm.date.value;
			}, function(err){
				alert(err);
			})
		    }
		}
		$scope.setNote = function(){
			$http.get("setNote?note="+noteform.note.value).then(function(response){
				alert(response.data["result"]);
			}, function(err){
				alert(err);
			})
		}
		
		$scope.searchOrdersBySoeid = function(){
			$http.get("searchOrdersBySoeid?soeid="+soeidform.soeidForSerch.value.toUpperCase(),{
				"Accept": "application/json;charset=utf-8",
				"Accept-Charset": "charset=utf-8"
			}).then(function(response){
				$scope.ordersBySoeid = response.data;
			}, function(err){
				console.log(err);
			})
		}
		$http.get("getAllAdminUsers",{
			"Accept": "application/json;charset=utf-8",
			"Accept-Charset": "charset=utf-8"
		}).then(function(response){
			$scope.adminUsers = response.data;
		}, function(err){
			console.log(err);
		})
		$scope.addAdminBySoeid = function(){
			$http.get("addAdminBySoeid?soeid="+adminUsersForm.soeidForAdd.value.toUpperCase(),{
				"Accept": "application/json;charset=utf-8",
				"Accept-Charset": "charset=utf-8"
			}).then(function(response){
				$scope.adminUsers = response.data;
			}, function(err){
				console.log(err);
			})
		}
		$scope.deleteAdminBySoeid = function(soeid){
			$http.get("deleteAdminBySoeid?soeid="+soeid,{
				"Accept": "application/json;charset=utf-8",
				"Accept-Charset": "charset=utf-8"
			}).then(function(response){
				$scope.adminUsers = response.data;
			}, function(err){
				console.log(err);
			})
		}
		$scope.download = function(dlType, location){
			var url = "download/"+dlType+"/"+location;
			window.location.href = url;
		}
		
	})
})