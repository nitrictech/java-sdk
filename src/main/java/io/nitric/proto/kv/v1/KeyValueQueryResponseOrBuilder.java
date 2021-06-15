// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: kv/v1/kv.proto

package io.nitric.proto.kv.v1;

public interface KeyValueQueryResponseOrBuilder extends
    // @@protoc_insertion_point(interface_extends:nitric.kv.v1.KeyValueQueryResponse)
    com.google.protobuf.MessageOrBuilder {

  /**
   * <pre>
   * The retrieved values
   * </pre>
   *
   * <code>repeated .google.protobuf.Struct values = 1;</code>
   */
  java.util.List<com.google.protobuf.Struct>
      getValuesList();
  /**
   * <pre>
   * The retrieved values
   * </pre>
   *
   * <code>repeated .google.protobuf.Struct values = 1;</code>
   */
  com.google.protobuf.Struct getValues(int index);
  /**
   * <pre>
   * The retrieved values
   * </pre>
   *
   * <code>repeated .google.protobuf.Struct values = 1;</code>
   */
  int getValuesCount();
  /**
   * <pre>
   * The retrieved values
   * </pre>
   *
   * <code>repeated .google.protobuf.Struct values = 1;</code>
   */
  java.util.List<? extends com.google.protobuf.StructOrBuilder>
      getValuesOrBuilderList();
  /**
   * <pre>
   * The retrieved values
   * </pre>
   *
   * <code>repeated .google.protobuf.Struct values = 1;</code>
   */
  com.google.protobuf.StructOrBuilder getValuesOrBuilder(
      int index);

  /**
   * <pre>
   * The query paging continuation token, when not null further results are available
   * </pre>
   *
   * <code>map&lt;string, .nitric.kv.v1.Key&gt; pagingToken = 2;</code>
   */
  int getPagingTokenCount();
  /**
   * <pre>
   * The query paging continuation token, when not null further results are available
   * </pre>
   *
   * <code>map&lt;string, .nitric.kv.v1.Key&gt; pagingToken = 2;</code>
   */
  boolean containsPagingToken(
      java.lang.String key);
  /**
   * Use {@link #getPagingTokenMap()} instead.
   */
  @java.lang.Deprecated
  java.util.Map<java.lang.String, io.nitric.proto.kv.v1.Key>
  getPagingToken();
  /**
   * <pre>
   * The query paging continuation token, when not null further results are available
   * </pre>
   *
   * <code>map&lt;string, .nitric.kv.v1.Key&gt; pagingToken = 2;</code>
   */
  java.util.Map<java.lang.String, io.nitric.proto.kv.v1.Key>
  getPagingTokenMap();
  /**
   * <pre>
   * The query paging continuation token, when not null further results are available
   * </pre>
   *
   * <code>map&lt;string, .nitric.kv.v1.Key&gt; pagingToken = 2;</code>
   */

  io.nitric.proto.kv.v1.Key getPagingTokenOrDefault(
      java.lang.String key,
      io.nitric.proto.kv.v1.Key defaultValue);
  /**
   * <pre>
   * The query paging continuation token, when not null further results are available
   * </pre>
   *
   * <code>map&lt;string, .nitric.kv.v1.Key&gt; pagingToken = 2;</code>
   */

  io.nitric.proto.kv.v1.Key getPagingTokenOrThrow(
      java.lang.String key);
}
