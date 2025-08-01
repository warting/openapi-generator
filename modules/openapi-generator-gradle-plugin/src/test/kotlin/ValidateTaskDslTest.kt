package org.openapitools.generator.gradle.plugin

import org.gradle.testkit.runner.GradleRunner
import org.gradle.testkit.runner.TaskOutcome.FAILED
import org.gradle.testkit.runner.TaskOutcome.SUCCESS
import org.gradle.util.GradleVersion
import org.testng.annotations.DataProvider
import org.testng.annotations.Test
import java.io.File
import java.nio.file.Files.createTempDirectory
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class ValidateTaskDslTest : TestBase() {
    override var temp: File = createTempDirectory(javaClass.simpleName).toFile()

    @DataProvider(name = "gradle_version_provider")
    fun gradleVersionProvider(): Array<Array<String?>> = arrayOf(
        arrayOf(null), // uses the version of Gradle used to build the plugin itself
        arrayOf("8.7"),
        arrayOf("7.6.4")
    )

    private fun getGradleRunner(gradleVersion: String?): GradleRunner {
        val gradleRunner = GradleRunner.create()
        return if (gradleVersion.isNullOrBlank()) {
            //Use the current version of Gradle
            gradleRunner
        } else {
            gradleRunner.withGradleVersion(gradleVersion)
        }
    }

    @Test(dataProvider = "gradle_version_provider")
    fun `openApiValidate should fail on non-file spec`(gradleVersion: String?) {
        // Arrange
        withProject(
            """
            | plugins {
            |   id 'org.openapi.generator'
            | }
            |
            | openApiValidate {
            |   inputSpec = "some_location"
            | }
        """.trimMargin()
        )

        // Act
        val result = getGradleRunner(gradleVersion)
            .withProjectDir(temp)
            .withArguments("openApiValidate")
            .withPluginClasspath()
            .buildAndFail()

        // Assert
        val gradleActualVersion = gradleVersion ?: GradleVersion.current().version
        val gradleVersionParts = gradleActualVersion.split(".")
        val isBeforeGradle7 = (gradleVersionParts.isEmpty() || gradleVersionParts[0].toInt() < 7)
        val expectedMessage = if (isBeforeGradle7) {
            "some_location' specified for property 'inputSpec' does not exist"
        } else {
            "An input file was expected to be present but it doesn't exist."
        }
        assertTrue(
            result.output.contains(expectedMessage),
            "Unexpected/no message presented to the user for a spec pointing to an invalid URI."
        )
        assertEquals(
            FAILED, result.task(":openApiValidate")?.outcome,
            "Expected a failed run, but found ${result.task(":openApiValidate")?.outcome}"
        )
    }

    @Test(dataProvider = "gradle_version_provider")
    fun `openApiValidate should succeed on valid spec`(gradleVersion: String?) {
        // Arrange
        val projectFiles = mapOf(
            "spec.yaml" to javaClass.classLoader.getResourceAsStream("specs/petstore-v3.0.yaml")
        )

        withProject(
            """
            | plugins {
            |   id 'org.openapi.generator'
            | }
            |
            | openApiValidate {
            |   inputSpec = file("spec.yaml").absolutePath
            | }
        """.trimMargin(), projectFiles
        )

        // Act
        val result = getGradleRunner(gradleVersion)
            .withProjectDir(temp)
            .withArguments("openApiValidate")
            .withPluginClasspath()
            .build()

        // Assert
        assertTrue(
            result.output.contains("Spec is valid."),
            "Unexpected/no message presented to the user for a valid spec."
        )
        assertEquals(
            SUCCESS, result.task(":openApiValidate")?.outcome,
            "Expected a successful run, but found ${result.task(":openApiValidate")?.outcome}"
        )
    }

    @Test(dataProvider = "gradle_version_provider")
    fun `openApiValidate should fail on invalid spec`(gradleVersion: String?) {
        // Arrange
        val projectFiles = mapOf(
            "spec.yaml" to javaClass.classLoader.getResourceAsStream("specs/petstore-v3.0-invalid-due-to-missing-info-attribute.yaml")
        )
        withProject(
            """
            | plugins {
            |   id 'org.openapi.generator'
            | }
            |
            | openApiValidate {
            |   inputSpec = file('spec.yaml').absolutePath
            | }
        """.trimMargin(), projectFiles
        )

        // Act
        val result = getGradleRunner(gradleVersion)
            .withProjectDir(temp)
            .withArguments("openApiValidate")
            .withPluginClasspath()
            .buildAndFail()

        // Assert
        assertTrue(
            result.output.contains("Spec is invalid."),
            "Unexpected/no message presented to the user for an invalid spec."
        )
        assertTrue(
            result.output.contains("attribute info is missing"),
            "Spec validation detail"
        )
        assertEquals(
            FAILED, result.task(":openApiValidate")?.outcome,
            "Expected a failed run, but found ${result.task(":openApiValidate")?.outcome}"
        )
    }

    @Test(dataProvider = "gradle_version_provider")
    fun `openApiValidate should fail on invalid spec with duplicate 200 status code`(gradleVersion: String?) {
        // Arrange
        val projectFiles = mapOf(
            "spec.yaml" to javaClass.classLoader.getResourceAsStream("specs/petstore-v3.0-invalid-due-to-duplicate-200-status-code.yaml")
        )
        withProject(
            """
            | plugins {
            |   id 'org.openapi.generator'
            | }
            |
            | openApiValidate {
            |   inputSpec = file('spec.yaml').absolutePath
            | }
        """.trimMargin(), projectFiles
        )

        // Act
        val result = getGradleRunner(gradleVersion)
            .withProjectDir(temp)
            .withArguments("openApiValidate")
            .withPluginClasspath()
            .buildAndFail()

        // Assert
        assertTrue(
            result.output.contains("Spec is invalid."),
            "Unexpected/no message presented to the user for an invalid spec."
        )
        assertTrue(
            result.output.contains("Duplicate field 200"),
            "Spec validation detail"
        )
        assertEquals(
            FAILED, result.task(":openApiValidate")?.outcome,
            "Expected a failed run, but found ${result.task(":openApiValidate")?.outcome}"
        )
    }

    @Test(dataProvider = "gradle_version_provider")
    fun `validateGoodSpec as defined task should succeed on valid spec`(gradleVersion: String?) {
        // Arrange
        val projectFiles = mapOf(
            "spec.yaml" to javaClass.classLoader.getResourceAsStream("specs/petstore-v3.0.yaml")
        )

        withProject(
            """
            | plugins {
            |   id 'org.openapi.generator'
            | }
            |
            | task validateGoodSpec(type: org.openapitools.generator.gradle.plugin.tasks.ValidateTask) {
            |    inputSpec.set(file("spec.yaml").absolutePath)
            | }
        """.trimMargin(), projectFiles
        )

        // Act
        val result = getGradleRunner(gradleVersion)
            .withProjectDir(temp)
            .withArguments("validateGoodSpec")
            .withPluginClasspath()
            .build()

        // Assert
        assertTrue(
            result.output.contains("Spec is valid."),
            "Unexpected/no message presented to the user for a valid spec."
        )
        assertEquals(
            SUCCESS, result.task(":validateGoodSpec")?.outcome,
            "Expected a successful run, but found ${result.task(":validateGoodSpec")?.outcome}"
        )
    }

    @Test(dataProvider = "gradle_version_provider")
    fun `validateBadSpec as defined task should fail on invalid spec`(gradleVersion: String?) {
        // Arrange
        val projectFiles = mapOf(
            "spec.yaml" to javaClass.classLoader.getResourceAsStream("specs/petstore-v3.0-invalid-due-to-missing-info-attribute.yaml")
        )
        withProject(
            """
            | plugins {
            |   id 'org.openapi.generator'
            | }
            |
            | task validateBadSpec(type: org.openapitools.generator.gradle.plugin.tasks.ValidateTask) {
            |    inputSpec.set(file("spec.yaml").absolutePath)
            | }
        """.trimMargin(), projectFiles
        )

        // Act
        val result = getGradleRunner(gradleVersion)
            .withProjectDir(temp)
            .withArguments("validateBadSpec")
            .withPluginClasspath()
            .buildAndFail()

        // Assert
        assertTrue(
            result.output.contains("Spec is invalid."),
            "Unexpected/no message presented to the user for an invalid spec."
        )
        assertTrue(
            result.output.contains("attribute info is missing"),
            "Unexpected/no message presented to the user for an invalid spec."
        )
        assertEquals(
            FAILED, result.task(":validateBadSpec")?.outcome,
            "Expected a failed run, but found ${result.task(":validateBadSpec")?.outcome}"
        )
    }

    @Test(dataProvider = "gradle_version_provider")
    fun `openApiValidate should succeed with recommendations on valid spec`(gradleVersion: String?) {
        // Arrange
        val projectFiles = mapOf(
            "spec.yaml" to javaClass.classLoader.getResourceAsStream("specs/petstore-v3.0-recommend.yaml")
        )

        // recommend = true is the default
        withProject(
            """
            | plugins {
            |   id 'org.openapi.generator'
            | }
            |
            | openApiValidate {
            |   inputSpec = file("spec.yaml").absolutePath
            | }
        """.trimMargin(), projectFiles
        )

        // Act
        val result = getGradleRunner(gradleVersion)
            .withProjectDir(temp)
            .withArguments("openApiValidate")
            .withPluginClasspath()
            .build()

        // Assert
        assertTrue(
            result.output.contains("Spec is valid."),
            "Unexpected/no message presented to the user for a valid spec."
        )
        assertTrue(
            result.output.contains("Spec has issues or recommendations."),
            "Unexpected/no recommendations message presented to the user for a valid spec."
        )
        assertEquals(
            SUCCESS, result.task(":openApiValidate")?.outcome,
            "Expected a successful run, but found ${result.task(":openApiValidate")?.outcome}"
        )
    }

    @Test(dataProvider = "gradle_version_provider")
    fun `openApiValidate should fail with treatWarningsAsErrors on valid spec with warnings`(gradleVersion: String?) {
        // Arrange
        val projectFiles = mapOf(
            "spec.yaml" to javaClass.classLoader.getResourceAsStream("specs/petstore-v3.0-recommend.yaml")
        )

        withProject(
            """
            | plugins {
            |   id 'org.openapi.generator'
            | }
            |
            | openApiValidate {
            |   inputSpec = file("spec.yaml").absolutePath
            |   treatWarningsAsErrors = true
            | }
        """.trimMargin(), projectFiles
        )

        // Act
        val result = getGradleRunner(gradleVersion)
            .withProjectDir(temp)
            .withArguments("openApiValidate")
            .withPluginClasspath()
            .buildAndFail()

        // Assert
        assertTrue(
            result.output.contains("Spec has issues or recommendations."),
            "Unexpected/no message presented to the user for a valid spec."
        )
        assertTrue(
            result.output.contains("Failing validation."),
            "Expected validation to fail due to warnings, but no failure message was found."
        )
        assertEquals(
            FAILED, result.task(":openApiValidate")?.outcome,
            "Expected a failed run, but found ${result.task(":openApiValidate")?.outcome}"
        )
    }

    @Test(dataProvider = "gradle_version_provider")
    fun `openApiValidate should succeed without recommendations on valid spec`(gradleVersion: String?) {
        // Arrange
        val projectFiles = mapOf(
            "spec.yaml" to javaClass.classLoader.getResourceAsStream("specs/petstore-v3.0-recommend.yaml")
        )

        withProject(
            """
            | plugins {
            |   id 'org.openapi.generator'
            | }
            |
            | openApiValidate {
            |   inputSpec = file("spec.yaml").absolutePath
            |   recommend = false
            | }
        """.trimMargin(), projectFiles
        )

        // Act
        val result = getGradleRunner(gradleVersion)
            .withProjectDir(temp)
            .withArguments("openApiValidate")
            .withPluginClasspath()
            .build()

        // Assert
        assertTrue(
            result.output.contains("Spec is valid."),
            "Unexpected/no message presented to the user for a valid spec."
        )
        assertTrue(
            result.output.contains("Spec has issues or recommendations.").not(),
            "Unexpected/recommendations message presented to the user for a valid spec"
        )
        assertEquals(
            SUCCESS, result.task(":openApiValidate")?.outcome,
            "Expected a successful run, but found ${result.task(":openApiValidate")?.outcome}"
        )
    }
}
