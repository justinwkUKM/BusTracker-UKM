<?php

use Illuminate\Database\Schema\Blueprint;
use Illuminate\Database\Migrations\Migration;

class CreateBusesTable extends Migration
{
    /**
     * Run the migrations.
     *
     * @return void
     */
    public function up()
    {
        Schema::create('buses', function (Blueprint $table) {
            $table->string('id');
            $table->string('plate_number');
            $table->string('color');
            $table->boolean('is_active');
            $table->timestamps(); // 'created_at' and 'updated_at' timestamps.

            $table->primary('id'); // Set 'id' as pk.
            $table->unique('plate_number'); // Set plate number as secondary pk.

            // Index 'is_active' to improve the read performance.
            $table->index('is_active');
        });
    }

    /**
     * Reverse the migrations.
     *
     * @return void
     */
    public function down()
    {
        Schema::dropIfExists('buses');
    }
}
