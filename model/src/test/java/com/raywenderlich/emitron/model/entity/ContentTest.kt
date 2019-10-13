package com.raywenderlich.emitron.model.entity

import com.raywenderlich.emitron.model.Attributes
import com.raywenderlich.emitron.model.Data
import com.raywenderlich.emitron.model.Relationships
import com.raywenderlich.emitron.model.utils.isEqualTo
import org.junit.Test

class ContentTest {


  @Test
  fun init() {
    val content = Content(
      contentId = "1",
      name = "Introduction to Kotlin Lambdas: Getting Started",
      description = "In this tutorial you will learn how to use lambda.",
      contributors = "Luke",
      professional = false,
      deleted = false,
      contentType = "screencast",
      difficulty = "beginner",
      releasedAt = "2019-08-08T00:00:00.000Z",
      technology = "Swift, iOS",
      duration = 408,
      streamUrl = "https://koenig-media.raywenderlich.com/",
      cardArtworkUrl = "https://koenig-media.raywenderlich.com/",
      videoId = "1",
      bookmarkId = "1",
      progressionId = "1",
      updatedAt = "2019-08-08T00:00:00.000Z"
    )

    // Assertions
    content.contentId isEqualTo "1"
    content.name isEqualTo "Introduction to Kotlin Lambdas: Getting Started"
    content.description isEqualTo "In this tutorial you will learn how to use lambda."
    content.contributors isEqualTo "Luke"
    content.professional isEqualTo false
    content.deleted isEqualTo false
    content.contentType isEqualTo "screencast"
    content.difficulty isEqualTo "beginner"
    content.releasedAt isEqualTo "2019-08-08T00:00:00.000Z"
    content.technology isEqualTo "Swift, iOS"
    content.duration isEqualTo 408
    content.streamUrl isEqualTo "https://koenig-media.raywenderlich.com/"
    content.cardArtworkUrl isEqualTo "https://koenig-media.raywenderlich.com/"
    content.bookmarkId isEqualTo "1"
    content.progressionId isEqualTo "1"
    content.updatedAt isEqualTo "2019-08-08T00:00:00.000Z"
  }

  @Test
  fun toData() {
    val content = Content(
      contentId = "1",
      name = "Introduction to Kotlin Lambdas: Getting Started",
      description = "In this tutorial you will learn how to use lambda.",
      contributors = "Luke",
      professional = false,
      deleted = false,
      contentType = "screencast",
      difficulty = "beginner",
      releasedAt = "2019-08-08T00:00:00.000Z",
      technology = "Swift, iOS",
      duration = 408,
      streamUrl = "https://koenig-media.raywenderlich.com/",
      cardArtworkUrl = "https://koenig-media.raywenderlich.com/",
      videoId = "1",
      bookmarkId = "1",
      progressionId = "1",
      updatedAt = "2019-08-08T00:00:00.000Z"
    )

    val data = content.toData()

    data isEqualTo Data(
      id = "1",
      type = "contents",
      attributes = Attributes(
        createdAt = null,
        description = "In this tutorial you will learn how to use lambda.",
        level = null,
        name = "Introduction to Kotlin Lambdas: Getting Started",
        slug = null,
        cardArtworkUrl = "https://koenig-media.raywenderlich.com/",
        contentType = "screencast",
        difficulty = "beginner",
        duration = 408,
        free = false,
        popularity = 0.0,
        releasedAt = "2019-08-08T00:00:00.000Z",
        uri = null,
        target = 0,
        progress = 0,
        finished = false,
        percentComplete = 0.0,
        updatedAt = null,
        technology = "Swift, iOS",
        contributors = "Luke",
        kind = null
      ),
      links = null,
      relationships = Relationships(
        content = null,
        contents = null,
        bookmark = com.raywenderlich.emitron.model.Content(
          datum = Data(
            id = "1",
            type = "bookmarks",
            attributes = null,
            links = null,
            relationships = null,
            meta = null,
            included = null
          ), links = null, meta = null, included = null
        ),
        domains = null,
        progression = null,
        groups = null,
        childContents = null
      ),
      meta = null,
      included = null
    )
  }

