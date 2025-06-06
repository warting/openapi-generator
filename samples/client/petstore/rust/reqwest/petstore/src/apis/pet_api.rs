/*
 * OpenAPI Petstore
 *
 * This is a sample server Petstore server. For this sample, you can use the api key `special-key` to test the authorization filters.
 *
 * The version of the OpenAPI document: 1.0.0
 * 
 * Generated by: https://openapi-generator.tech
 */


use reqwest;
use serde::{Deserialize, Serialize, de::Error as _};
use crate::{apis::ResponseContent, models};
use super::{Error, configuration, ContentType};


/// struct for typed errors of method [`add_pet`]
#[derive(Debug, Clone, Serialize, Deserialize)]
#[serde(untagged)]
pub enum AddPetError {
    Status405(),
    UnknownValue(serde_json::Value),
}

/// struct for typed errors of method [`delete_pet`]
#[derive(Debug, Clone, Serialize, Deserialize)]
#[serde(untagged)]
pub enum DeletePetError {
    Status400(),
    UnknownValue(serde_json::Value),
}

/// struct for typed errors of method [`find_pets_by_status`]
#[derive(Debug, Clone, Serialize, Deserialize)]
#[serde(untagged)]
pub enum FindPetsByStatusError {
    Status400(),
    UnknownValue(serde_json::Value),
}

/// struct for typed errors of method [`find_pets_by_tags`]
#[derive(Debug, Clone, Serialize, Deserialize)]
#[serde(untagged)]
pub enum FindPetsByTagsError {
    Status400(),
    UnknownValue(serde_json::Value),
}

/// struct for typed errors of method [`get_pet_by_id`]
#[derive(Debug, Clone, Serialize, Deserialize)]
#[serde(untagged)]
pub enum GetPetByIdError {
    Status400(),
    Status404(),
    UnknownValue(serde_json::Value),
}

/// struct for typed errors of method [`pets_explode_post`]
#[derive(Debug, Clone, Serialize, Deserialize)]
#[serde(untagged)]
pub enum PetsExplodePostError {
    Status400(),
    UnknownValue(serde_json::Value),
}

/// struct for typed errors of method [`pets_post`]
#[derive(Debug, Clone, Serialize, Deserialize)]
#[serde(untagged)]
pub enum PetsPostError {
    Status400(),
    UnknownValue(serde_json::Value),
}

/// struct for typed errors of method [`update_pet`]
#[derive(Debug, Clone, Serialize, Deserialize)]
#[serde(untagged)]
pub enum UpdatePetError {
    Status400(),
    Status404(),
    Status405(),
    UnknownValue(serde_json::Value),
}

/// struct for typed errors of method [`update_pet_with_form`]
#[derive(Debug, Clone, Serialize, Deserialize)]
#[serde(untagged)]
pub enum UpdatePetWithFormError {
    Status405(),
    UnknownValue(serde_json::Value),
}

/// struct for typed errors of method [`upload_file`]
#[derive(Debug, Clone, Serialize, Deserialize)]
#[serde(untagged)]
pub enum UploadFileError {
    UnknownValue(serde_json::Value),
}


/// This is the description for the addPet operation
pub fn add_pet(configuration: &configuration::Configuration, pet: models::Pet) -> Result<models::Pet, Error<AddPetError>> {
    // add a prefix to parameters to efficiently prevent name collisions
    let p_pet = pet;

    let uri_str = format!("{}/pet", configuration.base_path);
    let mut req_builder = configuration.client.request(reqwest::Method::POST, &uri_str);

    if let Some(ref user_agent) = configuration.user_agent {
        req_builder = req_builder.header(reqwest::header::USER_AGENT, user_agent.clone());
    }
    if let Some(ref token) = configuration.oauth_access_token {
        req_builder = req_builder.bearer_auth(token.to_owned());
    };
    req_builder = req_builder.json(&p_pet);

    let req = req_builder.build()?;
    let resp = configuration.client.execute(req)?;

    let status = resp.status();
    let content_type = resp
        .headers()
        .get("content-type")
        .and_then(|v| v.to_str().ok())
        .unwrap_or("application/octet-stream");
    let content_type = super::ContentType::from(content_type);

    if !status.is_client_error() && !status.is_server_error() {
        let content = resp.text()?;
        match content_type {
            ContentType::Json => serde_json::from_str(&content).map_err(Error::from),
            ContentType::Text => return Err(Error::from(serde_json::Error::custom("Received `text/plain` content type response that cannot be converted to `models::Pet`"))),
            ContentType::Unsupported(unknown_type) => return Err(Error::from(serde_json::Error::custom(format!("Received `{unknown_type}` content type response that cannot be converted to `models::Pet`")))),
        }
    } else {
        let content = resp.text()?;
        let entity: Option<AddPetError> = serde_json::from_str(&content).ok();
        Err(Error::ResponseError(ResponseContent { status, content, entity }))
    }
}

