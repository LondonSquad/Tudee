package com.london.tudee.presentation.screens.category.category_details

import com.london.tudee.domain.entities.Category

interface CategoryDetailsInteractions {
    fun getDoneTasksByCategoryId(categoryId: Int)
    fun getInProgressTasksByCategoryId(categoryId: Int)
    fun getToDoTasksByCategoryId(categoryId: Int)
    fun getCategoryNameById(categoryId: Int)
    fun showEditBottomSheet()
    fun hideEditBottomSheet()
    fun showDeleteBottomSheet()
    fun hideDeleteBottomSheet()
    fun editCategory(category: Category)
    fun deleteCategory(category: Category)
    fun refreshAfterChange()
    fun onCategoryDeleted()
    fun onCategoryEdited()
    fun onCategoryEditError()
    fun onCategoryDeleteError()
    fun clearMessages()
}
