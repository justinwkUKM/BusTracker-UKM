<?php

namespace App\Repositories;

use Illuminate\Http\Request;

interface BusRepository
{
    /**
     * @return mixed
     */
    public function getAll();

    /**
     * @param String $plate_number
     * @return \App\Models\Bus
     */
    public function get($plate_number);

    /**
     * @param Request $request
     * @return mixed
     */
    public function getByStatus(Request $request);

    /**
     * @param \Illuminate\Http\Request $request
     * @return mixed
     */
    public function store(Request $request);

    /**
     * @param \Illuminate\Http\Request $request
     * @param string  $plate_number
     * @return mixed
     */
    public function update(Request $request, $plate_number);

    /**
     * @param String $plate_number
     * @return mixed
     */
    public function remove($plate_number);
}
