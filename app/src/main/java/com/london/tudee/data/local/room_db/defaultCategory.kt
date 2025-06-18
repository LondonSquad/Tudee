package com.london.tudee.data.local.room_db

import com.london.tudee.R
import com.london.tudee.domain.entities.Category


val defaultCategory = listOf(
    Category(
        id = 0, name = "Education",
        iconPath = R.drawable.ic_education.toString(),
        arName = "التعليم",
        isDefault = true,
    ), Category(
        id = 0,
        name = "Shopping",
        arName = "التسوق",
        isDefault = false,
        iconPath = R.drawable.ic_shopping.toString()
    ), Category(
        id = 0,
        name = "Medical",
        arName = "الطب",
        isDefault = false,
        iconPath = R.drawable.ic_medical.toString()
    ), Category(
        id = 0, name = "Gym",
        iconPath = R.drawable.ic_gym.toString(),
        arName = "التمرين",
        isDefault = false,
    ), Category(
        id = 0,
        name = "Entertainment",
        arName = "الترفيه",
        isDefault = false,
        iconPath = R.drawable.ic_entertainment.toString()
    ), Category(
        id = 0,
        name = "Cooking",
        arName = "الطب",
        isDefault = false,
        iconPath = R.drawable.ic_cooking.toString()
    ), Category(
        id = 0,
        name = "Family & friend",
        arName = "الأسرة والاصدقاء",
        isDefault = false,
        iconPath = R.drawable.ic_family.toString()
    ), Category(
        id = 0,
        name = "Traveling",
        arName = "السفر",
        isDefault = false,
        iconPath = R.drawable.ic_travel.toString()
    ), Category(
        id = 0,
        name = "Agriculture",
        arName = "الزراعة",
        isDefault = false,
        iconPath = R.drawable.ic_agriculture.toString()
    ), Category(
        id = 0,
        name = "Coding",
        arName = "البرمجة",
        isDefault = false,
        iconPath = R.drawable.ic_coding.toString()
    ), Category(
        id = 0,
        name = "Adoration",
        arName = "الاعزاء",
        isDefault = false,
        iconPath = R.drawable.ic_adoration.toString()
    ), Category(
        id = 0,
        name = "Fixing bugs",
        arName = "التصليح",
        isDefault = false,
        iconPath = R.drawable.ic_bug_fix.toString()
    ), Category(
        id = 0,
        name = "Cleaning",
        arName = "التنظيف",
        isDefault = false,
        iconPath = R.drawable.ic_cleaning.toString()
    ), Category(
        id = 0,
        name = "Work",
        arName = "العمل",
        isDefault = false,
        iconPath = R.drawable.ic_work.toString()
    ), Category(
        id = 0,
        name = "Budgeting",
        arName = "الحسابات",
        isDefault = false,
        iconPath = R.drawable.ic_budgeting.toString()
    )
)
