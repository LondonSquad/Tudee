package com.london.tudee.presentation.screens.tasks

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.london.tudee.domain.entities.TaskStatus
import com.london.tudee.domain.services.TaskService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock.System
import kotlinx.datetime.DatePeriod
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate
import kotlinx.datetime.Month
import kotlinx.datetime.TimeZone
import kotlinx.datetime.atStartOfDayIn
import kotlinx.datetime.minus
import kotlinx.datetime.plus
import kotlinx.datetime.toLocalDateTime

class TasksScreenViewModel(
    private val taskService: TaskService,
) : ViewModel() {
    private val _uiState = MutableStateFlow(FilterTasksUiState())
    val uiState = _uiState.asStateFlow()

    init {
        getDoneTasks()
        getToDoTasks()
        getInProgressTasks()
    }

    private fun getDoneTasks() {
        viewModelScope.launch(Dispatchers.IO) {
            taskService.getByDateAndTaskStatus(
                timeStamp = _uiState.value.date,
                taskStatus = TaskStatus.DONE
            ).catch { throwable ->
                _uiState.update {
                    it.copy(isLoading = false, errMessage = throwable.message)
                }
            }.collect { tasks ->
                _uiState.update { state ->
                    state.copy(
                        isLoading = false,
                        errMessage = null,
                        doneTasks = tasks
                    )
                }
            }
        }
    }

    private fun getInProgressTasks() {
        viewModelScope.launch(Dispatchers.IO) {
            taskService.getByDateAndTaskStatus(
                timeStamp = _uiState.value.date,
                taskStatus = TaskStatus.IN_PROGRESS
            ).catch { throwable ->
                _uiState.update {
                    it.copy(isLoading = false, errMessage = throwable.message)
                }
            }.collect { tasks ->
                _uiState.update { state ->
                    state.copy(
                        isLoading = false,
                        errMessage = null,
                        inProgressTasks = tasks
                    )
                }
            }
        }
    }

    private fun getToDoTasks() {
        viewModelScope.launch(Dispatchers.IO) {
            taskService.getByDateAndTaskStatus(
                timeStamp = _uiState.value.date,
                taskStatus = TaskStatus.TODO
            ).catch { throwable ->
                _uiState.update {
                    it.copy(isLoading = false, errMessage = throwable.message)
                }
            }.collect { tasks ->
                _uiState.update { state ->
                    state.copy(
                        isLoading = false,
                        errMessage = null,
                        toDoTasks = tasks
                    )
                }
            }
        }
    }

    fun onDateChange(date: Long?) {
        _uiState.update {
            it.copy(date = date ?: System.now().toEpochMilliseconds())
        }
    }


    fun getTargetDates(date: Long, arrowAction: ArrowActions) {
        val currentDate = Instant.fromEpochMilliseconds(date)
            .toLocalDateTime(TimeZone.currentSystemDefault())
            .date

        val selectedDate = Instant.fromEpochMilliseconds(_uiState.value.date)
            .toLocalDateTime(TimeZone.currentSystemDefault())
            .date

        val targetDate = when (arrowAction) {
            ArrowActions.Next -> currentDate.plus(DatePeriod(months = 1))
            ArrowActions.Previous -> currentDate.minus(DatePeriod(months = 1))
        }

        onDateChange(targetDate.atStartOfDayIn(TimeZone.currentSystemDefault()).toEpochMilliseconds())

        _uiState.update { currentState ->
            val daysOfMonth = (1..targetDate.lengthOfMonth()).map { day ->
                val dateForDay = LocalDate(targetDate.year, targetDate.month, day)
                val dayOfWeek = dateForDay.dayOfWeek.name.take(3).lowercase().replaceFirstChar { it.uppercase() }

                DaysOfMonth(
                    dayOfMonth = day.toString(),
                    dayOfWeek = dayOfWeek,
                    isSelected = dateForDay == selectedDate
                )
            }
            currentState.copy(days = daysOfMonth)
        }
    }

    private fun LocalDate.lengthOfMonth(): Int {
        return when (month) {
            Month.FEBRUARY -> if (isLeapYear(year)) 29 else 28
            Month.APRIL, Month.JUNE, Month.SEPTEMBER, Month.NOVEMBER -> 30
            else -> 31
        }
    }

    private fun isLeapYear(year: Int): Boolean {
        return (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0)
    }


    fun selectDayCard(indexOfSelectedDay: Int) {
        val days = _uiState.value.days.toMutableList()
        days[indexOfSelectedDay].isSelected = true
        _uiState.update {
            it.copy(days = days)
        }
    }
}