package io.alienlabs.gichukipaul.vdeo;

import android.net.Uri;
import android.os.Bundle;
import android.widget.MediaController;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class Video extends AppCompatActivity {
    VideoView vd;
    MediaController md;
    private int pos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        vd = findViewById(R.id.videoView);
        md = new MediaController(this);
        pos = savedInstanceState != null ? savedInstanceState.getInt("pos") : 0;

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            Uri uri = Uri.parse(bundle.getString("uri"));
            vd.setVideoURI(uri);
            vd.setMediaController(md);
            md.setAnchorView(vd);
        }
    }


    @Override
    protected void onStart() {
        super.onStart();
        vd.seekTo(pos);
        vd.start();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("pos", pos);
    }

    @Override
    protected void onPause() {
        super.onPause();
        pos = vd.getCurrentPosition();
        vd.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        vd.seekTo(pos);
        vd.resume();
    }

    @Override
    protected void onStop() {
        super.onStop();
        vd.stopPlayback();
        pos = vd.getCurrentPosition();
    }
}