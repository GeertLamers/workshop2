package com.rsvier.workshop2.utility;

import java.util.List;

public interface GenericDAO<T> {

	public T create(T t);
	public List<T> findALl();
	public T findById(Class<T> type, Long id);
	public void update(T entity);
	public void delete(T entity);
	
}
