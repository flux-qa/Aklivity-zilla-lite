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
package io.aklivity.zilla.runtime.exporter.prometheus.internal.printer;

import static io.aklivity.zilla.runtime.engine.metrics.Metric.Kind.COUNTER;
import static io.aklivity.zilla.runtime.engine.metrics.Metric.Unit.BYTES;
import static io.aklivity.zilla.runtime.engine.metrics.Metric.Unit.NANOSECONDS;

import java.util.Map;
import java.util.function.Function;

import org.agrona.collections.Object2ObjectHashMap;

import io.aklivity.zilla.runtime.engine.metrics.Metric;

public class PrometheusMetricDescriptor
{
    private final Function<String, Metric> metricResolver;
    private final Map<String, String> names;
    private final Map<String, String> kinds;
    private final Map<String, String> descriptions;

    public PrometheusMetricDescriptor(
        Function<String, Metric> metricResolver)
    {
        this.metricResolver = metricResolver;
        this.names = new Object2ObjectHashMap<>();
        this.kinds = new Object2ObjectHashMap<>();
        this.descriptions = new Object2ObjectHashMap<>();
    }

    public String kind(
        String internalName)
    {
        String result = kinds.get(internalName);
        if (result == null)
        {
            result = metricResolver.apply(internalName).kind().toString().toLowerCase();
            kinds.put(internalName, result);
        }
        return result;
    }

    public String name(
        String internalName)
    {
        return names.computeIfAbsent(internalName, this::externalName);
    }

    private String externalName(
        String internalName)
    {
        String result;
        Metric metric = metricResolver.apply(internalName);
        result = metric.name();
        result = result.replace('.', '_');
        if (metric.unit() == BYTES)
        {
            result += "_bytes";
        }
        else if (metric.unit() == NANOSECONDS)
        {
            // we are converting nanoseconds values to milliseconds
            result += "_milliseconds";
        }
        if (metric.kind() == COUNTER)
        {
            result += "_total";
        }
        return result;
    }

    public boolean milliseconds(
        String internalName)
    {
        // we are converting nanoseconds values to milliseconds
        Metric metric = metricResolver.apply(internalName);
        return metric.unit() == NANOSECONDS;
    }

    public String description(
        String internalName)
    {
        String result = descriptions.get(internalName);
        if (result == null)
        {
            result = metricResolver.apply(internalName).description();
            descriptions.put(internalName, result);
        }
        return result;
    }
}
