// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: event/v1/event.proto

package io.nitric.proto.event.v1;

public interface NitricEventOrBuilder extends
    // @@protoc_insertion_point(interface_extends:nitric.event.v1.NitricEvent)
    com.google.protobuf.MessageOrBuilder {

  /**
   * <code>string id = 1;</code>
   * @return The id.
   */
  java.lang.String getId();
  /**
   * <code>string id = 1;</code>
   * @return The bytes for id.
   */
  com.google.protobuf.ByteString
      getIdBytes();

  /**
   * <code>string payloadType = 2;</code>
   * @return The payloadType.
   */
  java.lang.String getPayloadType();
  /**
   * <code>string payloadType = 2;</code>
   * @return The bytes for payloadType.
   */
  com.google.protobuf.ByteString
      getPayloadTypeBytes();

  /**
   * <code>.google.protobuf.Struct payload = 3;</code>
   * @return Whether the payload field is set.
   */
  boolean hasPayload();
  /**
   * <code>.google.protobuf.Struct payload = 3;</code>
   * @return The payload.
   */
  com.google.protobuf.Struct getPayload();
  /**
   * <code>.google.protobuf.Struct payload = 3;</code>
   */
  com.google.protobuf.StructOrBuilder getPayloadOrBuilder();
}
