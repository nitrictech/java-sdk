// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: storage/v1/storage.proto

package io.nitric.proto.storage.v1;

public interface StorageWriteRequestOrBuilder extends
    // @@protoc_insertion_point(interface_extends:nitric.storage.v1.StorageWriteRequest)
    com.google.protobuf.MessageOrBuilder {

  /**
   * <pre>
   * Nitric name of the bucket to store in
   *  this will be automatically resolved to the provider specific bucket identifier.
   * </pre>
   *
   * <code>string bucket_name = 1;</code>
   * @return The bucketName.
   */
  java.lang.String getBucketName();
  /**
   * <pre>
   * Nitric name of the bucket to store in
   *  this will be automatically resolved to the provider specific bucket identifier.
   * </pre>
   *
   * <code>string bucket_name = 1;</code>
   * @return The bytes for bucketName.
   */
  com.google.protobuf.ByteString
      getBucketNameBytes();

  /**
   * <pre>
   * Key to store the item under
   * </pre>
   *
   * <code>string key = 2;</code>
   * @return The key.
   */
  java.lang.String getKey();
  /**
   * <pre>
   * Key to store the item under
   * </pre>
   *
   * <code>string key = 2;</code>
   * @return The bytes for key.
   */
  com.google.protobuf.ByteString
      getKeyBytes();

  /**
   * <pre>
   * bytes array to store
   * </pre>
   *
   * <code>bytes body = 3;</code>
   * @return The body.
   */
  com.google.protobuf.ByteString getBody();
}
