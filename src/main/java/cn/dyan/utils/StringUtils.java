
package cn.dyan.utils;

import org.slf4j.Logger;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A utility class for string manipulation.
 */
final public class StringUtils {

    private static final Logger log = org.slf4j.LoggerFactory.getLogger(StringUtils.class);
    private static final String EMPTY = "";

    // Pattern to match consecutive characters that repeat three or more times.
    private static final String SPLIT_REGEX = "(.)\\1{2,}";

    // Pattern to match lowercase letters.
    private static final String LOWERCASE_REGEX = "^[a-z]+$";

    /**
     * Checks if a given string consists only of lowercase letters.
     *
     * @param str The input string to be examined.
     * @return Returns true if the string contains only lowercase letters; otherwise returns false.
     */
    public static boolean isLowerCase(String str) {
        Objects.requireNonNull(str, "Input string must not be null");
        return str.matches(LOWERCASE_REGEX);
    }

    /**
     * Removes consecutive characters that repeat three or more times in a string.
     *
     * @param str The string to process.
     * @return Returns the processed string with no consecutive characters repeating three or more times.
     */
    public static String removeConsecutiveDuplicates(String str) {
        return replaceConsecutiveDuplicates(str, false);
    }

    /**
     * Replaces consecutive characters repeating three or more times in a string with the preceding character according to alphabetical order.
     * If the repeating character is 'a', it is replaced with an empty string.
     *
     * @param str The string to process.
     * @return Returns the processed string with replacements made as per the rule.
     */
    public static String replaceConsecutiveDuplicates(String str) {
        return replaceConsecutiveDuplicates(str, true);
    }

    /**
     * An internal method to handle the actual replacement or removal of consecutive duplicate characters.
     *
     * @param str The input string to process.
     * @param isNeedToReplace A flag indicating whether to replace duplicates (true) or remove them (false).
     * @return Returns the processed string after handling consecutive duplicates.
     */
    private static String replaceConsecutiveDuplicates(String str, boolean isNeedToReplace) {
        // Ensures the input string is all lowercase.
        if(!isLowerCase(str)){
            throw new IllegalArgumentException("The input string must be in all lowercase.");
        }
        // Continuously processes the string until there are no more sequences of repeating characters three or more times.
        final Pattern pattern = Pattern.compile(SPLIT_REGEX);
        while (true) {
            Matcher matcher = pattern.matcher(str);
            // Breaks the loop if no matching sequence of repeating characters is found.
            if (!matcher.find()) {
                break;
            }
            // Acts accordingly based on whether to replace or remove the duplicates.
            if(isNeedToReplace) {
                char c = matcher.group().charAt(0);
                String replacement = getReplacementChar(c);
                // Replaces the repeating characters.
                str = String.join("", str.substring(0, matcher.start()), replacement, str.substring(matcher.end()));
                log.debug("{},{} is replaced by {}", str, matcher.group(), replacement);
            }else {
                // Removes the repeating characters.
                str = String.join("", str.substring(0, matcher.start()), str.substring(matcher.end()));
                log.debug("{},{} is removed", str, matcher.group());
            }
        }
        return str;
    }

    /**
     * Gets the replacement character for a given repeating character.
     * If the repeating character is 'a', an empty string is returned; otherwise, the previous letter in alphabetical order is returned.
     *
     * @param c The repeating character.
     * @return Returns the replacement character.
     */
    private static String getReplacementChar(char c) {
        if(c == 'a') {
            return EMPTY;
        }else {
            // Replaces with the preceding letter.
            return String.valueOf((char) ((c - 'a' - 1) + 'a'));
        }
    }
}