//
//@Composable
//fun AddOrEditTaskBottomSheet(
//    modifier: Modifier = Modifier,
//    @StringRes title: Int,
//    @StringRes buttonText: Int,
//    onDismiss: () -> Unit,
//    titleValue: String,
//    descriptionValue: String,
//    onTitleValueChange: (String) -> Unit,
//    onDescriptionValueChange: (String) -> Unit,
//    screenContent: @Composable () -> Unit
//) {
//    TudeeBottomSheetScreen(
//        showBottomSheet = true,
//        onDismiss = onDismiss,
//        screenContent = { screenContent() },
//        bottomSheetContent = {
//            AddOrEditTaskDetails(
//                modifier = modifier,
//                title = title,
//                titleValue = titleValue,
//                descriptionValue = descriptionValue,
//                onTitleValueChange = onTitleValueChange,
//                onDescriptionValueChange = onDescriptionValueChange
//            )
//        },
//        bottomSheetActions = {
//            TudeePrimaryButton(
//                modifier = Modifier.fillMaxWidth(),
//                text = stringResource(buttonText),
//                isDisabled = false,
//                onClick = onDismiss,
//            )
//
//            Spacer(modifier = Modifier.height(12.dp))
//
//            TudeeSecondaryButton(
//                modifier = Modifier.fillMaxWidth(),
//                text = stringResource(R.string.cancel),
//                onClick = onDismiss,
//            )
//        }
//    )
//}
//
//@ThemePreviews
//@Composable
//fun PreviewAddOrEditTaskBottomSheet() {
//    TudeeTheme {
//        AddOrEditTaskBottomSheet(
//            title = R.string.task_title,
//            buttonText = R.string.add,
//            onDismiss = {},
//            screenContent = {
//
//            },
//            titleValue = "",
//            descriptionValue = "",
//            onTitleValueChange = {},
//            onDescriptionValueChange = {}
//        )
//    }
//}