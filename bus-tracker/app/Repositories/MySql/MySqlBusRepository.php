<?php

namespace App\Repositories\MySql;

use Carbon\Carbon;
use Illuminate\Http\Request;
use App\Repositories\BusRepository;
use App\Models\Bus;
use Vinkla\Hashids\Facades\Hashids;

class MySqlBusRepository implements BusRepository
{
    public function getAll()
    {
        return Bus::all();
    }

    public function get($plate_number)
    {
        return Bus::find($plate_number);
    }

    /**
     * @param Request $request
     * @return mixed
     */
    public function getByStatus(Request $request)
    {
        return Bus::where('is_active', $request->inService)->get();
    }

    public function store(Request $request)
    {
        $currentTime = Carbon::now('Asia/Kuala_Lumpur');

        $bus = Bus::create([
            'id' => Hashids::encode($currentTime->timestamp),
            'plate_number' => $request->input('plateNumber'),
            'color' => $request->input('color'),
            'is_active' => true,
            'created_at' => $currentTime,
            'updated_at' => $currentTime
        ]);

        return $bus;
    }

    public function update(Request $request, $plate_number)
    {
        Bus::where('plate_number', $plate_number)
            ->update([
                'color' => $request->input('color'),
                'is_active' => $request->input('inService')
            ]);
    }

    public function remove($plate_number)
    {
        Bus::destroy($plate_number);
    }
}
