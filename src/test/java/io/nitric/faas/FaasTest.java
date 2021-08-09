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

import com.google.protobuf.ByteString;
import io.grpc.stub.StreamObserver;
import io.nitric.proto.faas.v1.ClientMessage;
import io.nitric.proto.faas.v1.FaasServiceGrpc;
import io.nitric.proto.faas.v1.ServerMessage;
import io.nitric.proto.faas.v1.TopicTriggerContext;
import io.nitric.proto.faas.v1.TriggerRequest;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.stubbing.Answer;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

import static org.junit.Assert.fail;

public class FaasTest {

    // Test a basic start scenario
    @Test
    public void start() {
        NitricFunction handler = Mockito.mock(NitricFunction.class);

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
            faas.startFunction(handler);
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
            // Ensure the function was never called
            Mockito.verifyNoInteractions(handler);
            // Ensure the function exits
            functionCompleteLatch.await(5, TimeUnit.SECONDS);
        } catch (Throwable t) {
            // Fail if it does not
            fail();
        }
    }

    @Test
    public void handleTopicTrigger() {
        NitricFunction handler = Mockito.mock(NitricFunction.class);
        // Create mock stub here...
        // it will be mocked to produce fake streams
        var mockStub = Mockito.mock(FaasServiceGrpc.FaasServiceStub.class);

        Mockito.when(handler.handle(Mockito.any())).then(invocation -> {
            // Here we can get the real stream observer that is sent to use when the function is
            // started
            Trigger trigger = invocation.getArgument(0);
            // return the client stream
            return trigger.buildResponse("test");
        });

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

        CountDownLatch initRecievedLatch = new CountDownLatch(1);
        Mockito.doAnswer((Answer<Object>) invocationOnMock -> {
            initRecievedLatch.countDown();
            return null;
        }).when(mockServerStream).onNext(Mockito.any());

        var faas = new Faas().stub(mockStub);

        // Run the function on a thread
        // We can by completing the server stream
        CountDownLatch functionCompleteLatch = new CountDownLatch(1);
        Executors.newCachedThreadPool().submit(() -> {
            faas.startFunction(handler);
            functionCompleteLatch.countDown();
        });

        // Wait up to five seconds for the function to start
        try {
            // Ensure we recieved an init request from the function
            // Fail if we do not
            initRecievedLatch.await(5, TimeUnit.SECONDS);
        } catch (Throwable t) {
            fail();
        }

        // Finish the stream and close
        clientStreamReference.get().onNext(ServerMessage
                .newBuilder()
                .setTriggerRequest(TriggerRequest.newBuilder()
                        .setData(ByteString.EMPTY)
                        .setTopic(TopicTriggerContext.newBuilder()
                                .setTopic("test")
                                .build()
                        )
                        .build()
                )
                .build()
        );

        try {
            // Ensure handler was called
            Mockito.verify(handler).handle(Mockito.any());
            // Close the stream
            clientStreamReference.get().onCompleted();
            // Ensure the function exits
            functionCompleteLatch.await(5, TimeUnit.SECONDS);
        } catch (Throwable t) {
            // Fail if it does not
            fail();
        }
    }

    @Test
    public void logging() {
        try {
            Faas.logError("name: %s", "value");
        } catch (Exception e) {
            fail();
        }

        try {
            Faas.logException(new NullPointerException(), "name: %s", "value");
        } catch (Exception e) {
            fail();
        }
    }

}
