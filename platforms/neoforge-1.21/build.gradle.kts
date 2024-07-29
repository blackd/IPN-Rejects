/*
 * Inventory Profiles Next
 *
 *   Copyright (c) 2019-2020 jsnimda <7615255+jsnimda@users.noreply.github.com>
 *   Copyright (c) 2021-2022 Plamen K. Kosseff <p.kosseff@gmail.com>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 *     along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import com.matthewprenger.cursegradle.CurseExtension
import com.matthewprenger.cursegradle.CurseProject
import net.neoforged.gradle.dsl.common.runs.run.Run
import com.modrinth.minotaur.dependencies.ModDependency
import org.anti_ad.mc.ipnrejects.buildsrc.configureCommon
import org.anti_ad.mc.ipnrejects.buildsrc.forgeCommonAfterEvaluate
import org.anti_ad.mc.ipnrejects.buildsrc.neoForgeCommonDependency
import org.anti_ad.mc.ipnrejects.buildsrc.platformsCommonConfig
import org.anti_ad.mc.ipnrejects.buildsrc.registerMinimizeJarTask
import proguard.gradle.ProGuardTask

val supported_minecraft_versions = listOf("1.21")
val mod_loader = "neoforge"
val mod_version = project.version
val minecraft_version = "1.21"
val minecraft_version_string = "1.21"
val forge_version = "21.0.141-beta"
val mod_artefact_version = project.ext["mod_artefact_version"]
val kotlin_for_forge_version = "5.3.0"
val mappingsMap = mapOf("channel" to "official",
                        "version" to "1.21")
val libIPN_version = "${project.name}:${project.ext["libIPN_version"]}"

logger.lifecycle("""
    ***************************************************
    Processing "${project.path}"
    supported versions: $supported_minecraft_versions
    loader: $mod_loader
    mod version: $mod_version
    building against MC: $minecraft_version
    ***************************************************
    """.trimIndent())

buildscript {
    repositories {
        maven { url = uri("https://maven.minecraftforge.net/maven") }
        mavenCentral()


        maven { url = uri("https://repo.spongepowered.org/repository/maven-public/") }
    }
    dependencies {
        classpath("com.guardsquare:proguard-gradle:7+")
    }
}

plugins {
    kotlin("jvm")
    kotlin("plugin.serialization")
    java
    idea
    `maven-publish`
    signing
    id("com.matthewprenger.cursegradle")
    id("com.modrinth.minotaur")
    id("io.github.goooler.shadow")
    id("net.neoforged.gradle.userdev")
    id ("net.neoforged.gradle.mixin") version "7.+"

}

configureCommon(JavaVersion.VERSION_21)
platformsCommonConfig()



java {
    toolchain.languageVersion = JavaLanguageVersion.of(21)
    sourceCompatibility = JavaVersion.VERSION_21
    targetCompatibility = JavaVersion.VERSION_21
}

group = "org.anti-ad.mc"

repositories {
    maven { url = uri("https://maven.minecraftforge.net/maven") }
    mavenCentral()
    mavenLocal()
    maven { url = uri("https://repo.spongepowered.org/repository/maven-public/") }

    maven {
        url = uri("https://www.cursemaven.com")
        content {
            includeGroup ("curse.maven")
        }
    }
    gradlePluginPortal()
/*
    maven {
        name = "kotlinforforge"
        url = uri("https://thedarkcolour.github.io/KotlinForForge/")
    }
*/
}

neoForgeCommonDependency(minecraft_version,
                      forge_version,
                      kotlin_for_forge_version,
                      libIPN_version)

dependencies {

}


afterEvaluate {
    project.sourceSets.getByName("main") {
        this.java.srcDirs("./src/shared/java")
        this.java.srcDirs("./src/shared/kotlin")
        resources.srcDirs("src/shared/resources")
    }
}

tasks.withType<JavaCompile>().all {
    dependsOn("processResources")
}


if ("true" == System.getProperty("idea.sync.active")) {
    afterEvaluate {
        tasks.withType<JavaCompile>().all {
            options.annotationProcessorPath = files()
            dependsOn("processResources")
        }
    }
}




