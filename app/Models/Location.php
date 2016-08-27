<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Model;

class Location extends Model
{
    protected $table = 'locations';

    protected $fillable = [
        'id', 'lat', 'route_name', 'lng', 'driver_ic', 'bus_plate_number', 'created_at', 'updated_at'
    ];

    // The primary key does not auto-increment.
    public $incrementing = false;

    public function bus()
    {
        return $this->belongsTo('App\Models\Bus', 'bus_plate_number');
    }

    public function driver()
    {
        return $this->belongsTo('App\Models\Driver', 'driver_ic');
    }

    public function route()
    {
        return $this->belongsTo('App\Models\Route', 'route_name');
    }
}
