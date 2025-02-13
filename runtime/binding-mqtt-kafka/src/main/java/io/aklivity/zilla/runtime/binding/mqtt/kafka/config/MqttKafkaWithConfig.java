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
package io.aklivity.zilla.runtime.binding.mqtt.kafka.config;

import java.util.function.Function;

import io.aklivity.zilla.runtime.engine.config.WithConfig;

public class MqttKafkaWithConfig extends WithConfig
{
    public final String messages;

    public static MqttKafkaWithConfigBuilder<MqttKafkaWithConfig> builder()
    {
        return new MqttKafkaWithConfigBuilder<>(MqttKafkaWithConfig.class::cast);
    }

    public static <T> MqttKafkaWithConfigBuilder<T> builder(
        Function<WithConfig, T> mapper)
    {
        return new MqttKafkaWithConfigBuilder<>(mapper);
    }

    MqttKafkaWithConfig(
        long compositeId,
        String messages)
    {
        super(compositeId);
        this.messages = messages;
    }
}
