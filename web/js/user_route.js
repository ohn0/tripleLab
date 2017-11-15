var labApp = angular.module('labApp', ['ngRoute']);

labApp.config(function ($routeProvider) {
    $routeProvider.when('/insertUser',{
        templateUrl: 'htmlPartials/user_insert.html',
        controller: 'user_insert_controller'
    }).when('/viewMecha',{
        templateUrl: 'htmlPartials/mechaList.html',
        controller: 'mecha_list_controller'
    }).when('/viewUsers',{
        templateUrl: 'htmlPartials/userList.html',
        controller: 'user_list_controller'
    }).when('/insertMecha', {
        templateUrl: 'htmlPartials/mecha_insert.html',
        controller: 'mecha_insert_controller'
    }).when('/update/:id', {
        templateUrl: 'htmlPartials/user_insert.html',
        controller: 'user_insert_controller'
    }).otherwise({
        redirectTo: '/viewMecha'
    });
});
