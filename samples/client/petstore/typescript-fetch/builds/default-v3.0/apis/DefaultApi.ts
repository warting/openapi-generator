/* tslint:disable */
/* eslint-disable */
/**
 * OpenAPI Petstore
 * This spec is mainly for testing Petstore server and contains fake endpoints, models. Please do not use this for any other purpose. Special characters: \" \\
 *
 * The version of the OpenAPI document: 1.0.0
 * 
 *
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */


import * as runtime from '../runtime';
import type {
  FooGetDefaultResponse,
} from '../models/index';
import {
    FooGetDefaultResponseFromJSON,
    FooGetDefaultResponseToJSON,
} from '../models/index';

/**
 * 
 */
export class DefaultApi extends runtime.BaseAPI {

    /**
     */
    async fooGetRaw(initOverrides?: RequestInit | runtime.InitOverrideFunction): Promise<runtime.ApiResponse<FooGetDefaultResponse>> {
        const queryParameters: any = {};

        const headerParameters: runtime.HTTPHeaders = {};


        let urlPath = `/foo`;

        const response = await this.request({
            path: urlPath,
            method: 'GET',
            headers: headerParameters,
            query: queryParameters,
        }, initOverrides);

        return new runtime.JSONApiResponse(response, (jsonValue) => FooGetDefaultResponseFromJSON(jsonValue));
    }

    /**
     */
    async fooGet(initOverrides?: RequestInit | runtime.InitOverrideFunction): Promise<FooGetDefaultResponse> {
        const response = await this.fooGetRaw(initOverrides);
        return await response.value();
    }

}
