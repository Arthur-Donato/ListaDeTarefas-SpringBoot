package br.com.ListaDeTarefas.Dtos;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record TaskRecordDto(@NotBlank @NotNull String title, @NotBlank @NotNull String description, @NotNull boolean isComplete) {
}
