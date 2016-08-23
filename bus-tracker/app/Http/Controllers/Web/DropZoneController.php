<?php

namespace App\Http\Controllers\Web;

use App\Http\Controllers\Controller;
use App\Repositories\DropZoneRepository;

class DropZoneController extends Controller
{
    protected $dropZone;

    public function __construt(DropZoneRepository $dropZone)
    {
        $this->dropZone = $dropZone;
    }

    public function index()
    {
        return view('dropzone/main');
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
