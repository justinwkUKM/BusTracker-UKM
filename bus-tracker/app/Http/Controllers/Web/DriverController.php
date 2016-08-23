<?php

namespace App\Http\Controllers\Web;

use App\Http\Controllers\Controller;
use App\Repositories\DriverRepository;

class DriverController extends Controller
{
    protected $driver;

    public function __construct(DriverRepository $driver)
    {
        $this->driver = $driver;
    }

    public function index()
    {
        $drivers = $this->driver->getAll();

        return view('driver/main', compact('drivers'));
    }

    public function show()
    {

    }

    public function store()
    {
    # code...
    }

    public function edit()
    {
    # code...
    }

    public function update()
    {
    # code...
    }

    public function remove()
    {
    # code...
    }
}
