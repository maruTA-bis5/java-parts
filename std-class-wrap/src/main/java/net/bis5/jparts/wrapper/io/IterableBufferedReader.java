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
import java.util.Iterator;
import java.util.NoSuchElementException;

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

	public IterableBufferedReader(Reader in) {
		super(in);
	}

	public IterableBufferedReader(Reader in, int sz) {
		super(in, sz);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Iterator<String> iterator() {
		return new BufferedReaderIterator();
	}

	protected class BufferedReaderIterator implements Iterator<String> {

		private String nextLine;

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
				nextLine = readLine();
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

}
