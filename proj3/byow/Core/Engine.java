package byow.Core;

//import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;
import byow.lab12.HexWorld;
import byow.Core.Room;
import edu.princeton.cs.algs4.Stopwatch;
import edu.princeton.cs.introcs.StdDraw;

import java.io.*;
import java.awt.Color;
import java.awt.Font;
import java.util.*;
import javax.sound.sampled.*;

//import java.applet.Applet;
//import java.applet.AudioClip;
//import java.net.URL;



public class Engine {

    /* Feel free to change the width and height. */
    public static final int WIDTH = 100;
    public static final int HEIGHT = 60;
    //    private static long SEED;
    private static long SEED;
    String r;

    private String history = "";
//    TERenderer ter = new TERenderer();

    private TETile avatar = Tileset.AVATAR;
    boolean save = false;





    public void interactWithKeyboard() throws javax.sound.sampled.LineUnavailableException,
            java.io.IOException, javax.sound.sampled.UnsupportedAudioFileException {

        System.out.println("Current Avatar: " + avatar);

        File theme = new File(".../proj3/gametheme.wav");
        AudioInputStream playtheme = AudioSystem.getAudioInputStream(theme);

        AudioFormat play = playtheme.getFormat();
        DataLine.Info info = new DataLine.Info(Clip.class, play);

        Clip audioClip = (Clip) AudioSystem.getLine(info);
        audioClip.open(playtheme);
        audioClip.start();
//
//        String pick = drawStartScreen();
        TETile[][] gameworld = null;
//        if (pick.equals("Quit")) {
//            System.exit(0);
//        } else if (pick.equals("avatar selection")) {
//            changeAvatar();
            interactWithKeyboard();
//        } else if (pick.equals("replay game")) {
//            replaySavedGame();
//        } else if (pick.equals("load game")) {
            playseed();
            actionplay();
            loadAvatar();
            Room map = new Room( WIDTH, HEIGHT, SEED);
            Room.Position positionavatar = map.drawWorldRooms(avatar);
            gameworld = map.randWorld;
            for (int i = 0; i < history.length(); i++) {
                int[] directions = directionMover(positionavatar, history.charAt(i));
                if (gameworld[directions[0]][directions[1]] == Tileset.WALL ||
                        gameworld[directions[0]][directions[1]] == Tileset.NOTHING) {
                    continue;
                }
                gameworld[positionavatar.getx()][positionavatar.gety()] = Tileset.FLOOR;
                gameworld[directions[0]][directions[1]] = avatar;
                positionavatar.x = directions[0];
                positionavatar.y = directions[1];
            }
//            playGame(SEED, history, positionavatar, gameworld, map);
//        } else if (pick.equals("new game")) {
            SEED = inputseed();

            Room mapp = new Room( WIDTH, HEIGHT, SEED);

//            Room.Position positionavatar = map.drawWorldRooms(avatar);
            gameworld = mapp.randWorld;
//
//            playGame(SEED, "", positionavatar, gameworld, map);
//        } else if (pick.equals("backstory")) {
//            backstory();
//        }
//        String start = startscreen();d
//        if (start.equals("Quit")) {
//            System.exit(0);
//        } else if (start.equals("load game")) {
//            randomRoom();
//        }
    }

