package com.example.unit4_pathway3_trainingsport.ui

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.unit4_pathway3_trainingsport.R
import com.example.unit4_pathway3_trainingsport.data.LocalSportsDataProvider
import com.example.unit4_pathway3_trainingsport.model.Sport
import com.example.unit4_pathway3_trainingsport.ui.theme.SportsTheme
import com.example.unit4_pathway3_trainingsport.utils.SportsContentType
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close

/**
 * Main composable that serves as container
 * which displays content according to [uiState] and [windowSize]
 */
@Composable
fun SportsApp(
    windowSize: WindowWidthSizeClass,
    onBackPressed: () -> Unit,
) {
    val viewModel: SportsViewModel = viewModel()
    val uiState by viewModel.uiState.collectAsState()
    val totalCalories by viewModel.totalCaloriesThisWeek.collectAsState()

    val contentType = when (windowSize) {
        WindowWidthSizeClass.Compact,
        WindowWidthSizeClass.Medium -> SportsContentType.ListOnly

        WindowWidthSizeClass.Expanded -> SportsContentType.ListAndDetail
        else -> SportsContentType.ListOnly
    }

    Scaffold(
        topBar = {
            SportsAppBar(
                onBackButtonClick = { viewModel.navigateToListPage() },
                isShowingListPage = uiState.isShowingListPage,
                windowSize = windowSize,
                isSelectionModeActive = uiState.isSelectionModeActive,
                selectedCount = uiState.selectedSports.size,
                onToggleSelectionMode = { viewModel.toggleSelectionMode() }
            )
        }
    ) { innerPadding ->
        Box(modifier = Modifier.fillMaxWidth()) {
            if (contentType == SportsContentType.ListAndDetail) {
                SportsListAndDetail(
                    sports = uiState.sportsList,
                    selectedSport = uiState.currentSport,
                    onClick = { sport ->
                        viewModel.updateCurrentSport(sport)
                    },
                    onBackPressed = onBackPressed,
                    contentPadding = innerPadding,
                    modifier = Modifier.fillMaxWidth()
                )
            } else {
                if (uiState.isShowingListPage) {
                    SportsList(
                        sports = uiState.sportsList,
                        onClick = {
                            viewModel.updateCurrentSport(it)
                            viewModel.navigateToDetailPage()
                        },
                        onSelect = { viewModel.toggleSportSelection(it) },
                        selectedSports = uiState.selectedSports,
                        modifier = Modifier.padding(horizontal = dimensionResource(R.dimen.padding_medium)),
                        contentPadding = innerPadding,
                    )
                } else {
                    SportsDetail(
                        selectedSport = uiState.currentSport,
                        contentPadding = innerPadding,
                        onBackPressed = { viewModel.navigateToListPage() }
                    )
                }
            }
            FloatingCalorieBox(totalCalories = totalCalories)
        }
    }
}

