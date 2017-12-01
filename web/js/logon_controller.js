labApp.controller('logon_controller', function ($scope, $http) {
    console.log("logon_controller");
    $scope.loginInfo = "";
    $scope.loginResult = "Enter login info";
    $scope.loggedIn = false;
    $scope.login = function () {
        console.log("logging in with following credentials: " +
                $scope.loginInfo.email + ' ' + $scope.loginInfo.password);

        var url = "webAPIs/logon.jsp?&email=" + $scope.loginInfo.email + "&password=" +
                $scope.loginInfo.password;
        $http.get(url).then(
                function (response) {
                    console.log("login success");
                    console.log(response);
                    if (response.data.errorMsg === "") {
                        $scope.loginResult = "Login successful, welcome " +
                                response.data.nickname;
                        $scope.loggedIn = true;
                    } else {
                        $scope.loginResult = "Login unsuccessful, incorrect username/password."
                    }
                },
                function (response) {
                    console.log("login failed");
                }
        );
    };
});