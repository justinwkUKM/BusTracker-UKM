<?php

namespace App\Providers;

use Illuminate\Support\ServiceProvider;

class RepositoryServiceProvider extends ServiceProvider
{
    /**
     * Register the application services.
     *
     * @return void
     */
    public function register()
    {
        $this->app->bind(
            'App\Repositories\BusRepository',
            'App\Repositories\MySql\MySqlBusRepository'
        );

        $this->app->bind(
            'App\Repositories\DeviceRepository',
            'App\Repositories\MySql\MySqlDeviceRepository'
        );

        $this->app->bind(
            'App\Repositories\DriverRepository',
            'App\Repositories\MySql\MySqlDriverRepository'
        );

        $this->app->bind(
            'App\Repositories\DropZoneRepository',
            'App\Repositories\MySql\MySqlDropZoneRepository'
        );

        $this->app->bind(
            'App\Repositories\LocationRepository',
            'App\Repositories\MySql\MySqlLocationRepository'
        );

        $this->app->bind(
            'App\Repositories\RoadRepository',
            'App\Repositories\MySql\MySqlRoadRepository'
        );

        $this->app->bind(
            'App\Repositories\RouteRepository',
            'App\Repositories\MySql\MySqlRouteRepository'
        );

        $this->app->bind(
            'App\Repositories\ScheduleRepository',
            'App\Repositories\MySql\MySqlScheduleRepository'
        );
    }
}
