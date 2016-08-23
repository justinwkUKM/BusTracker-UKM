<?php

namespace App\Repositories;

use Illuminate\Http\Request;

interface RouteRepository
{
    /**
     * @return mixed
     */
    public function getAll();

    /**
     * @return mixed
     */
    public function get();

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
     * @param $id
     * @return mixed
     */
    public function remove($id);
}
