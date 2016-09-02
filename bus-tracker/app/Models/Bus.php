<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Model;

class Bus extends Model
{
    protected $table = 'buses';

    protected $fillable = [
        'id', 'plate_number', 'color', 'is_active', 'created_at', 'updated_at'
    ];

    // The primary key does not auto-increment.
    public $incrementing = false;

    protected $casts = [
        'is_active' => 'boolean'
    ];

    public function location()
    {
        return $this->hasOne('App\Model\Location', 'bus_plate_number', 'plate_number');
    }
}