tasks.register<Copy>("copyMixinMappings") {
    dependsOn("compileJava")
    val inName = layout.buildDirectory.file("tmp/compileJava/mixin.refmap.json")
    val outName = layout.buildDirectory.file("resources/main/")
    from(inName)
    into(outName)
    rename {
        "ipn-rejects.refmap.json"
    }
    dependsOn("compileJava")
}


tasks.jar {
    manifest {
        attributes(mapOf(
            "MixinConfigs" to "mixins.ipn-rejects.json, mixins.ipn-rejects.cheats.json"
        ))
    }
    dependsOn("copyMixinMappings")
}

val shadowJarTask = tasks.named<ShadowJar>("shadowJar") {

    configurations = listOf(project.configurations["shaded"])

    archiveClassifier.set("shaded")
    setVersion(project.version)

    exclude("kotlin/**")
    exclude("kotlinx/**")

    exclude("**/*.stg")
    exclude("**/*.st")
    exclude("mappings/mappings.tiny")
    exclude("com/ibm/**")
    exclude("org/glassfish/**")
    exclude("org/intellij/**")
    exclude("org/jetbrains/**")
    exclude("org/jline/**")
    exclude("net/minecraftforge/**")
    exclude("io/netty/**")

    exclude("META-INF/maven/**")
    exclude("META-INF/com.android.tools/**")
    exclude("META-INF/proguard/**")
    exclude("META-INF/services/**")

    dependsOn("copyMixinMappings")
    minimize()
}.get()

val proguard by tasks.registering(ProGuardTask::class) {

    configuration("../../proguard.txt")
    printmapping {
        project.layout.buildDirectory.file("proguard/mappings.map")
    }

    val inName = shadowJarTask.archiveFile.get().asFile.absolutePath

    injars(inName)
    outjars("build/libs/${shadowJarTask.archiveFileName.get().replace("-shaded", "-all-proguard")}")

    doFirst {
        val classpath = configurations.runtimeClasspath.get().files + configurations.compileClasspath.get().files
        libraryjars( classpath)
    }

    dependsOn(shadowJarTask)
}

val minimizeJar = registerMinimizeJarTask()

afterEvaluate {
    forgeCommonAfterEvaluate(mod_loader, minecraft_version, mod_artefact_version?.toString().orEmpty())
}

var rcltName = ""

configurations {
    implementation.get().extendsFrom(this.findByName("shadedApi"))
}


mixin {
    config("mixins.ipn-rejects.json")
    config("mixins.ipn-rejects.cheats.json")
}


minecraft {
    mappings.version(mappingsMap)
}

runs {
    runs {
        val runConfig = Action<Run> {
            systemProperties(mapOf(
                    //"forge.logging.markers" to "SCAN,REGISTRIES,REGISTRYDUMP",
                    "forge.logging.console.level" to "debug",
                    "mixin.env.remapRefMap" to "true",
                    "mixin.env.refMapRemappingFile" to "${projectDir}/build/createSrgToMcp/output.srg",
                    "mixin.debug.verbose" to "true",
                    "mixin.debug.export" to "true",
                    "mixin.debug.dumpTargetOnFailure" to "true"
                                  ))
            programArguments("-mixin.config=mixins.ipn-rejects.json")
            programArguments("-mixin.config=mixins.ipn-rejects.cheats.json")
            programArguments("--width=1280", "--height=720", "--username=DEV")
            if (JavaVersion.current() >= JavaVersion.VERSION_11) {
                programArgument("--add-exports=java.base/sun.security.util=ALL-UNNAMED")
                programArgument("--add-opens=java.base/java.util.jar=ALL-UNNAMED")
            }
            //this.forceExit = false
        }
        named("client", runConfig)
        named("client") {
            workingDirectory.set(project.file("run"))
        }
        //create("server", runConfig)
        //create("data", runConfig)
    }
}


afterEvaluate {
    tasks.forEach {
        logger.info("******************* found task: {} {} {}", it, it.name, it.group)
    }
}

val sourceJar = tasks.create<Jar>("sourcesJar") {
    from(sourceSets["main"]?.allSource)
    archiveClassifier.set("sources")
    exclude("org/anti_ad/mc/common/gen/*.tokens")
}

