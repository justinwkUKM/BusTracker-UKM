<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Model;

class Route extends Model
{
    protected $table = 'routes';

    protected $fillable = [
        'id', 'name', 'description'
    ];

    // The primary key does not auto-increment.
    public $incrementing = false;

    // Timestamps are not concerned.
    public $timestamps = false;

    public function location()
    {
        return $this->hasOne('App\Model\Location', 'route_name', 'name');
    }
}
