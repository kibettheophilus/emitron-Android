package com.razeware.emitron.ui.player

import android.content.Context
import android.net.Uri
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.ext.cast.CastPlayer
import com.google.android.exoplayer2.ext.cast.SessionAvailabilityListener
import com.google.android.exoplayer2.extractor.ExtractorsFactory
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.source.hls.HlsMediaSource
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.ui.PlayerControlView
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter
import com.google.android.exoplayer2.upstream.DefaultHttpDataSource
import com.google.android.exoplayer2.upstream.cache.CacheDataSource
import com.google.android.exoplayer2.util.MimeTypes
import com.google.android.gms.cast.MediaInfo
import com.google.android.gms.cast.MediaMetadata
import com.google.android.gms.cast.MediaQueueItem
import com.google.android.gms.cast.framework.CastContext
import com.razeware.emitron.ui.player.cast.Episode

object PlayerManagerFactory {

  internal fun createMediaPlayer(
    playerView: PlayerView,
    trackSelector: DefaultTrackSelector
  ): SimpleExoPlayer {
    val mediaPlayer = SimpleExoPlayer.Builder(playerView.context, ExtractorsFactory.EMPTY)
      .setTrackSelector(trackSelector)
      .build()
    playerView.player = mediaPlayer
    return mediaPlayer
  }

  internal fun createCastMediaPlayer(
    castCtx: CastContext,
    castControlView: PlayerControlView,
    castPlayerEventListener: Player.EventListener,
    sessionListener: SessionAvailabilityListener
  ): CastPlayer {
    val castPlayer = CastPlayer(castCtx)
    castPlayer.addListener(castPlayerEventListener)
    castPlayer.setSessionAvailabilityListener(sessionListener)
    castControlView.player = castPlayer
    return castPlayer
  }

  internal fun buildMediaSource(
    episode: Episode,
    dataSourceFactory: DefaultHttpDataSource.Factory,
    cacheDataSourceFactory: CacheDataSource.Factory
  ): MediaSource {
    val uri = Uri.parse(episode.uri)
    return when (episode.mimeType) {
      MimeTypes.APPLICATION_M3U8 -> HlsMediaSource.Factory(dataSourceFactory)
        .setAllowChunklessPreparation(true)
        .createMediaSource(MediaItem.fromUri(uri))
      MimeTypes.APPLICATION_MP4 -> ProgressiveMediaSource
        .Factory(cacheDataSourceFactory)
        .createMediaSource(MediaItem.fromUri(uri))
      else -> {
        throw IllegalStateException("Unsupported type: " + episode.mimeType)
      }
    }
  }

  internal fun buildMediaQueueItem(episode: Episode): MediaQueueItem {
    val movieMetadata = MediaMetadata(MediaMetadata.MEDIA_TYPE_MOVIE)
    movieMetadata.putString(MediaMetadata.KEY_TITLE, episode.name)
    val mediaInfo = MediaInfo.Builder(episode.uri)
      .setStreamType(MediaInfo.STREAM_TYPE_BUFFERED).setContentType(episode.mimeType)
      .setMetadata(movieMetadata).build()
    return MediaQueueItem.Builder(mediaInfo).build()
  }

  internal fun buildTrackSelector(context: Context): DefaultTrackSelector {
    val trackSelectionFactory = AdaptiveTrackSelection.Factory()
    return DefaultTrackSelector(context, trackSelectionFactory).apply {
      parameters = DefaultTrackSelector.ParametersBuilder(context).build()
    }
  }

  internal fun buildDataSourceFactory(
    context: Context,
    userAgent: String
  ): DefaultHttpDataSource.Factory {
    val bandWidthMeter =
      DefaultBandwidthMeter.Builder(context).build()
    return DefaultHttpDataSource.Factory().apply {
      //userAgent, bandWidthMeter TODO figure our the meter

      setUserAgent(userAgent)
    }
  }
}
