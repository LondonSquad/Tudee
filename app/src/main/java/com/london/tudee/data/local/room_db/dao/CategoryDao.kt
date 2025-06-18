package com.london.tudee.data.local.room_db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.london.tudee.data.local.room_db.dto.CategoryDto
import kotlinx.coroutines.flow.Flow

@Dao
interface CategoryDao {
    @Query("SELECT * FROM CATEGORY_TABLE")
    fun getAll(): Flow<List<CategoryDto>>

    @Query("SELECT * FROM CATEGORY_TABLE WHERE id = :id")
    fun getById(id: Int): CategoryDto

    @Insert
    suspend fun insert(category: CategoryDto)

    @Update
    suspend fun update(category: CategoryDto)

    @Delete
    suspend fun delete(category: CategoryDto)
}