    private long inputseed() {
//        StdDraw.clear(Color.BLACK);
//        StdDraw.setPenColor(Color.WHITE);
//
//        String game = ".../proj3/gamebackground.png";
//
//
//
//        StdDraw.setXscale(0, WIDTH);
//        StdDraw.setYscale(0, HEIGHT);
//        StdDraw.enableDoubleBuffering();
//
//        StdDraw.picture(WIDTH / 2, HEIGHT / 2 , game);
//        StdDraw.text(WIDTH / 2, HEIGHT / 2 + HEIGHT / 4, "Please input a seed");
//        StdDraw.text(WIDTH / 2, HEIGHT / 2 + HEIGHT / 4 - 4, "Press (s) after inputing seed");
//        StdDraw.show();
        long seed = 0;
        while (true) {
            if (StdDraw.hasNextKeyTyped()) {
                char type = Character.toLowerCase(StdDraw.nextKeyTyped());
                if (type == 's') {
                    return seed;
                } else {
                    int currInt = Integer.parseInt(String.valueOf(type));
                    seed = seed * 10 + currInt;
                    System.out.println("seed: " + seed);
                }
            }
        }
    }

//    public String drawStartScreen() {
//        StdDraw.setCanvasSize(WIDTH * 10, HEIGHT * 10);
////        StdDraw.setPenColor(Color.WHITE);
//        Font font = new Font("Herculanum", Font.BOLD, 30);
//        StdDraw.setFont(font);
//
//        String background = "../proj3/background.png";
//
//
//        StdDraw.setXscale(0, WIDTH);
//        StdDraw.setYscale(0, HEIGHT);
//        StdDraw.enableDoubleBuffering();
//
//        StdDraw.clear(Color.BLACK);
//        StdDraw.setPenColor(Color.WHITE);
//        StdDraw.picture(WIDTH / 2, HEIGHT / 2 , background);
//
//        StdDraw.text(WIDTH / 2, HEIGHT / 2 + HEIGHT / 4, "Demon Slayer");
//        StdDraw.text(WIDTH / 2, HEIGHT / 2 + HEIGHT / 4 - 8, "New Game (N)");
//        StdDraw.text(WIDTH / 2, HEIGHT / 2 + HEIGHT / 4 - 12, "Load Game (L)");
//        StdDraw.text(WIDTH / 2, HEIGHT / 2 + HEIGHT / 4 - 16, "Quit (Q)");
//        StdDraw.text(WIDTH / 2, HEIGHT / 2 + HEIGHT / 4 - 20, "Replay Game (R)");
//        StdDraw.text(WIDTH / 2, HEIGHT / 2 + HEIGHT / 4 - 24, "Avatar Selection (A)");
//        StdDraw.text(WIDTH / 2, HEIGHT / 2 + HEIGHT / 4 - 28, "Backstory (B)");
//
//        StdDraw.show();
//
//        while (true) {
//            if (StdDraw.hasNextKeyTyped()) {
//                char curr = Character.toLowerCase(StdDraw.nextKeyTyped());
//                System.out.println("User pressed: " + curr);
//                if (curr == 'n') {
//                    return "new game";
//                } else if (curr == 'l') {
//                    return "load game";
//                } else if (curr == 'r') {
//                    return "replay game";
//                } else if (curr == 'q') {
//                    return "Quit";
//                } else if (curr == 'a') {
//                    return "avatar selection";
//                } else if (curr == 'b') {
//                    return "backstory";
//                }
//            }
//        }
//    }

    private void loadAvatar() {
        String savedAvatar = "";
        try {
            Reader savedFileAvatar = new FileReader("SavedAvatar.txt");
            int k;
            while ((k = savedFileAvatar.read()) != -1) {
                savedAvatar = savedAvatar + (char) k;
            }
            savedFileAvatar.close();
        } catch (IOException e) {
            helperErrorExit("IOException happened! Could not save :/");
        }
        System.out.println("Loading avatar...");
        System.out.println("Loaded avatar: " + savedAvatar);
        if (savedAvatar.equals("basic")) {

            avatar = Tileset.AVATAR;
        } else if (savedAvatar.equals("Nezuko")) {

            avatar = Tileset.NEZUKO;
        }

    }

//    private void changeAvatar() {
//        System.out.println("Changing avatar...");
//
//        String selection = ".../proj3/avatarselection.png";
//
//
//
//        StdDraw.setXscale(0, WIDTH);
//        StdDraw.setYscale(0, HEIGHT);
//        StdDraw.enableDoubleBuffering();
//
//        StdDraw.clear(Color.BLACK);
//        StdDraw.setPenColor(Color.WHITE);
//        StdDraw.picture(WIDTH / 2, HEIGHT / 2 , selection);
//
//
//        StdDraw.text(WIDTH / 2, HEIGHT / 2 + HEIGHT / 4, "Choose Avatar");
//        StdDraw.text(WIDTH / 2, HEIGHT / 2 + HEIGHT / 4 - 8, "Tanjiro(B)");
//        StdDraw.text(WIDTH / 2, HEIGHT / 2 + HEIGHT / 4 - 12, "Nezuko(N)");
////        StdDraw.text(WIDTH / 2, HEIGHT / 2 + HEIGHT / 4 - 16, "Demon(D)");
//
//        StdDraw.show();
//
//        while (true) {
//            if (StdDraw.hasNextKeyTyped()) {
//                char curr = Character.toLowerCase(StdDraw.nextKeyTyped());
//                if (curr == 'a') {
//                    avatar = Tileset.AVATAR;
//
//                    return;
//                } else if (curr == 'n') {
//                    avatar = Tileset.NEZUKO;
//
//                    return;
//                }
//            }
//        }
//    }

