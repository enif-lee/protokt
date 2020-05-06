/*
 * Copyright (c) 2019 Toast Inc.
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

package com.toasttab.protokt.codegen.impl

import arrow.core.None
import arrow.core.Some
import com.toasttab.protokt.codegen.algebra.AST
import com.toasttab.protokt.codegen.impl.STAnnotator.Context
import com.toasttab.protokt.codegen.protoc.Message
import com.toasttab.protokt.codegen.protoc.Oneof
import com.toasttab.protokt.codegen.protoc.StandardField
import com.toasttab.protokt.codegen.protoc.TypeDesc
import com.toasttab.protokt.codegen.protoc.respectJavaPackage
import com.toasttab.protokt.codegen.template.Renderers.ConcatWithScope

internal fun resolveMapEntry(m: Message, ctx: Context) =
    MapTypeParams(
        (m.fields[0] as StandardField).unqualifiedTypeName,
        (m.fields[1] as StandardField).typePClass.renderName(ctx.pkg)
    )

internal data class MapTypeParams(
    val kType: String,
    val vType: String
)

internal fun oneOfScope(f: Oneof, type: String, ctx: Context) =
    ctx.stripEnclosingMessageNamePrefix(
        ctx.stripRootMessageNamePrefix(
            ConcatWithScope.render(
                scope = type,
                value = f.name
            )
        )
    )

internal fun String.emptyToNone() =
    if (isEmpty()) {
        None
    } else {
        Some(this)
    }

internal fun kotlinPackage(ast: AST<TypeDesc>) =
    resolvePackage(
        ast.data.desc.options,
        ast.data.desc.packageName,
        ast.data.desc.context.respectJavaPackage()
    )
