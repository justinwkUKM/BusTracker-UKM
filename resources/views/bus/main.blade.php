@extends('layouts.app')

@section('style')
    <link rel="stylesheet" type="text/css" href="{{ url('css/snackBar.css') }}" />
    <link rel="stylesheet" type="text/css" href="{{ url('css/bootstrap-dialog.min.css') }}" />
@endsection

@section('header')
    <title>Buses</title>
@endsection

@section('content')
    <div class="container">
        <h3>Bus</h3>
        <div class="row">
            <table class="table">
                <thead>
                    <tr>
                        <th></th>
                        <th>Plate Number</th>
                        <th>Color</th>
                        <th>Actions</th>
                    </tr>
                </thead>
                <tbody>
                    @foreach($buses as $bus)
                        <tr class="table-active">
                            <td><input id="{{ $bus->plate_number }}" type="checkbox" /></td>
                            <td>{{ $bus->plate_number }}</td>
                            <td>{{ $bus->color }}</td>
                            <td>
                                <i class="glyphicon glyphicon-pencil action-edit"
                                   id="{{ $bus-> plate_number }}"
                                   aria-hidden="true"
                                   style='cursor: pointer'>
                                </i>
                                <i class="glyphicon glyphicon-trash action-remove"
                                   id="{{ $bus-> plate_number }}"
                                   aria-hidden="true"
                                   style='cursor: pointer'>
                                </i>
                            </td>
                        </tr>
                    @endforeach
                </tbody>
            </table>
            <button type="submit" class="btn btn-primary" data-toggle="modal" data-target="#createBusModal">
                Create
            </button>
        </div>
    </div>

    <!-- The snackbar. -->
    <div id="snackBar"></div>

    <!-- Create bus modal starts. -->
    <div class="modal fade" id="createBusModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                    <h4 class="modal-title" id="myModalLabel">New Bus</h4>
                </div>
                <form class="form-horizontal" role="form">
                    <div class="modal-body">
                        <div class="form-group{{ $errors->has('plateNumber') ? ' has-error' : '' }}">
                            <label for="plateNumber" class="col-md-4 control-label">Plate Number</label>
                            <div class="col-md-6">
                                <input id="plateNumber" type="text" class="form-control" name="plateNumber" value="{{ old('plateNumber') }}">
                                @if ($errors->has('subject'))
                                    <span class="help-block">
                                          <strong>{{ $errors->first('subject') }}</strong>
                                      </span>
                                @endif
                            </div>
                        </div>
                        <div class="form-group{{ $errors->has('color') ? ' has-error' : '' }}">
                            <label for="color" class="col-md-4 control-label">Color</label>
                            <div class="col-md-6">
                                <input id="color" type="text" class="form-control" name="color" value="{{ old('color') }}">
                                @if ($errors->has('color'))
                                    <span class="help-block">
                                          <strong>{{ $errors->first('message') }}</strong>
                                      </span>
                                @endif
                            </div>
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button type="submit" class="btn btn-danger">
                            Create
                        </button>
                    </div>
                </form>
            </div>
        </div>
    </div>
    <!-- Create alert modal ends. -->
@endsection

@section('script')
    <script src="{{ url('js/buses/store.js') }}"></script>
    <script src="{{ url('js/buses/edit.js') }}"></script>
    <script src="{{ url('js/buses/remove.js') }}"></script>
    <script src="{{ url('js/bootstrap-dialog.min.js') }}"></script>
@endsection
