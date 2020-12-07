package pt.ua.cm.keypad;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class SecondActivity extends AppCompatActivity {
    public static final String EXTRA_REPLY =
            "pt.ua.cm.keypad.extra.REPLY";
    private EditText nameReply;
    private EditText numberReply;
    private String[] reply = new String[2];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        nameReply=findViewById(R.id.editTextName);
        numberReply=findViewById(R.id.editTextPhoneNumber);
        Intent intent = getIntent();
        String oldName = intent.getStringExtra(MainActivity.EXTRA_MESSAGE_NAME);
        String oldNumber = intent.getStringExtra(MainActivity.EXTRA_MESSAGE_NUMBER);
        //SET HINT
        nameReply.setHint(oldName);
        nameReply.setHintTextColor(Color.parseColor("#808080"));
        numberReply.setHint(oldNumber);
        numberReply.setHintTextColor(Color.parseColor("#808080"));
    }

    public void returnReply(View view) {
        reply[0] = nameReply.getText().toString();
        reply[1] = numberReply.getText().toString();

        Intent replyIntent = new Intent();
        replyIntent.putExtra(EXTRA_REPLY, reply);
        setResult(RESULT_OK, replyIntent);
        finish();
    }




}