/**
 * Composable that displays the topBar and displays back button if back navigation is possible.
 */


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SportsAppBar(
    onBackButtonClick: () -> Unit,
    isShowingListPage: Boolean,
    windowSize: WindowWidthSizeClass,
    isSelectionModeActive: Boolean,
    selectedCount: Int,
    onToggleSelectionMode: () -> Unit,
    modifier: Modifier = Modifier
) {
    val isShowingDetailPage = windowSize != WindowWidthSizeClass.Expanded && !isShowingListPage

    TopAppBar(
        title = {
            if (isSelectionModeActive) {
                Text(text = "$selectedCount đã chọn")
            } else {
                Text(
                    text = if (isShowingDetailPage) {
                        stringResource(R.string.detail_fragment_label)
                    } else {
                        stringResource(R.string.list_fragment_label)
                    },
                    fontWeight = FontWeight.Bold
                )
            }
        },
        navigationIcon = {
            if (isSelectionModeActive) {
                IconButton(onClick = onToggleSelectionMode) {
                    Icon(
                        imageVector = Icons.Filled.Close,
                        contentDescription = "Thoát chế độ chọn"
                    )
                }
            } else if (isShowingDetailPage) {
                IconButton(onClick = onBackButtonClick) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = stringResource(R.string.back_button)
                    )
                }
            }
        },
        actions = {
            if (!isSelectionModeActive && isShowingListPage) {
                IconButton(onClick = onToggleSelectionMode) {
                    Icon(
                        imageVector = Icons.Filled.CheckCircle,
                        contentDescription = "Vào chế độ chọn"
                    )
                }
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary
        ),
        modifier = modifier,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SportsListItem(
    sport: Sport,
    onItemClick: (Sport) -> Unit,
    isSelected: Boolean,
    isSelectionModeActive: Boolean,
    modifier: Modifier = Modifier
) {
    Card(
        elevation = CardDefaults.cardElevation(),
        modifier = modifier,
        shape = RoundedCornerShape(dimensionResource(R.dimen.card_corner_radius)),
        onClick = {
            onItemClick(sport) }
    ) {
        val backgroundColor = if (isSelected) {
            MaterialTheme.colorScheme.primaryContainer
        } else {
            MaterialTheme.colorScheme.surfaceVariant
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .size(dimensionResource(R.dimen.card_image_height))
                .background(backgroundColor)
        ) {
            SportsListImageItem(
                sport = sport,
                modifier = Modifier.size(dimensionResource(R.dimen.card_image_height))
            )
            Column(
                modifier = Modifier
                    .padding(
                        vertical = dimensionResource(R.dimen.padding_small),
                        horizontal = dimensionResource(R.dimen.padding_medium)
                    )
                    .weight(1f)
            ) {
                Text(
                    text = stringResource(sport.titleResourceId),
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(bottom = dimensionResource(R.dimen.card_text_vertical_space))
                )
                Text(
                    text = stringResource(sport.subtitleResourceId),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.secondary,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 3
                )
                Spacer(Modifier.weight(1f))
                Row {
                    Text(
                        text = pluralStringResource(
                            R.plurals.player_count_caption,
                            sport.playerCount,
                            sport.playerCount
                        ),
                        style = MaterialTheme.typography.bodySmall
                    )
                    Spacer(Modifier.weight(1f))
                    if (sport.olympic) {
                        Text(
                            text = stringResource(R.string.olympic_caption),
                            style = MaterialTheme.typography.labelMedium
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun SportsListImageItem(sport: Sport, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
    ) {
        Image(
            painter = painterResource(sport.imageResourceId),
            contentDescription = null,
            alignment = Alignment.Center,
            contentScale = ContentScale.FillWidth
        )
    }
}

@Composable
private fun SportsList(
    sports: List<Sport>,
    onClick: (Sport) -> Unit,
    onSelect: (Sport) -> Unit,
    selectedSports: Set<Sport>,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp),
) {
    LazyColumn(
        contentPadding = contentPadding,
        verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_medium)),
        modifier = modifier.padding(top = dimensionResource(R.dimen.padding_medium)),
    ) {
        items(sports, key = { sport -> sport.id }) { sport ->
            SportsListItem(
                sport = sport,
                onItemClick = onClick,
                isSelected = selectedSports.contains(sport),
                isSelectionModeActive = true,
            )
        }
    }
}

@Composable
private fun SportsDetail(
    selectedSport: Sport,
    onBackPressed: () -> Unit,
    contentPadding: PaddingValues,
    modifier: Modifier = Modifier
) {
    BackHandler {
        onBackPressed()
    }
    val scrollState = rememberScrollState()
    val layoutDirection = LocalLayoutDirection.current
    Box(
        modifier = modifier
            .verticalScroll(state = scrollState)
            .padding(top = contentPadding.calculateTopPadding())
    ) {
        Column(
            modifier = Modifier
                .padding(
                    bottom = contentPadding.calculateTopPadding(),
                    start = contentPadding.calculateStartPadding(layoutDirection),
                    end = contentPadding.calculateEndPadding(layoutDirection)
                )
        ) {
            Box {
                Box {
                    Image(
                        painter = painterResource(selectedSport.sportsImageBanner),
                        contentDescription = null,
                        alignment = Alignment.TopCenter,
                        contentScale = ContentScale.FillWidth,
                    )
                }
                Column(
                    Modifier
                        .align(Alignment.BottomStart)
                        .fillMaxWidth()
                        .background(
                            Brush.verticalGradient(
                                listOf(Color.Transparent, MaterialTheme.colorScheme.scrim),
                                0f,
                                400f
                            )
                        )
                ) {
                    Text(
                        text = stringResource(selectedSport.titleResourceId),
                        style = MaterialTheme.typography.headlineLarge,
                        color = MaterialTheme.colorScheme.inverseOnSurface,
                        modifier = Modifier
                            .padding(horizontal = dimensionResource(R.dimen.padding_small))
                    )
                    Row(
                        modifier = Modifier.padding(dimensionResource(R.dimen.padding_small))
                    ) {
                        Text(
                            text = pluralStringResource(
                                R.plurals.player_count_caption,
                                selectedSport.playerCount,
                                selectedSport.playerCount
                            ),
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.inverseOnSurface,
                        )
                        Spacer(Modifier.weight(1f))
                        Text(
                            text = stringResource(R.string.olympic_caption),
                            style = MaterialTheme.typography.labelMedium,
                            color = MaterialTheme.colorScheme.inverseOnSurface,
                        )
                    }
                }
            }
            Text(
                text = stringResource(selectedSport.sportDetails),
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(
                    vertical = dimensionResource(R.dimen.padding_detail_content_vertical),
                    horizontal = dimensionResource(R.dimen.padding_detail_content_horizontal)
                )
            )
            Text(
                text = "Calo tiêu thụ mỗi giờ: ${selectedSport.caloriesPerHour} kcal",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(horizontal = dimensionResource(R.dimen.padding_medium))
            )

            Text(
                text = "Số giờ chơi mỗi tuần: ${selectedSport.hoursPerWeek}",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(horizontal = dimensionResource(R.dimen.padding_medium))
            )

            Text(
                text = "Tổng calo tiêu thụ mỗi tuần: ${selectedSport.totalCaloriesPerWeek} kcal",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(
                    horizontal = dimensionResource(R.dimen.padding_medium),
                    vertical = dimensionResource(R.dimen.padding_small)
                )
            )
        }
    }
}

@Composable
private fun SportsListAndDetail(
    sports: List<Sport>,
    selectedSport: Sport,
    onClick: (Sport) -> Unit,
    onBackPressed: () -> Unit,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp)
) {
    Row(
        modifier = modifier
    ) {
        SportsList(
            sports = sports,
            onClick = onClick,
            onSelect = {},
            selectedSports = emptySet(),
            contentPadding = PaddingValues(
                top = contentPadding.calculateTopPadding(),
            ),
            modifier = Modifier
                .weight(2f)
                .padding(horizontal = dimensionResource(R.dimen.padding_medium))
        )
        SportsDetail(
            selectedSport = selectedSport,
            modifier = Modifier.weight(3f),
            contentPadding = PaddingValues(
                top = contentPadding.calculateTopPadding(),
            ),
            onBackPressed = onBackPressed,
        )
    }
}


@Preview(name = "Tablet Preview",
    device = "spec:width=1280px,height=800px,dpi=240")
@Composable
fun SportsListAndDetailsPreview() {
    theme {
        Surface {
            SportsListAndDetail(
                sports = LocalSportsDataProvider.getSportsData(),
                selectedSport = LocalSportsDataProvider.getSportsData().getOrElse(0) {
                    LocalSportsDataProvider.defaultSport
                },
                onClick = {},
                onBackPressed = {},
            )
        }
    }
}

@Composable
fun FloatingCalorieBox(totalCalories: Int) {
    if (totalCalories > 0) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            contentAlignment = Alignment.BottomEnd
        ) {
            Card(
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
                shape = RoundedCornerShape(16.dp),
            ) {
                Text(
                    text = "Tổng calo tiêu thụ: $totalCalories kcal/tuần",
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                    modifier = Modifier.padding(12.dp)
                )
            }
        }
    }
}