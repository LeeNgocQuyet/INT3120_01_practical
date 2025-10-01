package com.example.unit3_pathway1_scrollapp.data
import com.example.unit3_pathway1_scrollapp.model.Affirmation
import com.example.unit3_pathway1_scrollapp.R
import com.example.unit3_pathway1_scrollapp.model.Topic

/*
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
*/
object dataSource {
    val topics = listOf(
        Topic(R.string.architecture, 58, R.drawable.architecture),
        Topic(R.string.crafts, 121, R.drawable.crafts),
        Topic(R.string.business, 78, R.drawable.business),
        Topic(R.string.culinary, 118, R.drawable.culinary),
        Topic(R.string.design, 423, R.drawable.design),
        Topic(R.string.fashion, 92, R.drawable.fashion),
        Topic(R.string.film, 165, R.drawable.film),
        Topic(R.string.gaming, 164, R.drawable.gaming),
        Topic(R.string.drawing, 326, R.drawable.drawing),
        Topic(R.string.lifestyle, 305, R.drawable.lifestyle),
        Topic(R.string.music, 212, R.drawable.music),
        Topic(R.string.painting, 172, R.drawable.painting),
        Topic(R.string.photography, 321, R.drawable.photography),
        Topic(R.string.tech, 118, R.drawable.tech)
    )
}
