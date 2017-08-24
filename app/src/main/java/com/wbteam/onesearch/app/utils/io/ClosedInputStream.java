package com.wbteam.onesearch.app.utils.io;

import java.io.InputStream;
import java.io.Serializable;
import java.io.Writer;

public class ClosedInputStream extends InputStream {

    /**
     * A singleton.
     */
    public static final ClosedInputStream CLOSED_INPUT_STREAM = new ClosedInputStream();

    /**
     * Returns -1 to indicate that the stream is closed.
     *
     * @return always -1
     */
    @Override
    public int read() {
        return -1;
    }

    /**
     * {@link Writer} implementation that outputs to a {@link StringBuilder}.
     * <p>
     * <strong>NOTE:</strong> This implementation, as an alternative to
     * <code>java.io.StringWriter</code>, provides an <i>un-synchronized</i>
     * (i.e. for use in a single thread) implementation for better performance.
     * For safe usage with multiple {@link Thread}s then
     * <code>java.io.StringWriter</code> should be used.
     *
     * @version $Revision: 1003647 $ $Date: 2010-10-01 16:53:59 -0400 (Fri, 01 Oct 2010) $
     * @since Commons IO 2.0
     */
    @SuppressWarnings("serial")
	public static class StringBuilderWriter extends Writer implements Serializable {

        private final StringBuilder builder;

        /**
         * Construct a new {@link StringBuilder} instance with default capacity.
         */
        public StringBuilderWriter() {
            this.builder = new StringBuilder();
        }

        /**
         * Construct a new {@link StringBuilder} instance with the specified capacity.
         *
         * @param capacity The initial capacity of the underlying {@link StringBuilder}
         */
        public StringBuilderWriter(int capacity) {
            this.builder = new StringBuilder(capacity);
        }

        /**
         * Construct a new instance with the specified {@link StringBuilder}.
         *
         * @param builder The String builder
         */
        public StringBuilderWriter(StringBuilder builder) {
            this.builder = (builder != null ? builder : new StringBuilder());
        }

        /**
         * Append a single character to this Writer.
         *
         * @param value The character to append
         * @return This writer instance
         */
        @Override
        public Writer append(char value) {
            builder.append(value);
            return this;
        }

        /**
         * Append a character sequence to this Writer.
         *
         * @param value The character to append
         * @return This writer instance
         */
        @Override
        public Writer append(CharSequence value) {
            builder.append(value);
            return this;
        }

        /**
         * Append a portion of a character sequence to the {@link StringBuilder}.
         *
         * @param value The character to append
         * @param start The index of the first character
         * @param end The index of the last character + 1
         * @return This writer instance
         */
        @Override
        public Writer append(CharSequence value, int start, int end) {
            builder.append(value, start, end);
            return this;
        }

        /**
         * Closing this writer has no effect.
         */
        @Override
        public void close() {
        }

        /**
         * Flushing this writer has no effect.
         */
        @Override
        public void flush() {
        }


        /**
         * Write a String to the {@link StringBuilder}.
         *
         * @param value The value to write
         */
        @Override
        public void write(String value) {
            if (value != null) {
                builder.append(value);
            }
        }

        /**
         * Write a portion of a character array to the {@link StringBuilder}.
         *
         * @param value The value to write
         * @param offset The index of the first character
         * @param length The number of characters to write
         */
        @Override
        public void write(char[] value, int offset, int length) {
            if (value != null) {
                builder.append(value, offset, length);
            }
        }

        /**
         * Return the underlying builder.
         *
         * @return The underlying builder
         */
        public StringBuilder getBuilder() {
            return builder;
        }

        /**
         * Returns {@link StringBuilder#toString()}.
         *
         * @return The contents of the String builder.
         */
        @Override
        public String toString() {
            return builder.toString();
        }
    }
}