/*
 * Copyright (c) 2021 Toast Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import com.google.protobuf.gradle.proto
import com.google.protobuf.gradle.protobuf
import com.toasttab.protokt.gradle.protokt

apply(plugin = "kotlin-kapt")

localProtokt()
pureKotlin()
enablePublishing()
compatibleWithAndroid()
trackKotlinApiCompatibility()

protokt {
    lite = true
}

dependencies {
    api(project(":extensions:protokt-extensions-api"))
    api(project(":protokt-runtime"))

    protobuf(libraries.protobufJava)
    compileOnly(libraries.protobufJava)

    implementation(libraries.autoServiceAnnotations)

    add("kapt", libraries.autoService)
}

sourceSets {
    main {
        proto {
            srcDir("../protokt-runtime/src/main/resources")
        }
    }
}