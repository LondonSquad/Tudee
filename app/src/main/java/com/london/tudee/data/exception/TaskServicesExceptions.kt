package com.london.tudee.data.exception

sealed class TaskException(message: String) : Exception(message){
    class TaskNotFoundException() :
        TaskException("Task not found")

    class TaskInsertException :
        TaskException("Failed to insert task")

    class TaskUpdateException :
        TaskException("Failed to update task")

    class TaskDeleteException :
        TaskException("Failed to delete task")

    class TaskLoadException :
        TaskException("Failed to load tasks")
}

