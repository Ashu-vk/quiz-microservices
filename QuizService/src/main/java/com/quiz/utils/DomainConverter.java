package com.quiz.utils;

public interface DomainConverter<T, V> {
	V toView(T t);
	T toDomain(V v);
}
