<?php

namespace App\Repositories\MySql;

use App\Models\DropZone;
use App\Repositories\DropZoneRepository;
use Carbon\Carbon;
use Illuminate\Http\Request;
use Vinkla\Hashids\Facades\Hashids;

class MySqlDropZoneRepository implements DropZoneRepository
{
    public function getAll()
    {
        $dropZones = DropZone::all();

        return $dropZones;
    }

    /**
     * @param String $id
     * @return \App\Models\DropZone
     */
    public function get($id)
    {
        $dropZone = DropZone::find($id);

        return $dropZone;
    }

    /**
     * @param Request $request
     * @return \App\Models\DropZone
     */
    public function store(Request $request)
    {
        $currentTime = Carbon::now('Asia/Kuala_Lumpur');

        $dropZone = DropZone::create([
           'id' => Hashids::encode($currentTime->timestamp),
            'name' => $request->input('name'),
            'description' => $request->input('description')
        ]);

        return $dropZone;
    }

    /**
     * @param Request $request
     * @return \App\Models\DropZone
     */
    public function update(Request $request)
    {
        $dropZone = DropZone::where('id', $request->input('id'))
            ->update([
                'name' => $request->input('name'),
                'description' => $request->input('description')
            ]);

        return $dropZone;
    }

    /**
     * @param String $id
     * @return int
     */
    public function destroy($id)
    {
        $dropZone = DropZone::destroy($id);

        return $dropZone;
    }
}
