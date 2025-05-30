//
// Name.swift
//
// Generated by openapi-generator
// https://openapi-generator.tech
//

import Foundation

@available(*, deprecated, renamed: "PetstoreClientAPI.Name")
public typealias Name = PetstoreClientAPI.Name

extension PetstoreClientAPI {

/** Model for testing model name same as property name */
public final class Name: @unchecked Sendable, Codable, ParameterConvertible, Hashable {

    public private(set) var name: Int
    public private(set) var snakeCase: NullEncodable<Int> = .encodeValue(11033)
    public private(set) var property: String?
    public private(set) var _123number: Int?

    public init(name: Int, snakeCase: NullEncodable<Int> = .encodeValue(11033), property: String? = nil, _123number: Int? = nil) {
        self.name = name
        self.snakeCase = snakeCase
        self.property = property
        self._123number = _123number
    }

    public enum CodingKeys: String, CodingKey, CaseIterable {
        case name
        case snakeCase = "snake_case"
        case property
        case _123number = "123Number"
    }

    // Encodable protocol methods

    public func encode(to encoder: Encoder) throws {
        var container = encoder.container(keyedBy: CodingKeys.self)
        try container.encode(name, forKey: .name)
        switch snakeCase {
        case .encodeNothing: break
        case .encodeNull, .encodeValue: try container.encode(snakeCase, forKey: .snakeCase)
        }
        try container.encodeIfPresent(property, forKey: .property)
        try container.encodeIfPresent(_123number, forKey: ._123number)
    }

    public static func == (lhs: Name, rhs: Name) -> Bool {
        lhs.name == rhs.name &&
        lhs.snakeCase == rhs.snakeCase &&
        lhs.property == rhs.property &&
        lhs._123number == rhs._123number
        
    }

    public func hash(into hasher: inout Hasher) {
        hasher.combine(name.hashValue)
        hasher.combine(snakeCase.hashValue)
        hasher.combine(property?.hashValue)
        hasher.combine(_123number?.hashValue)
        
    }
}

}
