/*
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


package org.openapitools.client.model;

import java.util.Objects;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.openapitools.client.JSON;

/**
 * ArrayOfNumberOnly
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", comments = "Generator version: 7.15.0-SNAPSHOT")
public class ArrayOfNumberOnly {
  public static final String SERIALIZED_NAME_ARRAY_NUMBER = "ArrayNumber";
  @SerializedName(SERIALIZED_NAME_ARRAY_NUMBER)
  @javax.annotation.Nullable
  private List<BigDecimal> arrayNumber = new ArrayList<>();

  public ArrayOfNumberOnly() {
  }

  public ArrayOfNumberOnly arrayNumber(@javax.annotation.Nullable List<BigDecimal> arrayNumber) {
    this.arrayNumber = arrayNumber;
    return this;
  }

  public ArrayOfNumberOnly addArrayNumberItem(BigDecimal arrayNumberItem) {
    if (this.arrayNumber == null) {
      this.arrayNumber = new ArrayList<>();
    }
    this.arrayNumber.add(arrayNumberItem);
    return this;
  }

  /**
   * Get arrayNumber
   * @return arrayNumber
   */
  @javax.annotation.Nullable
  public List<BigDecimal> getArrayNumber() {
    return arrayNumber;
  }

  public void setArrayNumber(@javax.annotation.Nullable List<BigDecimal> arrayNumber) {
    this.arrayNumber = arrayNumber;
  }



  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ArrayOfNumberOnly arrayOfNumberOnly = (ArrayOfNumberOnly) o;
    return Objects.equals(this.arrayNumber, arrayOfNumberOnly.arrayNumber);
  }

  @Override
  public int hashCode() {
    return Objects.hash(arrayNumber);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ArrayOfNumberOnly {\n");
    sb.append("    arrayNumber: ").append(toIndentedString(arrayNumber)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }


  public static HashSet<String> openapiFields;
  public static HashSet<String> openapiRequiredFields;

  static {
    // a set of all properties/fields (JSON key names)
    openapiFields = new HashSet<String>(Arrays.asList("ArrayNumber"));

    // a set of required properties/fields (JSON key names)
    openapiRequiredFields = new HashSet<String>(0);
  }

  /**
   * Validates the JSON Element and throws an exception if issues found
   *
   * @param jsonElement JSON Element
   * @throws IOException if the JSON Element is invalid with respect to ArrayOfNumberOnly
   */
  public static void validateJsonElement(JsonElement jsonElement) throws IOException {
      if (jsonElement == null) {
        if (!ArrayOfNumberOnly.openapiRequiredFields.isEmpty()) { // has required fields but JSON element is null
          throw new IllegalArgumentException(String.format("The required field(s) %s in ArrayOfNumberOnly is not found in the empty JSON string", ArrayOfNumberOnly.openapiRequiredFields.toString()));
        }
      }

      Set<Map.Entry<String, JsonElement>> entries = jsonElement.getAsJsonObject().entrySet();
      // check to see if the JSON string contains additional fields
      for (Map.Entry<String, JsonElement> entry : entries) {
        if (!ArrayOfNumberOnly.openapiFields.contains(entry.getKey())) {
          throw new IllegalArgumentException(String.format("The field `%s` in the JSON string is not defined in the `ArrayOfNumberOnly` properties. JSON: %s", entry.getKey(), jsonElement.toString()));
        }
      }
        JsonObject jsonObj = jsonElement.getAsJsonObject();
      // ensure the optional json data is an array if present
      if (jsonObj.get("ArrayNumber") != null && !jsonObj.get("ArrayNumber").isJsonNull() && !jsonObj.get("ArrayNumber").isJsonArray()) {
        throw new IllegalArgumentException(String.format("Expected the field `ArrayNumber` to be an array in the JSON string but got `%s`", jsonObj.get("ArrayNumber").toString()));
      }
  }

  public static class CustomTypeAdapterFactory implements TypeAdapterFactory {
    @SuppressWarnings("unchecked")
    @Override
    public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
       if (!ArrayOfNumberOnly.class.isAssignableFrom(type.getRawType())) {
         return null; // this class only serializes 'ArrayOfNumberOnly' and its subtypes
       }
       final TypeAdapter<JsonElement> elementAdapter = gson.getAdapter(JsonElement.class);
       final TypeAdapter<ArrayOfNumberOnly> thisAdapter
                        = gson.getDelegateAdapter(this, TypeToken.get(ArrayOfNumberOnly.class));

       return (TypeAdapter<T>) new TypeAdapter<ArrayOfNumberOnly>() {
           @Override
           public void write(JsonWriter out, ArrayOfNumberOnly value) throws IOException {
             JsonObject obj = thisAdapter.toJsonTree(value).getAsJsonObject();
             elementAdapter.write(out, obj);
           }

           @Override
           public ArrayOfNumberOnly read(JsonReader in) throws IOException {
             JsonElement jsonElement = elementAdapter.read(in);
             validateJsonElement(jsonElement);
             return thisAdapter.fromJsonTree(jsonElement);
           }

       }.nullSafe();
    }
  }

  /**
   * Create an instance of ArrayOfNumberOnly given an JSON string
   *
   * @param jsonString JSON string
   * @return An instance of ArrayOfNumberOnly
   * @throws IOException if the JSON string is invalid with respect to ArrayOfNumberOnly
   */
  public static ArrayOfNumberOnly fromJson(String jsonString) throws IOException {
    return JSON.getGson().fromJson(jsonString, ArrayOfNumberOnly.class);
  }

  /**
   * Convert an instance of ArrayOfNumberOnly to an JSON string
   *
   * @return JSON string
   */
  public String toJson() {
    return JSON.getGson().toJson(this);
  }
}