publishing {
    repositories {
        maven {
            val releasesRepoUrl = "https://maven.ipn-mod.org/releases"
            val snapshotsRepoUrl = "https://maven.ipn-mod.org/snapshots"
            logger.lifecycle("project.ext[\"mod_artefact_is_release\"] = ${project.ext["mod_artefact_is_release"]}")
            name = "ipnOfficialRepo"
            credentials(PasswordCredentials::class)
            authentication {
                create<BasicAuthentication>("basic")
            }
            url = uri(if (project.ext["mod_artefact_is_release"] as Boolean) releasesRepoUrl else snapshotsRepoUrl)
        }
    }
    publications {
        create<MavenPublication>("maven") {
            groupId = "org.anti_ad.mc"
            artifactId = "${rootProject.name}-${project.name}"
            version = project.version.toString()
            artifact(minimizeJar.outputs.files.first())
            artifact(sourceJar) {
                classifier = "sources"
            }
            afterEvaluate {
                artifact(tasks.named<DefaultTask>("makeDevJar")) {
                    classifier = "dev"
                }
            }
        }
    }

}

configure<CurseExtension> {

    if (System.getenv("CURSEFORGE_DEPOY_TOKEN") != null && System.getenv("IPNEXT_RELEASE") != null) {
        apiKey = System.getenv("CURSEFORGE_DEPOY_TOKEN")
    }

    val clasifier = if (System.getenv("IPN_CLASSIFIER") != null) {
        System.getenv("IPN_CLASSIFIER")
    } else {
        ""
    }

    project(closureOf<CurseProject> {
        id = "686712"
        changelogType = "markdown"
        changelog = file("../../description/out/pandoc-release_notes.md")
        releaseType = "release"
        supported_minecraft_versions.forEach {
            if (!it.lowercase().contains("pre") && !it.lowercase().contains("shanpshot")) {
                this.addGameVersion(it)
            }
        }
        val remappedJarFile = minimizeJar.outputs.files.first()
        logger.lifecycle("""
        +*************************************************+
        Will release ${remappedJarFile.path}
        +*************************************************+
    """.trimIndent())
        mainArtifact(remappedJarFile, closureOf<com.matthewprenger.cursegradle.CurseArtifact> {
            displayName = "IPN Rejects-$mod_loader-$minecraft_version_string-$mod_version$clasifier"
        })

        afterEvaluate {
            uploadTask.dependsOn("build")
        }
        relations(closureOf<com.matthewprenger.cursegradle.CurseRelation> {
            requiredDependency("kotlin-for-forge")
            requiredDependency("libipn")
        })
        addGameVersion("NeoForge")
    })
    options(closureOf<com.matthewprenger.cursegradle.Options> {
        debug = false
        javaIntegration = false
        forgeGradleIntegration = mod_loader == "forge"
    })
}

// ============
// modrith
// ============


modrinth {

    this.failSilently.set(true)

    if (System.getenv("IPNEXT_RELEASE") != null) {
        token.set(System.getenv("MODRINTH_TOKEN"))
    }

    val clasifier = if (System.getenv("IPN_CLASSIFIER") != null) {
        System.getenv("IPN_CLASSIFIER")
    } else {
        ""
    }

    projectId.set("JI9IF5oh")
    versionNumber.set("$mod_loader-$minecraft_version-$mod_version$clasifier") // Will fail if Modrinth has this version already
    val remappedJarFile = minimizeJar.outputs.files.first()
    uploadFile.set(remappedJarFile as Any) // This is the java jar task. If it can't find the jar, try 'jar.outputs.getFiles().asPath' in place of 'jar'
    gameVersions.addAll(supported_minecraft_versions)
    logger.lifecycle("""
        +*************************************************+
        Will release ${remappedJarFile.path}
        +*************************************************+
    """.trimIndent())
    versionName.set("IPN Rejects $mod_version for $mod_loader$clasifier $minecraft_version_string")
    this.changelog.set(project.rootDir.resolve("description/out/pandoc-release_notes.md").readText())
    loaders.add(mod_loader)
    dependencies.set(
        mutableListOf(
            ModDependency("ordsPcFz", "required"),
            ModDependency("onSQdWhM", "required")))
}
