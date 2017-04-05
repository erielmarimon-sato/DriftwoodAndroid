package com.example.erielmarimon.driftwoodsoccer;

import android.provider.Settings;
import android.util.Log;

import com.example.erielmarimon.driftwoodsoccer.models.Game;
import com.example.erielmarimon.driftwoodsoccer.models.Player;
import com.example.erielmarimon.driftwoodsoccer.util.Helper;
import com.example.erielmarimon.driftwoodsoccer.util.StringFormatter;

import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }


    @Test
    public void testObjectToJsonString(){
        Player player = Helper.createPlayerList(1)[0]; // creates a list of players with 1 element

        String expected = "{\"id\":\"00\",\"name\":\"test0\",\"username\":\"test0\",\"password\"" +
                ":\"test0\",\"totalGoals\":0,\"totalAssists\":0,\"noShow\":0,\"totalGames\":0,\"" +
                "effectiveness\":0,\"playingSince\":1490290820224,\"lastDayPlayed\":149029082022" +
                "4,\"active\":true}\n";
        String playerJsonString = Helper.objectToJsonString(player);

        System.out.print("EXPECTED: " + expected);
        System.out.print("ACTUAL: " + playerJsonString);

        // only the first 142 chars are guaranteed to be the same since dates are dynamic
        assertEquals(expected.substring(0,142), playerJsonString.substring(0,142));

    }

}