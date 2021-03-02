package capers;

import java.io.File;
import java.io.IOException;
import static capers.Utils.*;

/** A repository for Capers 
 * @author Claire Yoon
 * The structure of a Capers Repository is as follows:
 *
 * .capers/ -- top level folder for all persistent data in your lab12 folder
 *    - dogs/ -- folder containing all of the persistent data for dogs
 *    - story -- file containing the current story
 *
 * TODO: change the above structure if you do something different.
 */
public class CapersRepository {
    /** Current Working Directory. */
    static final File CWD = new File(System.getProperty("user.dir"));

    /** Main metadata folder. */
    static final File CAPERS_FOLDER = Utils.join(CWD, ".capers");
    static final File STORY = Utils.join(CAPERS_FOLDER, "story.txt");

    /**
     * Does required filesystem operations to allow for persistence.
     * (creates any necessary folders or files)
     * Remember: recommended structure (you do not have to follow):
     *
     * .capers/ -- top level folder for all persistent data in your lab12 folder
     *    - dogs/ -- folder containing all of the persistent data for dogs
     *    - story -- file containing the current story
     */
    public static void setupPersistence() {
        CAPERS_FOLDER.mkdir();
        try {
            STORY.createNewFile();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
        File dog1= join(CAPERS_FOLDER, "dogs");
        dog1.mkdir();
    }

    /**
     * Appends the first non-command argument in args
     * to a file called `story` in the .capers directory.
     * @param text String of the text to be appended to the story
     */
    public static void writeStory(String text) {
        String prev = readContentsAsString(STORY);
        String storystring = prev + text + "\n";
        Utils.writeContents(STORY, storystring);
        System.out.println(readContentsAsString(STORY));
    }

    /**
     * Creates and persistently saves a dog using the first
     * three non-command arguments of args (name, breed, age).
     * Also prints out the dog's information using toString().
     */
    public static void makeDog(String name, String breed, int age) {
        Dog dog1 = new Dog(name, breed, age);
        dog1.saveDog();
        Dog deserialdog = Dog.fromFile(name);
        System.out.print(deserialdog.toString());
    }

    /**
     * Advances a dog's age persistently and prints out a celebratory message.
     * Also prints out the dog's information using toString().
     * Chooses dog to advance based on the first non-command argument of args.
     * @param name String name of the Dog whose birthday we're celebrating.
     */
    public static void celebrateBirthday(String name) {
        File birthdog = Utils.join(Dog.DOG_FOLDER, name);
        Dog bdog = Utils.readObject(birthdog, Dog.class);
        bdog.haveBirthday();
        bdog.saveDog();
    }
}
