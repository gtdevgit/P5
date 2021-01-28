package com.cleanup.todoc.ui;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;
import com.cleanup.todoc.repository.ProjectDataRepository;
import com.cleanup.todoc.repository.TaskDataRepository;

import java.util.List;
import java.util.concurrent.Executor;

public class TaskViewModel extends ViewModel {

    private final ProjectDataRepository projectDataRepository;
    private final TaskDataRepository taskDataRepository;
    private final Executor executor;

    @Nullable
    private LiveData<List<Project>> projects;
    private LiveData<List<Task>> tasks;


    public TaskViewModel(ProjectDataRepository projectDataRepository, TaskDataRepository taskDataRepository, Executor executor) {
        this.projectDataRepository = projectDataRepository;
        this.taskDataRepository = taskDataRepository;
        this.executor = executor;
    }

    public void init(){
        // C'est moche !
        if (this.projects != null) {
            return;
        }
        projects = projectDataRepository.getProjects();
        tasks = taskDataRepository.getTasks();
    }

    public LiveData<List<Project>> getProjects() {
        return this.projects;
    }

    public LiveData<List<Task>> getTasks() {
        return this.tasks;
    }

    public void createTask(Task task){
        executor.execute(() -> {
            taskDataRepository.createTask(task);
        });
    }

    public void deleteTask(long id){
        executor.execute(() -> {
            taskDataRepository.deleteTask(id);
        });
    }

    public void updateTask(Task task) {
        executor.execute(() -> {
            taskDataRepository.updateItem(task);
        });
    }
}
