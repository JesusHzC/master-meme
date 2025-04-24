@file:OptIn(ExperimentalMaterial3Api::class)

package com.jesushz.mastermeme.home.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.jesushz.mastermeme.R
import com.jesushz.mastermeme.home.data.models.DropdownMenu
import kotlin.math.max

@Composable
fun HomeTopBar(
    menuIsExpanded: Boolean = false,
    menuSelected: DropdownMenu,
    onDropdownMenuSelected: (DropdownMenu) -> Unit,
    onDropdownMenuExpanded: () -> Unit,
    onDropdownMenuDismiss: () -> Unit
) {
    var menuMaxWidth by remember {
        mutableIntStateOf(0)
    }
    val menuMaxWidthDp = with(LocalDensity.current) { menuMaxWidth.toDp() }

    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        title = {
            Text(
                text = stringResource(R.string.your_memes),
                style = MaterialTheme.typography.titleLarge
            )
        },
        actions = {
            Box(
                modifier = Modifier.padding(16.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .clickable { onDropdownMenuExpanded() }
                        .onSizeChanged { size ->
                            menuMaxWidth = max(menuMaxWidth, size.width)
                        }
                ) {
                    Text(
                        text = when (menuSelected) {
                            DropdownMenu.FAVORITES -> stringResource(R.string.favorites_first)
                            DropdownMenu.NEWEST -> stringResource(R.string.newest_first)
                        },
                        style = MaterialTheme.typography.titleSmall,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Icon(
                        imageVector = Icons.Default.KeyboardArrowDown,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onBackground
                    )
                }
                DropdownMenu(
                    expanded = menuIsExpanded,
                    onDismissRequest = onDropdownMenuDismiss,
                    modifier = Modifier.width(menuMaxWidthDp)
                ) {
                    DropdownMenu.entries.forEach { option ->
                        DropdownMenuItem(
                            text = {
                                Text(
                                    text = when (option) {
                                        DropdownMenu.FAVORITES -> stringResource(R.string.favorites_first)
                                        DropdownMenu.NEWEST -> stringResource(R.string.newest_first)
                                    },
                                    style = MaterialTheme.typography.titleSmall
                                )
                            },
                            onClick = {
                                onDropdownMenuSelected(option)
                            }
                        )
                    }
                }
            }
        }
    )
}