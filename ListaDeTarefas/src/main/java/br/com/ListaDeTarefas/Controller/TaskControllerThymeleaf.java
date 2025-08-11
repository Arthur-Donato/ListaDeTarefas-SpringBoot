package br.com.ListaDeTarefas.Controller;

import br.com.ListaDeTarefas.Models.TaskModel;
import br.com.ListaDeTarefas.Repository.TaskRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@Controller
public class TaskControllerThymeleaf{

    private final TaskRepository taskRepository; // Conexao com o banco de dados

    @Autowired
    public TaskControllerThymeleaf(TaskRepository taskRepository){
        this.taskRepository = taskRepository;
    }


    @GetMapping(path = "/task")
    public String getAllTasks(Model model){
        model.addAttribute("tasks", taskRepository.findAll());
        model.addAttribute("task", new TaskModel());

        return "ListaDeTarefas";
    }

    @GetMapping(path = "/task/add")
    public String addNewTask(Model model){
        model.addAttribute("task", new TaskModel());

        return "AdicionarNovaTarefa";
    }

    @GetMapping(path = "/task/edit/{id}")
    public String editTask(@PathVariable(value = "id") UUID id, Model model){
        Optional<TaskModel> optionalTask = taskRepository.findById(id);

        if(optionalTask.isEmpty()){
            return "TarefaNaoEncontrada";
        }
        var oldTask = optionalTask.get();

        model.addAttribute("oldTask", oldTask);
        model.addAttribute("task", new TaskModel());
        model.addAttribute("taskID", id);

        return "EditarTarefa";
    }

    @PostMapping(path = "/add")
    public String postTask(@ModelAttribute TaskModel task){

        taskRepository.save(task);

        return "redirect:/task";
    }

    @PostMapping(path = "/edit/{id}")
    public String editTaskMap(@ModelAttribute TaskModel taskEdit, @PathVariable(value = "id") UUID id){
        Optional<TaskModel> oldTask = taskRepository.findById(id);

        if(oldTask.isEmpty()){
            return "TarefaNaoEncontrada";
        }

        var oldTaskEdit = oldTask.get();
        BeanUtils.copyProperties(taskEdit, oldTaskEdit);
        taskRepository.save(oldTaskEdit);

        return "redirect:/task";
    }

    @PutMapping(path = "/task/complete/{id}")
    public String completeTask(@PathVariable(value = "id") UUID id){
        Optional<TaskModel> taskOptional = taskRepository.findById(id);

        if(taskOptional.isPresent()){
            var taskModel = taskOptional.get();
            taskModel.setComplete(!taskModel.isComplete());
            taskRepository.save(taskModel);
            return "redirect:/task";
        }

        return "TarefaNaoEncontrada";
    }

    @DeleteMapping(path = "/task/delete/{id}")
    public String deleteTask(@PathVariable(value = "id") UUID id){
        Optional<TaskModel> taskOptional = taskRepository.findById(id);

        if(taskOptional.isPresent()){
            taskRepository.deleteById(id);
            return "redirect:/task";
        }

        return "TarefaNaoEncontrada";
    }

}
