<?php

namespace App\Repositories;

use Illuminate\Http\Request;

interface DriverRepository
{
    /**
     * @return mixed
     */
    public function getAll();

    /**
     * @param String $id
     * @return mixed
     */
    public function get($id);

    /**
     * @param Request $request
     * @return mixed
     */
    public function store(Request $request);

    /**
     * @param Request $request
     * @return mixed
     */
    public function update(Request $request);

    /**
     * @param String $id
     * @return mixed
     */
    public function remove($id);
}
