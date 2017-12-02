
labApp.controller('mecha_update_controller', function ($scope, $http, $routeParams, $location) {
    $http.get("webAPIs/checkStatus.jsp").then(
            function (response) {
                console.log("Status cehck success.");
                if (typeof response.data.email === "undefined") {
                    alert("you are not logged in.");
                    $location.url('htmlPartials/mechaList.html');
                }
                else {
                    console.log("You are logged in.");
                    // these booleans control which Save button the user will see in the 
                    // person_insert_update.html (partial html file). 
                    $scope.isUpdateMode = true;
                    $scope.isInsertMode = false;

                    // this will be used to label the heading on the person_insert_update.html (partial html file).
                    $scope.insertUpdate = "Update";

                    console.log("mecha_update_controller");
                    console.log($routeParams);
                    $scope.personId = $routeParams.id;

                    // blank out the new person in case the ajax get fails to populate newperson
                    $scope.newmecha = "";

                    // blank out error message object
                    $scope.myErrors = "";

                    var url = "webAPIs/mechaJson.jsp?id=" + $routeParams.id;
                    $http.get(url).then(
                            function (response) { // this function will run if http.get success
                                console.log("Mecha Update (get) ajax success");
                                console.log(response);
                                console.log("");
                                $scope.newmecha = response.data;
                                console.log($scope.newmecha);
                                $scope.errorMsg = "";
                            },
                            function (response) { // this function will run if http.get error
                                console.log("Mecha Update (get) ajax error");
                                console.log(response);
                                console.log("");
                                $scope.errorMsg = "Error: " + response.status + " " + response.statusText;

                            } // end of error fn
                    ); // closes off "then" function call

                }
            },
            function (response) {
                console.log("Uh-oh");
            }
    );

    $scope.updateSave = function () {

        // empty out all the field level user error messages in case of an ajax error 
        $scope.myErrors = "";
        $scope.isUpdateMode = true;
        var myData = JSON.stringify($scope.newmecha);
        myData = escape(myData);
        var url = "webAPIs/mechaUpdateAPI.jsp?jsonData=" + myData;

        $http.get(url).then(
                function (response) { // this function will run if http.get success
                    console.log("Mecha Update/Save ajax success");
                    console.log(response);
                    console.log("");
                    $scope.myErrors = response.data;
                    $scope.status = $scope.myErrors.errorMsg;
                    if ($scope.myErrors.errorMsg.length === 0) {
                        $scope.status = "Mecha Sucessfully Updated";
                    }
                },
                function (response) { // this function will run if http.get error
                    console.log("Mecha Update/Save ajax error");
                    console.log(response);
                    console.log("");
                    $scope.status = "Error: " + response.status + " " + response.statusText;

                } // end of error fn
        ); // closes off "then" function call
    };

});