    private int[] directionMover(Room.Position avPlace, char direction) {
        int[] answer = new int[2];
        if (direction == 'w') {
            answer[0] = avPlace.getx();
            answer[1] = avPlace.gety() + 1;
        } else if (direction == 'a') {
            answer[0] = avPlace.getx() - 1;
            answer[1] = avPlace.gety();
        } else if (direction == 's') {
            answer[0] = avPlace.getx();
            answer[1] = avPlace.gety() - 1;
        } else if (direction == 'd') {
            answer[0] = avPlace.getx() + 1;
            answer[1] = avPlace.gety();
        }
        return answer;
    }

//    private void playGame(long seed, String actions, Room.Position positionavatar,
//                          TETile[][] worldTiles, Room map) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
//        String actionsSoFar = actions;
//        boolean typed = false;
//        Stopwatch sw = new Stopwatch();
//
//        while (true) {
//            mouse(worldTiles, map.demonsoul, sw);
//            if (sw.elapsedTime() > 30 && map.demonsoul < 5) {
//                result("lose");
//            } else if (map.demonsoul >= 5) {
//                result("win");
//            } if (StdDraw.hasNextKeyTyped()) {
//                char curr = Character.toLowerCase(StdDraw.nextKeyTyped());
//                if (typed && (curr == 'q')) {
//
//                    SEED = seed;
//                    history = actionsSoFar;
//                    saveseedd();
//                    saveAvatar();
//                    System.out.println("The saved seed is: " + SEED);
//                    System.out.println("The saved actions are: " + history);
//                    System.exit(0);
//                } else if (curr == ':') {
//
//                    typed = true;
//                } else if (curr == 'a' || curr == 'w' || curr == 's' || curr == 'd') {
//                    actionsSoFar = actionsSoFar + curr;
//                    Room.ingame newAvatarWorld = map.move(curr, worldTiles, positionavatar, avatar);
//                    positionavatar = newAvatarWorld.avatarPosition;
//                    worldTiles = newAvatarWorld.worldTiles;
//                }
//            }
//        }
//    }
    private boolean helpersoul(int souls) {
        if (souls > 0) {
            return true;
        } else {
            return false;
        }
    }
    private void mouse(TETile[][] world, int demonsoul, Stopwatch sw) {
//        ter.renderFrame(world);
        StdDraw.setPenColor(Color.white);
        int mx = (int) StdDraw.mouseX();
        int my = (int) StdDraw.mouseY();
        String description = world[mx][my].description();
        if (!description.equals(world[mx][my].description())) {
            StdDraw.clear(Color.black);
            StdDraw.text(4, 48, world[mx][my].description());
        }
        StdDraw.textLeft(4, 48, world[mx][my].description());
        StdDraw.textLeft(4, 47, "Has DemonSouls:" + helpersoul(demonsoul));
        StdDraw.textLeft(4, 46, "# of Demon Souls: " + demonsoul);
        String timeleft = String.valueOf(30 - Math.round(sw.elapsedTime()));
        StdDraw.textLeft(4, 45, "Time Left:" + timeleft);
        StdDraw.show();
    }

    private void playseed() {
        try {
            Reader savedFileSeed = new FileReader("SEED.txt");
            SEED = 0;
            int i;
            while ((i = savedFileSeed.read()) != -1) {
                SEED = SEED * 10 + Character.getNumericValue(i);
            }
            savedFileSeed.close();
        } catch (IOException e) {
            helperErrorExit("IOException happened! Could not save :/");
        }
    }
    private void actionplay() {
        try {
            Reader savedFileActions = new FileReader("history.txt");
            history = "";
            int k;
            while((k = savedFileActions.read()) != -1) {
                history = history + (char) k;
            }
            savedFileActions.close();
        } catch (IOException e) {
            helperErrorExit("IOException happened! Could not save :/");
        }
    }
    private void saveseedd() {
        try {
            FileWriter seedWriter = new FileWriter("SEED.txt");
            String seedinput = Long.toString(SEED);
            seedWriter.write(seedinput);
            seedWriter.close();
            FileWriter actionWriter = new FileWriter("history.txt");
            actionWriter.write(history);
            actionWriter.close();
            System.out.println("Saved seed: " + SEED);
            System.out.println("Saved actions: " + history);
        } catch (IOException e){
            helperErrorExit("IOException happened! Could not save :/");
        }
    }

