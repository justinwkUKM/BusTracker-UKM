<?php

use Illuminate\Database\Schema\Blueprint;
use Illuminate\Database\Migrations\Migration;

class CreateLocationsTable extends Migration
{
    /**
     * Run the migrations.
     *
     * @return void
     */
    public function up()
    {
        Schema::create('locations', function (Blueprint $table) {
            $table->string('id');
            $table->string('route_name');
            $table->double('lat', 9, 6);
            $table->double('lng', 9, 6);
            $table->string('driver_ic');
            $table->string('bus_plate_number');
            $table->timestamps();

            // Set 'id' as primary key.
            $table->primary('id');

            // Set 'route_name' as fk references 'route'.
            $table->foreign('route_name')
                ->references('name')
                ->on('routes');

            // Set 'driver_ic' as fk references 'drivers'.
            $table->foreign('driver_ic')
                ->references('ic')
                ->on('drivers');

            // Set 'bus_plate_number' as fk references 'buses'.
            $table->foreign('bus_plate_number')
                ->references('plate_number')
                ->on('buses');
        });
    }

    /**
     * Reverse the migrations.
     *
     * @return void
     */
    public function down()
    {
        Schema::drop('locations');
    }
}
