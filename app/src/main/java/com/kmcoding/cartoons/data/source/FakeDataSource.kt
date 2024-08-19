package com.kmcoding.cartoons.data.source

import com.kmcoding.cartoons.domain.model.Cartoon

object FakeDataSource {
    val fakeCartoons =
        listOf(
            Cartoon(
                id = 1,
                title = "Fake cartoon 1",
                year = 1998,
                episodes = 5,
                episodeDuration = 22,
                coverUrl = "",
                creators = listOf("Ben Johnes"),
                genres = listOf("Comedy"),
            ),
            Cartoon(
                id = 2,
                title = "Fake cartoon 2",
                year = 1999,
                episodes = 15,
                episodeDuration = 31,
                coverUrl = "",
                creators = listOf("Ben Full", "Andy Morrison"),
                genres = listOf("Comedy", "Anime"),
            ),
            Cartoon(
                id = 3,
                title = "Fake cartoon 3",
                year = 2004,
                episodes = 22,
                episodeDuration = 34,
                coverUrl = "",
                creators = listOf("John Preston"),
                genres = listOf("Anime"),
            ),
        )

    fun getFakeCartoonsWithQuerySize(query: String): Int {
        return fakeCartoons.filter { it.title.contains(query) }.size
    }
}