    public static void helperErrorExit(String message) {
        if (message != null && !message.equals("")) {
            System.out.println(message);
            System.exit(0);
        }
    }
//    public void backstory() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
//
//        String back = ".../sp21-s1375/proj3/backstory.png";
//
//
//
//        StdDraw.setXscale(0, WIDTH);
//        StdDraw.setYscale(0, HEIGHT);
//        StdDraw.enableDoubleBuffering();
//
//        StdDraw.clear(Color.BLACK);
//        StdDraw.setPenColor(Color.WHITE);
//        StdDraw.picture(WIDTH / 2, HEIGHT / 2 , back);
//
//
//
//        Font font = new Font("Monaco", Font.ITALIC, 12);
//        StdDraw.setFont(font);
//        StdDraw.text(WIDTH / 2, HEIGHT * 5 / 6, "Press 'b' to navigate back to main menu.");
//        StdDraw.text(WIDTH / 2, HEIGHT * 5 / 6 - 2, "The story takes place in Taisho-era Japan. You are Tanjiro Kamado, wanting to save your sister");
//        StdDraw.text(WIDTH / 2, HEIGHT * 5 / 6 - 4, "Nezuko Kamado, as you seek a cure to Nezuko's demon curse. You  have joined the Demon Slayer Corps,");
//        StdDraw.text(WIDTH / 2, HEIGHT * 5 / 6 - 6, "that have been waging a secret war against demons for centuries as demons are former humans");
//        StdDraw.text(WIDTH / 2, HEIGHT * 5 / 6 - 8, "who sold their humanity in exchange for power and feed on humans. Demons can only be killed with ");
//        StdDraw.text(WIDTH / 2, HEIGHT * 5 / 6 - 10, "Sunsteel, injected with poison extracted from wisteria flowers, which you will be given beginning of the game.");
//        StdDraw.text(WIDTH / 2, HEIGHT * 5 / 6 - 12, "You will have three characters to choose from.");
//        StdDraw.text(WIDTH / 2, HEIGHT * 5 / 6 - 14, "Tanjiro: The standard character who will be given a Sunsteel Sword ");
//        StdDraw.text(WIDTH / 2, HEIGHT * 5 / 6 - 16, "to kill demons when seen and collect demon souls but you will have to look harder to find the demons");
//        StdDraw.text(WIDTH / 2, HEIGHT * 5 / 6 - 18, "Nezuko: You are a half demon who will not be given a sword,");
//        StdDraw.text(WIDTH / 2, HEIGHT * 5 / 6 - 20, "but will already be given demon souls and collect less");;
////        StdDraw.text(WIDTH / 2, HEIGHT * 5 / 6 - 22, "Demon: As a demon, your job is to collect as much demon souls without dying from the Demon Slayer Corps. You" );
////        StdDraw.text(WIDTH / 2, HEIGHT * 5 / 6 - 24, "will have a shorter time. However, you can eat the humans to increase your time span. Best of luck to you!");
//        StdDraw.text(WIDTH / 2, HEIGHT * 5 / 6 - 26, "Before the villagers start waking up and cannot move around freely, ");
//        StdDraw.text(WIDTH / 2, HEIGHT * 5 / 6 - 28, "you must collect as many demon souls as possible, or face consequences! ");
//        StdDraw.show();
//
//        while (true) {
//            if (StdDraw.hasNextKeyTyped()) {
//                char typeletter = Character.toLowerCase(StdDraw.nextKeyTyped());
//                if (typeletter == 't') {
//                    interactWithKeyboard();
//                    return;
//                }
//            }
//        }
//    }

    private void replaySavedGame() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        playseed();
        actionplay();
        loadAvatar();
        Room map = new Room( WIDTH, HEIGHT, SEED);
        Room.Position positionavatar = map.drawWorldRooms(avatar);
        TETile[][] gameworld = map.randWorld;
        Stopwatch sw = new Stopwatch();
        for (int i = 0; i < history.length(); i++) {
            mouse(gameworld, map.demonsoul, sw);
            char currAction = Character.toLowerCase(history.charAt(i));
            Room.ingame currAvatarWorld = map.move(currAction, gameworld, positionavatar, avatar);
            positionavatar = currAvatarWorld.avatarPosition;
            gameworld = currAvatarWorld.worldTiles;
            mouse(gameworld, map.demonsoul, sw);
            StdDraw.show();
            mouse(gameworld, map.demonsoul, sw);
            StdDraw.pause(400);
            mouse(gameworld, map.demonsoul, sw);
        }
//        String userChoice = drawReplayEndScreen();
//        if (userChoice.equals("Replay")) {
//            replaySavedGame();
//        } else if (userChoice.equals("Start Screen")) {
            interactWithKeyboard();
//        } else if (userChoice.equals("Quit")) {
            System.exit(0);
//        }

    }

