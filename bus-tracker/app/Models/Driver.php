<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Model;

class Driver extends Model
{
    protected $table = 'drivers';

    protected $fillable = [
        'id', 'ic', 'name'
    ];

    // The primary key does not auto-increment.
    public $incrementing = false;

    // Timestamps are not concerned.
    public $timestamps = false;

    /**
    * Get the location of driver when they are on duty.
    */
    public function position()
    {
        return $this->hasOne('App\Models\Location', 'driver_ic', 'ic');
    }
}
