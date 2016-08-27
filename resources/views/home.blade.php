@extends('layouts.app')

@section('header')
    <title>Dashboard</title>
@endsection

@section('content')
    <div class="container">
        <a href="{{ url('alerts') }}">Alerts</a><br>
        <a href="{{ url('buses') }}">Buses</a><br>
        <a href="{{ url('drivers') }}">Drivers</a><br>
        <a href="{{ url('reports') }}">Reports</a><br>
    </div>
@endsection
