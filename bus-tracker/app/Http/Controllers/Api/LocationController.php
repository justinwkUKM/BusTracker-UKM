<?php

namespace App\Http\Controllers\Api;

use App\Http\Controllers\ApiController;
use App\Repositories\LocationRepository;
use Cache;
use Illuminate\Http\Request;
use Illuminate\Http\Response;

class LocationController extends ApiController
{
    /**
     * @var \App\Repositories\LocationRepository
     */
    protected $location;

    /**
     * LocationController constructor.
     * @param $location
     */
    public function __construct(LocationRepository $location)
    {
        $this->location = $location;
    }

    /**
     * Display a listing of the resource.
     *
     * @return \Illuminate\Http\Response
     */
    public function index()
    {
        $locations = Cache::remember('latest-location', 5, function() {
            return $this->location->get();
        });

        return $locations;
    }

    /**
     * @param Request $request
     * @return response
     */
    public function store(Request $request)
    {
        return $this->location->store($request);
    }
}
