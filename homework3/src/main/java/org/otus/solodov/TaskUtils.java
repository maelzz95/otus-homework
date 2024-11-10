package org.otus.solodov;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

import org.otus.solodov.Task.TaskStatus;

public class TaskUtils {

    private static Stream<Task> getTasksByStatus(Stream<Task> taskStream, TaskStatus taskStatus) {
        return taskStream.filter(task -> task.getStatus() == taskStatus);
    }

    public static List<Task> getTasksByStatus(List<Task> taskList, TaskStatus taskStatus) {
        return getTasksByStatus(taskList.stream(), taskStatus).toList();
    }

    public static boolean taskListContainsElement(List<Task> taskList, long taskId) {
        return taskList.stream().anyMatch(task -> task.getId() == taskId);
    }

    public static List<Task> sortTaskListByStatus(List<Task> taskList) {
        return taskList.stream()
                .sorted(Comparator.comparing(task -> task.getStatus().name()))
                .toList();
    }

    public static long tasksCountByStatus(List<Task> taskList, TaskStatus taskStatus) {
        return getTasksByStatus(taskList.stream(), taskStatus).count();
    }


}
