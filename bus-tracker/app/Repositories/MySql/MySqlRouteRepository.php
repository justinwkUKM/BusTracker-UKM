<?php

namespace App\Repositories\MySql;

use Carbon\Carbon;
use Illuminate\Http\Request;
use App\Repositories\RouteRepository;
use App\Models\Route;
use Vinkla\Hashids\Facades\Hashids;

class MySqlRouteRepository implements RouteRepository
{
    public function getAll()
    {
        // TODO: Implement getAll() method.
    }

    public function get()
    {
        // TODO: Implement get() method.
    }

    public function store(Request $request)
    {
        $currentTime = Carbon::now('Asia/Kuala_Lumpur');

        $route = Route::create([
            'id' => Hashids::encode($currentTime->timestamp),
            'name' => $request->input('name'),
            'description' => $request->input('description')
        ]);

        return $route;
    }

    public function update(Request $request)
    {
        // TODO: Implement update() method.
    }

    public function remove($id)
    {
        // TODO: Implement remove() method.
    }
}
