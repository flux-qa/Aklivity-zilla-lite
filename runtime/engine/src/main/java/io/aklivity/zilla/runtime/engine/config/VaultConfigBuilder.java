/*
 * Copyright 2021-2024 Aklivity Inc.
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
package io.aklivity.zilla.runtime.engine.config;

import static java.util.Objects.requireNonNull;

import java.util.function.Function;

public final class VaultConfigBuilder<T> extends ConfigBuilder<T, VaultConfigBuilder<T>>
{
    private final Function<VaultConfig, T> mapper;

    private String name;
    private String type;
    private OptionsConfig options;

    private String namespace;

    VaultConfigBuilder(
        Function<VaultConfig, T> mapper)
    {
        this.mapper = mapper;
    }

    @Override
    @SuppressWarnings("unchecked")
    protected Class<VaultConfigBuilder<T>> thisType()
    {
        return (Class<VaultConfigBuilder<T>>) getClass();
    }

    public VaultConfigBuilder<T> namespace(
        String namespace)
    {
        this.namespace = namespace;
        return this;
    }

    public VaultConfigBuilder<T> name(
        String name)
    {
        this.name = name;
        return this;
    }

    public VaultConfigBuilder<T> type(
        String type)
    {
        this.type = requireNonNull(type);
        return this;
    }

    public <C extends ConfigBuilder<VaultConfigBuilder<T>, C>> C options(
        Function<Function<OptionsConfig, VaultConfigBuilder<T>>, C> options)
    {
        return options.apply(this::options);
    }

    public VaultConfigBuilder<T> options(
        OptionsConfig options)
    {
        this.options = options;
        return this;
    }

    @Override
    public T build()
    {
        return mapper.apply(new VaultConfig(namespace, name, type, options));
    }
}
