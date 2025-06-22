package com.london.tudee.data.local.roomdb

import com.london.tudee.R
import com.london.tudee.domain.entities.Category


fun defaultCategory() = listOf(
    Category(
        id = 0, title = "Education",
        iconRes = R.drawable.ic_education.toString(),
        // arName = "التعليم",
        isDefault = true, taskCount = 0,
        //   tint = TudeeTheme.colors.purpleAccent.value.toLong()
    ), Category(
        id = 0,
        title = "Shopping",
        // arName = "التسوق",
        iconRes = R.drawable.ic_shopping.toString(),
        isDefault = true,
        taskCount = 0,
        //  tint = TudeeTheme.colors.secondary.value.toLong()
    ), Category(
        id = 0,
        title = "Medical",
        //arName = "الطب",
        iconRes = R.drawable.ic_medical.toString(),
        isDefault = true,
        taskCount = 0,
        //tint = TudeeTheme.colors.primary.value.toLong()
    ), Category(
        id = 0, title = "Gym", iconRes = R.drawable.ic_gym.toString(),
        // arName = "التمرين",
        isDefault = true, taskCount = 0,
        //tint = TudeeTheme.colors.primary.value.toLong()
    ), Category(
        id = 0,
        title = "Entertainment",
        // arName = "الترفيه",
        iconRes = R.drawable.ic_entertainment.toString(),
        isDefault = true,
        taskCount = 0,
        //tint = TudeeTheme.colors.yellowAccent.value.toLong()
    ), Category(
        id = 0,
        title = "Cooking",
        // arName = "الطب",
        iconRes = R.drawable.ic_cooking.toString(),
        isDefault = true,
        taskCount = 0,
        //   tint = TudeeTheme.colors.pinkAccent.value.toLong()
    ), Category(
        id = 0,
        title = "Family & friend",
        // arName = "الأسرة والاصدقاء",
        iconRes = R.drawable.ic_family.toString(),
        isDefault = true,
        taskCount = 0,
        // tint = TudeeTheme.colors.secondary.value.toLong()
    ), Category(
        id = 0,
        title = "Traveling",
        // arName = "السفر",
        iconRes = R.drawable.ic_travel.toString(),
        isDefault = true,
        taskCount = 0,
        //tint = TudeeTheme.colors.yellowAccent.value.toLong()
    ), Category(
        id = 0,
        title = "Agriculture",
        // arName = "الزراعة",
        iconRes = R.drawable.ic_agriculture.toString(),
        isDefault = true,
        taskCount = 0,
        //tint = TudeeTheme.colors.greenAccent.value.toLong()
    ), Category(
        id = 0,
        title = "Coding",
        //  arName = "البرمجة",
        iconRes = R.drawable.ic_coding.toString(),
        isDefault = true,
        taskCount = 0,
        //tint = TudeeTheme.colors.purpleAccent.value.toLong()
    ), Category(
        id = 0,
        title = "Adoration",
        //arName = "الاعزاء",
        iconRes = R.drawable.ic_adoration.toString(),
        isDefault = true,
        taskCount = 0,
        //tint = TudeeTheme.colors.primary.value.toLong()
    ), Category(
        id = 0,
        title = "Fixing bugs",
        //arName = "التصليح",
        iconRes = R.drawable.ic_bug_fix.toString(),
        isDefault = true,
        taskCount = 0,
        // tint = TudeeTheme.colors.pinkAccent.value.toLong()
    ), Category(
        id = 0,
        title = "Cleaning",
        //arName = "التنظيف",
        iconRes = R.drawable.ic_cleaning.toString(),
        isDefault = true,
        taskCount = 0,
        // tint = TudeeTheme.colors.greenAccent.value.toLong()
    ), Category(
        id = 0,
        title = "Work",
        //arName = "العمل",
        iconRes = R.drawable.ic_work.toString(),
        isDefault = true,
        taskCount = 0,
        // tint = TudeeTheme.colors.secondary.value.toLong()
    ), Category(
        id = 0,
        title = "Budgeting",
        //arName = "الحسابات",
        iconRes = R.drawable.ic_budgeting.toString(),
        isDefault = true,
        taskCount = 0,
        // tint = TudeeTheme.colors.purpleAccent.value.toLong()
    ), Category(
        id = 0,
        title = "Self-care",
        //arName = "الحسابات",
        iconRes = R.drawable.ic_self_care.toString(),
        isDefault = true,
        taskCount = 0,
        //   tint = TudeeTheme.colors.yellowAccent.value.toLong()
    ), Category(
        id = 0,
        title = "Event",
        //arName = "الحسابات",
        iconRes = R.drawable.ic_event.toString(),
        isDefault = true,
        taskCount = 0,
        //tint = TudeeTheme.colors.pinkAccent.value.toLong()
    )
)