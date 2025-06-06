package org.openapitools.codegen.postman;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.swagger.v3.oas.models.tags.Tag;
import org.openapitools.codegen.*;
import org.openapitools.codegen.config.CodegenConfigurator;
import org.openapitools.codegen.languages.PostmanCollectionCodegen;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.openapitools.codegen.TestUtils.*;

public class PostmanCollectionCodegenTest {

    @Test
    public void testInitialConfigValues() throws Exception {
        final PostmanCollectionCodegen postmanCollectionCodegen = new PostmanCollectionCodegen();
        postmanCollectionCodegen.processOpts();

        assertEquals(postmanCollectionCodegen.folderStrategy, "Tags");
        assertEquals(postmanCollectionCodegen.postmanFile, "postman.json");

        assertNull(postmanCollectionCodegen.additionalProperties().get("codegenOperationsList"));
        assertNotNull(postmanCollectionCodegen.additionalProperties().get("codegenOperationsByTag"));
    }

    @Test
    public void testConfigWithFolderStrategyTags() throws Exception {
        final PostmanCollectionCodegen postmanCollectionCodegen = new PostmanCollectionCodegen();

        postmanCollectionCodegen.additionalProperties().put(postmanCollectionCodegen.FOLDER_STRATEGY, "Tags");
        postmanCollectionCodegen.processOpts();

        assertEquals(postmanCollectionCodegen.folderStrategy, "Tags");

        assertNull(postmanCollectionCodegen.additionalProperties().get("codegenOperationsList"));
        assertNotNull(postmanCollectionCodegen.additionalProperties().get("codegenOperationsByTag"));
    }

    @Test
    public void testBasicGeneration() throws IOException {

        File output = Files.createTempDirectory("postmantest_").toFile();
        output.deleteOnExit();

        final CodegenConfigurator configurator = new CodegenConfigurator()
                .setGeneratorName("postman-collection")
                .setInputSpec("src/test/resources/3_0/postman-collection/Basic.yaml")
                .setOutputDir(output.getAbsolutePath().replace("\\", "/"));

        final ClientOptInput clientOptInput = configurator.toClientOptInput();
        DefaultGenerator generator = new DefaultGenerator();
        List<File> files = generator.opts(clientOptInput).generate();

        files.forEach(File::deleteOnExit);

        Path path = Paths.get(output + "/postman.json");
        assertFileExists(path);
        assertFileContains(path, "\"schema\": \"https://schema.getpostman.com/json/collection/v2.1.0/collection.json\"");

        // verify request name (from summary)
        assertFileContains(path, "\"name\": \"Get User\"");
        // verify request endpoint
        TestUtils.assertFileContains(path, "\"name\": \"/users/:userId\"");

    }

    @Test
    public void testBasicGenerationJson() throws IOException {

        File output = Files.createTempDirectory("postmantest_").toFile();
        output.deleteOnExit();

        final CodegenConfigurator configurator = new CodegenConfigurator()
                .setGeneratorName("postman-collection")
                .setInputSpec("src/test/resources/3_0/postman-collection/BasicJson.json")
                .setOutputDir(output.getAbsolutePath().replace("\\", "/"));

        final ClientOptInput clientOptInput = configurator.toClientOptInput();
        DefaultGenerator generator = new DefaultGenerator();
        List<File> files = generator.opts(clientOptInput).generate();

        System.out.println(files);
        files.forEach(File::deleteOnExit);

        Path path = Paths.get(output + "/postman.json");
        assertFileExists(path);
        assertFileContains(path, "\"schema\": \"https://schema.getpostman.com/json/collection/v2.1.0/collection.json\"");
    }

