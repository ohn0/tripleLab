
labApp.controller('user_update_controller', function ($scope, $http, $routeParams) {

    // these booleans control which Save button the user will see in the 
    // person_insert_update.html (partial html file). 
    $scope.isUpdateMode = true;
    $scope.isInsertMode = false;

    // this will be used to label the heading on the person_insert_update.html (partial html file).
    $scope.insertUpdate = "Update";

    console.log("user_update_controller");
    console.log($routeParams);
    $scope.personId = $routeParams.id;

    // blank out the new person in case the ajax get fails to populate newperson
    $scope.newperson = "";

    // blank out error message object
    $scope.myErrors = "";

    var url = "webAPIs/userJson.jsp?id=" + $routeParams.id;
    $http.get(url).then(
            function (response) { // this function will run if http.get success
                console.log("Person Update (get) ajax success");
                console.log(response);
                console.log("");
                $scope.newperson = response.data;
                console.log($scope.newperson);
                $scope.errorMsg = "";
            },
            function (response) { // this function will run if http.get error
                console.log("Person Update (get) ajax error");
                console.log(response);
                console.log("");
                $scope.errorMsg = "Error: " + response.status + " " + response.statusText;

            } // end of error fn
    ); // closes off "then" function call

    $scope.updateSave = function () {

        // empty out all the field level user error messages in case of an ajax error 
        $scope.myErrors = "";
        $scope.isUpdateMode = true;
        var myData = JSON.stringify($scope.newperson);
        myData = escape(myData);
        var url = "webAPIs/userUpdateAPI.jsp?jsonData=" + myData;

        $http.get(url).then(
                function (response) { // this function will run if http.get success
                    console.log("Person Update/Save ajax success");
                    console.log(response);
                    console.log("");
                    $scope.myErrors = response.data;
                    $scope.status = $scope.myErrors.errorMsg;
                    if ($scope.myErrors.errorMsg.length === 0) {
                        $scope.status = "Person Sucessfully Updated";
                    }
                },
                function (response) { // this function will run if http.get error
                    console.log("Person Update/Save ajax error");
                    console.log(response);
                    console.log("");
                    $scope.status = "Error: " + response.status + " " + response.statusText;

                } // end of error fn
        ); // closes off "then" function call
    };

});