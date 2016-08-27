@extends('layouts.app')

@section('header')
    <title>Drivers</title>
@endsection

@section('content')
    <div class="container">
        <h3>Driver</h3>
        <div class="row">
            <table class="table">
                <thead>
                <tr>
                    <th></th>
                    <th>IC</th>
                    <th>Name</th>
                    <th>Gender</th>
                    <th>Actions</th>
                </tr>
                </thead>
                <tbody>
                @foreach($drivers as $driver)
                    <tr class="table-active">
                        <td><input id="{{ $driver->ic }}" type="checkbox" /></td>
                        <td>{{ $driver->ic }}</td>
                        <td>{{ $driver->name }}</td>
                        <td>{{ $driver->gender }}</td>
                        <td>
                            <i class="glyphicon glyphicon-pencil action-edit"
                               id="{{ $driver->ic }}"
                               aria-hidden="true">
                            </i>
                            <i class="glyphicon glyphicon-trash action-remove"
                               id="{{ $driver->ic }}"
                               aria-hidden="true">
                            </i>
                        </td>
                    </tr>
                @endforeach
                </tbody>
            </table>
            <button type="submit" class="btn btn-primary" data-toggle="modal" data-target="#createDriverModal">
                Create
            </button>
        </div>
    </div>
@endsection

