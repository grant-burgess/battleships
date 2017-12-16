package com.grantburgess.battleships;

import static com.grantburgess.battleships.Console.lines;

public class ConsoleRunner extends Runner {
    public static void main(String[] args) {
        GamePresenter gamePresenter = new GamePresenter(() ->
                lines.forEach(System.out::println)
        );

        run(args, gamePresenter);
    }
}