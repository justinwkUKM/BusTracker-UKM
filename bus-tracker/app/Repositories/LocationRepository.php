<?php

namespace App\Repositories;

use Illuminate\Http\Request;

interface LocationRepository
{
    /**
     * @return mixed
     */
    public function get();

    /**
     * @param Request $request
     * @return mixed
     */
    public function store(Request $request);

}
