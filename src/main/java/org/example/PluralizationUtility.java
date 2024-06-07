package org.example;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.common.collect.ImmutableSet;

public class PluralizationUtility {

    private static final Set<String> UNPLURALIZABLES = ImmutableSet.of(
            "accommodation", "advice", "alms", "aircraft", "aluminum",
            "bison", "binoculars", "bourgeois", "breadfruit", "cannons", "caribou",
            "cattle", "chassis", "chinos", "clippers", "clothing", "cod",
            "corps", "crossroads", "deer", "dice", "doldrums", "dungarees",
            "elks", "eyeglasses", "fish", "flares", "flours", "food", "fruits",
            "furniture", "gallows", "goldfish", "grapefruit", "greenfly", "grouses",
            "haddock", "halibuts", "head", "headquarters", "helps", "homework",
            "hovercrafts", "ides", "insignias", "jeans", "knickers", "knowledge",
            "kudos", "leggings", "lego", "legos", "luggage", "moose", "monkfish",
            "mullet", "news", "offspring", "oxygen", "pants", "pyjamas", "pliers",
            "police", "premises", "reindeer", "rendezvous", "salmon", "scissors",
            "shambles", "barracks", "sheep", "shellfish", "shorts", "shrimp",
            "smithereens", "spacecraft", "squid", "starfruit", "stones", "sugars",
            "swine", "tongs", "trousers", "trout", "tuna", "tweezers", "you",
            "wheat", "whitebait", "equipment", "information", "rice", "money",
            "species", "series", "interests", "services", "ems", "rental plates",
            "building materials", "confectionery accessories", "music instruments"
    );

    private static final Map<Pattern, String> PLURAL_RULES = new HashMap<>() {{
        put(Pattern.compile("(.*cafe)$"), "$1s");
        put(Pattern.compile("(.+ffe?)$"), "$1s");
        put(Pattern.compile("(.*)fe?$"), "$1ves");
        put(Pattern.compile("(.*)man$"), "$1men");
        put(Pattern.compile("(.*)is$"), "$1es");
        put(Pattern.compile("(.+[aeiou]y)$"), "$1s");
        put(Pattern.compile("(.+[^aeiou])y$"), "$1ies");
        put(Pattern.compile("(.+z)$"), "$1zes");
        put(Pattern.compile("(.+)([ei])x$"), "$1ices");
        put(Pattern.compile("(.+(s|x|sh|ch))(?<!is)$"), "$1es");
    }};

    private static final Map<String, String> EXCEPTIONS = new HashMap<>() {{
        put("mouse", "mice");
        put("louse", "lice");
        put("die", "dice");
        put("ox", "oxen");
        put("child", "children");
        put("person", "people");
        put("penny", "pence");
        put("foot", "feet");
        put("tooth", "teeth");
        put("goose", "geese");
        put("coffee", "coffees");
        put("roof", "roofs");
        put("proof", "proofs");
        put("datum", "data");
        put("memorandum", "memoranda");
        put("bacterium", "bacteria");
        put("stratum", "strata");
        put("stimulus", "stimuli");
        put("focus", "foci");
        put("radius", "radii");
        put("locus", "loci");
        put("fungus", "fungi");
        put("nucleus", "nuclei");
        put("terminus", "termini");
        put("bacillus", "bacilli");
        put("narcissus", "narcissi");
    }};

    private final Map<String, String> inMemoryPlurals = new HashMap<>();

    public String pluralize(String word) {
        String key = formatKey(word);
        if (inMemoryPlurals.containsKey(key)) {
            return inMemoryPlurals.get(key);
        }
        return cacheAndReturn(key, applyPluralizationRules(key));
    }

    private String formatKey(String word) {
        return word.toLowerCase().replace('_', ' ');
    }

    private String applyPluralizationRules(String key) {
        String[] parts = splitParts(key);
        String baseWord = parts[0];
        String secondPart = parts[1];

        if (UNPLURALIZABLES.contains(baseWord)) {
            return baseWord + secondPart;
        }
        if (EXCEPTIONS.containsKey(baseWord)) {
            return EXCEPTIONS.get(baseWord) + secondPart;
        }
        for (Map.Entry<Pattern, String> entry : PLURAL_RULES.entrySet()) {
            Matcher matcher = entry.getKey().matcher(baseWord);
            if (matcher.matches()) {
                return matcher.replaceFirst(entry.getValue()) + secondPart;
            }
        }
        return baseWord + "s" + secondPart; // DEFAULT_RULE
    }

    private String[] splitParts(String key) {
        String[] parts = key.split(" of ", 2);
        String secondPart = (parts.length > 1) ? " of " + parts[1] : "";
        return new String[]{parts[0], secondPart};
    }

    private String cacheAndReturn(String key, String result) {
        result = result.replace(' ', '_');
        inMemoryPlurals.put(key, result);
        return result;
    }
}
