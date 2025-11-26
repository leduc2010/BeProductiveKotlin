package com.duc.beproductivekotlin.adapter

import android.content.Context
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.duc.beproductivekotlin.R
import com.duc.beproductivekotlin.adapter.OnboardingAdapter.OnboardingViewHolder
import com.duc.beproductivekotlin.model.Onboarding

class OnboardingAdapter(
    private val context: Context,
    private val listOnboarding: MutableList<Onboarding>
) : RecyclerView.Adapter<OnboardingViewHolder?>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OnboardingViewHolder {
        val v = LayoutInflater.from(context).inflate(R.layout.item_onboarding, parent, false)
        return OnboardingViewHolder(v)
    }

    override fun onBindViewHolder(holder: OnboardingViewHolder, position: Int) {
        val onboarding = listOnboarding[position]

        holder.ivOnboarding.setImageResource(onboarding.imageRes)
        if (position == 0) {
            val htmlTitle =
                "Welcome to<br/><font color='#FFC120'>Be</font><font color='#6000F7'>Productive</font>"
            holder.tvTitle.text = Html.fromHtml(htmlTitle, Html.FROM_HTML_MODE_LEGACY)
        } else {
            holder.tvTitle.text = Html.fromHtml(
                context.getString(onboarding.title),
                Html.FROM_HTML_MODE_LEGACY
            )
        }
        holder.tvDescription.text = Html.fromHtml(
            context.getString(onboarding.description),
            Html.FROM_HTML_MODE_LEGACY
        )
    }

    override fun getItemCount(): Int {
        return listOnboarding.size
    }

    class OnboardingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var ivOnboarding: ImageView = itemView.findViewById<ImageView>(R.id.ivOnboarding)
        var tvTitle: TextView = itemView.findViewById<TextView>(R.id.tvTitle)
        var tvDescription: TextView = itemView.findViewById<TextView>(R.id.tvDescription)
    }
}
