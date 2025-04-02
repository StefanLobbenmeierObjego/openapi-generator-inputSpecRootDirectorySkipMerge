import org.openapitools.generator.gradle.plugin.tasks.GenerateTask

plugins {
    id("java")
    id("org.openapi.generator") version "7.12.0"

}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
    useJUnitPlatform()
}

val generatedSourcesPath = "$projectDir/build/generated/sources/openapi"
val generateJunoApi by
tasks.registering(GenerateTask::class) {
    val packageName = "com.ista.juno"
    generatorName.set("java")
    inputSpecRootDirectory = "$projectDir/src/main/resources/juno"
    // inputSpecRootDirectorySkipMerge = false
    outputDir.set(generatedSourcesPath)
    library.set("webclient")
    generateModelTests.set(false)
    generateApiTests.set(false)

    modelNameSuffix.set("Dto")
    apiPackage.set(packageName)
    modelPackage.set("$packageName.model")

    configOptions.set(
        mapOf(
            "useJakartaEe" to "true",
            "sourceFolder" to "api",
            "useBeanValidation" to "true",
            "useTags" to "true",
        )
    )

    globalProperties.set(mapOf("skipFormModel" to "false"))

    supportingFilesConstrainedTo =
        listOf(
            // base package
            "ApiClient",
            "JavaTimeFormatter",
            "RFC3339DateFormat",
            "ServerConfiguration",
            "ServerVariable",
            "StringUtil",

            // auth
            "ApiKeyAuth",
            "Authentication",
            "HttpBasicAuth",
            "HttpBearerAuth",
        )
            .map { "$it.java" }
    modelFilesConstrainedTo = listOf("")
    apiFilesConstrainedTo = listOf("")
}