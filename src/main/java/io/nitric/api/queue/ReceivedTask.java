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

package io.nitric.api.queue;

import io.nitric.api.NitricException;
import io.nitric.proto.queue.v1.QueueCompleteRequest;
import io.nitric.util.Contracts;

import java.util.Map;

/**
 * <p>
 *  Provides a Queue API Received Task class.
 * </p>
 *
 * @see Task
 */
public class ReceivedTask extends Task {

    final String leaseId;
    final String queue;

    /*
     * Enforce builder pattern.
     */
    ReceivedTask(String id, String payloadType, Map<String, Object> payload, String leaseId, String queue) {
        super(id, payloadType, payload);

        Contracts.requireNonBlank(leaseId, "leaseId");
        Contracts.requireNonBlank(queue, "queue");

        this.leaseId = leaseId;
        this.queue = queue;
    }

    // Public Methods ---------------------------------------------------------

    /**
     * Return the lease id unique to the pop request, this is used to complete,
     * extend the lease or release the task.
     *
     * @return the task lease id, unique to the pop request.
     */
    public String getLeaseId() {
        return leaseId;
    }

    /**
     * Return the name of the received task queue.
     *
     * @return the name of the received task queue.
     */
    public String getQueue() {
        return queue;
    }

    /**
     * Complete the received task using its lease id.
     *
     * @throws NitricException if a Queue Service API error occurs
     */
    public void complete() throws NitricException {
        var request = QueueCompleteRequest.newBuilder()
                .setQueue(queue)
                .setLeaseId(leaseId)
                .build();

        try {
            Queues.getServiceStub().complete(request);
        } catch (io.grpc.StatusRuntimeException sre) {
            throw NitricException.build(sre);
        }
    }

    /**
     * Return the string representation of this object.
     *
     * @return the string representation of this object
     */
    @Override
    public String toString() {
        return getClass().getSimpleName()
                + "[id=" + id
                + ", payloadType=" + payloadType
                + ", payload=" + payload
                + ", leaseId=" + leaseId
                + ", queue=" + queue
                + "]";
    }

}