<?php

namespace App\Repositories\MySql;

use App\Models\Location;
use App\Repositories\LocationRepository;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\DB;
use Carbon\Carbon;
use Vinkla\Hashids\Facades\Hashids;

class MySqlLocationRepository implements LocationRepository
{
    public function get()
    {
        $locations = DB::select(
            'SELECT locations.* '.
                'FROM locations '.
            'INNER JOIN ('.
                'SELECT MAX(locations.created_at) AS time '.
                    'FROM locations '.
                'INNER JOIN buses '.
                    'ON locations.bus_plate_number = buses.plate_number '.
                    'WHERE buses.is_active = 1 '.
                'GROUP BY locations.bus_plate_number'.
            ') latest '.
                'ON locations.created_at = latest.time'
        );

        return $locations;
    }

    public function store(Request $request)
    {
        $currentTime = Carbon::now('Asia/Kuala_Lumpur');

        return Location::create([
            'id' => Hashids::encode($currentTime->timestamp),
            'route_name' => $request->input('route'),
            'lat' => $request->input('lat'),
            'lng' => $request->input('lng'),
            'driver_ic' => $request->input('driver'),
            'bus_plate_number' => $request->input('bus'),
            'created_at' => $currentTime,
            'updated_at' => $currentTime
        ]);
    }
}
