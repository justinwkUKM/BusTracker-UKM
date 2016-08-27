<?php

/*
|--------------------------------------------------------------------------
| Application Routes
|--------------------------------------------------------------------------
|
| Here is where you can register all of the routes for an application.
| It's a breeze. Simply tell Laravel the URIs it should respond to
| and give it the controller to call when that URI is requested.
|
*/

Route::get('/', 'Web\HomeController@index');

Route::auth();

// Route for alerts.
Route::get('alerts', 'Web\AlertController@index');

// Route for buses.
Route::get('buses', 'Web\BusController@index');

// Route for drivers.
Route::get('drivers', 'Web\DriverController@index');

// Route for dropzones.
Route::get('dropzones', 'Web\DropZoneController@index');

// Route for location records.
Route::get('locations', 'Web\LocationController@index');

// Route for reports.
Route::get('reports', 'Web\ReportController@index');

// Route for routes.
Route::get('routes', 'Web\RouteController@index');


// Api route
Route::group(['prefix' => 'api'], function () {
    // Route for alerts.
    Route::resource('alerts', 'Api\AlertController', ['except' => [
        'create', 'edit'
    ]]);

    // Route for buses.
    Route::resource('buses', 'Api\BusController', ['except' => [
        'create', 'edit'
    ]]);

    Route::post('drivers', 'Api\DriverController@store');

    // Route for dropzones.
    Route::resource('dropzones', 'Api\DropZoneController', ['except' => [
        'create', 'edit'
    ]]);

    // Route for location records.
    Route::resource('locations', 'Api\LocationController', ['except' => [
        'create', 'edit', 'show', 'update', 'destroy'
    ]]);

    // Route for routes.
    Route::resource('routes', 'Api\RouteController', ['except' => [
        'create', 'edit'
    ]]);
});