  @Test
  fun fromList() {
    val dataList = listOf(
      Data(
        id = "1",
        type = "contents",
        attributes = Attributes(
          createdAt = "",
          description = "In this tutorial you will learn how to use lambda.",
          level = "",
          name = "Introduction to Kotlin Lambdas: Getting Started",
          slug = "",
          cardArtworkUrl = "https://koenig-media.raywenderlich.com/",
          contentType = "screencast",
          difficulty = "beginner",
          duration = 408,
          free = false,
          popularity = 0.0,
          releasedAt = "2019-08-08T00:00:00.000Z",
          uri = "",
          target = 0,
          progress = 0,
          finished = false,
          percentComplete = 0.0,
          updatedAt = "",
          technology = "Swift, iOS",
          contributors = "Luke",
          url = "",
          kind = ""
        ),
        links = null,
        relationships = Relationships(
          content = null,
          contents = null,
          bookmark = com.raywenderlich.emitron.model.Content(
            datum = Data(
              id = "1",
              type = null,
              attributes = null,
              links = null,
              relationships = null,
              meta = null,
              included = null
            ), links = null, meta = null, included = null
          ),
          domains = null,
          progression = com.raywenderlich.emitron.model.Content(
            datum = Data(
              id = "1",
              type = null,
              attributes = null,
              links = null,
              relationships = null,
              meta = null,
              included = null
            ), links = null, meta = null, included = null
          ),
          groups = null,
          childContents = null
        ),
        meta = null,
        included = null
      )
    )

    Content.listFrom(dataList).isEqualTo(
      listOf(
        Content(
          contentId = "1",
          name = "Introduction to Kotlin Lambdas: Getting Started",
          description = "In this tutorial you will learn how to use lambda.",
          contributors = "Luke",
          professional = true,
          deleted = false,
          contentType = "screencast",
          difficulty = "beginner",
          releasedAt = "2019-08-08T00:00:00.000Z",
          technology = "Swift, iOS",
          duration = 408,
          streamUrl = "",
          cardArtworkUrl = "https://koenig-media.raywenderlich.com/",
          videoId = null,
          bookmarkId = "1",
          progressionId = "1",
          updatedAt = ""
        )

      )
    )
  }

  @Test
  fun from() {
    val data = Data(
      id = "1",
      type = "contents",
      attributes = Attributes(
        createdAt = "",
        description = "In this tutorial you will learn how to use lambda.",
        level = "",
        name = "Introduction to Kotlin Lambdas: Getting Started",
        slug = "",
        cardArtworkUrl = "https://koenig-media.raywenderlich.com/",
        contentType = "screencast",
        difficulty = "beginner",
        duration = 408,
        free = false,
        popularity = 0.0,
        releasedAt = "2019-08-08T00:00:00.000Z",
        uri = "",
        target = 0,
        progress = 0,
        finished = false,
        percentComplete = 0.0,
        updatedAt = "",
        technology = "Swift, iOS",
        contributors = "Luke",
        url = "",
        kind = ""
      ),
      links = null,
      relationships = Relationships(
        content = null,
        contents = null,
        bookmark = com.raywenderlich.emitron.model.Content(
          datum = Data(
            id = "1",
            type = null,
            attributes = null,
            links = null,
            relationships = null,
            meta = null,
            included = null
          ), links = null, meta = null, included = null
        ),
        domains = null,
        progression = com.raywenderlich.emitron.model.Content(
          datum = Data(
            id = "1",
            type = null,
            attributes = null,
            links = null,
            relationships = null,
            meta = null,
            included = null
          ), links = null, meta = null, included = null
        ),
        groups = null,
        childContents = null
      ),
      meta = null,
      included = null
    )

    Content.from(data) isEqualTo Content(
      contentId = "1",
      name = "Introduction to Kotlin Lambdas: Getting Started",
      description = "In this tutorial you will learn how to use lambda.",
      contributors = "Luke",
      professional = true,
      deleted = false,
      contentType = "screencast",
      difficulty = "beginner",
      releasedAt = "2019-08-08T00:00:00.000Z",
      technology = "Swift, iOS",
      duration = 408,
      streamUrl = "",
      cardArtworkUrl = "https://koenig-media.raywenderlich.com/",
      videoId = null,
      bookmarkId = "1",
      progressionId = "1",
      updatedAt = ""
    )
  }
}
