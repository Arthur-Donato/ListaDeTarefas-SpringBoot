package br.com.ListaDeTarefas.Models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

import java.io.Serial;
import java.io.Serializable;
import java.util.UUID;

@Entity
@Table(name = "TB_TASKS")
public class TaskModel extends RepresentationModel<TaskModel> implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Setter
    @Getter
    private UUID id;

    @Getter
    @Setter
    private String title;
    @Setter
    @Getter
    private String description;
    @Setter
    @Getter
    private boolean isComplete = false;

    public TaskModel(String title, String description){
        this.title = title;
        this.description = description;
    }

    public TaskModel(){

    }


}
