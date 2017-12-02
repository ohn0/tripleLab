
labApp.controller('mecha_detail_controller', function ($scope, $http, $routeParams, $location) {
    $http.get("webAPIs/checkStatus.jsp").then(
            function (response) {
                console.log("Status cehck success.");
                if (typeof response.data.email === "undefined") {
                    alert("you are not logged in.");
                    $location.url('htmlPartials/mechaList.html');
                }
                else {
                    console.log("You are logged in.");
                }
            },
            function (response) {
                console.log("Uh-oh");
            }
    );
    console.log("mecha_detail_controller");
    console.log($routeParams);
//    $scope.personId = $routeParams.id;
    var url = "webAPIs/mechaJson.jsp?id=" + $routeParams.id;
    $scope.newmecha = "";
    $http.get(url).then(
            function (response) { // this function will run if http.get success
                console.log("mecha Detail ajax success");
                console.log(response);
                console.log("");
                $scope.newmecha = response.data;
                console.log($scope.newmecha);
                $scope.errorMsg = "";
            },
            function (response) { // this function will run if http.get error
                console.log("mecha Detail ajax error");
                console.log(response);
                console.log("");
                $scope.errorMsg = "Error: " + response.status + " " + response.statusText;

            } // end of error fn
    ); // closes off "then" function call

});