package org.otus.solodov;

import java.util.List;
import org.otus.solodov.Task.TaskStatus;

public class Main {
    public static void main(String[] args) {
        List<Task> taskList = List.of(
                new Task(1, "Task1", TaskStatus.IN_WORK),
                new Task(2, "Task2", TaskStatus.CLOSED),
                new Task(3, "Task3", TaskStatus.IN_WORK),
                new Task(4, "Task4", TaskStatus.CLOSED),
                new Task(5, "Task5", TaskStatus.OPEN));

        List<Task> closedTasks = TaskUtils.getTasksByStatus(taskList, TaskStatus.CLOSED);
        System.out.println("Closed tasks:");
        closedTasks.forEach(System.out::println);

        System.out.println("Tasks list contains task with ID 5: " + TaskUtils.taskListContainsElement(taskList, 5));

        List<Task> sortedTasks = TaskUtils.sortTaskListByStatus(taskList);
        System.out.println("Sorted tasks by status:");
        sortedTasks.forEach(System.out::println);

        long tasksInWork = TaskUtils.tasksCountByStatus(taskList, TaskStatus.IN_WORK);
        System.out.println("Tasks count in work: " + tasksInWork);
    }
}