<?php

namespace App\Http\Controllers\Web;

use Illuminate\Http\Request;
use App\Http\Controllers\Controller;
use App\Repositories\RouteRepository;
use App\Models\Route;

class RouteController extends Controller
{
    protected $route;

    public function __construct(RouteRepository $route)
    {
        $this->route = $route;
    }

    public function index()
    {
        return view('route/main');
    }
}