    @Test
    public void testValidatePostmanJson() throws IOException {

        File output = Files.createTempDirectory("postmantest_").toFile();
        output.deleteOnExit();

        final CodegenConfigurator configurator = new CodegenConfigurator()
                .setGeneratorName("postman-collection")
                .setInputSpec("src/test/resources/3_0/postman-collection/SampleProject.yaml")
                .setOutputDir(output.getAbsolutePath().replace("\\", "/"));

        final ClientOptInput clientOptInput = configurator.toClientOptInput();
        DefaultGenerator generator = new DefaultGenerator();
        List<File> files = generator.opts(clientOptInput).generate();

        files.forEach(File::deleteOnExit);

        final ObjectMapper mapper = new ObjectMapper();
        mapper.readTree(new File(output + "/postman.json"));

    }

    @Test
    public void testVariables() throws Exception {

        File output = Files.createTempDirectory("postmantest_").toFile();
        output.deleteOnExit();

        final CodegenConfigurator configurator = new CodegenConfigurator()
                .setGeneratorName("postman-collection")
                .setInputSpec("src/test/resources/3_0/postman-collection/SampleProject.yaml")
                .addAdditionalProperty(PostmanCollectionCodegen.PATH_PARAMS_AS_VARIABLES, true)
                .setOutputDir(output.getAbsolutePath().replace("\\", "/"));

        final ClientOptInput clientOptInput = configurator.toClientOptInput();
        DefaultGenerator generator = new DefaultGenerator();
        List<File> files = generator.opts(clientOptInput).generate();

        files.forEach(File::deleteOnExit);

        Path path = Paths.get(output + "/postman.json");
        assertFileExists(path);

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(new File(output + "/postman.json"));
        // verify json has variables
        assertTrue(jsonNode.get("variable") instanceof ArrayNode);
        assertEquals(6, (jsonNode.get("variable")).size());

        // verify param userId (without default value)
        TestUtils.assertFileContains(path,
                "key\": \"userId\", \"value\": \"\", \"type\": \"number\"");
        // verify param groupId (with default value)
        TestUtils.assertFileContains(path,
                "key\": \"groupId\", \"value\": \"1\", \"type\": \"number\"");

        // verify request endpoint
        TestUtils.assertFileContains(path, "\"name\": \"/users/:userId\"");
        // verify path parameter value
        TestUtils.assertFileContains(path, "key\": \"userId\", \"value\": \"{{userId}}\",");

    }

    @Test
    public void testVariablesInRequestExample() throws Exception {

        File output = Files.createTempDirectory("postmantest_").toFile();
        output.deleteOnExit();

        final CodegenConfigurator configurator = new CodegenConfigurator()
                .setGeneratorName("postman-collection")
                .setInputSpec("src/test/resources/3_0/postman-collection/BasicVariablesInExample.yaml")
                .addAdditionalProperty(PostmanCollectionCodegen.PATH_PARAMS_AS_VARIABLES, true)
                .setOutputDir(output.getAbsolutePath().replace("\\", "/"));

        final ClientOptInput clientOptInput = configurator.toClientOptInput();
        DefaultGenerator generator = new DefaultGenerator();
        List<File> files = generator.opts(clientOptInput).generate();

        System.out.println(files);
        files.forEach(File::deleteOnExit);

        Path path = Paths.get(output + "/postman.json");
        assertFileExists(path);

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(new File(output + "/postman.json"));
        // verify json has variables
        assertTrue(jsonNode.get("variable") instanceof ArrayNode);
        assertEquals(4, ((ArrayNode) jsonNode.get("variable")).size());

        assertFileContains(path, "{{MY_VAR_NAME}}");

        // verify request endpoint
        TestUtils.assertFileContains(path, "\"name\": \"/users/:userId\"");
        // verify path parameter value
        TestUtils.assertFileContains(path, "key\": \"userId\", \"value\": \"{{userId}}\",");

    }

