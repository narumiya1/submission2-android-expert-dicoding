package com.example.submission2fx.core.utils

import androidx.annotation.Nullable
import androidx.recyclerview.widget.DiffUtil
import com.example.submission2fx.core.domain.model.GhibliMv


class DiffUtils(private val oldList: List<GhibliMv>, private val newList: List<GhibliMv>) :
    DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].title == newList[newItemPosition].title
    }

    override fun areContentsTheSame(oldPosition: Int, newPosition: Int): Boolean {
        val (original_title_romanised,
            title,
            original_title,
            popularity,
            voteAverage,
            running_time,
            release_date,
            rt_score,
            favorite) = oldList[oldPosition]
        val (original_title_romanised1,
            title1,
            original_title1,
            popularity1,
            voteAverage1,
            running_time1,
            release_date1,
            rt_score1,
            favorite1) = newList[newPosition]

        return original_title_romanised == original_title_romanised1
                && title == title1
                && original_title == original_title1
                && popularity == popularity1
                && voteAverage == voteAverage1
                && running_time == running_time1
                && release_date == release_date1
                && rt_score == rt_score1
                && favorite == favorite1
    }

    @Nullable
    override fun getChangePayload(oldPosition: Int, newPosition: Int): Any? {
        return super.getChangePayload(oldPosition, newPosition)
    }
}
