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
        controller: 'mecha_insert_controller',
    }).when('/update/:id', {
        templateUrl: 'htmlPartials/user_insert.html',
        controller: 'user_update_controller'
    }).when('/mechaUpdate/:id', {
        templateUrl: 'htmlPartials/mecha_insert.html',
        controller: 'mecha_update_controller'
    }).when('/mechaDelete/:id', {
        templateUrl: 'htmlPartials/mechaList.html',
        controller: 'mecha_delete_controller'
    }).when('/mechaDetail/:id', {
        templateUrl: 'htmlPartials/mecha_detail.html',
        controller: 'mecha_detail_controller'
    }).when('/logOn', {
        templateUrl: 'htmlPartials/logOn.html',
        controller: 'logon_controller'
    }).when('/logOff', {
        controller: 'logoff_ocntroller',
        redirectTo: '/viewMecha'
    }).otherwise({
        redirectTo: '/viewMecha'
    });
});
