<?php

namespace App\Http\Controllers\Api;

use App\Http\Controllers\ApiController;
use App\Repositories\DriverRepository;
use Illuminate\Http\Request;

class DriverController extends ApiController
{
    /**
     * @var \App\Repositories\BusRepository
     */
    protected $driver;

    /**
     * DriverController constructor
     * @param \App\Repositories\DriverRepository $driver
     */
    public function __construct(DriverRepository $driver)
    {
        $this->driver = $driver;
    }

    /**
     * Display a listing of the resource.
     * @return \Illuminate\Http\Response
     */
    public function index()
    {
        $drivers = $this->driver->getAll();

        return $drivers;
    }

    /**
     * Store a newly created resource in storage.
     *
     * @param \Illuminate\Http\Request $request
     * @return \Illuminate\Http\Response
     */
    public function store(Request $request)
    {
        return $this->driver->store($request);
    }

}
