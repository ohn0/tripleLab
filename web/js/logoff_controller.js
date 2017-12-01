labApp.controller('logoff_controller', function ($scope, $http){
    console.log("logoff controller");
    $http.get("webAPIs/logoff.jsp").then(
        function(response){
            alert("You are logged off.");
        },
        function(response){
            console.log(response);
        }
        )
});