//    private String drawReplayEndScreen() {
//
//        StdDraw.setCanvasSize(WIDTH * 10, HEIGHT * 10);
//        Font font = new Font("Monaco", Font.BOLD, 30);
//        StdDraw.setFont(font);
//
//        StdDraw.setXscale(0, WIDTH);
//        StdDraw.setYscale(0, HEIGHT);
//        StdDraw.enableDoubleBuffering();
//
//        String game = ".../proj3/gamebackground.png";
//
//
//
//        StdDraw.clear(Color.BLACK);
//        StdDraw.picture(WIDTH / 2, HEIGHT / 2 , game);
//
//        StdDraw.setPenColor(Color.WHITE);
//        StdDraw.text(WIDTH / 2, HEIGHT / 2 + HEIGHT / 4, "Replay Ended");
//        StdDraw.text(WIDTH / 2, HEIGHT / 2 + HEIGHT / 4 - 8, "Replay (R)");
//        StdDraw.text(WIDTH / 2, HEIGHT / 2 + HEIGHT / 4 - 12, "Return to Start Screen (S)");
//        StdDraw.text(WIDTH / 2, HEIGHT / 2 + HEIGHT / 4 - 20, "Quit (Q)");
//
//        StdDraw.show();
//
//        System.out.println("Waiting for user game choice");
//        while (true) {
//            if (StdDraw.hasNextKeyTyped()) {
//                char curr = Character.toLowerCase(StdDraw.nextKeyTyped());
//
//                if (curr == 'r') {
//                    return "Replay";
//                } else if (curr == 's') {
//                    return "Start Screen";
//                } else if (curr == 'q') {
//                    return "Quit";
//                }
//            }
//        }
//    }



    /**
     * Method used for autograding and testing your code. The input string will be a series
     * of characters (for example, "n123sswwdasdassadwas", "n123sss:q", "lwww". The engine should
     * behave exactly as if the user typed these characters into the engine using
     * interactWithKeyboard.
     *
     * Recall that strings ending in ":q" should cause the game to quite save. For example,
     * if we do interactWithInputString("n123sss:q"), we expect the game to run the first
     * 7 commands (n123sss) and then quit and save. If we then do
     * interactWithInputString("l"), we should be back in the exact same state.
     *
     * In other words, both of these calls:
     *   - interactWithInputString("n123sss:q")
     *   - interactWithInputString("lww")
     *
     * should yield the exact same world state as:
     *   - interactWithInputString("n123sssww")
     *
     //     * @param ithe input string to feed to your program
     * @return the 2D TETile[][] representing the state of the world
     */



    public TETile[][] interactWithInputString(String input) {
        input = input.toLowerCase();
        if (!(input.contains("l") || input.contains("n"))) {
            throw new IllegalArgumentException("invalid input");
        } else {
            if (input.contains("n")) {
                long seed = parseSeed(input);
                SEED = seed;
                history = parseAction(input);
            } else {
                playseed();
                actionplay();
                loadAvatar();
                history += parseAction(input);
            }
        } return inputStringHelper(SEED, history);
        // passed in as an argument, and return a 2D tile representation of the
        // world that would have been drawn if the same inputs had been given
        // to interactWithKeyboard().
        //
        // See proj3.byow.InputDemo for a demo of how you can make a nice clean interface
        // that works for many different input types.
//        long user = Long.parseLong((input.substring(1, input.length() - 1)));
//        Random RANDOM = new Random(user);
//        WIDTH = RANDOM.nextInt(60) + 20;
//        HEIGHT = RANDOM.nextInt(80) + 20;
//
//        Room(WIDTH, HEIGHT, user);
//
//        TETile[][] world = new TETile[WIDTH][HEIGHT];
//        fillBoardWithNothing(world);
//        byow.Core.Room.Position anchor = new Position(10, 15);
//        byow.Core.Room.drawWorld(world, anchor, RANDOM);
//        byow.Core.Room.fillWalls(world);
//
//
//
////        TETile[][] finalWorldFrame = world;
//        return world;
    }

    private long parseSeed(String input) {
        input = input.toLowerCase();
        int seedBegin = input.toLowerCase().indexOf("n") + 1;
        int seedEnd = input.toLowerCase().indexOf("s");
        long seed = Long.parseLong(input.substring(seedBegin, seedEnd));
        return seed;
    }

    private String parseAction(String input) {
        input = input.toLowerCase();
        String action = "";
        if (input.contains("l")) {
            int actionBegin = input.indexOf("l") + 1;
            for (int i = actionBegin; i < input.length(); i++) {
                if (input.charAt(i) == ':' && input.charAt(i + 1) == 'q') {
                    save = true;
                    return action;
                }
                action += input.charAt(i);
            }
        } else {
            if (input.length() == Long.toString(SEED).length() + 2) {
                return "";
            }
            int actionBegin = input.indexOf("s") + 1;
            for (int i = actionBegin; i < input.length(); i++) {
                if (input.charAt(i) == ':' && input.charAt(i + 1) == 'q') {
                    save = true;
                    return action;
                }
                action += input.charAt(i);
            }
        }
        return action;
    }


    private TETile[][] inputStringHelper(long S, String historylog) {
        Room gamescreen = new Room(WIDTH, HEIGHT, S);
        Room.Position positionavatar = gamescreen.drawWorldRooms(avatar);
        TETile[][] gameworld = gamescreen.randWorld;
        for (int i = 0; i < historylog.length(); i++) {
            int[] directions = directionMover(positionavatar, historylog.charAt(i));
            if (gameworld[directions[0]][directions[1]] == Tileset.WALL ||
                    gameworld[directions[0]][directions[1]] == Tileset.NOTHING) {
                continue;
            }
            gameworld[positionavatar.getx()][positionavatar.gety()] = Tileset.FLOOR;
            System.out.println("change avatar position");
            gameworld[directions[0]][directions[1]] = avatar;
            positionavatar.x = directions[0];
            positionavatar.y = directions[1];
        }
        if (save) {
            saveseedd();
            saveAvatar();
        }
//        gamescreen.ter.renderFrame(gameworld);
        return gameworld;
    }


    public static void fillBoardWithNothing(TETile[][] tiles) {
        int height = tiles[0].length;
        int width = tiles.length;
        for (int x = 0; x < width; x+= 1) {
            for (int y = 0; y < height; y += 1) {
                tiles[x][y] = Tileset.NOTHING;
            }
        }
    }









    // Create a horizontal hallway with randomly generated length and width
