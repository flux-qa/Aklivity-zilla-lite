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
package io.aklivity.zilla.runtime.binding.mqtt.internal.stream.server.v4;

import static io.aklivity.zilla.runtime.binding.mqtt.internal.MqttConfiguration.PUBLISH_TIMEOUT;
import static io.aklivity.zilla.runtime.binding.mqtt.internal.MqttConfigurationTest.KEEP_ALIVE_MINIMUM_NAME;
import static io.aklivity.zilla.runtime.binding.mqtt.internal.MqttConfigurationTest.SUBSCRIPTION_ID_NAME;
import static io.aklivity.zilla.runtime.engine.EngineConfiguration.ENGINE_DRAIN_ON_CLOSE;
import static java.util.concurrent.TimeUnit.SECONDS;
import static org.junit.rules.RuleChain.outerRule;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.DisableOnDebug;
import org.junit.rules.TestRule;
import org.junit.rules.Timeout;

import io.aklivity.k3po.runtime.junit.annotation.Specification;
import io.aklivity.k3po.runtime.junit.rules.K3poRule;
import io.aklivity.zilla.runtime.engine.test.EngineRule;
import io.aklivity.zilla.runtime.engine.test.annotation.Configuration;
import io.aklivity.zilla.runtime.engine.test.annotation.Configure;

public class PingIT
{
    private final K3poRule k3po = new K3poRule()
        .addScriptRoot("net", "io/aklivity/zilla/specs/binding/mqtt/streams/network/v4")
        .addScriptRoot("app", "io/aklivity/zilla/specs/binding/mqtt/streams/application");

    private final TestRule timeout = new DisableOnDebug(new Timeout(10, SECONDS));

    private final EngineRule engine = new EngineRule()
        .directory("target/zilla-itests")
        .countersBufferCapacity(8192)
        .configure(PUBLISH_TIMEOUT, 1L)
        .configure(ENGINE_DRAIN_ON_CLOSE, false)
        .configure(SUBSCRIPTION_ID_NAME,
            "io.aklivity.zilla.runtime.binding.mqtt.internal.stream.server.v4.PingIT::supplySubscriptionId")
        .configurationRoot("io/aklivity/zilla/specs/binding/mqtt/config")
        .external("app0")
        .clean();

    @Rule
    public final TestRule chain = outerRule(engine).around(k3po).around(timeout);

    @Test
    @Configuration("server.yaml")
    @Specification({
        "${net}/ping/client",
        "${app}/session.connect/server"})
    public void shouldConnectThenPingRequestResponse() throws Exception
    {
        k3po.finish();
    }

    @Test
    @Configuration("server.yaml")
    @Specification({
        "${net}/ping.keep.alive/client",
        "${app}/subscribe.topic.filter.single.exact/server"})
    @Configure(name = KEEP_ALIVE_MINIMUM_NAME, value = "1")
    public void shouldPingAtKeepAliveInterval() throws Exception
    {
        k3po.start();
        Thread.sleep(1000);
        k3po.notifyBarrier("WAIT_1_SECOND");
        Thread.sleep(1000);
        k3po.notifyBarrier("WAIT_1_SECOND2");
        k3po.finish();
    }

    private static int subscriptionId = 0;
    public static int supplySubscriptionId()
    {
        return ++subscriptionId;
    }
}
