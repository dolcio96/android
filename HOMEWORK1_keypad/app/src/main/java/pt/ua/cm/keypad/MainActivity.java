package pt.ua.cm.keypad;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    HashMap< String, String> contactList = new HashMap< String, String>();
    String[] spinnerArray = new String[3];
    int i=0;
    public static final String EXTRA_MESSAGE_NAME =
            "Old Name";
    public static final String EXTRA_MESSAGE_NUMBER =
            "Old Number";
    AlertDialog.Builder adBuilder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //ADD LONG TAP LISTENER ON 0 BUTTON
        Button buttonPlus = (Button) findViewById(R.id.button_0);
        buttonPlus.setOnLongClickListener(new View.OnLongClickListener() {
            public boolean onLongClick(View v) {
                longclickOnZero();
                return true;
            }
        });
        //ADD LONG TAP LISTENER ON CANCEL BUTTON
        ImageButton cancelButton =(ImageButton)findViewById(R.id.cancelButton);
        cancelButton.setOnLongClickListener(new View.OnLongClickListener() {
            public boolean onLongClick(View v) {
                longclickOnCancel();
                return true;
            }
        });
        //ADD ELEMENT TO HASHMAP
        contactList.put("Mum", "0309034365");
        contactList.put("Dad", "0309030987");
        contactList.put("Sister", "0309089890");
        //ADD KEY OF HASHMAP IN ARRY
        for ( String key : contactList.keySet() ) {
                spinnerArray[i] = key;
                i++;
        }
        //CREATE LONG TAP LISTENER ON SPEED DIAL BUTTON
        final Button buttonRapid1 = (Button) findViewById(R.id.buttonCall1);
        buttonRapid1.setText(spinnerArray[0]);
        buttonRapid1.setOnLongClickListener(new View.OnLongClickListener() {
            public boolean onLongClick(View v) {
                longclickOnRapidCall(buttonRapid1.getText().toString());
                return true;
            }
        });
        final Button buttonRapid2 = (Button) findViewById(R.id.buttonCall2);
        buttonRapid2.setText(spinnerArray[1]);
        buttonRapid2.setOnLongClickListener(new View.OnLongClickListener() {
            public boolean onLongClick(View v) {
                longclickOnRapidCall(buttonRapid2.getText().toString());
                return true;
            }
        });
        final Button buttonRapid3 = (Button) findViewById(R.id.buttonCall3);
        buttonRapid3.setText(spinnerArray[2]);
        buttonRapid3.setOnLongClickListener(new View.OnLongClickListener() {
            public boolean onLongClick(View v) {
                longclickOnRapidCall(buttonRapid3.getText().toString());
                return true;
            }
        });

        adBuilder = new AlertDialog.Builder(this);
    }

    public void writeNumber(View view) {
        TextView display = findViewById(R.id.display);
        display.setText(display.getText().toString() + ((Button)view).getText());
    }

    public void delateDisplay(View view) {
        TextView display = findViewById(R.id.display);
        CharSequence number = display.getText();
        CharSequence numberRemoved=number.subSequence(0,number.length()-1);
        display.setText(numberRemoved);
    }

    public void call(View view) {
        Intent callIntent = new Intent(Intent.ACTION_VIEW);
        TextView display = findViewById(R.id.display);
        String number = "tel:"+display.getText().toString();
        Log.d("numero_di_telefono", number);
        callIntent.setData(Uri.parse(number));
        startActivity(callIntent);
    }

    public void longclickOnZero(){
        TextView display = findViewById(R.id.display);
        display.setText(display.getText().toString() + "+");
    }
    public void longclickOnCancel(){
        TextView display = findViewById(R.id.display);
        display.setText("");
    }
    public void writeTelephoneNumber(View view) {
        String key =((Button)view).getText().toString();
        String val = (String)contactList.get(key);
        TextView display = findViewById(R.id.display);
        display.setText(val);
    }

    public void longclickOnRapidCall(String key){

        Intent intent = new Intent(this, SecondActivity.class);
        String oldName = key;
        String oldNumber= contactList.get(key);
        intent.putExtra(EXTRA_MESSAGE_NAME, oldName);
        intent.putExtra(EXTRA_MESSAGE_NUMBER, oldNumber);
        if (key.equals(((Button) findViewById(R.id.buttonCall1)).getText().toString())){
        startActivityForResult(intent, 1);};
        if (key.equals(((Button) findViewById(R.id.buttonCall2)).getText().toString())){
            startActivityForResult(intent, 2);};
        if (key.equals(((Button) findViewById(R.id.buttonCall3)).getText().toString())){
            startActivityForResult(intent, 3);};


    }

    @Override
    public void onActivityResult(int requestCode,
                                 int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            String [] reply = data.getStringArrayExtra(SecondActivity.EXTRA_REPLY);
            String newName = reply[0];
            String newPhone = reply[1];
            String oldName="";
            Button buttonClicked=null;
            if (reply[0].equals("")||reply[1].equals("")||reply[0].equals(null)||reply[1].equals(null)) {
                Log.d("NOCHANGES","NO CHANGES BECAUSE SOME FIELD WAS EMPTY");
                adBuilder.setTitle("NO CHANGES")
                        .setMessage("One or both fields was empty, the old contact stays saved.")
                        .setPositiveButton(android.R.string.yes, null)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
            }
            else{
                if (requestCode == 1) {
                    buttonClicked = (Button) findViewById(R.id.buttonCall1);
                    oldName = buttonClicked.getText().toString();
                }
                if (requestCode == 2) {
                    buttonClicked = (Button) findViewById(R.id.buttonCall2);
                    oldName = buttonClicked.getText().toString();
                }
                if (requestCode == 3) {
                    buttonClicked = (Button) findViewById(R.id.buttonCall3);
                    oldName = buttonClicked.getText().toString();
                }
                contactList.remove(oldName);
                contactList.put(newName, newPhone);
                buttonClicked.setText(newName);
            }

        }
    }

}