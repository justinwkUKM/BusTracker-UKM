<?php

use Illuminate\Database\Seeder;
use App\Models\DropZone;

class DropZonesTableSeeder extends Seeder
{
    /**
     * Run the database seeds.
     *
     * @return void
     */
    public function run()
    {
        // Temporary disable mass assignment protection.
        DropZone::unguard();

        // Load json file.
        $json = File::get("database/data/DropZones.json");

        // Deserialize data into variable $data.
        $data = json_decode($json);

        // Insert data into database.
        foreach ($data as $obj) {
            DropZone::create([
                'name' => $obj->name,
                'description' => $obj->description,
                'lat' => $obj->lat,
                'lng' => $obj->lng
            ]);
        }
    }
}
