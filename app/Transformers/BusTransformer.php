<?php

namespace App\Transformers;

class BusTransformer extends Transformer
{
    /**
     * @param \Illuminate\Database\Eloquent\Model $bus
     * @return array
     */
    public function transform($bus)
    {
        return [
            'plateNumber' => $bus['plate_number'],
            'color' => $bus['color'],
            'inService' => $bus['is_active']
        ];
    }

}