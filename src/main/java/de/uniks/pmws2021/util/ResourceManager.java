package de.uniks.pmws2021.util;

import de.uniks.pmws2021.model.AttackStat;
import de.uniks.pmws2021.model.DefenceStat;
import de.uniks.pmws2021.model.Hero;
import de.uniks.pmws2021.model.HeroStat;
import javafx.scene.image.Image;
import org.fulib.yaml.YamlIdMap;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class ResourceManager {

    // Choose your own SAVED_HEROES_FOLDER_NAME and SAVED_HEROES_FILE_NAME
    // Add the saved-hero-folder-name to your .gitignore

    private static final Path HERO_FOLDER = Path.of("saves");
    private static final Path HERO_FILE = HERO_FOLDER.resolve("heroes.yaml");


    public static Image loadIcon(String className) {
        return new Image(ResourceManager.class.getResource("../images/" + className + ".png").toString());
    }

    public static String loadStyle(String styleName) {
        return new ResourceManager().getClass().getResource("../styles/" + styleName + ".css").toExternalForm();
    }

    // static constructor magic to create the file if absent
    static {
        try {
            if (!Files.isDirectory(HERO_FOLDER)) {
                Files.createDirectory(HERO_FOLDER);
            }
            if (!Files.exists(HERO_FILE)) {
                Files.createFile(HERO_FILE);
            }
        } catch (Exception e) {
            System.err.println("Error while loading " + HERO_FILE);
            e.printStackTrace();
        }
    }

    public static List<Hero> loadAllHeroes() {
        List<Hero> result = new ArrayList<>();

        try {
            // try to read heroList from File
            String heroesString = Files.readString(HERO_FILE);

            // parse yaml-string to YamlIdMap
            YamlIdMap yamlIdMap = new YamlIdMap(Hero.class.getPackageName());

            // decode map
            yamlIdMap.decode(heroesString);

            // map the decoded yaml data to real java objects and return list of heros
            for (Object object : yamlIdMap.getObjIdMap().values()) {
                if (object instanceof Hero) {
                    HeroStat stat1 = ((Hero) object).getStats().get(0);
                    HeroStat stat2 = ((Hero) object).getStats().get(1);

                    if (stat1 instanceof AttackStat) {
                        ((Hero) object).withoutStats(stat1, stat2);
                        ((Hero) object).withStats(new AttackStat().setCost(stat1.getCost()).setValue(stat1.getValue()).setHero(stat1.getHero()).setLevel(stat1.getLevel()), new DefenceStat().setCost(stat2.getCost()).setValue(stat2.getValue()).setHero(stat2.getHero()).setLevel(stat2.getLevel()));
                    } else if (stat1 instanceof DefenceStat) {
                        ((Hero) object).withoutStats(stat1, stat2);
                        ((Hero) object).withStats(new AttackStat().setCost(stat2.getCost()).setValue(stat2.getValue()).setHero(stat2.getHero()).setLevel(stat2.getLevel()), new DefenceStat().setCost(stat1.getCost()).setValue(stat1.getValue()).setHero(stat1.getHero()).setLevel(stat1.getLevel()));
                    }
                    result.add((Hero) object);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static void saveHero(Hero victor) {
        try {
            // load all existing heroes
            List<Hero> oldHeroes = loadAllHeroes();

            // delete existing hero with the same name as the victor
            oldHeroes.removeIf(oldHero -> oldHero.getName().equals((victor.getName())));

            // add copy of victor to list
            Hero toSave = new Hero(victor).getCopy();
            oldHeroes.add(toSave);

            // serialize as yaml
            YamlIdMap yamlIdMap = new YamlIdMap(Hero.class.getPackageName());
            yamlIdMap.discoverObjects(oldHeroes);
            String yamlData = yamlIdMap.encode();

            // save as .yaml
            Files.writeString(HERO_FILE, yamlData);
        }
        catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error while saving Hero: File -> " + HERO_FILE);
        }
    }

    public static void deleteHero(Hero selectedHero) {
        try {
            // load all existing heroes
            List<Hero> oldHeroes = loadAllHeroes();

            // delete existing hero with the same name as the selectedHero
            oldHeroes.removeIf(heroToDelete -> heroToDelete.getName().equals(selectedHero.getName()));

            // serialize as yaml
            YamlIdMap yamlIdMap = new YamlIdMap(Hero.class.getPackageName());
            yamlIdMap.discoverObjects(oldHeroes);
            String yamlData = yamlIdMap.encode();

            // save as .yaml
            Files.writeString(HERO_FILE, yamlData);
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error while deleting Hero: File -> " + selectedHero.getName());
        }
    }
}