    @Test
    public void testSkipPostmanVariables() throws Exception {

        File output = Files.createTempDirectory("postmantest_").toFile();
        output.deleteOnExit();

        final CodegenConfigurator configurator = new CodegenConfigurator()
                .setGeneratorName("postman-collection")
                .addAdditionalProperty(PostmanCollectionCodegen.POSTMAN_VARIABLES, false)
                .setInputSpec("src/test/resources/3_0/postman-collection/BasicVariablesInExample.yaml")
                .addAdditionalProperty(PostmanCollectionCodegen.PATH_PARAMS_AS_VARIABLES, true)
                .setOutputDir(output.getAbsolutePath().replace("\\", "/"));

        final ClientOptInput clientOptInput = configurator.toClientOptInput();
        DefaultGenerator generator = new DefaultGenerator();
        List<File> files = generator.opts(clientOptInput).generate();

        System.out.println(files);
        files.forEach(File::deleteOnExit);

        Path path = Paths.get(output + "/postman.json");
        assertFileExists(path);

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(new File(output + "/postman.json"));
        // verify json has variables
        assertTrue(jsonNode.get("variable") instanceof ArrayNode);
        assertEquals(2, ((ArrayNode) jsonNode.get("variable")).size());
    }

    @Test
    public void testGenerateWithoutPathParamsVariables() throws Exception {

        File output = Files.createTempDirectory("postmantest_").toFile();
        output.deleteOnExit();

        final CodegenConfigurator configurator = new CodegenConfigurator()
                .setGeneratorName("postman-collection")
                .setInputSpec("src/test/resources/3_0/postman-collection/SampleProject.yaml")
                .setOutputDir(output.getAbsolutePath().replace("\\", "/"));

        DefaultGenerator generator = new DefaultGenerator();
        List<File> files = generator.opts(configurator.toClientOptInput()).generate();

        System.out.println(files);
        files.forEach(File::deleteOnExit);

        assertFileExists(Paths.get(output + "/postman.json"));

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(new File(output + "/postman.json"));
        // verify json has variables
        assertTrue(jsonNode.get("variable") instanceof ArrayNode);
        assertEquals(4, ((ArrayNode) jsonNode.get("variable")).size());
    }

    @Test
    public void testComponentExamples() throws Exception {

        File output = Files.createTempDirectory("postmantest_").toFile();
        output.deleteOnExit();

        final CodegenConfigurator configurator = new CodegenConfigurator()
                .setGeneratorName("postman-collection")
                .setInputSpec("src/test/resources/3_0/postman-collection/SampleProject.yaml")
                .setOutputDir(output.getAbsolutePath().replace("\\", "/"));

        final ClientOptInput clientOptInput = configurator.toClientOptInput();
        DefaultGenerator generator = new DefaultGenerator();
        List<File> files = generator.opts(clientOptInput).generate();

        System.out.println(files);
        files.forEach(File::deleteOnExit);

        Path path = Paths.get(output + "/postman.json");
        assertFileExists(path);
        // verify response body comes from components/examples
        assertFileContains(path, "\"name\": \"Example request for Get User\"");
        assertFileContains(path, "\"raw\": \"{\\n  \\\"id\\\" : 777,\\n  \\\"firstName\\\" : \\\"Alotta\\\",\\n \\\"lastName\\\" : \\\"Rotta\\\",\\n ");
    }

    @Test
    public void testNamingRequestsWithUrl() throws Exception {

        File output = Files.createTempDirectory("postmantest_").toFile();
        output.deleteOnExit();

        final CodegenConfigurator configurator = new CodegenConfigurator()
                .setGeneratorName("postman-collection")
                .setInputSpec("src/test/resources/3_0/postman-collection/SampleProject.yaml")
                .addAdditionalProperty(PostmanCollectionCodegen.PATH_PARAMS_AS_VARIABLES, true)
                .setOutputDir(output.getAbsolutePath().replace("\\", "/"));

        final ClientOptInput clientOptInput = configurator.toClientOptInput();
        DefaultGenerator generator = new DefaultGenerator();
        List<File> files = generator.opts(clientOptInput).generate();

        System.out.println(files);
        files.forEach(File::deleteOnExit);

        Path path = Paths.get(output + "/postman.json");
        assertFileExists(path);
        // verify request name (from path)
        assertFileContains(path, "\"name\": \"/users/:userId\"");
        // verify path parameter value
        TestUtils.assertFileContains(path, "key\": \"userId\", \"value\": \"{{userId}}\",");

    }