//    private static void horizontalHallway(TETile[][] board, Position position) {
//        int roomWidth = RANDOM.nextInt(20) + 2;
//        int roomHeight = RANDOM.nextInt(2) + 1;
//        for (int x = 0; x < roomWidth; x++) {
//            for (int y = 0; y < roomHeight; y++) {
//                board[x + position.x][y + position.y] = Tileset.FLOOR;
//            }
//        }
//    }
//
//    // Create a vertical hallway with randomly generated length and width
//    private static void verticalHallway(TETile[][] board, Position position) {
//        int roomWidth = RANDOM.nextInt(2) + 1;
//        int roomHeight = RANDOM.nextInt(20) + 2;
//        for (int x = 0; x < roomWidth; x++) {
//            for (int y = 0; y < roomHeight; y++) {
//                board[x + position.x][y + position.y] = Tileset.FLOOR;
//            }
//        }
//    }

    //    have randomly located rooms that are of random size

//    public static void makeRoom(TETile[][] board) {
//        int numRoom;
//        Room r;
//        int w = RandomUtils.(seed);
//        int h;
//        Position p;
//        Random r = new Random(SEED);
//        for (int i = 0; i < numRoom; i ++) {
//
//        }
//
//        Room room = new Room(width, height, x, y);
////        if case if room overlaps with new room made
//        ArrayList<> positions = new ArrayList<Position>();
//        for (int i = 0; i < w; i ++) {
//            for (int j = 0; j < h; j++) {
//                Position newPos = new Position(i + p.x, j + p.y);
//                positions.add(newPos);
//            }
//        }
//    }

    // Fill in the world with shapes






