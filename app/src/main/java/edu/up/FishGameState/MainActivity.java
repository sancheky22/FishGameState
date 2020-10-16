package edu.up.FishGameState;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


/**
 * @author Kyle Sanchez
 * @author Ryan Enslow
 * @author Carina Pineda
 * @author Linda Nguyen
 **/

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
        //Creating deep copy of firstInstance (This will be secondInstance)
        FishGameState secondInstance = new FishGameState(firstInstance);

        firstInstance.setPlayer4Score(100);
        firstInstance.placePenguin(firstInstance.getPieceArray()[0][0],3,3);
        firstInstance.movePenguin(firstInstance.getPieceArray()[0][0],4,4);

        // do some stuff to first instance

        String fs = firstInstance.toString();
        multiLineText.setText(fs);

        // Displaying that player 1 has made a move.
        // This can be seen on lines 51 and 52
        multiLineText.append("\nPlayer 1 has moved their piece from 3,3 to 4,4 \n\n");

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

              multiLineText.append("The second and fourth instance are equal \n");

              multiLineText.append("SECOND INSTANCE: \n");
              multiLineText.append(secondString + "\n");
              multiLineText.append("FOURTH INSTANCE: \n");
              multiLineText.append(fourthString + "\n");


        }else{
            multiLineText.append("second and fourth instance are not equal\n");
            // print that they're not equal
        }

        // Print both strings to the multi-line EditText for visual inspection
        // append strings to multi-line EditText


    }
}