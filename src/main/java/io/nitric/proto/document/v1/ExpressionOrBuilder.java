// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: document/v1/document.proto

package io.nitric.proto.document.v1;

public interface ExpressionOrBuilder extends
    // @@protoc_insertion_point(interface_extends:nitric.document.v1.Expression)
    com.google.protobuf.MessageOrBuilder {

  /**
   * <pre>
   * The query operand or attribute
   * </pre>
   *
   * <code>string operand = 1;</code>
   * @return The operand.
   */
  java.lang.String getOperand();
  /**
   * <pre>
   * The query operand or attribute
   * </pre>
   *
   * <code>string operand = 1;</code>
   * @return The bytes for operand.
   */
  com.google.protobuf.ByteString
      getOperandBytes();

  /**
   * <pre>
   * The query operator [ == | &lt; | &lt;= | &gt; | &gt;= | startsWith ]
   * </pre>
   *
   * <code>string operator = 2;</code>
   * @return The operator.
   */
  java.lang.String getOperator();
  /**
   * <pre>
   * The query operator [ == | &lt; | &lt;= | &gt; | &gt;= | startsWith ]
   * </pre>
   *
   * <code>string operator = 2;</code>
   * @return The bytes for operator.
   */
  com.google.protobuf.ByteString
      getOperatorBytes();

  /**
   * <pre>
   * The query expression value
   * </pre>
   *
   * <code>.nitric.document.v1.ExpressionValue value = 3;</code>
   * @return Whether the value field is set.
   */
  boolean hasValue();
  /**
   * <pre>
   * The query expression value
   * </pre>
   *
   * <code>.nitric.document.v1.ExpressionValue value = 3;</code>
   * @return The value.
   */
  io.nitric.proto.document.v1.ExpressionValue getValue();
  /**
   * <pre>
   * The query expression value
   * </pre>
   *
   * <code>.nitric.document.v1.ExpressionValue value = 3;</code>
   */
  io.nitric.proto.document.v1.ExpressionValueOrBuilder getValueOrBuilder();
}
