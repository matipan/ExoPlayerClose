package me.matiaspan.exoplayerclose;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		Button bt = (Button) findViewById(R.id.video_bt);
		bt.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent video = new Intent(
					MainActivity.this, VideoPlayerActivity.class);
				startActivity(video);
			}
		});
	}
}
