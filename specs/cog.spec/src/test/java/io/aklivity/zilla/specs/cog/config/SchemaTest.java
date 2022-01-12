/*
 * Copyright 2021-2022 Aklivity Inc.
 *
 * Aklivity licenses this file to you under the Apache License,
 * version 2.0 (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */
package io.aklivity.zilla.specs.cog.config;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;

import jakarta.json.JsonException;
import jakarta.json.JsonObject;

import org.junit.Rule;
import org.junit.Test;

public class SchemaTest
{
    @Rule
    public final ConfigSchemaRule schema = new ConfigSchemaRule()
        .schemaPatch("io/aklivity/zilla/specs/cog/schema/test.json")
        .configurationRoot("io/aklivity/zilla/specs/cog/config");

    @Test
    public void shouldValidateServerBinding()
    {
        JsonObject config = schema.validate("server.json");

        assertThat(config, not(nullValue()));
    }

    @Test
    public void shouldValidateServerBindingWithRoutesAndNoExit()
    {
        JsonObject config = schema.validate("server.binding.with.routes.and.no.exit.json");

        assertThat(config, not(nullValue()));
    }

    @Test(expected = JsonException.class)
    public void shouldRejectServerBindingWithNoType()
    {
        schema.validate("server.binding.with.no.type.json");
    }

    @Test(expected = JsonException.class)
    public void shouldRejectServerBindingWithNoKind()
    {
        schema.validate("server.binding.with.no.kind.json");
    }

    @Test(expected = JsonException.class)
    public void shouldRejectServerBindingWithNoExit()
    {
        schema.validate("server.binding.with.no.exit.json");
    }
}
