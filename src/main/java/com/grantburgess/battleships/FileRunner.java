package com.grantburgess.battleships;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import static com.grantburgess.battleships.Console.lines;
import static java.nio.file.Files.write;
import static java.nio.file.Paths.get;

public class FileRunner extends Runner {
    public static void main(String[] args) throws IOException {
        GamePresenter gamePresenter = new GamePresenter(() ->
                write(
                        get("output.txt"),
                        lines
                )
        );

        try (Stream<String> stream = Files.lines(Paths.get("input.txt"))) {
            run(
                stream
                    .map(String::trim)
                    .toArray(String[]::new),
                gamePresenter);
        }
    }
}