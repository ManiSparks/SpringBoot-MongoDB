package in.mani.springbootmongodb.service;

import java.util.List;

import javax.validation.ConstraintViolationException;

import in.mani.springbootmongodb.ExceptionHandling.TodoCollectionException;
import in.mani.springbootmongodb.model.TodoDTO;

public interface TodoService {
	public void  createTodo(TodoDTO todo) throws ConstraintViolationException, TodoCollectionException;
	
	public List<TodoDTO> getAllTodos(); 
	
	public TodoDTO getSingleTodo(String id) throws TodoCollectionException;
	
	public void updateTodo(String id, TodoDTO todo) throws TodoCollectionException;
	
	public void deleteTodoById(String id)throws TodoCollectionException;
	

}