    @Test
    public void testExampleFromSchema() throws IOException, Exception {

        File output = Files.createTempDirectory("postmantest_").toFile();
        output.deleteOnExit();

        final CodegenConfigurator configurator = new CodegenConfigurator()
                .setGeneratorName("postman-collection")
                .addAdditionalProperty(PostmanCollectionCodegen.REQUEST_PARAMETER_GENERATION, "Schema")
                .setInputSpec("src/test/resources/3_0/postman-collection/SampleProject.yaml")
                .setOutputDir(output.getAbsolutePath().replace("\\", "/"));

        DefaultGenerator generator = new DefaultGenerator();
        List<File> files = generator.opts(configurator.toClientOptInput()).generate();

        System.out.println(files);
        files.forEach(File::deleteOnExit);

        Path path = Paths.get(output + "/postman.json");
        assertFileExists(path);
        // verify request name (from path)
        assertFileContains(path, "{\\n \\\"firstName\\\": \\\"<string>\\\",\\n \\\"lastName\\\": \\\"<string>\\\",\\n \\\"email\\\": \\\"<string>\\\",\\n \\\"dateOfBirth\\\": \\\"<date>\\\"\\n}");

    }

    @Test
    public void testSecuritySchemes() throws Exception {

        File output = Files.createTempDirectory("postmantest_").toFile();
        output.deleteOnExit();

        final CodegenConfigurator configurator = new CodegenConfigurator()
                .setGeneratorName("postman-collection")
                .setInputSpec("src/test/resources/3_0/postman-collection/SampleProject.yaml")
                .setOutputDir(output.getAbsolutePath().replace("\\", "/"));

        final ClientOptInput clientOptInput = configurator.toClientOptInput();
        DefaultGenerator generator = new DefaultGenerator();
        List<File> files = generator.opts(clientOptInput).generate();

        System.out.println(files);
        files.forEach(File::deleteOnExit);

        Path path = Paths.get(output + "/postman.json");
        assertFileExists(path);
        // check auth basic (1st security scheme in OpenAPI file)
        assertFileContains(path, "\"auth\": { \"type\": \"basic\", \"basic\": [");
        // check auth apiKey NOT found
        assertFileNotContains(path, "\"auth\": { \"type\": \"apikey\", \"apikey\": [");
    }

    @Test
    public void extractExampleByName() {
        String str = "#/components/examples/get-user-basic";

        assertEquals("get-user-basic", new PostmanCollectionCodegen().extractExampleByName(str));
    }

    @Test
    public void mapToPostmanType() {
        assertEquals("string", new PostmanCollectionCodegen().mapToPostmanType("String"));
        assertEquals("number", new PostmanCollectionCodegen().mapToPostmanType("integer"));
        assertEquals("any", new PostmanCollectionCodegen().mapToPostmanType("object"));
    }

    @Test
    public void testJsonExampleIncludingValueWithCommas() throws Exception {

        File output = Files.createTempDirectory("postmantest_").toFile();
        output.deleteOnExit();

        final CodegenConfigurator configurator = new CodegenConfigurator()
                .setGeneratorName("postman-collection")
                .setInputSpec("src/test/resources/3_0/postman-collection/JsonWithCommasInJsonExample.json")
                .setOutputDir(output.getAbsolutePath().replace("\\", "/"));

        final ClientOptInput clientOptInput = configurator.toClientOptInput();
        DefaultGenerator generator = new DefaultGenerator();
        List<File> files = generator.opts(clientOptInput).generate();

        System.out.println(files);
        files.forEach(File::deleteOnExit);

        Path path = Paths.get(output + "/postman.json");
        assertFileExists(path);
        // check value with commas within quotes
        assertFileContains(path, "\\\"acceptHeader\\\" : \\\"text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8\\\"");
    }

