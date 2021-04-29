package io.nitric.util;

/*-
 * #%L
 * Nitric Java SDK
 * %%
 * Copyright (C) 2021 Nitric Pty Ltd
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

import java.util.Objects;

/**
 * <p>
 *  Provides a gRPC Managed Channel object by use by Nitric Service Clients.
 * </p>
 *
 * <p>
 *  The default host and port values are '127.0.0.1' and 50051 respectively. To change these
 *  default values set the respective Environment Variables: NITRIC_SERVICE_HOST and
 *  NITRIC_SERVICE_PORT.
 * </p>
 *
 * @since 1.0
 */
public class GrpcChannelProvider {

    private static final Object LOCK = new Object();

    protected static final String NITRIC_SERVICE_HOST_DEFAULT = "127.0.0.1";
    protected static final String NITRIC_SERVICE_PORT_DEFAULT  = "50051";
    protected static final String NITRIC_SERVICE_HOST_ENV_VAR_NAME = "NITRIC_SERVICE_HOST";
    protected static final String NITRIC_SERVICE_PORT_ENV_VAR_NAME = "NITRIC_SERVICE_PORT";

    protected static ManagedChannel channel;

    // Public Methods ---------------------------------------------------------

    /**
     * @return the Nitric gRPC Managed Channel.
     */
    public static ManagedChannel getChannel() {

        if (channel != null && !channel.isShutdown() && !channel.isTerminated()) {
            return channel;
        }

        synchronized (LOCK) {
            if (channel == null || channel.isShutdown() || channel.isTerminated()) {
                var target = getEnvVar(NITRIC_SERVICE_HOST_ENV_VAR_NAME, NITRIC_SERVICE_HOST_DEFAULT)
                        + ":" + getEnvVar(NITRIC_SERVICE_PORT_ENV_VAR_NAME, NITRIC_SERVICE_PORT_DEFAULT);

                channel = ManagedChannelBuilder.forTarget(target)
                        .usePlaintext()
                        .build();
            }
        }

        return channel;
    }

    /**
     * @return the string representation of this object
     */
    @Override
    public String toString() {
        return getClass().getName() + "[channel=" + channel + "]";
    }

    // Protected Methods ------------------------------------------------------

    /**
     * Return the environment variable value if defined or the default value
     * otherwise.
     *
     * @param varName the environmental variable name (required)
     * @param defaultValue the default fallback value (required)
     * @return the environment variable value or default if not defined
     */
    protected static String getEnvVar(String varName, String defaultValue) {
        Objects.requireNonNull(varName, "varName parameter is required");
        Objects.requireNonNull(defaultValue, "defaultValue parameter is required");

        return (System.getenv(varName) != null) ? System.getenv(varName) : defaultValue;
    }

}
