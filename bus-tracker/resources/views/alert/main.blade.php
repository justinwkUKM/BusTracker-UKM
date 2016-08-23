@extends('layouts.app')

@section('style')
    <link rel="stylesheet" type="text/css" href="{{ url('css/snackBar.css') }}" />
    <link rel="stylesheet" type="text/css" href="{{ url('css/bootstrap-dialog.min.css') }}" />
@endsection

@section('header')
    <title>Alerts</title>
@endsection

@section('content')
    <div class="container">
        <h3>Alerts</h3>
        <div class="row">
            <div class="col-md-5 input-group pull-right">
                <input id="search" type="text" class="form-control" placeholder="search">
                <div class="input-group-btn">
                    <button type="button" class="btn btn-default"><i class="glyphicon glyphicon-search"></i></button>
                    <button type="button" class="btn btn-default">All</button>
                    <button type="button" class="btn btn-default">Closed</button>
                    <button type="button" class="btn btn-default">Opened</button>
                    <button type="button" class="btn btn-default">
                        <i class="glyphicon glyphicon-chevron-down"></i>
                    </button>
                </div>
            </div>
        </div>
        <div class="row">
            <div class=".table-responsive">
                <table class="table">
                    <thead>
                        <tr>
                            <th>Creator</th>
                            <th>Subject</th>
                            <th>Status</th>
                            <th>Last updated</th>
                            <th>Created</th>
                        </tr>
                    </thead>
                    <tbody>
                    </tbody>
                </table>
            </div>
            <button class="btn btn-primary action-create">
                Create
            </button>
        </div>

        <!-- Modal -->
        <div class="modal fade" id="response-modal" role="dialog">
            <div class="modal-dialog">
                <!-- Modal content-->
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal">&times;</button>
                        <h4 class="modal-title">Modal Header</h4>
                    </div>
                    <div class="modal-body">
                        <div class="messages">
                            <!-- messages populated by jquery -->
                        </div>
                        <div class="input-group">
                            <input id="message" type="text" class="form-control" placeholder="Update alert...">
                            <span class="input-group-btn">
                                <button class="update btn btn-primary" type="button">Update</button>
                            </span>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
@endsection

@section('script')
    <script src="{{ url('js/moment.js') }}"></script>
    <script src="{{ url('js/firebase/firebase.js') }}"></script>
    <script src="{{ url('js/firebase/init.js') }}"></script>
    <script src="{{ url('js/bootstrap-dialog.min.js') }}"></script>
    <script src="{{ url('js/alerts/read.js') }}"></script>
    <script src="{{ url('js/alerts/response.js') }}"></script>
    <script src="{{ url('js/alerts/store.js') }}"></script>
@endsection