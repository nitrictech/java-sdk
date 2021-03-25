package io.nitric.api.event;

import io.nitric.proto.event.v1.TopicGrpc;
import io.nitric.proto.event.v1.TopicListRequest;
import io.nitric.util.GrpcChannelProvider;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 *  Provides an Event API topic client.
 * </p>
 *
 * <p>
 *  The example below illustrates the Topic API.
 * </p>
 *
 * <pre>
 *  var client = TopicClient.newBuilder().build();
 *
 *  List&lt;NitricTopic&gt; topics = client.list();
 * </pre>
 *
 * @see NitricTopic
 * @see NitricEvent
 * @see EventClient
 *
 * @since 1.0
 */
public class TopicClient {

    final TopicGrpc.TopicBlockingStub serviceStub;

    /*
     * Enforce builder pattern.
     */
    TopicClient(TopicClient.Builder builder) {
        this.serviceStub = builder.serviceStub;
    }

    // Public Methods ---------------------------------------------------------

    /**
     * List the available event topics.
     *
     * @return the list of available topics
     */
    public List<NitricTopic> list() {

        var request = TopicListRequest.newBuilder().build();

        var response = serviceStub.list(request);

        return response.getTopicsList()
                .stream()
                .map(topic -> NitricTopic.build(topic.getName()))
                .collect(Collectors.toList());
    }

    /**
     * Create an new TopicClient builder.
     *
     * @return new TopicClient builder
     */
    public static TopicClient.Builder newBuilder() {
        return new TopicClient.Builder();
    }

    /**
     * @return the string representation of this object
     */
    @Override
    public String toString() {
        return getClass().getSimpleName() + "[serviceStub=" + serviceStub + "]";
    }

    // Inner Classes ----------------------------------------------------------

    /**
     * Provides a TopicClient Builder.
     */
    public static class Builder {

        private TopicGrpc.TopicBlockingStub serviceStub;

        /*
         * Enforce builder pattern.
         */
        Builder() {
        }

        /**
         * Set  the GRPC service stub for mock testing.
         *
         * @param serviceStub the GRPC service stub to inject
         * @return the TopicClient builder
         */
        TopicClient.Builder serviceStub(TopicGrpc.TopicBlockingStub serviceStub) {
            this.serviceStub = serviceStub;
            return this;
        }

        /**
         * @return build a new TopicClient
         */
        public TopicClient build() {
            if (serviceStub == null) {
                var channel = GrpcChannelProvider.getChannel();
                this.serviceStub = TopicGrpc.newBlockingStub(channel);
            }

            return new TopicClient(this);
        }
    }
}