package com.london.tudee.data.exception

import com.london.tudee.data.exception.CategoryExceptionMessages.CATEGORY_NOT_FOUND
import com.london.tudee.data.exception.CategoryExceptionMessages.ADD_CATEGORY_FAILED
import com.london.tudee.data.exception.CategoryExceptionMessages.UPDATE_CATEGORY_FAILED
import com.london.tudee.data.exception.CategoryExceptionMessages.DELETE_CATEGORY_FAILED
import com.london.tudee.data.exception.CategoryExceptionMessages.GET_ALL_CATEGORIES_FAILED
import com.london.tudee.data.exception.CategoryExceptionMessages.GET_CATEGORY_ICON_FAILED

open class CategoriesServicesException(message: String) : Exception(message)

class CategoryNotFoundException : CategoriesServicesException(CATEGORY_NOT_FOUND)

class AddCategoryException : CategoriesServicesException(ADD_CATEGORY_FAILED)

class EditCategoryException : CategoriesServicesException(UPDATE_CATEGORY_FAILED)

class DeleteCategoryException : CategoriesServicesException(DELETE_CATEGORY_FAILED)

class GetAllCategoriesException : CategoriesServicesException(GET_ALL_CATEGORIES_FAILED)

class GetCategoryIconException : CategoriesServicesException(GET_CATEGORY_ICON_FAILED)


object CategoryExceptionMessages {
    const val CATEGORY_NOT_FOUND = "The requested category was not found."
    const val ADD_CATEGORY_FAILED = "Failed to add the category. Please try again."
    const val UPDATE_CATEGORY_FAILED = "Failed to update the category. Please check your input."
    const val DELETE_CATEGORY_FAILED = "Failed to delete the category. It may not exist or is in use."
    const val GET_ALL_CATEGORIES_FAILED = "Unable to retrieve categories at the moment."
    const val GET_CATEGORY_ICON_FAILED = "Failed to retrieve the icon of the category."
}