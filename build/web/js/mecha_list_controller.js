labApp.controller('mecha_list_controller', function($scope, $http){
    
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