/// 
pub fn delete_pet(configuration: &configuration::Configuration, pet_id: i64, api_key: Option<&str>) -> Result<(), Error<DeletePetError>> {
    // add a prefix to parameters to efficiently prevent name collisions
    let p_pet_id = pet_id;
    let p_api_key = api_key;

    let uri_str = format!("{}/pet/{petId}", configuration.base_path, petId=p_pet_id);
    let mut req_builder = configuration.client.request(reqwest::Method::DELETE, &uri_str);

    if let Some(ref user_agent) = configuration.user_agent {
        req_builder = req_builder.header(reqwest::header::USER_AGENT, user_agent.clone());
    }
    if let Some(param_value) = p_api_key {
        req_builder = req_builder.header("api_key", param_value.to_string());
    }
    if let Some(ref token) = configuration.oauth_access_token {
        req_builder = req_builder.bearer_auth(token.to_owned());
    };

    let req = req_builder.build()?;
    let resp = configuration.client.execute(req)?;

    let status = resp.status();

    if !status.is_client_error() && !status.is_server_error() {
        Ok(())
    } else {
        let content = resp.text()?;
        let entity: Option<DeletePetError> = serde_json::from_str(&content).ok();
        Err(Error::ResponseError(ResponseContent { status, content, entity }))
    }
}

/// Multiple status values can be provided with comma separated strings. This is also a multi-line description to test rust doc comments 
pub fn find_pets_by_status(configuration: &configuration::Configuration, status: Vec<String>, r#type: Option<Vec<String>>) -> Result<Vec<models::Pet>, Error<FindPetsByStatusError>> {
    // add a prefix to parameters to efficiently prevent name collisions
    let p_status = status;
    let p_type = r#type;

    let uri_str = format!("{}/pet/findByStatus", configuration.base_path);
    let mut req_builder = configuration.client.request(reqwest::Method::GET, &uri_str);

    req_builder = match "csv" {
        "multi" => req_builder.query(&p_status.into_iter().map(|p| ("status".to_owned(), p.to_string())).collect::<Vec<(std::string::String, std::string::String)>>()),
        _ => req_builder.query(&[("status", &p_status.into_iter().map(|p| p.to_string()).collect::<Vec<String>>().join(",").to_string())]),
    };
    if let Some(ref param_value) = p_type {
        req_builder = match "csv" {
            "multi" => req_builder.query(&param_value.into_iter().map(|p| ("type".to_owned(), p.to_string())).collect::<Vec<(std::string::String, std::string::String)>>()),
            _ => req_builder.query(&[("type", &param_value.into_iter().map(|p| p.to_string()).collect::<Vec<String>>().join(",").to_string())]),
        };
    }
    if let Some(ref user_agent) = configuration.user_agent {
        req_builder = req_builder.header(reqwest::header::USER_AGENT, user_agent.clone());
    }
    if let Some(ref token) = configuration.oauth_access_token {
        req_builder = req_builder.bearer_auth(token.to_owned());
    };

    let req = req_builder.build()?;
    let resp = configuration.client.execute(req)?;

    let status = resp.status();
    let content_type = resp
        .headers()
        .get("content-type")
        .and_then(|v| v.to_str().ok())
        .unwrap_or("application/octet-stream");
    let content_type = super::ContentType::from(content_type);

    if !status.is_client_error() && !status.is_server_error() {
        let content = resp.text()?;
        match content_type {
            ContentType::Json => serde_json::from_str(&content).map_err(Error::from),
            ContentType::Text => return Err(Error::from(serde_json::Error::custom("Received `text/plain` content type response that cannot be converted to `Vec&lt;models::Pet&gt;`"))),
            ContentType::Unsupported(unknown_type) => return Err(Error::from(serde_json::Error::custom(format!("Received `{unknown_type}` content type response that cannot be converted to `Vec&lt;models::Pet&gt;`")))),
        }
    } else {
        let content = resp.text()?;
        let entity: Option<FindPetsByStatusError> = serde_json::from_str(&content).ok();
        Err(Error::ResponseError(ResponseContent { status, content, entity }))
    }
}

