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
   * <code>.google.protobuf.Struct pagingToken = 2;</code>
   * @return Whether the pagingToken field is set.
   */
  boolean hasPagingToken();
  /**
   * <pre>
   * The query paging continuation token, when not null further results are available
   * </pre>
   *
   * <code>.google.protobuf.Struct pagingToken = 2;</code>
   * @return The pagingToken.
   */
  com.google.protobuf.Struct getPagingToken();
  /**
   * <pre>
   * The query paging continuation token, when not null further results are available
   * </pre>
   *
   * <code>.google.protobuf.Struct pagingToken = 2;</code>
   */
  com.google.protobuf.StructOrBuilder getPagingTokenOrBuilder();
}
