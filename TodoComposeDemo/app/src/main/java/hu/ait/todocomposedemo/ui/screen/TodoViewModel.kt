package hu.ait.todocomposedemo.ui.screen

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hu.ait.todocomposedemo.data.TodoDAO
import hu.ait.todocomposedemo.data.TodoItem
import hu.ait.todocomposedemo.data.TodoPriority
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TodoViewModel @Inject constructor(val todoDAO: TodoDAO) : ViewModel() {

    fun getAllToDoList(): Flow<List<TodoItem>> {
        return todoDAO.getAllTodos()
    }

    suspend fun getAllTodoNum(): Int {
        return todoDAO.getTodosNum()
    }

    suspend fun getImportantTodoNum(): Int {
        return todoDAO.getImportantTodosNum()
    }

    fun addTodoList(todoItem: TodoItem) {
        // launch coroutine when viewModel is in focus
        viewModelScope.launch(Dispatchers.IO) {
            todoDAO.insert(todoItem)
        }
    }


    fun removeTodoItem(todoItem: TodoItem) {
        viewModelScope.launch{
            todoDAO.delete(todoItem)
        }
    }

    fun editTodoItem(editedTodo: TodoItem) {
        viewModelScope.launch {
            todoDAO.update(editedTodo)
        }
    }

    fun changeTodoState(todoItem: TodoItem, value: Boolean) {
        // make new instance triggering state change in table
        val updatedToDo = todoItem.copy()
        updatedToDo.isDone = value
        viewModelScope.launch {
            todoDAO.update(updatedToDo)
        }
    }

    fun clearAllTodos() {
        viewModelScope.launch{
            todoDAO.deleteAllTodos()
        }
    }
}