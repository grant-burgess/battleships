package com.grantburgess.battleships;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class RunnerTest {
    @Rule
    public ExpectedException shouldThrow = ExpectedException.none();


    @Test
    public void testAttemptToSetAShipWithAnUnknownDirection() {
        String[] args = new String[4];
        args[0] = "10";
        args[1] = "(0, 0, X)";
        args[2] = "(0, 0) MRMLMM";
        args[3] = "(1, 3, N)";

        shouldThrow.expect(RuntimeException.class);
        shouldThrow.expectMessage("Unknown ship direction. Should be N, E, S or W");

        TestRunner.main(args);
    }
}

class TestRunner extends Runner {
    public static void main(String[] args) {
        GamePresenter gamePresenter = new GamePresenter(new MockConsole());

        run(args, gamePresenter);
    }
}