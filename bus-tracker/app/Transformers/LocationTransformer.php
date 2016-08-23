<?php

namespace App\Transformers;

use Illuminate\Database\Eloquent\Model;

class LocationTransformer extends Transformer
{
    /**
     * @param  Model $location
     * @return array
     */
    public function transform($location)
    {
        return [
            'lat' => $location['lat'],
            'lng' => $location['lng'],
            'driver' => $location['driver_ic'],
            'bus' => $location['bus_plate_number'],
            'time' => $location['created_at']
        ];
    }

}