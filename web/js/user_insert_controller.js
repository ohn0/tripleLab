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
    
//    $scope.newperson = {
//        id:'', email:'', password:'',nickname:'', rolename:''
//    };
    $scope.newperson = "";
    $scope.myErrors = "";
    $scope.isUpdateMode = false;
    $scope.isInsertMode = false;

    //Create a new person (this is the Insert/Save button)
    $scope.insertSave = function () {
        console.log("creating person");
        console.log($scope.newperson);
        $scope.isInsertMode = true;
        $scope.isUpdateMode = false;
        // empty out all the field level user error messages in case of an ajax error 
        $scope.myErrors = "";

        var myData = JSON.stringify($scope.newperson);
        myData = escape(myData);
        var url = "webAPIs/insertUserAPI.jsp?jsonData=" + myData;

        $http.get(url).then(
                function (response) { // this function will run if http.get success
                    console.log("Person Insert/Save ajax success");
                    console.log(response);
                    console.log("");
                    $scope.myErrors = response.data;
                    $scope.status = $scope.myErrors.errorMsg;
                    if ($scope.myErrors.errorMsg.length === 0) {
                        $scope.status = "Person Sucessfully Inserted";
                    }
                },
                function (response) { // this function will run if http.get error
                    console.log("Person Insert/Save ajax error");
                    console.log(response);
                    console.log("");
                    $scope.status = "Error: " + response.status + " " + response.statusText;

                } // end of error fn

        ); // closes off "then" function call

    };  // end of function insertSave
    
    $scope.updateSave = function() {
        // empty out all the field level user error messages in case of an ajax error 
        $scope.isUpdateMode = true;
        $scope.isInsertMode = false;
        $scope.myErrors = "";

        var myData = JSON.stringify($scope.newperson);
        myData = escape(myData);
        var url = "webAPIs/updateUserAPI.jsp?jsonData=" + myData;

        $http.get(url).then(
                function (response) { // this function will run if http.get success
                    console.log("User Update/Save ajax success");
                    console.log(response);
                    console.log("");
                    $scope.myErrors = response.data;
                    $scope.status = $scope.myErrors.errorMsg;
                    if ($scope.myErrors.errorMsg.length === 0) {
                        $scope.status = "User Sucessfully Updated";
                    }
                },
                function (response) { // this function will run if http.get error
                    console.log("User Update/Save ajax error");
                    console.log(response);
                    console.log("");
                    $scope.status = "Error: " + response.status + " " + response.statusText;

                } // end of error fn
        ); // closes off "then" function call
    };  
    

});  // end of insert controller