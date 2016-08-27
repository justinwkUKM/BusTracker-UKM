<?php

namespace App\Repositories\MySql;

use Carbon\Carbon;
use Illuminate\Http\Request;
use App\Repositories\RouteRepository;
use App\Models\Route;
use Vinkla\Hashids\Facades\Hashids;

class MySqlRouteRepository implements RouteRepository
{
    /**
     * @return \Illuminate\Database\Eloquent\Collection|static[]
     */
    public function getAll()
    {
        return Route::all();
    }

    /**
     * @param String $id
     * @return \App\Models\Route
     */
    public function get($id)
    {
        $route = Route::find($id);

        return $route;
    }

    /**
     * @param Request $request
     * @return \App\Models\Route
     */
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

    /**
     * @param Request $request
     * @return \App\Models\Route
     */
    public function update(Request $request)
    {
        $route = Route::where('id', $request->input('id'))
            ->update([
                'name' => $request->input('name'),
                'description' => $request->input('description')
            ]);

        return $route;
    }

    /**
     * @param String $id
     * @return int
     */
    public function destroy($id)
    {
        $route = Route::destroy($id);

        return $route;
    }
}
