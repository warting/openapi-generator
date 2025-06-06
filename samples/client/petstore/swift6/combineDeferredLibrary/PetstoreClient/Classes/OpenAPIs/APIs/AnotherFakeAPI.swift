//
// AnotherFakeAPI.swift
//
// Generated by openapi-generator
// https://openapi-generator.tech
//

import Foundation
#if canImport(Combine)
import Combine
#endif

open class AnotherFakeAPI {

    /**
     To test special tags
     
     - parameter body: (body) client model 
     - parameter apiConfiguration: The configuration for the http request.
     - returns: AnyPublisher<Client, Error>
     */
    #if canImport(Combine)
    @available(macOS 10.15, iOS 13.0, tvOS 13.0, watchOS 6.0, *)
    open class func call123testSpecialTags(body: Client, apiConfiguration: PetstoreClientAPIConfiguration = PetstoreClientAPIConfiguration.shared) -> AnyPublisher<Client, Error> {
        let requestBuilder = call123testSpecialTagsWithRequestBuilder(body: body, apiConfiguration: apiConfiguration)
        let requestTask = requestBuilder.requestTask
        return Deferred { Future<Client, Error> { promise in
            nonisolated(unsafe) let promise = promise
            requestBuilder.execute { result in
                switch result {
                case let .success(response):
                    promise(.success(response.body))
                case let .failure(error):
                    promise(.failure(error))
                }
            }
        }
        .handleEvents(receiveCancel: {
            requestTask.cancel()
        })
        .eraseToAnyPublisher()
        }
        .eraseToAnyPublisher()
    }
    #endif

    /**
     To test special tags
     - PATCH /another-fake/dummy
     - To test special tags and operation ID starting with number
     - parameter body: (body) client model 
     - parameter apiConfiguration: The configuration for the http request.
     - returns: RequestBuilder<Client> 
     */
    open class func call123testSpecialTagsWithRequestBuilder(body: Client, apiConfiguration: PetstoreClientAPIConfiguration = PetstoreClientAPIConfiguration.shared) -> RequestBuilder<Client> {
        let localVariablePath = "/another-fake/dummy"
        let localVariableURLString = apiConfiguration.basePath + localVariablePath
        let localVariableParameters = JSONEncodingHelper.encodingParameters(forEncodableObject: body, codableHelper: apiConfiguration.codableHelper)

        let localVariableUrlComponents = URLComponents(string: localVariableURLString)

        let localVariableNillableHeaders: [String: (any Sendable)?] = [
            "Content-Type": "application/json",
        ]

        let localVariableHeaderParameters = APIHelper.rejectNilHeaders(localVariableNillableHeaders)

        let localVariableRequestBuilder: RequestBuilder<Client>.Type = apiConfiguration.requestBuilderFactory.getBuilder()

        return localVariableRequestBuilder.init(method: "PATCH", URLString: (localVariableUrlComponents?.string ?? localVariableURLString), parameters: localVariableParameters, headers: localVariableHeaderParameters, requiresAuthentication: false, apiConfiguration: apiConfiguration)
    }
}
