<?php

namespace App\Transformers;

use Illuminate\Database\Eloquent\Collection;
use Illuminate\Database\Eloquent\Model;

abstract class Transformer
{
    /**
     * @param Collection $items
     * @return array
     */
    public function transformCollection($items)
    {
        return array_map([$this, 'transform'], $items->toArray());
    }

    /**
     * @param Model $item
     * @return array
     */
    public abstract function transform($item);
}