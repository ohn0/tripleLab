labApp.controller('mecha_insert_controller', function ($scope, $http) {
    $scope.isLoggedIn = false;
    $http.get("webAPIs/checkStatus.jsp").then(
        function(response){
            console.log("Status check success.");
            if(typeof response.data.email === "undefined"){
                console.log("You are not logged in.");
                $scope.isLoggedIn = false;
                $scope.status = "You are not logged in, you will not be able to\n\
                                modify the database.";
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
    console.log("mecha_insert_controller");
    console.log("??");
    // these booleans control which Save button the user will see in the 
    // person_insert_update.html (partial html file). 

    // this will be used to label the heading on the person_insert_update.html (partial html file).
    $scope.insertMecha = "Insert";

    // When the user first clicks insert, they will see the person_insert_update.html partial 
    // and at that time, all the user data fields should have empty string (not undefined) 
    // and there is a second person object that holds all the field level error messages - 
    // clear all of those out too... 
    
    $scope.newmecha = "";
    $scope.myErrors = "";

    //Create a new person (this is the Insert/Save button)
    $scope.insertSave = function () {
        
        if(!$scope.isLoggedIn){
            $scope.status = "Please log in to modify the database.";
            return ;
        }
        console.log("creating mecha");
        console.log($scope.newmecha);

        // empty out all the field level user error messages in case of an ajax error 
        $scope.myErrors = "";

        var myData = JSON.stringify($scope.newmecha);
        myData = escape(myData);
        var url = "webAPIs/insertMechaAPI.jsp?jsonData=" + myData;

        $http.get(url).then(
                function (response) { // this function will run if http.get success
                    console.log("Mecha Insert/Save ajax success");
                    console.log(response);
                    console.log("");
                    $scope.myErrors = response.data;
                    $scope.status = $scope.myErrors.errorMsg;
                    if ($scope.myErrors.errorMsg.length === 0) {
                        $scope.status = "Mecha Sucessfully Inserted";
                    }
                },
                function (response) { // this function will run if http.get error
                    console.log("Mecha Insert/Save ajax error");
                    console.log(response);
                    console.log("");
                    $scope.status = "Error: " + response.status + " " + response.statusText;

                } // end of error fn

        ); // closes off "then" function call

    };  // end of function insertSave

});  // end of insert controller