package com.grantburgess.battleships;

import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public abstract class Runner {
    public static void run(String[] args, GamePresenter gamePresenter) {
        String[] filteredArgs = Arrays.stream(args)
                .filter(arg -> !arg.isEmpty())
                .map(w ->
                        Stream.of(w.split("\\s+"))
                                .collect(Collectors.joining())
                )
                .toArray(String[]::new);
        createMap(filteredArgs[0], gamePresenter);
        addShips(filteredArgs[1], gamePresenter);
        processOperations(filteredArgs, gamePresenter);
        endGame(gamePresenter);
    }

    private static void createMap(String arg, GamePresenter gamePresenter) {
        gamePresenter.getGame().createMap(Integer.parseInt(arg));
    }

    private static void addShips(String arg, GamePresenter gamePresenter) {
        String[] ships = Arrays.stream(arg.toLowerCase().trim().split("[\\(\\)]"))
                .filter(x -> !x.isEmpty())
                .toArray(String[]::new);

        Arrays.stream(ships)
                .forEach(ship -> {
                    String[] shipCoordinates = Arrays.stream(ship.toLowerCase().trim().split(","))
                            .map(String::trim)
                            .toArray(String[]::new);

                    gamePresenter.getGame().addShip(
                            new Ship(
                                new Point(
                                        Integer.parseInt(shipCoordinates[0]),
                                        Integer.parseInt(shipCoordinates[1])
                                ),
                            parseShipDirection(shipCoordinates[2])));
                });
    }

    private static ShipDirection parseShipDirection(String arg) {
        ShipDirection shipDirection;

        switch (arg) {
            case "n":
                shipDirection = ShipDirection.NORTH;
                break;
            case "e":
                shipDirection = ShipDirection.EAST;
                break;
            case "s":
                shipDirection = ShipDirection.SOUTH;
                break;
            case "w":
                shipDirection = ShipDirection.WEST;
                break;
            default:
                throw new RuntimeException("Unknown ship direction. Should be N, E, S or W");
        }

        return shipDirection;
    }

    private static void processOperations(String[] args, GamePresenter gamePresenter) {
        for(int i = 2; i < args.length; i++) {
            gamePresenter.execute(parseOperation(args[i]));
        }
    }

    private static String parseOperation(String arg) {
        String argWithNoSpaces = Arrays.stream(arg.toLowerCase().split(" ")).collect(Collectors.joining());
        String coordinates = getCoordinatesFromArgument(argWithNoSpaces);

        return generateCommand(argWithNoSpaces, coordinates);
    }

    private static String getCoordinatesFromArgument(String argWithNoSpaces) {
        return argWithNoSpaces.substring(1, argWithNoSpaces.indexOf(')'));
    }

    private static String generateCommand(String argWithNoSpaces, String coordinates) {
        String command;

        if (isMoveRotateOperation(argWithNoSpaces)) {
            String operations = argWithNoSpaces.substring(argWithNoSpaces.indexOf(')') + 1);
            command = String.format("movement %s %s",
                                    operations,
                                    coordinates);
        }
        else {
            command = String.format("shoot %s",
                    coordinates);
        }

        return command;
    }

    private static boolean isMoveRotateOperation(String arg) {
        return arg.contains("l") || arg.contains("m") || arg.contains("r");
    }

    private static void endGame(GamePresenter gamePresenter) {
        gamePresenter.printEndOfGameMessages();
    }
}