<?php

namespace App\Http\Controllers\Web;

use App\Http\Controllers\Controller;
use App\Repositories\BusRepository;
use App\Transformers\BusTransformer;
use Illuminate\Http\Request;

class BusController extends Controller
{
    protected $bus;

    public function __construct(BusRepository $bus)
    {
        $this->bus = $bus;
    }

    public function index(Request $request)
    {
        if ($request->has('inService')) {
            $buses = $this->bus->getByStatus($request);
        }
        else {
            $buses = $this->bus->getAll();
        }

        return view('bus/main', compact('buses'));
    }

    public function show($plate_number)
    {
        $bus = $this->bus->get($plate_number);

        return view('bus/display', compact('bus'));

    }

    public function edit($plate_number)
    {
        $bus = $this->bus->get($plate_number);

        return view('bus/edit', compact('bus'));
    }

    public function update(Request $request, $plate_number)
    {
        $this->bus->update($request, $plate_number);
    }

    public function remove($plate_number)
    {
        $this->bus->destroy($plate_number);
    }
}
