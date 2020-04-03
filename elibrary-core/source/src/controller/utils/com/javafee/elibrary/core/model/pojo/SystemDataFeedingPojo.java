package com.javafee.elibrary.core.model.pojo;

import java.util.List;
import java.util.function.Consumer;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SystemDataFeedingPojo implements Cloneable {
	private String feedType;
	private String data;
	private List<Consumer> actions;

	@Override
	public Object clone() {
		SystemDataFeedingPojo result = null;
		try {
			result = (SystemDataFeedingPojo) super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return result;
	}
}