/// Multiple tags can be provided with comma separated strings. Use tag1, tag2, tag3 for testing.
pub fn find_pets_by_tags(configuration: &configuration::Configuration, tags: Vec<String>) -> Result<Vec<models::Pet>, Error<FindPetsByTagsError>> {
    // add a prefix to parameters to efficiently prevent name collisions
    let p_tags = tags;

    let uri_str = format!("{}/pet/findByTags", configuration.base_path);
    let mut req_builder = configuration.client.request(reqwest::Method::GET, &uri_str);

    req_builder = match "csv" {
        "multi" => req_builder.query(&p_tags.into_iter().map(|p| ("tags".to_owned(), p.to_string())).collect::<Vec<(std::string::String, std::string::String)>>()),
        _ => req_builder.query(&[("tags", &p_tags.into_iter().map(|p| p.to_string()).collect::<Vec<String>>().join(",").to_string())]),
    };
    if let Some(ref user_agent) = configuration.user_agent {
        req_builder = req_builder.header(reqwest::header::USER_AGENT, user_agent.clone());
    }
    if let Some(ref token) = configuration.oauth_access_token {
        req_builder = req_builder.bearer_auth(token.to_owned());
    };

    let req = req_builder.build()?;
    let resp = configuration.client.execute(req)?;

    let status = resp.status();
    let content_type = resp
        .headers()
        .get("content-type")
        .and_then(|v| v.to_str().ok())
        .unwrap_or("application/octet-stream");
    let content_type = super::ContentType::from(content_type);

    if !status.is_client_error() && !status.is_server_error() {
        let content = resp.text()?;
        match content_type {
            ContentType::Json => serde_json::from_str(&content).map_err(Error::from),
            ContentType::Text => return Err(Error::from(serde_json::Error::custom("Received `text/plain` content type response that cannot be converted to `Vec&lt;models::Pet&gt;`"))),
            ContentType::Unsupported(unknown_type) => return Err(Error::from(serde_json::Error::custom(format!("Received `{unknown_type}` content type response that cannot be converted to `Vec&lt;models::Pet&gt;`")))),
        }
    } else {
        let content = resp.text()?;
        let entity: Option<FindPetsByTagsError> = serde_json::from_str(&content).ok();
        Err(Error::ResponseError(ResponseContent { status, content, entity }))
    }
}

/// Returns a single pet
pub fn get_pet_by_id(configuration: &configuration::Configuration, pet_id: i64) -> Result<models::Pet, Error<GetPetByIdError>> {
    // add a prefix to parameters to efficiently prevent name collisions
    let p_pet_id = pet_id;

    let uri_str = format!("{}/pet/{petId}", configuration.base_path, petId=p_pet_id);
    let mut req_builder = configuration.client.request(reqwest::Method::GET, &uri_str);

    if let Some(ref user_agent) = configuration.user_agent {
        req_builder = req_builder.header(reqwest::header::USER_AGENT, user_agent.clone());
    }
    if let Some(ref apikey) = configuration.api_key {
        let key = apikey.key.clone();
        let value = match apikey.prefix {
            Some(ref prefix) => format!("{} {}", prefix, key),
            None => key,
        };
        req_builder = req_builder.header("api_key", value);
    };

    let req = req_builder.build()?;
    let resp = configuration.client.execute(req)?;

    let status = resp.status();
    let content_type = resp
        .headers()
        .get("content-type")
        .and_then(|v| v.to_str().ok())
        .unwrap_or("application/octet-stream");
    let content_type = super::ContentType::from(content_type);

    if !status.is_client_error() && !status.is_server_error() {
        let content = resp.text()?;
        match content_type {
            ContentType::Json => serde_json::from_str(&content).map_err(Error::from),
            ContentType::Text => return Err(Error::from(serde_json::Error::custom("Received `text/plain` content type response that cannot be converted to `models::Pet`"))),
            ContentType::Unsupported(unknown_type) => return Err(Error::from(serde_json::Error::custom(format!("Received `{unknown_type}` content type response that cannot be converted to `models::Pet`")))),
        }
    } else {
        let content = resp.text()?;
        let entity: Option<GetPetByIdError> = serde_json::from_str(&content).ok();
        Err(Error::ResponseError(ResponseContent { status, content, entity }))
    }
}

