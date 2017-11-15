labApp.controller('user_insert_controller', function ($scope, $http) {
    console.log("user_insert_controller");
    console.log("??");
    // these booleans control which Save button the user will see in the 
    // person_insert_update.html (partial html file). 

    // this will be used to label the heading on the person_insert_update.html (partial html file).
    $scope.insertUser = "Insert";

    // When the user first clicks insert, they will see the person_insert_update.html partial 
    // and at that time, all the user data fields should have empty string (not undefined) 
    // and there is a second person object that holds all the field level error messages - 
    // clear all of those out too... 
    
    $scope.newuser = "";
    $scope.myErrors = "";

    //Create a new person (this is the Insert/Save button)
    $scope.insertSave = function () {
        console.log("creating Mecha");
        console.log($scope.newuser);

        // empty out all the field level user error messages in case of an ajax error 
        $scope.myErrors = "";

        var myData = JSON.stringify($scope.newuser);
        myData = escape(myData);
        var url = "webAPIs/insertUserAPI.jsp?jsonData=" + myData;

        $http.get(url).then(
                function (response) { // this function will run if http.get success
                    console.log("User Insert/Save ajax success");
                    console.log(response);
                    console.log("");
                    $scope.myErrors = response.data;
                    $scope.status = $scope.myErrors.errorMsg;
                    if ($scope.myErrors.errorMsg.length === 0) {
                        $scope.status = "User Sucessfully Inserted";
                    }
                },
                function (response) { // this function will run if http.get error
                    console.log("User Insert/Save ajax error");
                    console.log(response);
                    console.log("");
                    $scope.status = "Error: " + response.status + " " + response.statusText;

                } // end of error fn

        ); // closes off "then" function call

    };  // end of function insertSave

});  // end of insert controller