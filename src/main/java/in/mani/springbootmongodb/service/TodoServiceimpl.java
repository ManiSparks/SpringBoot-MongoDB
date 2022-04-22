package in.mani.springbootmongodb.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.validation.ConstraintViolationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.mani.springbootmongodb.ExceptionHandling.TodoCollectionException;
import in.mani.springbootmongodb.model.TodoDTO;
import in.mani.springbootmongodb.repository.TodoRepository;

@Service
public class TodoServiceimpl implements TodoService{
	
	@Autowired
	private TodoRepository todorepo;
			
	public void createTodo(TodoDTO todo) throws ConstraintViolationException, TodoCollectionException
	{
		Optional<TodoDTO> todoOptional=todorepo.findByTodo(todo.getTodo());
		if(todoOptional.isPresent())
		{
			throw new  TodoCollectionException(TodoCollectionException.TodoAlreadyExists());
		}
		else
		{
			todo.setCreatedAt(new Date(System.currentTimeMillis()));
		    todorepo.save(todo);
		}
	
	}

	@Override
	public List<TodoDTO> getAllTodos() {
		List<TodoDTO> todos=todorepo.findAll();
		if(todos.size() >0)
		{
			return todos;
		}
		else
		{
			return new ArrayList<TodoDTO>();
		}
	}

	public TodoDTO getSingleTodo(String id) throws TodoCollectionException {
		Optional<TodoDTO> optionaltodo=todorepo.findById(id);
		if(!optionaltodo.isPresent())
		{
			throw new TodoCollectionException(TodoCollectionException.NotFoundException(id));
		}
		else
		{
			return optionaltodo.get();
		}
	}

	public void updateTodo(String id, TodoDTO todo) throws TodoCollectionException {
		Optional<TodoDTO> todowithid=todorepo.findById(id);
		Optional<TodoDTO> todowithsamename=todorepo.findByTodo(todo.getTodo());
		
		
		if(todowithid.isPresent())
		{
			if(todowithsamename.isPresent() && !todowithsamename.get().getId().equals(id))
			{
			
				throw new TodoCollectionException(TodoCollectionException.TodoAlreadyExists());
				
			}
			
			TodoDTO todtooupdate = todowithid.get();
			
			todtooupdate.setCompleted(todo.getCompleted());
			todtooupdate.setTodo(todo.getTodo());
			todtooupdate.setDescription(todo.getDescription());
			//todtooupdate.setCompleted(todo.getCompleted());
			todtooupdate.setUpdatedAt(new Date(System.currentTimeMillis()));
			todorepo.save(todtooupdate);
				
				
		}
		else
		{
			throw new TodoCollectionException(TodoCollectionException.NotFoundException(id));
		}
	}

	
	public void deleteTodoById(String id) throws TodoCollectionException {
		Optional<TodoDTO> todooptional=todorepo.findById(id);
		
		if(!todooptional.isPresent())
		{
			throw new TodoCollectionException(TodoCollectionException.NotFoundException(id)); 
		}
		else
		{
			todorepo.deleteById(id);
		}

	}

}