package me.matiaspan.exoplayerclose;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

public class VideoPlayerActivity extends AppCompatActivity {
	private SimpleExoPlayerView mVideoView;
	private SimpleExoPlayer mPlayer;
	private boolean playWhenReady = true;
	private long playbackPosition;
	private int currentWindow;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_video_player);

		mVideoView = (SimpleExoPlayerView) findViewById(R.id.exo_player);
		ImageButton bt = (ImageButton) findViewById(R.id.exo_close);
		bt.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				VideoPlayerActivity.this.finish();
			}
		});
	}

	private void initializePlayer() {
		mPlayer = ExoPlayerFactory.newSimpleInstance(
			new DefaultRenderersFactory(this),
			new DefaultTrackSelector(), new DefaultLoadControl());

		mVideoView.setPlayer(mPlayer);

		mPlayer.setPlayWhenReady(playWhenReady);
		mPlayer.seekTo(currentWindow, playbackPosition);

		Uri uri = Uri.parse("asset:///video.mp4");
		MediaSource mediaSource = buildMediaSource(uri);
		mPlayer.prepare(mediaSource, true, false);
		mVideoView.setControllerShowTimeoutMs(3000);
	}

	private MediaSource buildMediaSource(Uri uri) {
		return new ExtractorMediaSource(uri,
			new DefaultDataSourceFactory(
				this, Util.getUserAgent(this,
				getString(R.string.app_name))),
			new DefaultExtractorsFactory(), null, null);
	}

	@Override
	protected void onResume() {
		super.onResume();
		hideSystemUi();
		initializePlayer();
	}

	@Override
	protected void onPause() {
		super.onPause();
		releasePlayer();
	}

	private void releasePlayer() {
		if (mPlayer != null) {
			playbackPosition = mPlayer.getCurrentPosition();
			currentWindow = mPlayer.getCurrentWindowIndex();
			playWhenReady = mPlayer.getPlayWhenReady();
			mPlayer.release();
			mPlayer = null;
		}
	}

	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		super.onWindowFocusChanged(hasFocus);
		if (hasFocus) {
			hideSystemUi();
		}
	}

	private void hideSystemUi() {
		getWindow().getDecorView().setSystemUiVisibility(
			View.SYSTEM_UI_FLAG_LAYOUT_STABLE
				| View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
				| View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
				| View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
				| View.SYSTEM_UI_FLAG_FULLSCREEN
				| View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
	}
}
