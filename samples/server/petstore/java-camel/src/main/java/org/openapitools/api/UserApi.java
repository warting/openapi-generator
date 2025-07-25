/*
* NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech) (7.15.0-SNAPSHOT).
* https://openapi-generator.tech
* Do not edit the class manually.
*/
package org.openapitools.api;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestParamType;
import org.springframework.stereotype.Component;
import org.openapitools.model.*;
import org.apache.camel.model.rest.RestBindingMode;
import org.apache.camel.LoggingLevel;

@Component
public class UserApi extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        onException(Exception.class)
            .log(LoggingLevel.ERROR, "${exception.message}: ${exception.stacktrace}")
            .handled(true)
            .process("validationErrorProcessor");
        

        /**
        POST /user : Create user
        **/
        rest()
            .securityDefinitions()
                .apiKey("api_key")
                    .withHeader("api_key")
                
            .endSecurityDefinition()
            .post("/user")
                .description("Create user")
                .id("createUserApi")
                .consumes("application/json")
                .type(User.class)
                
                .param()
                    .name("user")
                    .type(RestParamType.body)
                    .required(true)
                    .description("Created user object")
                .endParam()
                .to("direct:validate-createUser");
        

        /**
        POST /user/createWithArray : Creates list of users with given input array
        **/
        rest()
            .securityDefinitions()
                .apiKey("api_key")
                    .withHeader("api_key")
                
            .endSecurityDefinition()
            .post("/user/createWithArray")
                .description("Creates list of users with given input array")
                .id("createUsersWithArrayInputApi")
                .consumes("application/json")
                .type(User[].class)
                
                .param()
                    .name("user")
                    .type(RestParamType.body)
                    .required(true)
                    .description("List of user object")
                .endParam()
                .to("direct:validate-createUsersWithArrayInput");
        

        /**
        POST /user/createWithList : Creates list of users with given input array
        **/
        rest()
            .securityDefinitions()
                .apiKey("api_key")
                    .withHeader("api_key")
                
            .endSecurityDefinition()
            .post("/user/createWithList")
                .description("Creates list of users with given input array")
                .id("createUsersWithListInputApi")
                .consumes("application/json")
                .type(User[].class)
                
                .param()
                    .name("user")
                    .type(RestParamType.body)
                    .required(true)
                    .description("List of user object")
                .endParam()
                .to("direct:validate-createUsersWithListInput");
        

        /**
        DELETE /user/{username} : Delete user
        **/
        rest()
            .securityDefinitions()
                .apiKey("api_key")
                    .withHeader("api_key")
                
            .endSecurityDefinition()
            .delete("/user/{username}")
                .description("Delete user")
                .id("deleteUserApi")
                .param()
                    .name("username")
                    .type(RestParamType.path)
                    .required(true)
                    .description("The name that needs to be deleted")
                .endParam()
                .to("direct:validate-deleteUser");
        

        /**
        GET /user/{username} : Get user by user name
        **/
        rest()
            .get("/user/{username}")
                .description("Get user by user name")
                .id("getUserByNameApi")
                .produces("application/xml, application/json")
                .outType(User.class)
                .param()
                    .name("username")
                    .type(RestParamType.path)
                    .required(true)
                    .description("The name that needs to be fetched. Use user1 for testing.")
                .endParam()
                .to("direct:validate-getUserByName");
        

        /**
        GET /user/login : Logs user into the system
        **/
        rest()
            .get("/user/login")
                .description("Logs user into the system")
                .id("loginUserApi")
                .produces("application/xml, application/json")
                .outType(String.class)
                .param()
                    .name("username")
                    .type(RestParamType.query)
                    .required(true)
                    .description("The user name for login")
                .endParam()
                .param()
                    .name("password")
                    .type(RestParamType.query)
                    .required(true)
                    .description("The password for login in clear text")
                .endParam()
                .to("direct:validate-loginUser");
        

        /**
        GET /user/logout : Logs out current logged in user session
        **/
        rest()
            .securityDefinitions()
                .apiKey("api_key")
                    .withHeader("api_key")
                
            .endSecurityDefinition()
            .get("/user/logout")
                .description("Logs out current logged in user session")
                .id("logoutUserApi")
                .to("direct:validate-logoutUser");
        

        /**
        PUT /user/{username} : Updated user
        **/
        rest()
            .securityDefinitions()
                .apiKey("api_key")
                    .withHeader("api_key")
                
            .endSecurityDefinition()
            .put("/user/{username}")
                .description("Updated user")
                .id("updateUserApi")
                .consumes("application/json")
                .type(User.class)
                
                .param()
                    .name("username")
                    .type(RestParamType.path)
                    .required(true)
                    .description("name that need to be deleted")
                .endParam()
                .param()
                    .name("user")
                    .type(RestParamType.body)
                    .required(true)
                    .description("Updated user object")
                .endParam()
                .to("direct:validate-updateUser");
        
    }
}
