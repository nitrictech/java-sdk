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

import static org.junit.Assert.fail;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.stubbing.Answer;

import io.grpc.stub.StreamObserver;
import io.nitric.proto.faas.v1.ClientMessage;
import io.nitric.proto.faas.v1.FaasServiceGrpc;
import io.nitric.proto.faas.v1.ServerMessage;

public class FaasTest {

    NitricFunction handlerA = trigger -> trigger.buildResponse("Success");

    // Test a basic start scenario
    @Test public void start() {
        // Create mock stub here...
        // it will be mocked to produce fake streams
        var mockStub = Mockito.mock(FaasServiceGrpc.FaasServiceStub.class);

        // Can use to verify we received client messages
        StreamObserver<ClientMessage> mockServerStream = Mockito.mock(StreamObserver.class);

        AtomicReference<StreamObserver<ServerMessage>> clientStreamReference = new AtomicReference<>();
        Mockito.when(mockStub.triggerStream(Mockito.any())).then(invocation -> {
            // Here we can get the real stream observer that is sent to use when the function is
            // started
            StreamObserver<ServerMessage> clientStream = invocation.getArgument(0);

            clientStreamReference.set(clientStream);

            // return the client stream
            return mockServerStream;
        });

        AtomicReference<ClientMessage> initRequestReference = new AtomicReference<>();
        CountDownLatch initRecievedLatch = new CountDownLatch(1);
        Mockito.doAnswer((Answer<Object>) invocationOnMock -> {
            initRequestReference.set(invocationOnMock.getArgument(0));
            initRecievedLatch.countDown();
            return null;
        }).when(mockServerStream).onNext(Mockito.any());

        var faas = new Faas().stub(mockStub);

        // Run the function on a thread
        // We can by completing the server stream
        CountDownLatch functionCompleteLatch = new CountDownLatch(1);
        Executors.newCachedThreadPool().submit(() -> {
            faas.startFunction(handlerA);
            functionCompleteLatch.countDown();
        });

        // Wait up to five seconds for the function to start
        try {
            // Ensure we recieved an init request from the function
            // Fail if we do not
            initRecievedLatch.await(5, TimeUnit.SECONDS);
            // Ensure its an init request
            assert initRequestReference.get().hasInitRequest();
        } catch (Throwable t) {
            fail();
        }

        // Finish the stream and close
        clientStreamReference.get().onCompleted();

        try {
            // Ensure the function exits
            functionCompleteLatch.await(5, TimeUnit.SECONDS);
        } catch (Throwable t) {
            // Fail if it does not
            fail();
        }
    }
}
