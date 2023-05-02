package com.example.personalcoach.view.bottomNavigation.setting.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.personalcoach.ui.theme.ExtendedJetTheme

data class MenuItemModel(
    val title: String,
    val currentIndex: Int = 0,
    val values: List<String>
)

@Composable
internal fun MenuItem(
    model: MenuItemModel,
    onItemSelected: ((Int) -> Unit)? = null
){
    val isDropdownOpen = remember {
        mutableStateOf(false)
    }

    val currentPosition = remember {
        mutableStateOf(model.currentIndex)
    }

    Column(
        
    ) {
        Box(
            modifier = Modifier
                .background(ExtendedJetTheme.colors.primaryBackground)
                .fillMaxWidth()){
            Row(
                modifier = Modifier
                    .clickable {
                        isDropdownOpen.value = true
                    }
                    .padding(ExtendedJetTheme.shapes.padding)
                    .background(ExtendedJetTheme.colors.primaryBackground),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = model.title,
                    style = ExtendedJetTheme.typography.body,
                    color = ExtendedJetTheme.colors.primaryText,
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = ExtendedJetTheme.shapes.padding)
                )

                Text(
                    text = model.values[currentPosition.value],
                    style = ExtendedJetTheme.typography.body,
                    color = ExtendedJetTheme.colors.secondaryText
                )
            }
            Divider(
                modifier = Modifier
                    .padding(start = 16.dp)
                    .align(Alignment.BottomStart),
                thickness = 0.5.dp,
                color = ExtendedJetTheme.colors.secondaryText.copy(
                    alpha = 0.3f
                )
            )

            DropdownMenu(expanded = isDropdownOpen.value, onDismissRequest = {
                isDropdownOpen.value = false
            },
                modifier = Modifier
                    .fillMaxWidth()
                    .background(ExtendedJetTheme.colors.primaryBackground))
            {
               model.values.forEachIndexed{index, value -> 
                   DropdownMenuItem(text = {
                      Text(text = value, style = ExtendedJetTheme.typography.body,
                      color = ExtendedJetTheme.colors.primaryText)
                   }, onClick = {
                       currentPosition.value = index
                       isDropdownOpen.value = false
                       onItemSelected?.invoke(index)
                   })
               }
            }
        }
    }
}