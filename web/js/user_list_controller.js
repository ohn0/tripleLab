labApp.controller('user_list_controller', function($scope, $http){
    
    $scope.erroMsg = "";
    $http.get("webAPIs/getUserListAPI.jsp").then(
        function(response){
            console.log("Got user data");
            console.log(response);
            $scope.users = response.data.userList;
            $scope.errorMsg = response.data.dbError;
        },
        function(response){
            console.log("Unable to get user data.");
            console.log(response);
            $scope.errorMsg = "Error:" + response.status + " " + response.statusText;
        }
    );
});


