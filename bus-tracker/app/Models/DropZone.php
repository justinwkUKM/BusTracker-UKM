<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Model;

class DropZone extends Model
{
    protected $table = 'drop_zones';

    protected $fillable = [
        'id', 'name', 'description', 'lat', 'lng'
    ];

    // Timestamps are not concerned.
    public $timestamps = false;
}
