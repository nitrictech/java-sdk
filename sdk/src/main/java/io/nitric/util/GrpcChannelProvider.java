/*-
 * #%L
 * Nitric Java SDK
 * %%
 * Copyright (C) 2021 Nitric Technologies Pty Ltd
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

package io.nitric.util;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

/**
 * <p>
 *  Provides Nitric Membrane GRPC Channel objects by use by Nitric clients.
 * </p>
 *
 * <p>
 *  The default host and port values are '127.0.0.1' and 50051 respectively. To change these
 *  default values set the respective Environment Variables: <code>NITRIC_SERVICE_HOST</code> and
 *  <code>NITRIC_SERVICE_PORT</code>.
 * </p>
 */
public class GrpcChannelProvider {

    private static final Object LOCK = new Object();

    protected static final String NITRIC_SERVICE_HOST_DEFAULT = "127.0.0.1";
    protected static final String NITRIC_SERVICE_PORT_DEFAULT  = "50051";
    protected static final String NITRIC_SERVICE_HOST_ENV_VAR_NAME = "NITRIC_SERVICE_HOST";
    protected static final String NITRIC_SERVICE_PORT_ENV_VAR_NAME = "NITRIC_SERVICE_PORT";

    protected static ManagedChannel channel;

    /*
     * Enforce static method usage
     */
    private GrpcChannelProvider() {
    }

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
                channel = ManagedChannelBuilder.forTarget(getTarget())
                        .usePlaintext()
                        .build();
            }
        }

        return channel;
    }

    /**
     * Return the Membrane Server connection target.
     *
     * @return the Membrane Server connection target.
     */
    public static String getTarget() {
        return getEnvVar(NITRIC_SERVICE_HOST_ENV_VAR_NAME, NITRIC_SERVICE_HOST_DEFAULT)
            + ":" + getEnvVar(NITRIC_SERVICE_PORT_ENV_VAR_NAME, NITRIC_SERVICE_PORT_DEFAULT);
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
        Contracts.requireNonBlank(varName, "varName");
        Contracts.requireNonBlank(defaultValue, "defaultValue");

        return (System.getenv(varName) != null) ? System.getenv(varName) : defaultValue;
    }

}
