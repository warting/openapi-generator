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


package org.openapitools.client.auth;

import com.github.scribejava.core.builder.ServiceBuilder;
import com.github.scribejava.core.model.OAuth2AccessToken;

@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", comments = "Generator version: 7.15.0-SNAPSHOT")
public class OauthClientCredentialsGrant extends OAuth {

  public OauthClientCredentialsGrant(String authorizationUrl, String tokenUrl, String scopes) {
    super(authorizationUrl, tokenUrl, scopes);
  }

  @Override
  protected OAuth2AccessToken getOAuth2AccessToken() {
    try {
      return service.getAccessTokenClientCredentialsGrant(scopes);
    } catch (Exception e) {
      throw new RuntimeException("Failed to get oauth token", e);
    }
  }

  @Override
  protected OAuthFlow getFlow() {
    return OAuthFlow.APPLICATION;
  }

  /**
   * Configures the client credentials flow
   *
   * @param clientId
   * @param clientSecret
   */
  public void configure(String clientId, String clientSecret) {
    service = new ServiceBuilder(clientId)
            .apiSecret(clientSecret)
            .defaultScope(scopes)
            .build(new DefaultApi20Impl(authorizationUrl, tokenUrl));
  }
}
