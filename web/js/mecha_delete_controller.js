labApp.controller('mecha_delete_controller', function($scope, $http, $routeParams){
        function deletePerson(id) {
            var url = "webAPIs/deleteMecha.jsp?id=" + id;
            console.log("url to invoke: " + url);
            $scope.deleteMsg = "";

            $http.get(url).then(
                    function (response) { // this function will run if http.get success
                        console.log("Mecha Delete ajax success");
                        console.log(response);
                        console.log("");
                        $scope.deleteMsg = response.data.errorMsg;
                        if ($scope.deleteMsg.length === 0) {
                            $scope.deleteMsg = "Sucessfully deleted mecha with ID " + id;
                        } else {
                            $scope.deleteMsg = "Delete Error: " + $scope.deleteMsg;
                        }
                    },
                    function (response) { // this function will run if http.get error
                        console.log("Mecha delete ajax error");
                        console.log(response);
                        console.log("");
                        $scope.deleteMsg = "Delete Error: " + response.status + " " + response.statusText;

                    } // end of error fn
            ); // closes off "then" function call
        } // deletePerson

    console.log("mecha delete controller");
    console.log($routeParams);
    if ($routeParams.id) {
        console.log("First I will delete the mecha with ID " + $routeParams.id);
        deletePerson($routeParams.id);
    } else {
        console.log("Listing mecha without deleting first.");
    }
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



