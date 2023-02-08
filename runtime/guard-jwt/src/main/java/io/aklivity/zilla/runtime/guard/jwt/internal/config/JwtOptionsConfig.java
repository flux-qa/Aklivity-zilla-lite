/*
 * Copyright 2021-2022 Aklivity Inc
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
package io.aklivity.zilla.runtime.guard.jwt.internal.config;

import static java.util.Optional.ofNullable;

import java.time.Duration;
import java.util.List;
import java.util.Optional;

import io.aklivity.zilla.runtime.engine.config.OptionsConfig;

public class JwtOptionsConfig extends OptionsConfig
{
    public final String issuer;
    public final String audience;
    public final List<JwtKeyConfig> keys;
    public final Optional<Duration> challenge;

    public final Optional<String> keysURL;

    public JwtOptionsConfig(
        String issuer,
        String audience,
        List<JwtKeyConfig> keys,
        Duration challenge)
    {
        this(issuer, audience, keys, challenge, null);
    }

    public JwtOptionsConfig(
            String issuer,
            String audience,
            List<JwtKeyConfig> keys,
            Duration challenge,
            String keysURL)
    {
        this.issuer = issuer;
        this.audience = audience;
        this.keys = keys;
        this.challenge = ofNullable(challenge);
        this.keysURL = ofNullable(keysURL);
    }
}
