/*
 * Copyright (c) 2013. All rights reserved.
 * Original Author: ms
 * Creation Date: Nov 8, 2013
 */
package org.jarmoni.util.lang;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class ReflectionsTest {

	// CHECKSTYLE:OFF
	@Rule
	public ExpectedException ee = ExpectedException.none();

	// CHECKSTYLE:ON

	@Test
	public void testArrayFromList() {
		final List<String> listOfString = Arrays.asList("one", "two", "three");
		final String[] arrayOfString = Reflections.arrayFromList(listOfString, String.class);
		assertEquals("one", arrayOfString[0]);
		assertEquals("two", arrayOfString[1]);
		assertEquals("three", arrayOfString[2]);
	}

	@Test
	public void testCreateObject() throws Exception {
		final CharSequence seq = Reflections.createObject("java.lang.StringBuilder", CharSequence.class);
		assertTrue(seq instanceof StringBuilder);
	}

	@Test
	public void testCreateException() throws Exception {
		final RuntimeException exception = Reflections.createException(IllegalStateException.class, "some message");
		assertTrue(exception.getClass().equals(IllegalStateException.class));
		assertEquals("some message", exception.getMessage());
	}

	@Test
	public void testCreateExceptionThrowIt() {
		this.ee.expect(IllegalStateException.class);
		this.ee.expectMessage("some message");
		final RuntimeException exception = Reflections.createException(IllegalStateException.class, "some message");
		throw exception;
	}

	@Test
	public void testCreateExceptionNoRuntimeThrowIt() throws Exception {
		this.ee.expect(IOException.class);
		this.ee.expectMessage("some message");
		final Exception exception = Reflections.createException(IOException.class, "some message");
		throw exception;
	}

	@Test
	public void testCreateExceptionWithCause() throws Exception {
		final RuntimeException exception = Reflections.createException(IllegalStateException.class, "some message",
				new FileNotFoundException());
		assertTrue(exception.getClass().equals(IllegalStateException.class));
		assertEquals("some message", exception.getMessage());
		assertTrue(exception.getCause().getClass().equals(FileNotFoundException.class));
	}

	@Test
	public void testCreateExceptionWithCauseNullMessage() throws Exception {
		final RuntimeException exception = Reflections.createException(IllegalStateException.class, null,
				new IllegalArgumentException());
		assertTrue(exception.getClass().equals(IllegalStateException.class));
		assertTrue(exception.getCause().getClass().equals(IllegalArgumentException.class));
	}

	@Test
	public void testCreateExceptionNullCause() throws Exception {
		final RuntimeException exception = Reflections.createException(IllegalStateException.class, "some message", null);
		assertTrue(exception.getClass().equals(IllegalStateException.class));
		assertEquals("some message", exception.getMessage());
	}

	@Test
	public void testCreateExceptionNullMessageNullCause() throws Exception {
		final RuntimeException exception = Reflections.createException(IllegalStateException.class, null, null);
		assertTrue(exception.getClass().equals(IllegalStateException.class));
	}

	@Test
	public void testCreateExceptionInvalidConstructor() throws Exception {
		this.ee.expect(IllegalStateException.class);
		this.ee.expectMessage("No constructor with no arguments found");
		Reflections.createException(TestException.class, null, null);
	}

	public final class TestException extends RuntimeException {

		private static final long serialVersionUID = 6473254339477287888L;

		public TestException() {
			super();
		}
	}
}
