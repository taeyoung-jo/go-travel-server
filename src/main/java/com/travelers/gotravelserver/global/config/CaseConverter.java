package com.travelers.gotravelserver.global.config;

import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.ConverterFactory;

public class CaseConverter implements ConverterFactory<String, Enum> {

	@Override
	public <T extends Enum> Converter<String, T> getConverter(Class<T> targetType) {
		return new StringToEnum<>(targetType);
	}

	private static class StringToEnum<T extends Enum> implements Converter<String, T> {
		private final Class<T> type;

		public StringToEnum(Class<T> type) {
			this.type = type;
		}

		@Override
		public T convert(String source) {
			if (source == null || source.isEmpty())
				return null;
			for (T constant : type.getEnumConstants()) {
				if (constant.name().equalsIgnoreCase(source.trim())) {
					return constant;
				}
			}
			throw new IllegalArgumentException("Invalid value '" + source + "' for enum " + type.getSimpleName());
		}
	}
}
