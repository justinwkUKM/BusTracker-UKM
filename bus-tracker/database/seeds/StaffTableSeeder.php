<?php

use Illuminate\Database\Seeder;
use App\Models\Staff;

class StaffTableSeeder extends Seeder
{
    /**
     * Run the database seeds.
     *
     * @return void
     */
    public function run()
    {
        // Temporary disable mass assignment protection.
        Staff::unguard();

        // Load json file.
        $json = File::get("database/data/Staff.json");

        // Deserialize data into variable $data.
        $data = json_decode($json);

        // Insert data into database.
        foreach ($data as $obj) {
            Staff::create([
                'name' => $obj->name,
                'email' => $obj->email,
                'password' => $obj->password,
                'remember_token' => $obj->remember_token,
                'created_at' => $obj->created_at,
                'updated_at' => $obj->updated_at
            ]);
        }
    }
}
