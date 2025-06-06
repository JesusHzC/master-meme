@file:OptIn(ExperimentalFoundationApi::class)

package com.jesushz.mastermeme.home.presentation

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.jesushz.mastermeme.R
import com.jesushz.mastermeme.home.presentation.components.HomeTopBar
import com.jesushz.mastermeme.core.presentation.designsystem.components.MasterMemeScaffold
import com.jesushz.mastermeme.core.presentation.designsystem.theme.MasterMemeTheme
import com.jesushz.mastermeme.home.data.MemeTemplate
import com.jesushz.mastermeme.home.domain.Meme
import com.jesushz.mastermeme.home.presentation.components.HomeActionsTopBar
import com.jesushz.mastermeme.home.presentation.components.HomeBottomSheet
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeScreenRoot(
    viewModel: HomeViewModel = koinViewModel(),
    onNavigateToEditor: (MemeTemplate) -> Unit,
    onShareMemes: (List<Meme>) -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    HomeScreen(
        state = state,
        onAction = { action ->
            when (action) {
                is HomeAction.OnTemplateSelected -> {
                    onNavigateToEditor(action.template)
                }
                is HomeAction.OnShareClick -> {
                    onShareMemes(action.memes)
                }
                else -> viewModel.onAction(action)
            }
        }
    )
}

@Composable
private fun HomeScreen(
    state: HomeState,
    onAction: (HomeAction) -> Unit
) {
    val selectedMemes = remember { mutableStateListOf<Meme>() }
    val selectionMode = selectedMemes.isNotEmpty()

    BackHandler(enabled = selectionMode) {
        selectedMemes.clear()
    }

    HomeBottomSheet(
        showBottomSheet = state.showBottomSheet,
        onDismissBottomSheet = {
            onAction(HomeAction.OnDismissBottomSheet)
        },
        onTemplateSelected = {
            onAction(HomeAction.OnTemplateSelected(it))
        }
    )
    MasterMemeScaffold(
        showFabButton = true,
        onFabButtonClick = {
            onAction(HomeAction.OnFabButtonClick)
        },
        topBar = {
            if (selectionMode) {
                HomeActionsTopBar(
                    title = selectedMemes.size.toString(),
                    onCloseClick = {
                        selectedMemes.clear()
                    },
                    onShareClick = {
                        onAction(HomeAction.OnShareClick(selectedMemes.toList()))
                    },
                    onDeleteClick = {
                        onAction(HomeAction.OnDeleteClick(selectedMemes.toList()))
                        selectedMemes.clear()
                    }
                )
            } else {
                HomeTopBar(
                    menuIsExpanded = state.dropdownIsExpanded,
                    menuSelected = state.menuSelected,
                    onDropdownMenuSelected = {
                        onAction(HomeAction.OnDropDownMenuSelected(it))
                    },
                    onDropdownMenuExpanded = {
                        onAction(HomeAction.OnDropdownMenuExpanded)
                    },
                    onDropdownMenuDismiss = {
                        onAction(HomeAction.OnDropdownMenuDismiss)
                    },
                )
            }
        }
    ) { innerPadding ->
        when {
            state.memesList.isEmpty() -> {
                EmptyList(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                )
            }
            else -> {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                        .padding(16.dp)
                ) {
                    items(
                        items = state.memesList,
                        key = { it.id }
                    ) { meme ->
                        MemeItem(
                            meme = meme,
                            isSelected = selectedMemes.contains(meme),
                            selectionMode = selectionMode,
                            onSelectToggle = {
                                if (selectedMemes.contains(meme)) {
                                    selectedMemes.remove(meme)
                                } else {
                                    selectedMemes.add(meme)
                                }
                            },
                            onToggleFavorite = {
                                onAction(HomeAction.OnToggleFavorite(meme.id))
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun MemeItem(
    meme: Meme,
    isSelected: Boolean,
    selectionMode: Boolean,
    onSelectToggle: () -> Unit,
    onToggleFavorite: () -> Unit
) {
    meme.image?.let {
        Box(
            modifier = Modifier
                .combinedClickable(
                    onLongClick = onSelectToggle,
                    onClick = {
                        if (selectionMode) {
                            onSelectToggle()
                        }
                    }
                )
        ) {
            Image(
                modifier = Modifier.aspectRatio(1f),
                bitmap = meme.image,
                contentDescription = null,
                contentScale = ContentScale.FillBounds
            )
            if (isSelected) {
                IconButton(
                    onClick = {},
                    colors = IconButtonDefaults.iconButtonColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = MaterialTheme.colorScheme.onPrimary
                    ),
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .size(24.dp)
                        .padding(3.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = stringResource(R.string.item_selected),
                    )
                }
            }
            if (!selectionMode) {
                IconButton(
                    onClick = onToggleFavorite,
                    modifier = Modifier.align(Alignment.BottomEnd)
                ) {
                    Icon(
                        imageVector = if (meme.isFavorite) {
                            Icons.Filled.Favorite
                        } else {
                            Icons.Outlined.FavoriteBorder
                        },
                        contentDescription = stringResource(R.string.mark_as_favorite),
                        tint = MaterialTheme.colorScheme.onPrimary,
                        modifier = Modifier.shadow(8.dp, shape = CircleShape)
                    )
                }
            }
        }
    }
}


@Composable
private fun EmptyList(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.empty_data),
                contentDescription = null,
                modifier = Modifier
                    .height(300.dp),
                contentScale = ContentScale.FillHeight
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = stringResource(R.string.tap_to_create_new_meme),
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}

@Preview
@Composable
private fun HomeScreenPreview() {
    MasterMemeTheme {
        HomeScreen(
            state = HomeState(),
            onAction = {}
        )
    }
}