    @Test
    public void testGeneratedVariables() throws Exception {

        File output = Files.createTempDirectory("postmantest_").toFile();
        output.deleteOnExit();

        final CodegenConfigurator configurator = new CodegenConfigurator()
                .setGeneratorName("postman-collection")
                .setInputSpec("src/test/resources/3_0/postman-collection/BasicVariablesInExample.yaml")
                .setOutputDir(output.getAbsolutePath().replace("\\", "/"));

        final ClientOptInput clientOptInput = configurator.toClientOptInput();
        DefaultGenerator generator = new DefaultGenerator();
        List<File> files = generator.opts(clientOptInput).generate();

        System.out.println(files);
        files.forEach(File::deleteOnExit);

        Path path = Paths.get(output + "/postman.json");
        assertFileExists(path);

        assertFileContains(path, "\\\"createDate\\\" : \\\"{{$guid}}\\\"");
        assertFileContains(path, "\\\"dateOfBirth\\\" : \\\"{{$isoTimestamp}}\\\"");

    }

    @Test
    public void testSkipGeneratedVariables() throws IOException, Exception {

        File output = Files.createTempDirectory("postmantest_").toFile();
        output.deleteOnExit();

        final CodegenConfigurator configurator = new CodegenConfigurator()
                .setGeneratorName("postman-collection")
                .addAdditionalProperty(PostmanCollectionCodegen.POSTMAN_GUID, false)
                .addAdditionalProperty(PostmanCollectionCodegen.POSTMAN_ISO_TIMESTAMP, false)
                .setInputSpec("src/test/resources/3_0/postman-collection/BasicVariablesInExample.yaml")
                .setOutputDir(output.getAbsolutePath().replace("\\", "/"));

        final ClientOptInput clientOptInput = configurator.toClientOptInput();
        DefaultGenerator generator = new DefaultGenerator();
        List<File> files = generator.opts(clientOptInput).generate();

        System.out.println(files);
        files.forEach(File::deleteOnExit);

        Path path = Paths.get(output + "/postman.json");
        assertFileExists(path);

        assertFileNotContains(path, "\\\"createDate\\\" : \\\"{{$guid}}\\\"");
        assertFileNotContains(path, "\\\"dateOfBirth\\\" : \\\"{{$isoTimestamp}}\\\"");

    }

    @Test
    public void testFormatDescription() {

        final String DESCRIPTION = "## Description \n\n Text with markdown \n";
        final String EXPECTED = "## Description \\n\\n Text with markdown \\n";

        assertEquals(EXPECTED, new PostmanCollectionCodegen().formatDescription(DESCRIPTION));
    }

    @Test
    public void testExtractPlaceholders() {
        final String INPUT = "Input with {{placeholder_1}} and {{placeholder_2}}";

        assertEquals(2, new PostmanCollectionCodegen().extractPlaceholders(INPUT).size());
    }

    @Test
    public void testExtractWithDuplicatePlaceholders() {
        final String INPUT = "Input with {{placeholder_1}}, {{placeholder_2}} and again {{placeholder_2}}";

        assertEquals(2, new PostmanCollectionCodegen().extractPlaceholders(INPUT).size());
    }

    @Test
    public void testExtractPlaceholdersIncludingGuidFormula() {
        final String INPUT = "Input with {{placeholder_1}} and {{$guid}}";

        assertEquals(1, new PostmanCollectionCodegen().extractPlaceholders(INPUT).size());
    }

    @Test
    public void testIsPostmanDynamicVariable() {
        assertTrue(new PostmanCollectionCodegen().isPostmanDynamicVariable("$guid"));
    }

