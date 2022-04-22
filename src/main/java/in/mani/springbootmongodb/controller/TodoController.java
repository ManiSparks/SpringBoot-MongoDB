package in.mani.springbootmongodb.controller;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.net.ssl.HttpsURLConnection;
import javax.validation.ConstraintViolationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import in.mani.springbootmongodb.ExceptionHandling.TodoCollectionException;
import in.mani.springbootmongodb.model.TodoDTO;
import in.mani.springbootmongodb.repository.TodoRepository;
import in.mani.springbootmongodb.service.TodoService;

@RestController
public class TodoController {
	
	@Autowired
	private TodoService todoservice;
	
	@Autowired
	private TodoRepository todorepo;
	
	/*
	 * @GetMapping("/todos") public ResponseEntity<?> getAllTodos() { List<TodoDTO>
	 * todos=todorepo.findAll(); if(todos.size() > 0) {
	 * System.out.println("im here"); return new
	 * ResponseEntity<List<TodoDTO>>(todos,HttpStatus.OK);
	 * 
	 * } else { System.out.println("im here  else");
	 * 
	 * return new ResponseEntity<>("No Todos aVailable",HttpStatus.NOT_FOUND);
	 * 
	 * }
	 * 
	 * }
	 */
	
	@GetMapping("/todos")
	public ResponseEntity<?> getAllTodos()
	{
		List<TodoDTO> todos=todoservice.getAllTodos();
		return new ResponseEntity<>(todos, todos.size() >0 ? HttpStatus.OK : HttpStatus.NOT_FOUND);
		
	
		
	}
	
	
	
	/*
	 * @PostMapping("/todos") public ResponseEntity<?> createTodo(@RequestBody
	 * TodoDTO todo) {
	 * 
	 * try { todo.setCreatedAt(new Date(System.currentTimeMillis()));
	 * todorepo.save(todo); return new ResponseEntity<TodoDTO>(todo, HttpStatus.OK);
	 * } catch (Exception e) { return new ResponseEntity<>(e.getMessage(),
	 * HttpStatus.INTERNAL_SERVER_ERROR); } //return null;
	 * 
	 * }
	 */
	
	
	@PostMapping("/todos")
	public ResponseEntity<?> createTodo(@RequestBody TodoDTO todo)
	{
		
		try {
			todoservice.createTodo(todo);
			return new ResponseEntity<TodoDTO>(todo, HttpStatus.OK);
			
		    } 
		catch (ConstraintViolationException e) 
		  {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
		  }
		catch(TodoCollectionException e)
		{
			return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
		}
	}
	
	
	/*
	 * @GetMapping("/todo/{id}") public ResponseEntity<?>
	 * getSingleTodo(@PathVariable("id") String id){ Optional<TodoDTO>
	 * todooptional=todorepo.findById(id);
	 * 
	 * if(todooptional.isPresent()) { return new
	 * ResponseEntity<>(todooptional.get(), HttpStatus.OK); } else { return new
	 * ResponseEntity<>("todo not found with id"+id,HttpStatus.NOT_FOUND); }
	 * 
	 * 
	 * }
	 */
	
	
	@GetMapping("/todo/{id}")
	public ResponseEntity<?> getSingleTodo(@PathVariable("id") String id){
	try {
		return new  ResponseEntity<>(todoservice.getSingleTodo(id),HttpStatus.OK);
	} catch (Exception e) {
		return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
	}
	
	}
	
	
	/*
	 * @PutMapping("/todo/{id}") public ResponseEntity<?>
	 * updatebyTodo(@PathVariable("id") String id, @RequestBody TodoDTO todo){
	 * Optional<TodoDTO> todooptional=todorepo.findById(id);
	 * 
	 * if(todooptional.isPresent()) { TodoDTO todotoSave=todooptional.get();
	 * todotoSave.setCompleted(todo.getCompleted() != null ? todo.getCompleted() :
	 * todotoSave.getCompleted()); todotoSave.setTodo(todo.getTodo() != null ?
	 * todo.getTodo():todotoSave.getTodo()); todotoSave.setUpdatedAt(new
	 * Date(System.currentTimeMillis()));
	 * todotoSave.setDescription(todo.getDescription() != null ?
	 * todo.getDescription():todotoSave.getDescription());
	 * todorepo.save(todotoSave); return new ResponseEntity<>(todotoSave,
	 * HttpStatus.OK); } else { return new
	 * ResponseEntity<>("todo not found with id"+id,HttpStatus.NOT_FOUND); }
	 * 
	 * 
	 * }
	 */
	
	
	@PutMapping("/todo/{id}")
	public ResponseEntity<?> updatebyTodo(@PathVariable("id") String id, @RequestBody TodoDTO todo){
		
	try {
		todoservice.updateTodo(id, todo);
		return new ResponseEntity<>("update Todo with id"+id, HttpStatus.OK);
	} catch (ConstraintViolationException e) {
		return new ResponseEntity<>(e.getMessage(),HttpStatus.UNPROCESSABLE_ENTITY);
	}
	catch(TodoCollectionException e)
	{
		return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
	}
	}
	
	
	
	/*
	 * @DeleteMapping("/todo/{id}") public ResponseEntity<?>
	 * deleteByID(@PathVariable("id") String id) { try { todorepo.deleteById(id);
	 * return new ResponseEntity<>("Successfully deleted with id"+id,
	 * HttpStatus.OK);
	 * 
	 * 
	 * } catch (Exception e) { // TODO: handle exception return new
	 * ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND); } }
	 */
	
	@DeleteMapping("/todo/{id}")
	public ResponseEntity<?> deleteByID(@PathVariable("id") String id)
	{
		try {
			todoservice.deleteTodoById(id);
			return new  ResponseEntity<>("Successfully deleted with id"+id, HttpStatus.OK);
			
			
		} catch (TodoCollectionException e) {
			// TODO: handle exception
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}

}
