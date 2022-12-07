package com.shop.pilotes.mapper;

public interface Mapper<T, V> {

    T mapToDto(V v);

}