    // test helpers
    @Test
    public void getPostmanTypeNumber() {
        CodegenProperty codegenProperty = new CodegenProperty();
        codegenProperty.isNumeric = true;

        assertEquals("number", new PostmanCollectionCodegen().getPostmanType(codegenProperty));
    }

    @Test
    public void getPostmanTypeDate() {
        CodegenProperty codegenProperty = new CodegenProperty();
        codegenProperty.isDate = true;

        assertEquals("date", new PostmanCollectionCodegen().getPostmanType(codegenProperty));
    }

    @Test
    public void getPostmanTypeString() {
        CodegenProperty codegenProperty = new CodegenProperty();
        codegenProperty.isString = true;

        assertEquals("string", new PostmanCollectionCodegen().getPostmanType(codegenProperty));
    }

    @Test
    public void getExampleFromSchema() {
        final String EXPECTED = "{\\n \\\"firstname\\\": \\\"<string>\\\",\\n \\\"lastname\\\": \\\"<string>\\\",\\n \\\"age\\\": \\\"<number>\\\",\\n \\\"birthDate\\\": \\\"<date>\\\"\\n}";

        CodegenParameter codegenParameter = new CodegenParameter();
        codegenParameter.vars.add(new CodegenProperty() {{
            baseName = "firstname";
            isString = true;
        }});
        codegenParameter.vars.add(new CodegenProperty() {{
            baseName = "lastname";
            isString = true;
        }});
        codegenParameter.vars.add(new CodegenProperty() {{
            baseName = "age";
            isNumeric = true;
        }});
        codegenParameter.vars.add(new CodegenProperty() {{
            baseName = "birthDate";
            isDate = true;
        }});

        assertEquals(EXPECTED, new PostmanCollectionCodegen().getJsonFromSchema(codegenParameter));
    }

    @Test
    public void formatJson() {

        final String EXPECTED = "{\\n  \\\"id\\\" : 1,\\n  \\\"city\\\" : \\\"Amsterdam\\\"\\n}";
        final String JSON = "{\"id\":1,\"city\":\"Amsterdam\"}";

        assertEquals(EXPECTED, new PostmanCollectionCodegen().formatJson(JSON));

    }

    @Test
    public void formatJsonIncludingCommas() {

        final String EXPECTED = "{\\n  \\\"id\\\" : 1,\\n  \\\"list\\\" : \\\"AMS,LON,ROM\\\"\\n}";
        final String JSON = "{\"id\":1,\"list\":\"AMS,LON,ROM\"}";

        assertEquals(EXPECTED, new PostmanCollectionCodegen().formatJson(JSON));

    }

    @Test
    public void getAttributesFromJson() {

        final String JSON = "{\"id\":1,\"list\":\"AMS,LON,ROM\"}";
        assertEquals(2, new PostmanCollectionCodegen().getAttributes(JSON).length);

    }

    @Test
    public void convertObjectNodeToJson() {

        final String EXPECTED = "{\\n  \\\"id\\\" : 1,\\n  \\\"city\\\" : \\\"Amsterdam\\\"\\n}";

        ObjectMapper mapper = new ObjectMapper();
        ObjectNode city = mapper.createObjectNode();

        city.put("id", 1);
        city.put("city", "Amsterdam");

        assertEquals(EXPECTED, new PostmanCollectionCodegen().convertToJson(city));

    }

    @Test
    public void convertObjectNodeIncludingDoubleQuoteToJson() {

        final String EXPECTED = "{\\n  \\\"id\\\" : 1,\\n  \\\"city\\\" : \\\"it is \\\\\"Amsterdam\\\\\" \\\"\\n}";

        ObjectMapper mapper = new ObjectMapper();
        ObjectNode city = mapper.createObjectNode();

        city.put("id", 1);
        city.put("city", "it is \"Amsterdam\" ");

        assertEquals(EXPECTED, new PostmanCollectionCodegen().convertToJson(city));

    }

