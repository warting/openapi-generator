// <auto-generated>
/*
 * OpenAPI Petstore
 *
 * This spec is mainly for testing Petstore server and contains fake endpoints, models. Please do not use this for any other purpose. Special characters: \" \\
 *
 * The version of the OpenAPI document: 1.0.0
 * Generated by: https://github.com/openapitools/openapi-generator.git
 */

using System;
using System.Collections;
using System.Collections.Generic;
using System.Collections.ObjectModel;
using System.Linq;
using System.IO;
using System.Text;
using System.Text.RegularExpressions;
using System.Text.Json;
using System.Text.Json.Serialization;
using System.ComponentModel.DataAnnotations;
using OpenAPIClientUtils = Org.OpenAPITools.Client.ClientUtils;
using Org.OpenAPITools.Client;

namespace Org.OpenAPITools.Model
{
    /// <summary>
    /// Descendant1
    /// </summary>
    public partial class Descendant1 : TestDescendants, IValidatableObject
    {
        /// <summary>
        /// Initializes a new instance of the <see cref="Descendant1" /> class.
        /// </summary>
        /// <param name="alternativeName">alternativeName</param>
        /// <param name="descendantName">descendantName</param>
        [JsonConstructor]
        public Descendant1(string alternativeName, string descendantName) : base(alternativeName)
        {
            DescendantName = descendantName;
            OnCreated();
        }

        partial void OnCreated();

        /// <summary>
        /// Gets or Sets DescendantName
        /// </summary>
        [JsonPropertyName("descendantName")]
        public string DescendantName { get; set; }

        /// <summary>
        /// Returns the string presentation of the object
        /// </summary>
        /// <returns>String presentation of the object</returns>
        public override string ToString()
        {
            StringBuilder sb = new StringBuilder();
            sb.Append("class Descendant1 {\n");
            sb.Append("  ").Append(base.ToString()?.Replace("\n", "\n  ")).Append("\n");
            sb.Append("  DescendantName: ").Append(DescendantName).Append("\n");
            sb.Append("}\n");
            return sb.ToString();
        }
    }

    /// <summary>
    /// A Json converter for type <see cref="Descendant1" />
    /// </summary>
    public class Descendant1JsonConverter : JsonConverter<Descendant1>
    {
        /// <summary>
        /// Deserializes json to <see cref="Descendant1" />
        /// </summary>
        /// <param name="utf8JsonReader"></param>
        /// <param name="typeToConvert"></param>
        /// <param name="jsonSerializerOptions"></param>
        /// <returns></returns>
        /// <exception cref="JsonException"></exception>
        public override Descendant1 Read(ref Utf8JsonReader utf8JsonReader, Type typeToConvert, JsonSerializerOptions jsonSerializerOptions)
        {
            int currentDepth = utf8JsonReader.CurrentDepth;

            if (utf8JsonReader.TokenType != JsonTokenType.StartObject && utf8JsonReader.TokenType != JsonTokenType.StartArray)
                throw new JsonException();

            JsonTokenType startingTokenType = utf8JsonReader.TokenType;

            Option<string> alternativeName = default;
            Option<string> descendantName = default;
            Option<TestDescendantsObjectType?> objectType = default;

            while (utf8JsonReader.Read())
            {
                if (startingTokenType == JsonTokenType.StartObject && utf8JsonReader.TokenType == JsonTokenType.EndObject && currentDepth == utf8JsonReader.CurrentDepth)
                    break;

                if (startingTokenType == JsonTokenType.StartArray && utf8JsonReader.TokenType == JsonTokenType.EndArray && currentDepth == utf8JsonReader.CurrentDepth)
                    break;

                if (utf8JsonReader.TokenType == JsonTokenType.PropertyName && currentDepth == utf8JsonReader.CurrentDepth - 1)
                {
                    string localVarJsonPropertyName = utf8JsonReader.GetString();
                    utf8JsonReader.Read();

                    switch (localVarJsonPropertyName)
                    {
                        case "alternativeName":
                            alternativeName = new Option<string>(utf8JsonReader.GetString());
                            break;
                        case "descendantName":
                            descendantName = new Option<string>(utf8JsonReader.GetString());
                            break;
                        case "objectType":
                            string objectTypeRawValue = utf8JsonReader.GetString();
                            if (objectTypeRawValue != null)
                                objectType = new Option<TestDescendantsObjectType?>(TestDescendantsObjectTypeValueConverter.FromStringOrDefault(objectTypeRawValue));
                            break;
                        default:
                            break;
                    }
                }
            }

            if (!alternativeName.IsSet)
                throw new ArgumentException("Property is required for class Descendant1.", nameof(alternativeName));

            if (!descendantName.IsSet)
                throw new ArgumentException("Property is required for class Descendant1.", nameof(descendantName));

            if (!objectType.IsSet)
                throw new ArgumentException("Property is required for class Descendant1.", nameof(objectType));

            if (alternativeName.IsSet && alternativeName.Value == null)
                throw new ArgumentNullException(nameof(alternativeName), "Property is not nullable for class Descendant1.");

            if (descendantName.IsSet && descendantName.Value == null)
                throw new ArgumentNullException(nameof(descendantName), "Property is not nullable for class Descendant1.");

            if (objectType.IsSet && objectType.Value == null)
                throw new ArgumentNullException(nameof(objectType), "Property is not nullable for class Descendant1.");

            return new Descendant1(alternativeName.Value, descendantName.Value);
        }

        /// <summary>
        /// Serializes a <see cref="Descendant1" />
        /// </summary>
        /// <param name="writer"></param>
        /// <param name="descendant1"></param>
        /// <param name="jsonSerializerOptions"></param>
        /// <exception cref="NotImplementedException"></exception>
        public override void Write(Utf8JsonWriter writer, Descendant1 descendant1, JsonSerializerOptions jsonSerializerOptions)
        {
            writer.WriteStartObject();

            WriteProperties(writer, descendant1, jsonSerializerOptions);
            writer.WriteEndObject();
        }

        /// <summary>
        /// Serializes the properties of <see cref="Descendant1" />
        /// </summary>
        /// <param name="writer"></param>
        /// <param name="descendant1"></param>
        /// <param name="jsonSerializerOptions"></param>
        /// <exception cref="NotImplementedException"></exception>
        public void WriteProperties(Utf8JsonWriter writer, Descendant1 descendant1, JsonSerializerOptions jsonSerializerOptions)
        {
            if (descendant1.AlternativeName == null)
                throw new ArgumentNullException(nameof(descendant1.AlternativeName), "Property is required for class Descendant1.");

            if (descendant1.DescendantName == null)
                throw new ArgumentNullException(nameof(descendant1.DescendantName), "Property is required for class Descendant1.");

            writer.WriteString("alternativeName", descendant1.AlternativeName);

            writer.WriteString("descendantName", descendant1.DescendantName);

            writer.WriteString("objectType", TestDescendantsObjectTypeValueConverter.ToJsonValue(descendant1.ObjectType));
        }
    }
}
