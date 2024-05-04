package edu.quinnipiac.ser210.milestone2

import android.app.Dialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.AdapterView.OnItemClickListener
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.findViewTreeLifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import edu.quinnipiac.ser210.milestone2.data.Character
import edu.quinnipiac.ser210.milestone2.data.Scroll
import edu.quinnipiac.ser210.milestone2.databinding.ListItemBinding
import kotlin.math.max

class AveragesAdapter(
	private val character: Character,
	private val levelsToPromotion: Int,
	private val scrolls: List<Scroll>
) : RecyclerView.Adapter<AveragesAdapter.AveragesViewHolder>() {
	private val unpromotedLevels = levelsToPromotion - character.baseLevel + 1
	private val promotedLevels = if (character.canPromote) 20 else 0
	private val totalLevels: Int
		get() = unpromotedLevels + promotedLevels
	private val scrollsByPosition = ArrayList<HashMap<Scroll, Boolean>>()
	private val scrollSumsByPosition = ArrayList<HashMap<Scroll, Int>>()

	init {
		for (i: Int in 0..totalLevels) {
			scrollsByPosition.add(HashMap())
			scrollSumsByPosition.add(HashMap())
		}
	}

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AveragesViewHolder {
		val adapterLayout =
			LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
		val lifecycleOwner = parent.findViewTreeLifecycleOwner()!!
		val averagesViewHolder =
			AveragesViewHolder(adapterLayout, character, levelsToPromotion, scrolls) {averagesViewHolder: AveragesViewHolder ->
				val startingIndex = max(averagesViewHolder.adapterPosition, 1)
				scrollsByPosition[startingIndex] = averagesViewHolder.activeScrolls.value!!
				for (index in startingIndex..totalLevels) {
					val activeScrolls = scrollsByPosition[index]
					val lastScrollSums = scrollSumsByPosition[index - 1]
					val scrollSums = scrollSumsByPosition[index]
					for (scroll in scrolls) {
						scrollSums[scroll] = (lastScrollSums[scroll] ?: 0) + if(activeScrolls[scroll] == true) 1 else 0
					}
				}
				notifyDataSetChanged()
			}
		averagesViewHolder.activeScrolls.observe(lifecycleOwner) {

		}
		return averagesViewHolder
	}

	override fun getItemCount(): Int {
		return totalLevels
	}

	override fun onBindViewHolder(holder: AveragesViewHolder, position: Int) {
		val promoted = position >= unpromotedLevels
		val level = position + character.baseLevel - if (promoted) 1 else 0
		holder.bind(level, promoted, scrollsByPosition[holder.adapterPosition], scrollSumsByPosition[holder.adapterPosition])
	}

	class AveragesViewHolder(
		private val view: View,
		private val character: Character,
		private val levelsToPromotion: Int,
		private val scrolls: List<Scroll>,
		val updateAdapter: (AveragesViewHolder) -> Unit
	) : RecyclerView.ViewHolder(view) {
		private val binding = ListItemBinding.bind(itemView)
		val activeScrolls: LiveData<HashMap<Scroll, Boolean>>
			get() = _activeScrolls
		private val _activeScrolls = MutableLiveData<HashMap<Scroll, Boolean>>()
		var level = 0
		private var promoted = false

		class ScrollListener(
			private val activeScrolls: MutableLiveData<HashMap<Scroll, Boolean>>,
			private val scrolls: List<Scroll>,
			private val source: AveragesViewHolder
		) :
			OnItemClickListener {
			override fun onItemClick(
				parent: AdapterView<*>?,
				view: View?,
				position: Int,
				id: Long
			) {
				val scroll = scrolls[position]
				activeScrolls.value!![scroll] = !(activeScrolls.value!![scroll] ?: false)
//				source.update()
				source.updateAdapter(source)
			}

		}

		fun bind(level: Int, promoted: Boolean, scrolls: HashMap<Scroll, Boolean>, scrollSums: HashMap<Scroll, Int>) {
			_activeScrolls.value = scrolls
			this.level = level
			this.promoted = promoted
			view.setOnClickListener {
				val dialog = Dialog(view.context)
				dialog.setContentView(R.layout.scroll_popup)
				val list = dialog.findViewById<ListView>(R.id.List)
				val scrollNames = Array(this.scrolls.size) { this.scrolls[it].name }
				val adapter = ArrayAdapter(
					dialog.context,
					android.R.layout.simple_list_item_multiple_choice,
					scrollNames
				)
				list.adapter = adapter
				list.onItemClickListener = ScrollListener(_activeScrolls,
					this.scrolls, this)
				list.choiceMode = ListView.CHOICE_MODE_MULTIPLE
				for (index: Int in this.scrolls.indices) {
					val scroll = this.scrolls[index]
					list.setItemChecked(index, _activeScrolls.value!![scroll] ?: false)
				}
				dialog.show()
			}

			if (character.canPromote) {
				val unpromotedLevel = if (promoted) levelsToPromotion else level
				val promotedLevel = if (promoted) level - levelsToPromotion + 1 else 0
				binding.levelView.text = String.format("%d/%d", unpromotedLevel, promotedLevel)
				if (promotedLevel == 1) {
					view.setOnClickListener { }
				}
			} else {
				binding.levelView.text = level.toString()
			}
			if (level == 1) {
				view.setOnClickListener { }
			}

			binding.HPView.text = character.getAverageHP(level, scrollSums).toString()
			binding.strView.text = character.getAverageStr(level, promoted, scrollSums).toString()
			binding.magView.text = character.getAverageMag(level, promoted, scrollSums).toString()
			binding.sklView.text = character.getAverageSkl(level, promoted, scrollSums).toString()
			binding.spdView.text = character.getAverageSpd(level, promoted, scrollSums).toString()
			binding.lckView.text = character.getAverageLck(level, scrollSums).toString()
			binding.defView.text = character.getAverageDef(level, promoted, scrollSums).toString()
			binding.conView.text = character.getAverageCon(level, promoted, scrollSums).toString()
			binding.movView.text = character.getAverageMov(level, promoted, scrollSums).toString()
		}

	}
}