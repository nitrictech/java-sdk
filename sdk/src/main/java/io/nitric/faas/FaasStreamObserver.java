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

package io.nitric.faas;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicReference;

import io.grpc.StatusRuntimeException;
import io.grpc.Status.Code;
import io.grpc.stub.StreamObserver;
import io.nitric.proto.faas.v1.ClientMessage;
import io.nitric.proto.faas.v1.ServerMessage;
import io.nitric.proto.faas.v1.TriggerRequest;
import io.nitric.proto.faas.v1.TriggerResponse;
import io.nitric.util.GrpcChannelProvider;

/**
 * Provides the FaaS Nitric GRCP stream handler.
 */
class FaasStreamObserver implements StreamObserver<ServerMessage> {

    final TriggerProcessor triggerProcessor;
    final AtomicReference<StreamObserver<ClientMessage>> clientObserver;
    final CountDownLatch finishedLatch;

    // Constructor ------------------------------------------------------------

    /**
     * Create a new Faas Stream Observer object.
     *
     * @param triggerProcessor the GRPC TriggerProcessor
     * @param clientObserver the client observer
     * @param finishedLatch the finish latch
     */
    protected FaasStreamObserver(
        TriggerProcessor triggerProcessor,
        AtomicReference<StreamObserver<ClientMessage>> clientObserver,
        CountDownLatch finishedLatch
    ) {
        this.triggerProcessor = triggerProcessor;
        this.clientObserver = clientObserver;
        this.finishedLatch = finishedLatch;
    }

    // Public Methods ---------------------------------------------------------

    @Override
    public void onNext(ServerMessage serverMessage) {
        // We got a new message from the server
        switch (serverMessage.getContentCase()) {
            case INIT_RESPONSE:
                // We have an init ack from the membrane
                // XXX: NO OP for now
                break;

            case TRIGGER_REQUEST:
                TriggerRequest request = serverMessage.getTriggerRequest();

                TriggerResponse response = triggerProcessor.process(request);

                if (response != null) {
                    // Write back the response to the server
                    clientObserver.get().onNext(
                            ClientMessage
                                    .newBuilder()
                                    .setId(serverMessage.getId())
                                    .setTriggerResponse(response)
                                    .build());
                }
                break;

            default:
                Faas.logError("onNext() default case %s reached", serverMessage.getContentCase());
                break;
        }
    }

    @Override
    public void onError(Throwable error) {
        if (error instanceof StatusRuntimeException) {
            var sre = (StatusRuntimeException) error;
            if (sre.getStatus().getCode().equals(Code.UNAVAILABLE)) {
                Faas.logError(error,
                         "error occurred connecting to Nitric membrane on %s",
                         GrpcChannelProvider.getTarget());
                return;
            }
        }
        Faas.logError(error, "error occurred");
    }

    @Override
    public void onCompleted() {
        // The server has indicated that streaming is now over we can exit
        // Unlock from exit
        finishedLatch.countDown();
    }

}