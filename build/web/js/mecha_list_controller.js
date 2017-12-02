labApp.controller('mecha_list_controller', function($scope, $http){
    $scope.isLoggedIn = false;
    $http.get("webAPIs/checkStatus.jsp").then(
        function(response){
            console.log("Status check success.");
            if(typeof response.data.email === "undefined"){
                console.log("You are not logged in.");
                $scope.isLoggedIn = false;
            }
            else{
                console.log("You are logged in.\n" + response.data.toString());
                $scope.isLoggedIn = true;
            }
        },
        function(response){
            console.log(response);
        }
    );

    $scope.erroMsg = "";
    $http.get("webAPIs/getMechaListAPI.jsp").then(
        function(response){
            console.log("Got mecha data");
            console.log(response);
            $scope.mechs = response.data.recordList;
            $scope.errorMsg = response.data.dbError;
        },
        function(response){
            console.log("Unable to get mech data.");
            console.log(response);
            $scope.errorMsg = "Error:" + response.status + " " + response.statusText;
        }
    );
});


