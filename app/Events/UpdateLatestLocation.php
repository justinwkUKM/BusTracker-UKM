<?php

namespace App\Events;

use App\Models\Location;
use Illuminate\Queue\SerializesModels;
use Illuminate\Contracts\Broadcasting\ShouldBroadcast;

class UpdateLatestLocation extends Event implements ShouldBroadcast
{
    use SerializesModels;

    public $location;

    /**
     * Create a new event instance.
     *
     * @param Location  $location
     */
    public function __construct(Location $location)
    {
        $this->location = $location;
    }

    /**
     * Get the channels the event should be broadcast on.
     *
     * @return array
     */
    public function broadcastOn()
    {
        return ['traffic-channel'];
    }
}
