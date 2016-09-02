<?php

namespace app\Http\Controllers\Web;

use App\Http\Controllers\Controller;

class ReportController extends Controller
{
    public function index()
    {
        return view('report/main');
    }
}