//    public void result(String r) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
//        if (r == "win") {
//            win();
//        } else {
//            lose();
//        }
//    }

//    private void win() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
//
//        String winscreen = ".../proj3/win.png";
//
//
//
//        StdDraw.setXscale(0, WIDTH);
//        StdDraw.setYscale(0, HEIGHT);
//        StdDraw.enableDoubleBuffering();
//
//        StdDraw.clear(Color.BLACK);
//        StdDraw.setPenColor(Color.WHITE);
//
//        StdDraw.picture(WIDTH / 2, HEIGHT / 2 , winscreen);
//        StdDraw.text(WIDTH / 2, HEIGHT * 3 / 4, "Congratulations! You won!");
//        StdDraw.text(WIDTH / 2, HEIGHT * 3 / 4 - 4, "You have collected the demon souls");
//        StdDraw.text(WIDTH / 2, HEIGHT * 3 / 4 - 6, "without dying");
//        StdDraw.text(WIDTH / 2, HEIGHT * 3 / 4 - 10, "Press 'm' to return to main menu.");
//        StdDraw.show();
//
//        while (true) {
//            if (StdDraw.hasNextKeyTyped()) {
//                char curr = Character.toLowerCase(StdDraw.nextKeyTyped());
//                if (curr == 'm') {
//                    interactWithKeyboard();
//                    return;
//                }
//            }
//        }
//    }

//    private void lose() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
////        File lost = new File("/Users/claireyoon/Desktop/61B/sp21-s1375/proj3/lost.mp3");
////        AudioInputStream playtheme = AudioSystem.getAudioInputStream(theme);
////
////        AudioFormat play = playtheme.getFormat();
////        DataLine.Info info = new DataLine.Info(Clip.class, play);
////
////        Clip audioClip = (Clip) AudioSystem.getLine(info);
////        audioClip.open(playtheme);
////        audioClip.start();
//
//        String losescreen = ".../proj3/lose.png";
//
//
//
//        StdDraw.setXscale(0, WIDTH);
//        StdDraw.setYscale(0, HEIGHT);
//        StdDraw.enableDoubleBuffering();
//
//        StdDraw.clear(Color.BLACK);
//        StdDraw.setPenColor(Color.WHITE);
//
//        StdDraw.picture(WIDTH / 2, HEIGHT / 2 , losescreen);
//        StdDraw.text(WIDTH / 2, HEIGHT * 3 / 4, "Sorry, the sun is up and have lost..");
//        StdDraw.text(WIDTH / 2, HEIGHT * 3 / 4 - 4, "Demons are running wild in the village");
//        StdDraw.text(WIDTH / 2, HEIGHT * 3 / 4 - 6, "ahhhh...");
////insert people screaming and blood rushing sounds
//        StdDraw.text(WIDTH / 2, HEIGHT * 3 / 4 - 10, "Press 'm' to return to main menu.");
//        StdDraw.show();
//
//        while (true) {
//            if (StdDraw.hasNextKeyTyped()) {
//                char curr = Character.toLowerCase(StdDraw.nextKeyTyped());
//                if (curr == 'm') {
//                    interactWithKeyboard();
//                    return;
//                }
//            }
//        }
//    }
    private void saveAvatar() {
        if (avatar == Tileset.AVATAR) {
            try {
                FileWriter avatarWriter = new FileWriter("SavedAvatar.txt");
                avatarWriter.write("basic");
                avatarWriter.close();
                System.out.println("Saved avatar: Avatar");
            } catch (IOException e) {
                helperErrorExit("IOException happened! Could not save :/");
            }
        } else if (avatar == Tileset.NEZUKO) {
            try {
                FileWriter avatarWriter = new FileWriter("SavedAvatar.txt");
                avatarWriter.write("Nezuko");
                avatarWriter.close();
                System.out.println("Saved avatar: NEZUKO");
            } catch (IOException e) {
                helperErrorExit("IOException happened! Could not save :/");
            }
        }

    }

    public static void main(String[] args) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        Engine engine = new Engine();
        engine.interactWithKeyboard();
        engine.interactWithKeyboard();

//        try {
//            URL theme = new URL("/Users/claireyoon/Desktop/61B/sp21-s1375/proj3/gametheme.wav" );
//            AudioClip ac = Applet.newAudioClip(theme);
//            ac.play();
//
//
//            System.in.read();
//        } catch (Exception e) {
//            System.out.println(e);
//        }
    }





}