    @Test
    public void convertLinkedHashMapToJson() {

        final String EXPECTED = "{\\n \\\"id\\\": 1,\\n \\\"city\\\": \\\"Amsterdam\\\",\\n \\\"safe\\\": true\\n}";

        LinkedHashMap<String, Object> city = new LinkedHashMap<>();
        city.put("id", 1);
        city.put("city", "Amsterdam");
        city.put("safe", true);

        assertEquals(EXPECTED, new PostmanCollectionCodegen().convertToJson(city));

    }

    @Test
    public void convertNestedLinkedHashMapToJson() {

        final String EXPECTED =
                "{\\n " +
                        "\\\"id\\\": 1,\\n \\\"city\\\": \\\"Amsterdam\\\",\\n " +
                        "\\\"country\\\": {\\n \\\"id\\\": 2,\\n \\\"code\\\": \\\"NL\\\"\\n}" +
                        "\\n}";

        LinkedHashMap<String, Object> city = new LinkedHashMap<>();
        city.put("id", 1);
        city.put("city", "Amsterdam");
        LinkedHashMap<String, Object> country = new LinkedHashMap<>();
        country.put("id", 2);
        country.put("code", "NL");
        city.put("country", country);

        assertEquals(EXPECTED, new PostmanCollectionCodegen().convertToJson(city));

    }

    @Test
    public void convertNestedArrayListToJson() {

        final String EXPECTED =
                "{\\n " +
                        "\\\"id\\\": 1,\\n \\\"city\\\": \\\"Amsterdam\\\",\\n " +
                        "\\\"tags\\\": [\\\"ams\\\", \\\"adam\\\"]" +
                        "\\n}";

        LinkedHashMap<String, Object> city = new LinkedHashMap<>();
        city.put("id", 1);
        city.put("city", "Amsterdam");
        ArrayList<String> tags = new ArrayList<>();
        tags.add("ams");
        tags.add("adam");
        city.put("tags", tags);

        assertEquals(EXPECTED, new PostmanCollectionCodegen().convertToJson(city));

    }

    @Test
    public void convertNestedEmptyArrayListToJson() {

        final String EXPECTED =
                "{\\n " +
                        "\\\"id\\\": 1,\\n \\\"city\\\": \\\"Amsterdam\\\",\\n " +
                        "\\\"tags\\\": []" +
                        "\\n}";

        LinkedHashMap<String, Object> city = new LinkedHashMap<>();
        city.put("id", 1);
        city.put("city", "Amsterdam");
        ArrayList<String> tags = new ArrayList<>();
        city.put("tags", tags);

        assertEquals(EXPECTED, new PostmanCollectionCodegen().convertToJson(city));

    }

    @Test
    public void testAddToList() {

        PostmanCollectionCodegen postmanCollectionCodegen = new PostmanCollectionCodegen();

        CodegenOperation operationUsers = new CodegenOperation();
        operationUsers.path = "/users";
        postmanCollectionCodegen.addToList(operationUsers);

        CodegenOperation operationGroups = new CodegenOperation();
        operationGroups.path = "/groups";
        postmanCollectionCodegen.addToList(operationGroups);

        CodegenOperation operationUserId = new CodegenOperation();
        operationUserId.path = "/users/{id}";
        postmanCollectionCodegen.addToList(operationUserId);

        assertEquals(3, postmanCollectionCodegen.codegenOperationsList.size());
        // verify order
        assertEquals("/groups", postmanCollectionCodegen.codegenOperationsList.get(0).path);
        assertEquals("/users", postmanCollectionCodegen.codegenOperationsList.get(1).path);
        assertEquals("/users/{id}", postmanCollectionCodegen.codegenOperationsList.get(2).path);
    }

