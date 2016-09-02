<?php

namespace App\Repositories;

use Illuminate\Http\Request;

interface DropZoneRepository
{
    /**
     * @return mixed
     */
    public function getAll();

    /**
     * @param $id
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
     * @param $id
     * @return mixed
     */
    public function remove($id);
}
