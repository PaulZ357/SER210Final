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

	init {
		for (i: Int in 0..<totalLevels) {
			scrollsByPosition.add(HashMap())
		}
	}

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AveragesViewHolder {
		val adapterLayout =
			LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
		val averagesViewHolder =
			AveragesViewHolder(adapterLayout, character, levelsToPromotion, scrolls)
		averagesViewHolder.activeScrolls.observe(parent.findViewTreeLifecycleOwner()!!) {
			scrollsByPosition[averagesViewHolder.level] = it
		}
		return averagesViewHolder
	}

	override fun getItemCount(): Int {
		return totalLevels
	}

	override fun onBindViewHolder(holder: AveragesViewHolder, position: Int) {
		val promoted = position >= unpromotedLevels
		val level = position + character.baseLevel - if (promoted) 1 else 0
		holder.bind(level, promoted, scrollsByPosition[level])
	}

	class AveragesViewHolder(
		private val view: View,
		private val character: Character,
		private val levelsToPromotion: Int,
		private val scrolls: List<Scroll>
	) : RecyclerView.ViewHolder(view) {
		private val binding = ListItemBinding.bind(itemView)
		val activeScrolls: LiveData<HashMap<Scroll, Boolean>>
			get() = _activeScrolls
		private val _activeScrolls = MutableLiveData<HashMap<Scroll, Boolean>>()
		var level = 0

		class ScrollListener(
			private val activeScrolls: MutableLiveData<HashMap<Scroll, Boolean>>,
			private val scrolls: List<Scroll>
		) :
			OnItemClickListener {
			override fun onItemClick(
				parent: AdapterView<*>?,
				view: View?,
				position: Int,
				id: Long
			) {
				val scroll = scrolls[position]
				if (activeScrolls.value!!.contains(scroll)) {
					activeScrolls.value!![scroll] = !activeScrolls.value!![scroll]!!
				} else {
					activeScrolls.value!![scroll] = true
				}
			}

		}

		fun bind(level: Int, promoted: Boolean, activeScrolls: HashMap<Scroll, Boolean>) {
			this.level = level
			view.setOnClickListener {
				val dialog = Dialog(view.context)
				dialog.setContentView(R.layout.scroll_popup)
				val list = dialog.findViewById<ListView>(R.id.List)
				val scrollNames = Array(scrolls.size) { scrolls[it].name }
				val adapter = ArrayAdapter(
					dialog.context,
					android.R.layout.simple_list_item_multiple_choice,
					scrollNames
				)
				list.adapter = adapter
				list.onItemClickListener = ScrollListener(_activeScrolls, scrolls)
				list.choiceMode = ListView.CHOICE_MODE_MULTIPLE
				for (index: Int in scrolls.indices) {
					val scroll = scrolls[index]
					if (activeScrolls.contains(scroll)) {
						list.setItemChecked(index, activeScrolls[scroll]!!)
					}
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
			_activeScrolls.value = activeScrolls

			val scrolls = HashMap<Scroll, Int>()
			val heimScroll = DataApplication.defaultScrolls[0]
			scrolls[heimScroll] = level - character.baseLevel
			binding.HPView.text = character.getAverageHP(level, scrolls).toString()
			binding.strView.text = character.getAverageStr(level, promoted, scrolls).toString()
			binding.magView.text = character.getAverageMag(level, promoted, scrolls).toString()
			binding.sklView.text = character.getAverageSkl(level, promoted, scrolls).toString()
			binding.spdView.text = character.getAverageSpd(level, promoted, scrolls).toString()
			binding.lckView.text = character.getAverageLck(level, scrolls).toString()
			binding.defView.text = character.getAverageDef(level, promoted, scrolls).toString()
			binding.conView.text = character.getAverageCon(level, promoted, scrolls).toString()
			binding.movView.text = character.getAverageMov(level, promoted, scrolls).toString()
		}

	}
}