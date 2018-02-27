package com.javafee.common;

public final class Query {
	public static final String selectBookAuthor = "from Book b where :author in elements(b.author)";
}
