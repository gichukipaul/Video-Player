package io.alienlabs.gichukipaul.vdeo;

import android.net.Uri;
import android.os.Bundle;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.ui.PlayerView;

public class Exoplayer extends AppCompatActivity {
    private PlayerView playerView;
    private static Player player;
    private long progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exoplayer);
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        playerView = findViewById(R.id.video);
        progress = savedInstanceState != null ? savedInstanceState.getLong("progress") : 0;
        player = new SimpleExoPlayer.Builder(this).build();
        playerView.setPlayer(player);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            Uri uri = Uri.parse(bundle.getString("uri"));
            MediaItem mediaItem = MediaItem.fromUri(uri);
            progress = player.getContentPosition();
            player.setMediaItem(mediaItem);
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putLong("progress", progress);
    }

    @Override
    protected void onStart() {
        super.onStart();
        player.seekTo(progress);
        player.prepare();
    }

    @Override
    protected void onResume() {
        super.onResume();
        player.seekTo(0,progress);
        player.setPlayWhenReady(true);
        player.play();
    }

    @Override
    protected void onPause() {
        super.onPause();
        progress = player.getContentPosition();
        player.pause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        player.stop();
        progress = player.getContentPosition();

    }
}