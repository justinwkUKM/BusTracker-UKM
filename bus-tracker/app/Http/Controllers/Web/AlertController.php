<?php

namespace app\Http\Controllers\Web;

use App\Http\Controllers\Controller;

class AlertController extends Controller
{
    public function index()
    {
        return view('alert/main');
    }
}