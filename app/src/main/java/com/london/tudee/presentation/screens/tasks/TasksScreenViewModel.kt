package com.london.tudee.presentation.screens.tasks

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.london.tudee.domain.entities.Priority
import com.london.tudee.domain.entities.Task
import com.london.tudee.domain.entities.TaskStatus
import com.london.tudee.domain.services.TaskService
import com.london.tudee.presentation.utils.DateFormatter.toDayNumber
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
import kotlin.math.log

class TasksScreenViewModel(
    private val taskService: TaskService,
) : ViewModel() {
    private val _uiState = MutableStateFlow(FilterTasksUiState())
    val uiState = _uiState.asStateFlow()

    init {
        // addTestTask()
        getTargetDates(date = _uiState.value.date, arrowAction = ArrowActions.None)
        getDoneTasks()
        getToDoTasks()
        getInProgressTasks()
    }

//    private fun addTestTask() {
//        viewModelScope.launch(Dispatchers.IO) {
//            taskService.add(
//                Task(
//                    title = "11111111111111111111111111111111111111111111111111111111111111111111111111111 dasfasdxasdas",
//                    description = "Test Task Description",
//                    taskStatus = TaskStatus.TODO,
//                    priority = Priority.LOW,
//                    categoryId = 1,
//                )
//            )
//        }
//    }

    private fun getDoneTasks() {
        viewModelScope.launch(Dispatchers.IO) {
            val targetDate = Instant.fromEpochMilliseconds(_uiState.value.date)
                .toLocalDateTime(TimeZone.currentSystemDefault()).date

            val (startOfDayMillis, endOfDayMillis) = getDayRangeMillis(targetDate)
            taskService.getTasksForDay(
                start = startOfDayMillis, end = endOfDayMillis, taskStatus = TaskStatus.DONE
            ).catch { throwable ->
                _uiState.update {
                    it.copy(isLoading = false, errMessage = throwable.message)
                }
            }.collect { tasks ->
                _uiState.update { state ->
                    state.copy(
                        isLoading = false, errMessage = null, doneTasks = tasks
                    )
                }
            }
        }
    }

    private fun getInProgressTasks() {
        viewModelScope.launch(Dispatchers.IO) {
            val targetDate = Instant.fromEpochMilliseconds(_uiState.value.date)
                .toLocalDateTime(TimeZone.currentSystemDefault()).date

            val (startOfDayMillis, endOfDayMillis) = getDayRangeMillis(targetDate)

            taskService.getTasksForDay(
                start = startOfDayMillis, end = endOfDayMillis, taskStatus = TaskStatus.IN_PROGRESS
            ).catch { throwable ->
                _uiState.update {
                    it.copy(isLoading = false, errMessage = throwable.message)
                }
            }.collect { tasks ->
                _uiState.update { state ->
                    Log.d("23342q42", "getInProgressTasks: $tasks")
                    state.copy(
                        isLoading = false, errMessage = null, inProgressTasks = tasks
                    )
                }
            }
        }
    }

    private fun getToDoTasks() {
        viewModelScope.launch(Dispatchers.IO) {
            val targetDate = Instant.fromEpochMilliseconds(_uiState.value.date)
                .toLocalDateTime(TimeZone.currentSystemDefault()).date
            val (startOfDayMillis, endOfDayMillis) = getDayRangeMillis(targetDate)
            taskService.getTasksForDay(
                start = startOfDayMillis, end = endOfDayMillis, taskStatus = TaskStatus.TODO
            ).catch { throwable ->
                _uiState.update {
                    it.copy(isLoading = false, errMessage = throwable.message)
                }
            }.collect { tasks ->
                _uiState.update { state ->
                    state.copy(
                        isLoading = false, errMessage = null, toDoTasks = tasks
                    )
                }
            }
        }
    }

    fun onDateChange(date: Long?) {
        _uiState.update {
            it.copy(date = date ?: System.now().toEpochMilliseconds())
        }
        getDoneTasks()
        getToDoTasks()
        getInProgressTasks()
    }


    fun getTargetDates(date: Long, arrowAction: ArrowActions) {
        val currentDate = Instant.fromEpochMilliseconds(date)
            .toLocalDateTime(TimeZone.currentSystemDefault()).date

        val selectedDate = Instant.fromEpochMilliseconds(_uiState.value.date)
            .toLocalDateTime(TimeZone.currentSystemDefault()).date

        val targetDate = when (arrowAction) {
            ArrowActions.Next -> currentDate.plus(DatePeriod(months = 1))
            ArrowActions.Previous -> currentDate.minus(DatePeriod(months = 1))
            ArrowActions.None -> currentDate
        }

        onDateChange(
            targetDate.atStartOfDayIn(TimeZone.currentSystemDefault()).toEpochMilliseconds()
        )

        _uiState.update { currentState ->
            val daysOfMonth = (1..targetDate.lengthOfMonth()).map { day ->
                val dateForDay = LocalDate(targetDate.year, targetDate.month, day)
                val dayOfWeek = dateForDay.dayOfWeek.name.take(3).lowercase()
                    .replaceFirstChar { it.uppercase() }

                DaysOfMonth(
                    dayOfMonth = day.toString(),
                    dayOfWeek = dayOfWeek,
                    isSelected = dateForDay == selectedDate,
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

    private fun isLeapYear(year: Int): Boolean =
        (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0)


    fun selectDayCard(indexOfSelectedDay: Int) {
        val days = _uiState.value.days.toMutableList()
        val currentDate = Instant.fromEpochMilliseconds(_uiState.value.date)
            .toLocalDateTime(TimeZone.currentSystemDefault()).date
        days.forEach { it.isSelected = false }
        days[indexOfSelectedDay].isSelected = true
        val selectedDayOfMonth = days[indexOfSelectedDay].dayOfMonth.toInt()
        val selectedDate =
            LocalDate(currentDate.year, Month(currentDate.monthNumber), selectedDayOfMonth)
        val dateInMillis =
            selectedDate.atStartOfDayIn(TimeZone.currentSystemDefault()).toEpochMilliseconds()

        days[indexOfSelectedDay].date = dateInMillis
        onDateChange(dateInMillis)
        _uiState.update {
            it.copy(days = days, dayItemIndex = indexOfSelectedDay, date = dateInMillis)
        }
    }

    private fun getDayRangeMillis(targetDate: LocalDate): Pair<Long, Long> {
        val timeZone = TimeZone.currentSystemDefault()
        val startOfDayMillis = targetDate.atStartOfDayIn(timeZone).toEpochMilliseconds()
        val endOfDayMillis =
            targetDate.plus(DatePeriod(days = 1)).atStartOfDayIn(timeZone).toEpochMilliseconds() - 1
        return Pair(startOfDayMillis, endOfDayMillis)
    }
}