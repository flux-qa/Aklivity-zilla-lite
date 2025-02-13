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
package io.aklivity.zilla.runtime.model.core.config;

import java.util.function.Function;

import io.aklivity.zilla.runtime.engine.config.ModelConfig;

public class Int64ModelConfig extends ModelConfig
{
    public static final String INT_64 = "int64";

    public final String format;
    public final long max;
    public final long min;
    public final long multiple;
    public final boolean exclusiveMax;
    public final boolean exclusiveMin;

    public Int64ModelConfig(
        String format,
        long max,
        long min,
        boolean exclusiveMax,
        boolean exclusiveMin,
        long multiple)
    {
        super(INT_64);
        this.format = format;
        this.max = max;
        this.min = min;
        this.exclusiveMax = exclusiveMax;
        this.exclusiveMin = exclusiveMin;
        this.multiple = multiple;
    }

    public static <T> Int64ModelConfigBuilder<T> builder(
        Function<ModelConfig, T> mapper)
    {
        return new Int64ModelConfigBuilder<>(mapper::apply);
    }

    public static Int64ModelConfigBuilder<Int64ModelConfig> builder()
    {
        return new Int64ModelConfigBuilder<>(Int64ModelConfig.class::cast);
    }
}
