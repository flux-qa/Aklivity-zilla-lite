/*
 * Copyright 2021-2024 Aklivity Inc
 *
 * Licensed under the Aklivity Community License (the "License"); you may not use
 * this file except in compliance with the License.  You may obtain a copy of the
 * License at
 *
 *   https://www.aklivity.io/aklivity-community-license/
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OF ANY KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations under the License.
 */
package io.aklivity.zilla.runtime.binding.http.filesystem.config;

import java.util.function.Function;

import io.aklivity.zilla.runtime.engine.config.ConditionConfig;

public final class HttpFileSystemConditionConfig extends ConditionConfig
{
    public final String path;
    public final String method;

    HttpFileSystemConditionConfig(
        String path,
        String method)
    {
        this.path = path;
        this.method = method;
    }

    public static HttpFileSystemConditionConfigBuilder<HttpFileSystemConditionConfig> builder()
    {
        return new HttpFileSystemConditionConfigBuilder<>(HttpFileSystemConditionConfig.class::cast);
    }

    public static <T> HttpFileSystemConditionConfigBuilder<T> builder(
        Function<ConditionConfig, T> mapper)
    {
        return new HttpFileSystemConditionConfigBuilder<>(mapper);
    }
}