/// Returns a list of pets
pub fn pets_explode_post(configuration: &configuration::Configuration, page_explode: Option<models::Page>) -> Result<Vec<models::Pet>, Error<PetsExplodePostError>> {
    // add a prefix to parameters to efficiently prevent name collisions
    let p_page_explode = page_explode;

    let uri_str = format!("{}/pets/explode", configuration.base_path);
    let mut req_builder = configuration.client.request(reqwest::Method::POST, &uri_str);

    if let Some(ref param_value) = p_page_explode {
        req_builder = req_builder.query(&param_value);
    }
    if let Some(ref user_agent) = configuration.user_agent {
        req_builder = req_builder.header(reqwest::header::USER_AGENT, user_agent.clone());
    }

    let req = req_builder.build()?;
    let resp = configuration.client.execute(req)?;

    let status = resp.status();
    let content_type = resp
        .headers()
        .get("content-type")
        .and_then(|v| v.to_str().ok())
        .unwrap_or("application/octet-stream");
    let content_type = super::ContentType::from(content_type);

    if !status.is_client_error() && !status.is_server_error() {
        let content = resp.text()?;
        match content_type {
            ContentType::Json => serde_json::from_str(&content).map_err(Error::from),
            ContentType::Text => return Err(Error::from(serde_json::Error::custom("Received `text/plain` content type response that cannot be converted to `Vec&lt;models::Pet&gt;`"))),
            ContentType::Unsupported(unknown_type) => return Err(Error::from(serde_json::Error::custom(format!("Received `{unknown_type}` content type response that cannot be converted to `Vec&lt;models::Pet&gt;`")))),
        }
    } else {
        let content = resp.text()?;
        let entity: Option<PetsExplodePostError> = serde_json::from_str(&content).ok();
        Err(Error::ResponseError(ResponseContent { status, content, entity }))
    }
}

/// Returns a list of pets
pub fn pets_post(configuration: &configuration::Configuration, page: Option<models::Page>) -> Result<Vec<models::Pet>, Error<PetsPostError>> {
    // add a prefix to parameters to efficiently prevent name collisions
    let p_page = page;

    let uri_str = format!("{}/pets", configuration.base_path);
    let mut req_builder = configuration.client.request(reqwest::Method::POST, &uri_str);

    if let Some(ref param_value) = p_page {
        req_builder = req_builder.query(&[("page", &serde_json::to_string(param_value)?)]);
    }
    if let Some(ref user_agent) = configuration.user_agent {
        req_builder = req_builder.header(reqwest::header::USER_AGENT, user_agent.clone());
    }

    let req = req_builder.build()?;
    let resp = configuration.client.execute(req)?;

    let status = resp.status();
    let content_type = resp
        .headers()
        .get("content-type")
        .and_then(|v| v.to_str().ok())
        .unwrap_or("application/octet-stream");
    let content_type = super::ContentType::from(content_type);

    if !status.is_client_error() && !status.is_server_error() {
        let content = resp.text()?;
        match content_type {
            ContentType::Json => serde_json::from_str(&content).map_err(Error::from),
            ContentType::Text => return Err(Error::from(serde_json::Error::custom("Received `text/plain` content type response that cannot be converted to `Vec&lt;models::Pet&gt;`"))),
            ContentType::Unsupported(unknown_type) => return Err(Error::from(serde_json::Error::custom(format!("Received `{unknown_type}` content type response that cannot be converted to `Vec&lt;models::Pet&gt;`")))),
        }
    } else {
        let content = resp.text()?;
        let entity: Option<PetsPostError> = serde_json::from_str(&content).ok();
        Err(Error::ResponseError(ResponseContent { status, content, entity }))
    }
}

/// 
pub fn update_pet(configuration: &configuration::Configuration, pet: models::Pet) -> Result<models::Pet, Error<UpdatePetError>> {
    // add a prefix to parameters to efficiently prevent name collisions
    let p_pet = pet;

    let uri_str = format!("{}/pet", configuration.base_path);
    let mut req_builder = configuration.client.request(reqwest::Method::PUT, &uri_str);

    if let Some(ref user_agent) = configuration.user_agent {
        req_builder = req_builder.header(reqwest::header::USER_AGENT, user_agent.clone());
    }
    if let Some(ref token) = configuration.oauth_access_token {
        req_builder = req_builder.bearer_auth(token.to_owned());
    };
    req_builder = req_builder.json(&p_pet);

    let req = req_builder.build()?;
    let resp = configuration.client.execute(req)?;

    let status = resp.status();
    let content_type = resp
        .headers()
        .get("content-type")
        .and_then(|v| v.to_str().ok())
        .unwrap_or("application/octet-stream");
    let content_type = super::ContentType::from(content_type);

    if !status.is_client_error() && !status.is_server_error() {
        let content = resp.text()?;
        match content_type {
            ContentType::Json => serde_json::from_str(&content).map_err(Error::from),
            ContentType::Text => return Err(Error::from(serde_json::Error::custom("Received `text/plain` content type response that cannot be converted to `models::Pet`"))),
            ContentType::Unsupported(unknown_type) => return Err(Error::from(serde_json::Error::custom(format!("Received `{unknown_type}` content type response that cannot be converted to `models::Pet`")))),
        }
    } else {
        let content = resp.text()?;
        let entity: Option<UpdatePetError> = serde_json::from_str(&content).ok();
        Err(Error::ResponseError(ResponseContent { status, content, entity }))
    }
}

