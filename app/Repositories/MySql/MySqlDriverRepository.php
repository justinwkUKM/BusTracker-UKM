<?php

namespace App\Repositories\MySql;

use Carbon\Carbon;
use Illuminate\Http\Request;
use App\Repositories\DriverRepository;
use App\Models\Driver;
use Vinkla\Hashids\Facades\Hashids;

class MySqlDriverRepository implements DriverRepository
{
    public function getAll()
    {
        return Driver::all();
    }

    public function get($id)
    {
        return Driver::find($id);
    }

    public function store(Request $request)
    {
        $currentTime = Carbon::now('Asia/Kuala_Lumpur');

        return Driver::create([
            'id' => Hashids::encode($currentTime->timestamp),
            'ic' => $request->input('ic'),
            'name' => $request->input('name')
        ]);
    }

    public function update(Request $request)
    {
        return Driver::where('ic', $request->input('ic'))
            ->update([
                'name' => $request->input('name')
            ]);
    }

    /**
     * @param String $ic
     * @return int
     */
    public function destroy($ic)
    {
        return Driver::destroy($ic);
    }
}
