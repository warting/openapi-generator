# gPRC for petstore

This is a sample server Petstore server. For this sample, you can use the api key `special-key` to test the authorization filters.

## Overview
These files were generated by the [OpenAPI Generator](https://openapi-generator.tech) project.

- API version: 1.0.0
- Package version: 
- Generator version: 7.15.0-SNAPSHOT
- Build package: org.openapitools.codegen.languages.ProtobufSchemaCodegen

## Usage

Below are some usage examples for Go and Ruby. For other languages, please refer to https://grpc.io/docs/quickstart/.

### Go
```
# assuming `protoc-gen-go` has been installed with `go get -u github.com/golang/protobuf/protoc-gen-go`
mkdir /var/tmp/go/petstore
protoc --go_out=/var/tmp/go/petstore services/*
protoc --go_out=/var/tmp/go/petstore models/*
```

### Ruby
```
# assuming `grpc_tools_ruby_protoc` has been installed via `gem install grpc-tools`
RUBY_OUTPUT_DIR="/var/tmp/ruby/petstore"
mkdir $RUBY_OUTPUT_DIR
grpc_tools_ruby_protoc --ruby_out=$RUBY_OUTPUT_DIR --grpc_out=$RUBY_OUTPUT_DIR/lib services/*
grpc_tools_ruby_protoc --ruby_out=$RUBY_OUTPUT_DIR --grpc_out=$RUBY_OUTPUT_DIR/lib models/*
```
