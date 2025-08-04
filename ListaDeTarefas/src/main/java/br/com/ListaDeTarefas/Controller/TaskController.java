package br.com.ListaDeTarefas.Controller;

import br.com.ListaDeTarefas.Dtos.TaskRecordDto;
import br.com.ListaDeTarefas.Models.TaskModel;
import br.com.ListaDeTarefas.Repository.TaskRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    @Autowired
    TaskRepository taskRepository;

    @GetMapping
    public ResponseEntity<List<TaskModel>> getAllTasks(){
        List<TaskModel> taskList = taskRepository.findAll();
        if(!taskList.isEmpty()){
            for(TaskModel task : taskList){
                task.add(linkTo(methodOn(TaskController.class).getOneTask(task.getId())).withSelfRel());
            }
        }
        return ResponseEntity.ok(taskList); // Sempre retornar status OK para informar que a requisicao foi bem sucedida, mesmo que a lista esteja vazia
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getOneTask(@PathVariable(value = "id") UUID id){
        Optional<TaskModel> task = taskRepository.findById(id);

        if(task.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Tarefa nao foi encontrada");
        }

        task.get().add(linkTo(methodOn(TaskController.class).getAllTasks()).withSelfRel());
        return ResponseEntity.status(HttpStatus.OK).body(task.get());
    }

    @PostMapping
    public ResponseEntity<TaskModel> postTask(@RequestBody @Valid TaskRecordDto taskRecord){
        var task = new TaskModel();
        BeanUtils.copyProperties(taskRecord, task);
        return ResponseEntity.status(HttpStatus.CREATED).body(taskRepository.save(task));
    }

    @DeleteMapping(path = "/delete/{id}")
    public ResponseEntity<String> deleteTaskById(@PathVariable(value = "id") UUID id){
        Optional<TaskModel> taskDelete = taskRepository.findById(id);

        if(taskDelete.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("A tarefa nao foi encontrada");
        }

        taskRepository.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK).body("A tarefa foi excluida com sucesso!");
    }

    @PutMapping(path = "/put/{id}")
    public ResponseEntity<Object> putTaskById(@PathVariable(value = "id") UUID id, @RequestBody @Valid TaskRecordDto taskRecord){
        Optional<TaskModel> task = taskRepository.findById(id);

        if(task.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("A tarefa nao foi encontrada");
        }

    }
}
