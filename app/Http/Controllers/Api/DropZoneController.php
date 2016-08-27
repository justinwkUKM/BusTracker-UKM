<?php

namespace App\Http\Controllers\Api;

use App\Http\Controllers\ApiController;
use App\Repositories\DropZoneRepository;
use App\Transformers\DropZoneTransformer;
use Illuminate\Http\Request;

class DropZoneController extends ApiController
{
    protected $dropZone;
    protected $dropZoneTransformer;

    /**
     * DropZoneController constructor.
     * @param \App\Repositories\DropZoneRepository  $dropZone
     * @param \App\Transformers\DropZoneTransformer  $dropZoneTransformer
     */
    public function __construct(DropZoneRepository $dropZone, DropZoneTransformer $dropZoneTransformer)
    {
        $this->dropZone = $dropZone;
        $this->dropZoneTransformer = $dropZoneTransformer;
    }

    /**
     * Display a listing of the resource.
     *
     * @return \Illuminate\Http\Response
     */
    public function index()
    {
        $this->dropZone->getAll();
    }

    /**
     * Store a newly created resource in storage.
     *
     * @param  \Illuminate\Http\Request  $request
     * @return \Illuminate\Http\Response
     */
    public function store(Request $request)
    {
        $this->dropZone->store($request);
    }

    /**
     * Display the specified resource.
     *
     * @param  int  $id
     * @return \Illuminate\Http\Response
     */
    public function show($id)
    {
        $this->dropZone->get($id);
    }

    /**
     * Update the specified resource in storage.
     *
     * @param  \Illuminate\Http\Request  $request
     * @param  int  $id
     * @return \Illuminate\Http\Response
     */
    public function update(Request $request, $id)
    {
        $this->dropZone->update($request, $id);
    }

    /**
     * Remove the specified resource from storage.
     *
     * @param  int  $id
     * @return \Illuminate\Http\Response
     */
    public function destroy($id)
    {
        $this->dropZone->destroy($id);
    }
}
