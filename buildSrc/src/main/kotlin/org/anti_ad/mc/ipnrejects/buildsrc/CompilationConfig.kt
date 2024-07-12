/*
 * Inventory Profiles Next
 *
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

package org.anti_ad.mc.ipnrejects.buildsrc

import org.gradle.api.JavaVersion
import org.gradle.api.Project
import org.gradle.api.plugins.ExtraPropertiesExtension
import org.gradle.api.plugins.JavaPluginExtension
import org.gradle.api.tasks.bundling.Jar
import org.gradle.api.tasks.compile.JavaCompile
import org.gradle.kotlin.dsl.*

import org.gradle.language.jvm.tasks.ProcessResources

fun Project.configureCompilation(javaVersion: JavaVersion = JavaVersion.VERSION_17, jarBaseName: String) {
    apply(plugin = "maven-publish")
    apply(plugin = "idea")

    configure<JavaPluginExtension> {
        sourceCompatibility = javaVersion
        targetCompatibility = javaVersion
    }

    tasks.withType<JavaCompile> {
        options.encoding = "UTF-8"
        doFirst {
            options.compilerArgs.add("-Xlint:all")
        }
    }



    tasks.withType<ProcessResources> {
        include("**/*")
        filesMatching(listOf("**/*.json", "**/*.txt", "**/*.toml", "**/*.xml")) {
            filter<org.apache.tools.ant.filters.ReplaceTokens>(
                "tokens" to mapOf(
                    "VERSION" to version.toString(),
                    "DESCRIPTION" to properties["ipnext.description"],
                    "WIKI" to properties["ipnext.docs"],
                    "SOURCE" to properties["ipnext.scm"],
                    "ISSUES" to properties["ipnext.tracker"],
                    "LICENSE" to properties["ipnext.license"],
                    "LIBIPN_VERSION" to (project.extensions["ext"] as ExtraPropertiesExtension)["libIPN_version"]))
        }
    }

    tasks.withType<Jar> {
        archiveBaseName.set("$jarBaseName-${archiveBaseName.get()}")
        from("../LICENSE", "../../LICENSE")
    }
}
