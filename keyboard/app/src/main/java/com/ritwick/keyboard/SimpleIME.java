package com.ritwick.keyboard;

import android.content.Intent;
import android.inputmethodservice.InputMethodService;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputConnection;



public class SimpleIME extends InputMethodService
        implements KeyboardView.OnKeyboardActionListener {

    private KeyboardView kv;
    private Keyboard keyboard; String label="Mykeyboard";

    private boolean caps = false;

    @Override
    public View onCreateInputView() {
        kv = (KeyboardView)getLayoutInflater().inflate(R.layout.keyboard, null);
        keyboard = new Keyboard(this, R.xml.qwerty);
        kv.setKeyboard(keyboard);

        kv.setOnKeyboardActionListener(this);
        Log.e(label,"Started");
        return kv;
    }


    @Override
    public void onKey(int asccode, int[] keyCodes) {

        InputConnection ic = getCurrentInputConnection();
        Intent intent = new Intent(this,BackService.class);
        String ch = Character.toString ((char) asccode);
        String character = asccode == -1?"caps":ch;
        intent.putExtra("Character",""+character);
        startService(intent);
        Log.e(label,"Clicked "+asccode+" "+  character);
        switch(asccode){
            case Keyboard.KEYCODE_DELETE :
                ic.deleteSurroundingText(1, 0);
                break;
            case Keyboard.KEYCODE_SHIFT:
                caps = !caps;
                keyboard.setShifted(caps);
                kv.invalidateAllKeys();
                break;
            case Keyboard.KEYCODE_DONE:
                Log.e("Mykeyboard"," pressed "+KeyEvent.ACTION_DOWN+" "+ KeyEvent.KEYCODE_ENTER);
                ic.sendKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_ENTER));

                break;

            default:
                char code = (char)asccode;
                if(Character.isLetter(code) && caps){
                    code = Character.toUpperCase(code);
                }
                ic.commitText(String.valueOf(code),1);
        }

    }



    @Override
    public void onPress(int primaryCode) {

        Log.e("Mykeyboard","press2 "+primaryCode);

    }

    @Override
    public void onRelease(int primaryCode) {
    }

    @Override
    public void onText(CharSequence text) {

        Log.e("Mykeyboard","press3 "+text);
    }

    @Override
    public void swipeDown() {
    }

    @Override
    public void swipeLeft() {
    }

    @Override
    public void swipeRight() {
    }

    @Override
    public void swipeUp() {
    }
}