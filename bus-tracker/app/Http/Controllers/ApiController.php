<?php

namespace App\Http\Controllers;

use Illuminate\Http\Response;

class ApiController extends Controller
{
    protected $statusCode = 200;

    /**
     * @return int
     */
    public function getStatusCode()
    {
        return $this->statusCode;
    }

    /**
     * @param int $statusCode
     * @return $this
     */
    public function setStatusCode($statusCode)
    {
        $this->statusCode = $statusCode;

        return $this;
    }

    /**
     * @param $message
     * @param array $headers
     * @return \Illuminate\Http\Response
     */
    public function respond($message, $headers = [])
    {
        return response($message, $this->getStatusCode(), $headers);
    }

    /**
     * @param String $message
     * @return \Illuminate\Http\Response
     */
    public function respondWithMessage($message)
    {
        return $this->respond([
            'error' => [
                'message' => $message,
                'status' => $this->getStatusCode()
            ]
        ]);
    }

    /**
     * @param String $message
     * @return \Illuminate\Http\Response
     */
    public function respondNotFound($message)
    {
        // Set error 404 and respond.
        return $this->setStatusCode(Response::HTTP_NOT_FOUND)->respondWithMessage($message);
    }

}
