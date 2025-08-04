package br.com.ListaDeTarefas.Initializr;

import br.com.ListaDeTarefas.Models.TaskModel;
import br.com.ListaDeTarefas.Repository.TaskRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class DataInitializer implements CommandLineRunner {
    private final TaskRepository taskRepository;

    public DataInitializer(TaskRepository taskRepository){
        this.taskRepository = taskRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if(taskRepository.count() == 0){
            TaskModel primeiraTarefa = new TaskModel("Realizar teste de persistencia", "Adicionar alguns objetos no banco de dados para fazer o teste de conexao e de codigo");
            TaskModel segundaTarefa = new TaskModel("Ser uma pessoa melhor",  "Melhorar como pessoa");

            taskRepository.saveAll(Arrays.asList(primeiraTarefa, segundaTarefa));

            System.out.println("Foram adicionadas duas tarefas dentro do banco de dados");

            taskRepository.findAll().forEach(System.out::println);
        }
        else{
            System.out.println("Ja foram adicionadas tarefas dentro do banco de dados!");
        }

        System.out.println("Banco de dados foi inicializado com sucesso!");
    }
}
