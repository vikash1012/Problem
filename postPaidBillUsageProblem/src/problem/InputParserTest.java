package problem;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class InputParserTest {

    @Test
    void shouldparseInputWhenStringLengthIsFive() {
        String input="200 isd sms to vikash";
        String expectedOutput[]={"200","isd","sms"};

        String actualOutput[]=InputParser.parseInput(input);

        assertArrayEquals(expectedOutput,actualOutput);
    }
    @Test
    void shouldparseInputWhenStringLengthIsFiveWhenUsageISNotInSequece() {
        String input="local 200 sms to vikash";
        String expectedOutput[]={};

        String actualOutput[]=InputParser.parseInput(input);

        assertArrayEquals(expectedOutput,actualOutput);
    }
    @Test
    void shouldparseInputWhenStringLengthIsFiveWhenInputNotInSequence() {
        String input="200 smd isd to vikash";
        String expectedOutput[]={};

        String actualOutput[]=InputParser.parseInput(input);

        assertArrayEquals(expectedOutput,actualOutput);
    }
    @Test
    void shouldParseInputWhenStringLengthIsSix(){
        String input="150 min isd call to vikash";
        String expectedOutput[]={"150","isd","call"};

        String actualOutput[]=InputParser.parseInput(input);

        assertArrayEquals(expectedOutput,actualOutput);

    }
    @Test
    void shouldParseInputWhenStringLengthIsSixButNotValidSequence(){
        String input="150 isd min call to vikash";
        String expectedOutput[]={};

        String actualOutput[]=InputParser.parseInput(input);

        assertArrayEquals(expectedOutput,actualOutput);

    }
    @Test
    void shouldParseInputWhenStringLengthIsZero(){
        String input="";
        String expectedOutput[]={};

        String actualOutput[] =InputParser.parseInput(input);

        assertArrayEquals(expectedOutput,actualOutput);

    }
    @Test
    void shouldParseInputWhenStringIsNull(){
        String input=null;
        String expectdOutput[]={};

        String actualOutput[]=InputParser.parseInput(input);

        assertArrayEquals(expectdOutput,actualOutput);
    }
    @Test
    void shouldParseInputWhenStringLengthOtherThanFiveSixZeroNull(){
        String input=" 25 min isd call and 10 sms to vikash";
        String expectedOutput[]={};

        String actualOutput[]=InputParser.parseInput(input);

        assertArrayEquals(expectedOutput,actualOutput);

    }
    @Test
    void shouldParseInputWhenStringLengthIsSixButNotInCorrectFormat(){
        String input="std 150 min call to vikash";
        String expectedOutput[]={};

        String actualOutput[]=InputParser.parseInput(input);

        assertArrayEquals(expectedOutput,actualOutput);

    }
}