package edu.up.FishGameState;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText multiLineText;
    private Button runTestButton;
    private FishGameState g;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //For testing
       g = new FishGameState();

        multiLineText = (EditText) findViewById(R.id.multi_line_text);
        runTestButton = (Button) findViewById(R.id.run_test_button);

        //multiLineText.setOn
        runTestButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        // for testing
        Log.d("BUTTON","Run Test Has Been Clicked");

        /**
         * External Citation
         * Date: October 14, 2020
         * Problem: Didn't know how to clear a multi-line
         * Resource: https://stackoverflow.com/questions/4794621/clearing-a-multiline-edittext
         **/
        // Clearing the text of the multi-line EditText (from previous run)
        multiLineText.getEditableText().clear();

        //Creating new instance of the game state class
        FishGameState firstInstance = new FishGameState();

        String fs = firstInstance.toString();
        multiLineText.setText(fs);

        //Creating deep copy of firstInstance (This will be secondInstance)
        FishGameState secondInstance = new FishGameState(firstInstance);

        // TODO: call methods
        /**
         *
         * Here will be where we call each method in the game state class once,
         * using "firstInstance"
         *
         * */


        //Creating new instance of the game state class (Third Instance)
        FishGameState thirdInstance = new FishGameState();

        //Creating deep copy of thirdInstance (This will be fourthInstance)
        FishGameState fourthInstance = new FishGameState(thirdInstance);

        // Calling the toString() method on secondInstance and fourthInstance
        String secondString = secondInstance.toString();

        String fourthString = fourthInstance.toString();

        //Checking if strings are identical using .equals()
        if(secondString.equals(fourthString)){
            // print message that they're equal
            /**
             * SOMETHING LIKE THIS:
             *
             * multiLineText.append("The second and fourth instance are equal");
             *
             * multiLineText.append("SecondInstance: \n");
             * multiLineText.append(secondString + "\n");
             * multiLineText.append("FourthInstance: \n");
             * multiLineText.append(fourthString + "\n");
             *
             */
        }else{
            // print that they're not equal
        }

        // Print both strings to the multi-line EditText for visual inspection
        // append strings to multi-line EditText


    }
}