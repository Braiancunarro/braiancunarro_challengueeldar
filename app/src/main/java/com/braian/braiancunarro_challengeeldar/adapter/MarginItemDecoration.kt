package com.braian.braiancunarro_challengeeldar.adapter

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class MarginItemDecoration(private val margin: Int) : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        with(outRect) {
            if (parent.getChildAdapterPosition(view) == 0) {
                // Si es el primer elemento, no aplicar márgenes a la izquierda
                left = 0
            } else {
                // Para otros elementos, aplicar márgenes a la izquierda
                left = margin
            }
            // Aplicar márgenes a la derecha para todos los elementos
            right = margin
        }
    }
}
