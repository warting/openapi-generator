/*
 * Copyright 2018 OpenAPI-Generator Contributors (https://openapi-generator.tech)
 * Copyright 2018 SmartBear Software
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.openapitools.codegen.languages;

import lombok.Setter;
import org.openapitools.codegen.*;
import org.openapitools.codegen.languages.features.CXFServerFeatures;
import org.openapitools.codegen.languages.features.GzipTestFeatures;
import org.openapitools.codegen.languages.features.LoggingTestFeatures;
import org.openapitools.codegen.languages.features.UseGenericResponseFeatures;
import org.openapitools.codegen.model.ModelMap;
import org.openapitools.codegen.model.OperationsMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.List;

public class JavaCXFServerCodegen extends AbstractJavaJAXRSServerCodegen
        implements CXFServerFeatures, GzipTestFeatures, LoggingTestFeatures, UseGenericResponseFeatures {
    private final Logger LOGGER = LoggerFactory.getLogger(JavaCXFServerCodegen.class);

    public static final String USE_ABSTRACTION_FOR_FILES = "useAbstractionForFiles";

    @Setter protected boolean addConsumesProducesJson = false;

    @Setter protected boolean generateSpringApplication = false;

    @Setter protected boolean useSpringAnnotationConfig = false;

    @Setter protected boolean useSwaggerFeature = false;

    @Setter protected boolean useSwaggerUI = false;

    @Setter protected boolean useWadlFeature = false;

    @Setter protected boolean useMultipartFeature = false;

    @Setter protected boolean useBeanValidationFeature = false;

    @Setter protected boolean generateSpringBootApplication = false;

    @Setter protected boolean generateJbossDeploymentDescriptor = false;

    @Setter protected boolean useGzipFeature = false;

    @Setter protected boolean useGzipFeatureForTests = false;

    @Setter protected boolean useLoggingFeature = false;

    @Setter protected boolean useLoggingFeatureForTests = false;

    @Setter protected boolean useAnnotatedBasePath = false;

    @Setter protected boolean generateNonSpringApplication = false;

    @Setter protected boolean useGenericResponse = false;

    @Setter protected boolean useAbstractionForFiles = false;

    public JavaCXFServerCodegen() {
        super();

        supportsInheritance = true;
        useTags = true;

        artifactId = "openapi-cxf-server";

        outputFolder = "generated-code/JavaJaxRS-CXF";

        // clioOptions default redefinition need to be updated
        updateOption(CodegenConstants.ARTIFACT_ID, this.getArtifactId());
        updateOption(USE_TAGS, String.valueOf(true));

        apiTemplateFiles.put("apiServiceImpl.mustache", ".java");

        // clear model and api doc template as this codegen
        // does not support auto-generated markdown doc at the moment
        //TODO: add doc templates
        modelDocTemplateFiles.remove("model_doc.mustache");
        apiDocTemplateFiles.remove("api_doc.mustache");


        typeMapping.put("date", "LocalDate");

        importMapping.put("LocalDate", "org.joda.time.LocalDate");

        embeddedTemplateDir = templateDir = JAXRS_TEMPLATE_DIRECTORY_NAME + File.separator + "cxf";

        cliOptions.add(CliOption.newBoolean(GENERATE_SPRING_APPLICATION, "Generate Spring application"));
        cliOptions.add(CliOption.newBoolean(USE_SPRING_ANNOTATION_CONFIG, "Use Spring Annotation Config"));

        cliOptions.add(CliOption.newBoolean(USE_SWAGGER_FEATURE, "Use Swagger Feature"));
        cliOptions.add(CliOption.newBoolean(USE_SWAGGER_UI, "Use Swagger UI"));

        cliOptions.add(CliOption.newBoolean(USE_WADL_FEATURE, "Use WADL Feature"));
        cliOptions.add(CliOption.newBoolean(USE_MULTIPART_FEATURE, "Use Multipart Feature"));

        cliOptions.add(CliOption.newBoolean(USE_GZIP_FEATURE, "Use Gzip Feature"));
        cliOptions.add(CliOption.newBoolean(USE_GZIP_FEATURE_FOR_TESTS, "Use Gzip Feature for tests"));

        cliOptions.add(CliOption.newBoolean(USE_BEANVALIDATION_FEATURE, "Use BeanValidation Feature"));
        cliOptions.add(CliOption.newBoolean(USE_LOGGING_FEATURE, "Use Logging Feature"));
        cliOptions.add(CliOption.newBoolean(USE_LOGGING_FEATURE_FOR_TESTS, "Use Logging Feature for tests"));

        cliOptions.add(CliOption.newBoolean(GENERATE_SPRING_BOOT_APPLICATION, "Generate Spring Boot application"));
        cliOptions.add(
                CliOption.newBoolean(GENERATE_JBOSS_DEPLOYMENT_DESCRIPTOR, "Generate Jboss Deployment Descriptor"));

        cliOptions
                .add(CliOption.newBoolean(ADD_CONSUMES_PRODUCES_JSON, "Add @Consumes/@Produces Json to API interface"));

        cliOptions.add(CliOption.newBoolean(USE_ANNOTATED_BASE_PATH, "Use @Path annotations for basePath"));

        cliOptions.add(CliOption.newBoolean(GENERATE_NON_SPRING_APPLICATION, "Generate non-Spring application"));
        cliOptions.add(CliOption.newBoolean(USE_GENERIC_RESPONSE, "Use generic response"));
        cliOptions.add(CliOption.newBoolean(USE_ABSTRACTION_FOR_FILES, "Use alternative types instead of java.io.File to allow passing bytes without a file on disk."));

    }

    @Override
    public void processOpts() {
        super.processOpts();

        convertPropertyToBooleanAndWriteBack(ADD_CONSUMES_PRODUCES_JSON, this::setAddConsumesProducesJson);
        convertPropertyToBooleanAndWriteBack(USE_GENERIC_RESPONSE, this::setUseGenericResponse);
        convertPropertyToBooleanAndWriteBack(GENERATE_SPRING_APPLICATION, this::setGenerateSpringApplication);
        if (generateSpringApplication) {
            this.setUseSwaggerFeature(convertPropertyToBooleanAndWriteBack(USE_SWAGGER_FEATURE));
            this.setUseSwaggerUI(convertPropertyToBooleanAndWriteBack(USE_SWAGGER_UI));

            this.setUseWadlFeature(convertPropertyToBooleanAndWriteBack(USE_WADL_FEATURE));
            this.setUseMultipartFeature(convertPropertyToBooleanAndWriteBack(USE_MULTIPART_FEATURE));
            this.setUseGzipFeature(convertPropertyToBooleanAndWriteBack(USE_GZIP_FEATURE));
            this.setUseGzipFeatureForTests(convertPropertyToBooleanAndWriteBack(USE_GZIP_FEATURE_FOR_TESTS));
            this.setUseLoggingFeature(convertPropertyToBooleanAndWriteBack(USE_LOGGING_FEATURE));
            this.setUseLoggingFeatureForTests(convertPropertyToBooleanAndWriteBack(USE_LOGGING_FEATURE_FOR_TESTS));
            this.setUseSpringAnnotationConfig(convertPropertyToBooleanAndWriteBack(USE_SPRING_ANNOTATION_CONFIG));

            boolean useBeanValidationFeature = convertPropertyToBooleanAndWriteBack(USE_BEANVALIDATION_FEATURE);
            this.setUseBeanValidationFeature(useBeanValidationFeature);
            if (useBeanValidationFeature) {
                LOGGER.info("make sure your target server supports Bean Validation 1.1");
            }

            this.setGenerateSpringBootApplication(convertPropertyToBooleanAndWriteBack(GENERATE_SPRING_BOOT_APPLICATION));
        }

        convertPropertyToBooleanAndWriteBack(GENERATE_JBOSS_DEPLOYMENT_DESCRIPTOR, this::setGenerateJbossDeploymentDescriptor);

        convertPropertyToBooleanAndWriteBack(USE_ANNOTATED_BASE_PATH, this::setUseAnnotatedBasePath);

        convertPropertyToBooleanAndWriteBack(GENERATE_NON_SPRING_APPLICATION, this::setGenerateNonSpringApplication);

        convertPropertyToBooleanAndWriteBack(USE_ABSTRACTION_FOR_FILES, this::setUseAbstractionForFiles);

        supportingFiles.clear(); // Don't need extra files provided by AbstractJAX-RS & Java Codegen

        supportingFiles.add(new SupportingFile("server/pom.mustache", "", "pom.xml")
                .doNotOverwrite());

        supportingFiles.add(new SupportingFile("server/openapi-generator-ignore.mustache", "", ".openapi-generator-ignore")
                .doNotOverwrite());

        if (this.generateSpringApplication) {
            supportingFiles.add(new SupportingFile("server/readme.md", "", "readme.md")
                    .doNotOverwrite());
            supportingFiles.add(new SupportingFile("server/ApplicationContext.xml.mustache",
                    ("src/main/resources"), "ApplicationContext.xml")
                    .doNotOverwrite());
            supportingFiles.add(new SupportingFile("server/web.mustache",
                    ("src/main/webapp/WEB-INF"), "web.xml")
                    .doNotOverwrite());
            supportingFiles.add(new SupportingFile("server/context.xml.mustache",
                    ("src/main/webapp/WEB-INF"), "context.xml")
                    .doNotOverwrite());

            // Jboss
            if (generateJbossDeploymentDescriptor) {
                supportingFiles.add(new SupportingFile("server/jboss-web.xml.mustache",
                        ("src/main/webapp/WEB-INF"), "jboss-web.xml")
                        .doNotOverwrite());

            }

            // Spring Boot
            if (this.generateSpringBootApplication) {
                supportingFiles.add(new SupportingFile("server/SpringBootApplication.mustache",
                        (testFolder + '/' + apiPackage).replace(".", "/"), "SpringBootApplication.java")
                        .doNotOverwrite());
                supportingFiles.add(new SupportingFile("server/application.properties.mustache",
                        (testResourcesFolder + '/'), "application.properties")
                        .doNotOverwrite());

            }
        }

        if (this.generateNonSpringApplication) {
            supportingFiles.add(new SupportingFile("server/nonspring-web.mustache",
                    ("src/main/webapp/WEB-INF"), "web.xml")
                    .doNotOverwrite());
        }
    }

    @Override
    public String getName() {
        return "jaxrs-cxf";
    }

    @Override
    public void postProcessModelProperty(CodegenModel model, CodegenProperty property) {
        super.postProcessModelProperty(model, property);
        model.imports.remove("ApiModelProperty");
        model.imports.remove("ApiModel");
        model.imports.remove("JsonFormat");

        //Add imports for Jackson when model has inner enum
        if (isJackson()) {
            if (Boolean.FALSE.equals(model.isEnum) && Boolean.TRUE.equals(model.hasEnums)) {
                model.imports.add("JsonCreator");
                model.imports.add("JsonValue");
            }
        }
    }

    @Override
    public OperationsMap postProcessOperationsWithModels(OperationsMap objs, List<ModelMap> allModels) {
        objs = super.postProcessOperationsWithModels(objs, allModels);
        removeImport(objs, "java.util.List");
        return objs;
    }

    @Override
    public String getHelp() {
        return "Generates a Java JAXRS Server application based on Apache CXF framework.";
    }


}
