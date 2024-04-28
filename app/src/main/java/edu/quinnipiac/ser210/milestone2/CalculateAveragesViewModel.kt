package edu.quinnipiac.ser210.milestone2

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import edu.quinnipiac.ser210.milestone2.data.Character
import edu.quinnipiac.ser210.milestone2.data.CharacterDao
import kotlinx.coroutines.launch
import kotlin.math.max

class CalculateAveragesViewModel(private val characterDao: CharacterDao) : ViewModel() {
	val characters: LiveData<List<Character>> = characterDao.getCharacters().asLiveData()
	val characterIndex: LiveData<Int>
		get() = _characterIndex
	private var _characterIndex = MutableLiveData(0)
	val levelIndex: LiveData<Int>
		get() = _levelIndex
	private var _levelIndex = MutableLiveData(10)
	val levels: LiveData<Array<Int>>
		get() = _levels
	private var _levels = MutableLiveData(Array(11) { it + 10 })
	val adapter: LiveData<AveragesAdapter>
		get() = _adapter
	private var _adapter = MutableLiveData<AveragesAdapter>()

	init {
		updateLevels()
		updateAdapter()
	}

	private fun updateLevels() {
		viewModelScope.launch {
			characterDao.getCharacters().collect { characterList ->
				val character = characterList[characterIndex.value!!]
				val baseLevel: Int = character.baseLevel
				if (baseLevel != (levels.value?.get(0) ?: 10)) {
					val promotionLevel = max(baseLevel, 10)
					val levelsArray = Array(21 - promotionLevel) { it + promotionLevel }
					_levels = MutableLiveData(levelsArray)
					if ((levelIndex.value!! >= levelsArray.size)) {
						_levelIndex.value = levelsArray.size - 1
					}
				}

			}
		}
	}

	private fun updateAdapter() {
		viewModelScope.launch {
			characterDao.getCharacters().collect {
				val character = it[characterIndex.value!!]
				val promotionLevel = levels.value?.get(levelIndex.value!!)
				_adapter.value = AveragesAdapter(
					character,
					if ((promotionLevel != null) && character.canPromote) promotionLevel else 20
				)
			}
		}
	}

	fun setCharacterIndex(index: Int) {
		_characterIndex.value = index
		updateLevels()
		updateAdapter()
	}

	fun setLevelIndex(index: Int) {
		_levelIndex.value = index
		updateAdapter()
	}
}

class CalculateAveragesViewModelFactory(private val characterDao: CharacterDao) :
	ViewModelProvider.Factory {
	override fun <T : ViewModel> create(modelClass: Class<T>): T {
		if (modelClass.isAssignableFrom(CalculateAveragesViewModel::class.java)) {
			@Suppress("UNCHECKED_CAST")
			return CalculateAveragesViewModel(characterDao) as T
		}
		throw IllegalArgumentException("Unknown ViewModel class")
	}
}