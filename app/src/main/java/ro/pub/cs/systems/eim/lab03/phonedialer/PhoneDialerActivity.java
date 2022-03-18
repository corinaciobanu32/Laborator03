package ro.pub.cs.systems.eim.lab03.phonedialer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import ro.pub.cs.systems.eim.lab03.phonedialer.general.Constants.Constants;


public class PhoneDialerActivity extends AppCompatActivity {

    private EditText phoneNumberEditText;
    private Button keyboardButton;
    private ImageButton deleteButton;
    private ImageButton callButton;
    private ImageButton dropCallButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_dialer);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        phoneNumberEditText = (EditText)findViewById(R.id.phone_number_edit_text);
        callButton = (ImageButton)findViewById(R.id.call_image_button);
        callButton.setOnClickListener(callButtonListener);
        dropCallButton = (ImageButton)findViewById(R.id.drop_image_button);
        dropCallButton.setOnClickListener(dropCallButtonListener);
        deleteButton = (ImageButton)findViewById(R.id.delete_image_button);
        deleteButton.setOnClickListener(deleteButtonListener);
        for (int index = 0; index < Constants.buttonIds.length; index++) {
            keyboardButton = (Button)findViewById(Constants.buttonIds[index]);
            keyboardButton.setOnClickListener(keyboardButtonListener);
        }
    }

    private KeyboardButtonListener keyboardButtonListener = new KeyboardButtonListener();

    private class KeyboardButtonListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            phoneNumberEditText.setText(phoneNumberEditText.getText().toString() + ((Button) view).getText().toString());
        }
    }

    private DeleteButtonListener deleteButtonListener = new DeleteButtonListener();

    private class DeleteButtonListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            int stringNumberLen = phoneNumberEditText.getText().toString().length();
            if (stringNumberLen > 0) {
                phoneNumberEditText.setText(phoneNumberEditText.getText().toString().substring(0, stringNumberLen - 1));
            }
        }
    }

    private CallButtonListener callButtonListener = new CallButtonListener();
    private class CallButtonListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            if (ContextCompat.checkSelfPermission(PhoneDialerActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(
                        PhoneDialerActivity.this,
                        new String[]{Manifest.permission.CALL_PHONE},
                        1);
            } else {
                Intent intent = new Intent(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:" + phoneNumberEditText.getText().toString()));
                startActivity(intent);
            }
        }
    }

    private DropCallButtonListener dropCallButtonListener = new DropCallButtonListener();
    private class DropCallButtonListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            finish();
        }
    }

}