    @Test
    public void testAddToMap() {

        PostmanCollectionCodegen postmanV2Generator = new PostmanCollectionCodegen();

        CodegenOperation operationUsers = new CodegenOperation();
        operationUsers.path = "/users";
        operationUsers.tags = new ArrayList<>(Arrays.asList(new Tag().name("basic")));
        postmanV2Generator.addToMap(operationUsers);

        CodegenOperation operationGroups = new CodegenOperation();
        operationGroups.path = "/groups";
        operationGroups.tags = new ArrayList<>(Arrays.asList(new Tag().name("basic")));
        postmanV2Generator.addToMap(operationGroups);

        CodegenOperation operationUserId = new CodegenOperation();
        operationUserId.path = "/users/{id}";
        operationUserId.tags = new ArrayList<>(Arrays.asList(new Tag().name("basic")));
        postmanV2Generator.addToMap(operationUserId);

        // verify tag 'basic'
        assertEquals(1, postmanV2Generator.codegenOperationsByTag.size());
        assertEquals(true, postmanV2Generator.codegenOperationsByTag.containsKey("basic"));

        List<CodegenOperation> operations = postmanV2Generator.codegenOperationsByTag.get("basic");
        // verify order
        assertEquals("/groups", operations.get(0).path);
        assertEquals("/users", operations.get(1).path);
        assertEquals("/users/{id}", operations.get(2).path);
    }

    @Test
    public void testAddToMapUsingDefaultTag() {

        PostmanCollectionCodegen postmanV2Generator = new PostmanCollectionCodegen();

        CodegenOperation operationUsers = new CodegenOperation();
        operationUsers.path = "/users";
        postmanV2Generator.addToMap(operationUsers);

        // verify tag 'default' is used
        assertEquals(1, postmanV2Generator.codegenOperationsByTag.size());
        assertEquals(true, postmanV2Generator.codegenOperationsByTag.containsKey("default"));
    }

    @Test
    public void testResponses() throws IOException {

        File output = Files.createTempDirectory("postmantest_").toFile();
        output.deleteOnExit();

        final CodegenConfigurator configurator = new CodegenConfigurator()
                .setGeneratorName("postman-collection")
                .setInputSpec("src/test/resources/3_0/postman-collection/SampleProject.yaml")
                .setOutputDir(output.getAbsolutePath().replace("\\", "/"));

        DefaultGenerator generator = new DefaultGenerator();
        List<File> files = generator.opts(configurator.toClientOptInput()).generate();

        System.out.println(files);
        //files.forEach(File::deleteOnExit);

        Path path = Paths.get(output + "/postman.json");
        TestUtils.assertFileExists(path);

        TestUtils.assertFileContains(path,  "\"response\": [\n" +
                "                                        {\"name\": \"User Updated\",\n" +
                "                                        \"code\": 200,\n" +
                "                                        \"status\": \"OK\",\n" +
                "                                        \"header\": [{\n" +
                "                                        \"key\": \"Content-Type\",\n" +
                "                                        \"value\": \"application/json\"}\n" +
                "                                        ],\n" +
                "                                        \"_postman_previewlanguage\": \"json\",\n" +
                "                                        \"cookie\": [],\n" +
                "                                        \"body\" : \"{\\n  \\\"id\\\" : 1,\\n");

        TestUtils.assertFileContains(path,  "\"response\": [\n" +
                "                                        {\"name\": \"User Updated\",\n" +
                "                                        \"code\": 200,\n" +
                "                                        \"status\": \"OK\",\n" +
                "                                        \"header\": [{\n" +
                "                                        \"key\": \"Content-Type\",\n" +
                "                                        \"value\": \"application/json\"}\n" +
                "                                        ],\n" +
                "                                        \"_postman_previewlanguage\": \"json\",\n" +
                "                                        \"cookie\": [],\n" +
                "                                        \"body\" : \"{\\n  \\\"id\\\" : 2,\\n");

    }

}
