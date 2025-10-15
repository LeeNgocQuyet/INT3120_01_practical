package com.example.unit4_pathway3_trainingsport.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.unit4_pathway3_trainingsport.data.LocalSportsDataProvider
import com.example.unit4_pathway3_trainingsport.model.Sport
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update

/**
 * View Model for Sports app
 */
class SportsViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(SportsUiState())
    val uiState: StateFlow<SportsUiState> = _uiState.asStateFlow()


    init {
        val sportsData = LocalSportsDataProvider.getSportsData()
        _uiState.value = SportsUiState(
            sportsList = sportsData,
            currentSport = sportsData.firstOrNull() ?: LocalSportsDataProvider.defaultSport
        )
    }

    val totalCaloriesThisWeek: StateFlow<Int> =
        uiState.map { it.selectedSports.sumOf { sport -> sport.totalCaloriesPerWeek } }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = 0
            )

    fun toggleSportSelection(sport: Sport) {
        _uiState.update { currentState ->
            val currentSelected = currentState.selectedSports.toMutableSet()
            if (currentSelected.contains(sport)) {
                currentSelected.remove(sport)
            } else {
                currentSelected.add(sport)
            }
            currentState.copy(selectedSports = currentSelected)
        }
    }

    fun toggleSelectionMode() {
        _uiState.update { currentState ->
            val isNowActive = !currentState.isSelectionModeActive
            val selectedSports = if (isNowActive) currentState.selectedSports else emptySet()
            currentState.copy(
                isSelectionModeActive = isNowActive,
                selectedSports = selectedSports
            )
        }
    }

    fun updateCurrentSport(selectedSport: Sport) {
        _uiState.update {
            it.copy(currentSport = selectedSport)
        }
    }

    fun navigateToListPage() {
        _uiState.update {
            it.copy(isShowingListPage = true)
        }
    }

    fun navigateToDetailPage() {
        _uiState.update {
            it.copy(isShowingListPage = false)
        }
    }
}

data class SportsUiState(
    val sportsList: List<Sport> = emptyList(),
    val currentSport: Sport = LocalSportsDataProvider.defaultSport,
    val isShowingListPage: Boolean = true,
    val selectedSports: Set<Sport> = emptySet(),
    val isSelectionModeActive: Boolean = false
)
