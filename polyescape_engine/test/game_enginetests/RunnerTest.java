package game_enginetests;

import org.junit.Before;
import org.junit.Test;
import polytech.teamf.game_engine.Runner;


import static junit.framework.Assert.assertEquals;

public class RunnerTest{


    private Runner runner;

        @Before
        public void setUp() {

            runner = new Runner("[ { \"type\" : \"CaesarCipherPlugin\", \"description\" : \"dit 42\", \"plain_text\" : \"COUCOU\" ,\"cipher_padding\" : \"1\" } ]");
        }


        @Test
        public void getDescriptionTests(){
            assertEquals("test  description : ",runner.getDescription().getString("description") , "dit 42 DPVDPV");
            assertEquals("test  format : ",runner.getDescription().getString("answer_format") , "text");


        }


        @Test
        public void sendGuess_GetResponseTests(){

            try {

            assertEquals("test wrong answer" , runner.sendGuess_GetResponse("{attempt : 72 }").getString("success"), "false");
            assertEquals("test right answer" , runner.sendGuess_GetResponse("{attempt :COUCOU}").getString("success"), "true");
            } catch (Exception e) {
                e.printStackTrace();
            }

        }


        @Test
        public void nextPluginTest(){

            assertEquals("test is finished" ,  runner.nextPlugin().getString("status") , "finish");

            runner = new Runner("[ { \"type\" : \"CaesarCipherPlugin\", \"description\" : \"dit 42\", \"plain_text\" : \"COUCOU\" ,\"cipher_padding\" : \"1\" },  { \"type\" : \"CaesarCipherPlugin\", \"description\" : \"dit 42\", \"plain_text\" : \"COUCOU\" ,\"cipher_padding\" : \"1\" } ]");

            assertEquals("test is finished" ,  runner.nextPlugin().getString("status") , "ok");
        }


    }





