<?php

namespace App\Http\Controllers\Web;

use Illuminate\Http\Request;
use Carbon\Carbon;
use App\Http\Controllers\Controller;
use App\Repositories\LocationRepository;
use App\Models\Position;

class LocationController extends Controller
{
    protected $location;

    public function __construct(LocationRepository $location)
    {
        $this->location = $location;
    }

    public function index()
    {
        return view('location/main');
    }
}
