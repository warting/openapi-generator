<?php
/**
 * PetApiInterface
 *
 * PHP version 8.1.1
 *
 * @category Class
 * @package  OpenAPI\Server
 * @author   OpenAPI Generator team
 * @link     https://github.com/openapitools/openapi-generator
 */

/**
 * OpenAPI Petstore
 *
 * This is a sample server Petstore server. For this sample, you can use the api key `special-key` to test the authorization filters.
 *
 * The version of the OpenAPI document: 1.0.0
 * 
 * Generated by: https://github.com/openapitools/openapi-generator.git
 *
 */

/**
 * NOTE: This class is auto generated by the openapi generator program.
 * https://github.com/openapitools/openapi-generator
 * Do not edit the class manually.
 */

namespace OpenAPI\Server\Api;

use Symfony\Component\HttpFoundation\File\UploadedFile;
use OpenAPI\Server\Model\ApiResponse;
use OpenAPI\Server\Model\Pet;

/**
 * PetApiInterface Interface Doc Comment
 *
 * @category Interface
 * @package  OpenAPI\Server\Api
 * @author   OpenAPI Generator team
 * @link     https://github.com/openapitools/openapi-generator
 */
interface PetApiInterface
{

    /**
     * Sets authentication method petstore_auth
     *
     * @param string|null $value Value of the petstore_auth authentication method.
     *
     * @return void
     */
    public function setpetstore_auth(?string $value): void;

    /**
     * Sets authentication method api_key
     *
     * @param string|null $value Value of the api_key authentication method.
     *
     * @return void
     */
    public function setapi_key(?string $value): void;

    /**
     * Operation addPet
     *
     * Add a new pet to the store
     *
     * @param  Pet $pet  Pet object that needs to be added to the store (required)
     * @param  int     &$responseCode    The HTTP Response Code
     * @param  array   $responseHeaders  Additional HTTP headers to return with the response ()
     *
     * @return array|object|null
     */
    public function addPet(
        Pet $pet,
        int &$responseCode,
        array &$responseHeaders
    ): array|object|null;

    /**
     * Operation deletePet
     *
     * Deletes a pet
     *
     * @param  int $petId  Pet id to delete (required)
     * @param  string|null $apiKey   (optional)
     * @param  int     &$responseCode    The HTTP Response Code
     * @param  array   $responseHeaders  Additional HTTP headers to return with the response ()
     *
     * @return void
     */
    public function deletePet(
        int $petId,
        ?string $apiKey,
        int &$responseCode,
        array &$responseHeaders
    ): void;

    /**
     * Operation downloadFile
     *
     * downloads an image
     *
     * @param  int $petId  ID of pet to download an image from (required)
     * @param  int     &$responseCode    The HTTP Response Code
     * @param  array   $responseHeaders  Additional HTTP headers to return with the response ()
     *
     * @return mixed
     */
    public function downloadFile(
        int $petId,
        int &$responseCode,
        array &$responseHeaders
    ): mixed;

    /**
     * Operation findPetsByStatus
     *
     * Finds Pets by status
     *
     * @param  array $status  Status values that need to be considered for filter (required) (deprecated)
     * @param  int     &$responseCode    The HTTP Response Code
     * @param  array   $responseHeaders  Additional HTTP headers to return with the response ()
     *
     * @return array|object|null
     */
    public function findPetsByStatus(
        array $status,
        int &$responseCode,
        array &$responseHeaders
    ): array|object|null;

    /**
     * Operation findPetsByTags
     *
     * Finds Pets by tags
     *
     * @param  array $tags  Tags to filter by (required)
     * @param  int     &$responseCode    The HTTP Response Code
     * @param  array   $responseHeaders  Additional HTTP headers to return with the response ()
     *
     * @return array|object|null
     * @deprecated
     */
    public function findPetsByTags(
        array $tags,
        int &$responseCode,
        array &$responseHeaders
    ): array|object|null;

    /**
     * Operation getPetById
     *
     * Find pet by ID
     *
     * @param  int $petId  ID of pet to return (required)
     * @param  int     &$responseCode    The HTTP Response Code
     * @param  array   $responseHeaders  Additional HTTP headers to return with the response ()
     *
     * @return array|object|null
     */
    public function getPetById(
        int $petId,
        int &$responseCode,
        array &$responseHeaders
    ): array|object|null;

    /**
     * Operation petAge
     *
     * Get the age of the pet
     *
     * @param  int $petId  ID of pet (required)
     * @param  int     &$responseCode    The HTTP Response Code
     * @param  array   $responseHeaders  Additional HTTP headers to return with the response ()
     *
     * @return int
     */
    public function petAge(
        int $petId,
        int &$responseCode,
        array &$responseHeaders
    ): int;

    /**
     * Operation petAvailableForSale
     *
     * Whether the pet can currently be bought
     *
     * @param  int $petId  ID of pet (required)
     * @param  int     &$responseCode    The HTTP Response Code
     * @param  array   $responseHeaders  Additional HTTP headers to return with the response ()
     *
     * @return bool
     */
    public function petAvailableForSale(
        int $petId,
        int &$responseCode,
        array &$responseHeaders
    ): bool;

    /**
     * Operation updatePet
     *
     * Update an existing pet
     *
     * @param  Pet $pet  Pet object that needs to be added to the store (required)
     * @param  int     &$responseCode    The HTTP Response Code
     * @param  array   $responseHeaders  Additional HTTP headers to return with the response ()
     *
     * @return array|object|null
     */
    public function updatePet(
        Pet $pet,
        int &$responseCode,
        array &$responseHeaders
    ): array|object|null;

    /**
     * Operation updatePetWithForm
     *
     * Updates a pet in the store with form data
     *
     * @param  int $petId  ID of pet that needs to be updated (required)
     * @param  string|null $name  Updated name of the pet (optional)
     * @param  string|null $status  Updated status of the pet (optional)
     * @param  int     &$responseCode    The HTTP Response Code
     * @param  array   $responseHeaders  Additional HTTP headers to return with the response ()
     *
     * @return void
     */
    public function updatePetWithForm(
        int $petId,
        ?string $name,
        ?string $status,
        int &$responseCode,
        array &$responseHeaders
    ): void;

    /**
     * Operation uploadFile
     *
     * uploads an image
     *
     * @param  int $petId  ID of pet to update (required)
     * @param  string|null $additionalMetadata  Additional data to pass to server (optional)
     * @param  UploadedFile|null $file  file to upload (optional)
     * @param  int     &$responseCode    The HTTP Response Code
     * @param  array   $responseHeaders  Additional HTTP headers to return with the response ()
     *
     * @return array|object|null
     */
    public function uploadFile(
        int $petId,
        ?string $additionalMetadata,
        ?UploadedFile $file,
        int &$responseCode,
        array &$responseHeaders
    ): array|object|null;
}
