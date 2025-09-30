package com.example.unit3_pathway1_scrollapp.data
import com.example.unit3_pathway1_scrollapp.model.Affirmation
import com.example.unit3_pathway1_scrollapp.R

class datasource {
        fun loadAffirmations(): List<Affirmation> {
            return listOf<Affirmation>(
                Affirmation(R.string.cat1, R.drawable.cat1),
                Affirmation(R.string.cat2, R.drawable.cat2),
                Affirmation(R.string.cat3, R.drawable.cat3),
                Affirmation(R.string.cat4, R.drawable.cat4),
                Affirmation(R.string.cat5, R.drawable.cat5),
                Affirmation(R.string.cat6, R.drawable.cat6),
                Affirmation(R.string.cat7, R.drawable.cat7),
                Affirmation(R.string.cat8, R.drawable.cat8),
                Affirmation(R.string.cat9, R.drawable.cat9),
                Affirmation(R.string.cat10, R.drawable.cat10),
                Affirmation(R.string.cat11, R.drawable.cat11))
        }
    }
