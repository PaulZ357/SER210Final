package edu.quinnipiac.ser210.milestone2

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import edu.quinnipiac.ser210.milestone2.data.Character
import edu.quinnipiac.ser210.milestone2.databinding.ListItemBinding

class AveragesAdapter(
	private val character: Character,
	private val levelsToPromotion: Int
) : RecyclerView.Adapter<AveragesAdapter.AveragesViewHolder>() {
	private val unpromotedLevels = levelsToPromotion - character.baseLevel + 1
	private val promotedLevels = if (character.canPromote) 20 else 0
	private val totalLevels: Int
		get() = unpromotedLevels + promotedLevels

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AveragesViewHolder {
		val adapterLayout =
			LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
		return AveragesViewHolder(adapterLayout, character, levelsToPromotion)
	}

	override fun getItemCount(): Int {
		return totalLevels
	}

	override fun onBindViewHolder(holder: AveragesViewHolder, position: Int) {
		val promoted = position >= unpromotedLevels
		holder.bind(position + if(promoted) 0 else 1, promoted)
	}

	class AveragesViewHolder(
		view: View,
		private val character: Character,
		private val levelsToPromotion: Int
	) : RecyclerView.ViewHolder(view) {
		private val binding = ListItemBinding.bind(itemView)
		fun bind(level: Int, promoted: Boolean) {
			if (character.canPromote) {
				val unpromotedLevel = if (promoted) levelsToPromotion else level
				val promotedLevel = if (promoted) level - levelsToPromotion + 1 else 0
				binding.levelView.text = String.format("%d/%d", unpromotedLevel, promotedLevel)
			}
			else {
				binding.levelView.text = level.toString()
			}
			binding.HPView.text = character.getAverageHP(level).toString()
			binding.strView.text = character.getAverageStr(level, promoted).toString()
			binding.magView.text = character.getAverageMag(level, promoted).toString()
			binding.sklView.text = character.getAverageSkl(level, promoted).toString()
			binding.spdView.text = character.getAverageSpd(level, promoted).toString()
			binding.lckView.text = character.getAverageLck(level).toString()
			binding.defView.text = character.getAverageDef(level, promoted).toString()
			binding.conView.text = character.getAverageCon(level, promoted).toString()
			binding.movView.text = character.getAverageMov(level, promoted).toString()
		}

	}
}