<?php

namespace App\Http\Controllers\Api;

use App\Http\Controllers\ApiController;
use App\Models\Bus;
use App\Repositories\BusRepository;
use App\Transformers\BusTransformer;
use Illuminate\Http\Response;
use Illuminate\Http\Request;

class BusController extends ApiController
{
    /**
     * @var \App\Repositories\BusRepository
     */
    protected $bus;

    /**
     * @var \App\Transformers\BusTransformer
     */
    protected $busTransformer;

    /**
     * BusController constructor
     * @param \App\Repositories\BusRepository $bus
     * @param \App\Transformers\BusTransformer $busTransformer
     */
    public function __construct(BusRepository $bus, BusTransformer $busTransformer)
    {
        $this->bus = $bus;
        $this->busTransformer = $busTransformer;
    }

    /**
     * Display a listing of the resource.
     * @param \Illuminate\Http\Request  request
     * @return \Illuminate\Http\Response
     */
    public function index(Request $request)
    {
        if ($request->has('inService')) {
            $buses = $this->bus->getByStatus($request);
        }
        else {
            $buses = $this->bus->getAll();
        }

        return response([
            'bus' => $this->busTransformer->transformCollection($buses)
        ], Response::HTTP_OK);
    }

    /**
     * Store a newly created resource in storage.
     *
     * @param \Illuminate\Http\Request $request
     * @return \Illuminate\Http\Response
     */
    public function store(Request $request)
    {
        return $this->bus->store($request);
    }

    /**
     * Display the specified resource.
     *
     * @param  String  $plate_number
     * @return \Illuminate\Http\Response
     */
    public function show($plate_number)
    {
        $bus = $this->bus->get($plate_number);

        if (! $bus) {
            return $this->respondNotFound("Bus not found.");
        }
        else {
            return response([
                'bus' => $this->busTransformer->transform($bus)
            ], Response::HTTP_OK);
        }
    }

    /**
     * Update the specified resource in storage.
     *
     * @param  \Illuminate\Http\Request  $request
     * @param  string  $plate_number
     * @return \Illuminate\Http\Response
     */
    public function update(Request $request, $plate_number)
    {
        return $this->bus->update($request, $plate_number);
    }

    /**
     * Remove the specified resource from storage.
     *
     * @param  String  $plate_number
     * @return int
     */
    public function destroy($plate_number)
    {
        return $this->bus->destroy($plate_number);
    }
}
