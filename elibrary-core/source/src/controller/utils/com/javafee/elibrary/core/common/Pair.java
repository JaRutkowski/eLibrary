package com.javafee.elibrary.core.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Pair<T, K> {
	private T first;
	private K second;
}