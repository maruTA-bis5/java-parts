/*
 * Copyright 2016 Maruyama Takayuki
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *  
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * 	Unless required by applicable law or agreed to in writing, software
 * 	distributed under the License is distributed on an "AS IS" BASIS,
 * 	WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * 	See the License for the specific language governing permissions and
 * 	limitations under the License.
 */
package net.bis5.jparts.wrapper.io;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.io.UncheckedIOException;
import java.nio.CharBuffer;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.stream.Stream;

/**
 * {@link Iterable} implemented {@link BufferedReader}.
 * 
 * <dl>
 * <dt>Usage:</dt>
 * <dd>
 * 
 * <pre>
 * try (IterableBufferedReader reader = new IterableBufferedReader(new FileReader("/path/to/file"))) {
 * 	for (String line : reader) {
 * 		System.out.println(line);
 * 	}
 * }
 * </pre>
 * 
 * </dd>
 * <dt>Notice:</dt>
 * <dd>Java8なら大人しく{@link BufferedReader#lines()}を使うべきです。やってることはそう変わりませんので。</dd>
 * </dl>
 * 
 * @author Maruyama Takayuki
 */
public class IterableBufferedReader extends BufferedReader implements Iterable<String> {

	protected final BufferedReader reader;

	public IterableBufferedReader(BufferedReader in) {
		super(null);
		reader = Objects.requireNonNull(in);
	}

	public IterableBufferedReader(Reader in) {
		this(new BufferedReader(in));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Iterator<String> iterator() {
		return new BufferedReaderIterator(reader);
	}

	protected static class BufferedReaderIterator implements Iterator<String> {

		protected final BufferedReader reader;
		private String nextLine;

		public BufferedReaderIterator(BufferedReader reader) {
			this.reader = Objects.requireNonNull(reader);
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public boolean hasNext() {
			if (nextLine != null) {
				// already buffered, but not called #next()
				return true;
			}
			try {
				nextLine = reader.readLine();
			} catch (IOException ex) {
				throw new UncheckedIOException(ex);
			}
			return nextLine != null;
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public String next() {
			if (nextLine != null || hasNext()) {
				String line = nextLine;
				nextLine = null;
				return line;
			} else {
				throw new NoSuchElementException();
			}
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public int hashCode() {
		return reader.hashCode();
	}

	/**
	 * {@inheritDoc}
	 */
	public int read(CharBuffer target) throws IOException {
		return reader.read(target);
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean equals(Object obj) {
		return reader.equals(obj);
	}

	/**
	 * {@inheritDoc}
	 */
	public int read(char[] cbuf) throws IOException {
		return reader.read(cbuf);
	}

	/**
	 * {@inheritDoc}
	 */
	public int read() throws IOException {
		return reader.read();
	}

	/**
	 * {@inheritDoc}
	 */
	public int read(char[] cbuf, int off, int len) throws IOException {
		return reader.read(cbuf, off, len);
	}

	/**
	 * {@inheritDoc}
	 */
	public String toString() {
		return reader.toString();
	}

	/**
	 * {@inheritDoc}
	 */
	public String readLine() throws IOException {
		return reader.readLine();
	}

	/**
	 * {@inheritDoc}
	 */
	public long skip(long n) throws IOException {
		return reader.skip(n);
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean ready() throws IOException {
		return reader.ready();
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean markSupported() {
		return reader.markSupported();
	}

	/**
	 * {@inheritDoc}
	 */
	public void mark(int readAheadLimit) throws IOException {
		reader.mark(readAheadLimit);
	}

	/**
	 * {@inheritDoc}
	 */
	public void reset() throws IOException {
		reader.reset();
	}

	/**
	 * {@inheritDoc}
	 */
	public void close() throws IOException {
		reader.close();
	}

	/**
	 * {@inheritDoc}
	 */
	public Stream<String> lines() {
		return reader.lines();
	}

}
