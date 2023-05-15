package com.frogobox.kamusapps.views.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.frogobox.kamusapps.R;
import com.frogobox.kamusapps.models.dataclass.Dictionary;

import java.util.Locale;

public class DetailActivity extends AppCompatActivity {

    public static String EXTRA_DICTIONARY = "extra_dictionary";
    private TextToSpeech mTTs;
    private Button mSpeech;
    private TextView mTextViewTitle, mTextViewDesc;
    private String title, desc;

    @SuppressLint({"MissingInflatedId", "CutPasteId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        setTitle(R.string.title_activity_detail);

        mTextViewTitle = findViewById(R.id.textTitle);
        mTextViewDesc = findViewById(R.id.textDesc);
        mSpeech = findViewById(R.id.button);

        Dictionary extraDictionary = getIntent().getParcelableExtra(EXTRA_DICTIONARY);
        title = extraDictionary.getWord();
        desc = extraDictionary.getDescription();

        mTextViewTitle.setText(title);
        mTextViewDesc.setText(desc);

        mTTs = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                    int result = mTTs.setLanguage(Locale.ENGLISH);
                    if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                        Log.e("TTS", "Language not Supported");
                    } else {
                        mSpeech.setEnabled(true);
                    }
                } else {
                    Log.e("TTS", "Initalization failed");

                }

            }
        });

        mSpeech.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                speak();
            }
        });

    }


    private void speak() {
        String text = title;
        float pitch = 0.5f;
        float speed = 0.6f;

        mTTs.setPitch(pitch);
        mTTs.setSpeechRate(speed);

        mTTs.speak(text, TextToSpeech.QUEUE_FLUSH, null);

    }

    @Override
    protected void onDestroy() {
        if(mTTs == null){
            mTTs.stop();
            mTTs.shutdown();
        }
        super.onDestroy();
    }

}