/// 
pub fn update_pet_with_form(configuration: &configuration::Configuration, pet_id: i64, name: Option<&str>, status: Option<&str>) -> Result<(), Error<UpdatePetWithFormError>> {
    // add a prefix to parameters to efficiently prevent name collisions
    let p_pet_id = pet_id;
    let p_name = name;
    let p_status = status;

    let uri_str = format!("{}/pet/{petId}", configuration.base_path, petId=p_pet_id);
    let mut req_builder = configuration.client.request(reqwest::Method::POST, &uri_str);

    if let Some(ref user_agent) = configuration.user_agent {
        req_builder = req_builder.header(reqwest::header::USER_AGENT, user_agent.clone());
    }
    if let Some(ref token) = configuration.oauth_access_token {
        req_builder = req_builder.bearer_auth(token.to_owned());
    };
    let mut multipart_form_params = std::collections::HashMap::new();
    if let Some(param_value) = p_name {
        multipart_form_params.insert("name", param_value.to_string());
    }
    if let Some(param_value) = p_status {
        multipart_form_params.insert("status", param_value.to_string());
    }
    req_builder = req_builder.form(&multipart_form_params);

    let req = req_builder.build()?;
    let resp = configuration.client.execute(req)?;

    let status = resp.status();

    if !status.is_client_error() && !status.is_server_error() {
        Ok(())
    } else {
        let content = resp.text()?;
        let entity: Option<UpdatePetWithFormError> = serde_json::from_str(&content).ok();
        Err(Error::ResponseError(ResponseContent { status, content, entity }))
    }
}

/// 
pub fn upload_file(configuration: &configuration::Configuration, pet_id: i64, additional_metadata: Option<&str>, file: Option<std::path::PathBuf>) -> Result<models::ApiResponse, Error<UploadFileError>> {
    // add a prefix to parameters to efficiently prevent name collisions
    let p_pet_id = pet_id;
    let p_additional_metadata = additional_metadata;
    let p_file = file;

    let uri_str = format!("{}/pet/{petId}/uploadImage", configuration.base_path, petId=p_pet_id);
    let mut req_builder = configuration.client.request(reqwest::Method::POST, &uri_str);

    if let Some(ref user_agent) = configuration.user_agent {
        req_builder = req_builder.header(reqwest::header::USER_AGENT, user_agent.clone());
    }
    if let Some(ref token) = configuration.oauth_access_token {
        req_builder = req_builder.bearer_auth(token.to_owned());
    };
    let mut multipart_form = reqwest::blocking::multipart::Form::new();
    if let Some(param_value) = p_additional_metadata {
        multipart_form = multipart_form.text("additionalMetadata", param_value.to_string());
    }
    if let Some(param_value) = p_file {
        multipart_form = multipart_form.file("file", param_value)?;
    }
    req_builder = req_builder.multipart(multipart_form);

    let req = req_builder.build()?;
    let resp = configuration.client.execute(req)?;

    let status = resp.status();
    let content_type = resp
        .headers()
        .get("content-type")
        .and_then(|v| v.to_str().ok())
        .unwrap_or("application/octet-stream");
    let content_type = super::ContentType::from(content_type);

    if !status.is_client_error() && !status.is_server_error() {
        let content = resp.text()?;
        match content_type {
            ContentType::Json => serde_json::from_str(&content).map_err(Error::from),
            ContentType::Text => return Err(Error::from(serde_json::Error::custom("Received `text/plain` content type response that cannot be converted to `models::ApiResponse`"))),
            ContentType::Unsupported(unknown_type) => return Err(Error::from(serde_json::Error::custom(format!("Received `{unknown_type}` content type response that cannot be converted to `models::ApiResponse`")))),
        }
    } else {
        let content = resp.text()?;
        let entity: Option<UploadFileError> = serde_json::from_str(&content).ok();
        Err(Error::ResponseError(ResponseContent { status, content, entity }))
    }
}

