package com.mingtai.base.util;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Random;

public class StringUtils {
    public static final String[] EMPTY_STRING = new String[0];

    /**
     * @param input
     * @param separator
     * @return
     */
    public static String[] split(String input, String separator) {
        if (input == null)
            return null;
        if (input.equals(""))
            return EMPTY_STRING;
        if ((separator == null) || ("".equals(separator))) {
            return new String[]{input};
        }
        int cursor = 0;
        int lastPos = 0;
        ArrayList list = new ArrayList();
        while ((cursor = input.indexOf(separator, cursor)) != -1) {
            if (cursor > lastPos) {
                String token = input.substring(lastPos, cursor);
                list.add(token);
            }
            lastPos = cursor + separator.length();
            cursor = lastPos;
        }
        if (lastPos < input.length()) {
            list.add(input.substring(lastPos));
        }
        return (String[]) list.toArray(new String[list.size()]);
    }

    /**
     * @param input
     * @param seperator
     * @return
     */
    public static String[] splitStrictly(String input, String seperator) {
        if (input == null)
            return null;
        if (input.equals("")) {
            return EMPTY_STRING;
        }
        ArrayList list = new ArrayList();
        int cursor = 0;
        int beginPos = 0;
        while (true) {
            cursor = input.indexOf(seperator, beginPos);
            if (cursor == -1) break;
            list.add(input.substring(beginPos, cursor));
            beginPos = cursor + 1;
        }
        list.add(input.substring(beginPos));
        return (String[]) list.toArray(new String[list.size()]);
    }

    public static boolean isBlank(String input) {
        if ((input == null) || ("".equals(input)))
            return true;
        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            if ((c != ' ') && (c != '\t') && (c != '\r') && (c != '\n'))
                return false;
        }
        return true;
    }

    public static String trimTail(StringBuffer sb, char tail) {
        if ((sb.length() > 0) && (sb.charAt(sb.length() - 1) == tail))
            sb.deleteCharAt(sb.length() - 1);
        return sb.toString();
    }

    public static String replaceOnce(String template, String placeholder, String replacement) {
        int pos = template.indexOf(placeholder);
        if (pos < 0) {
            return template;
        }
        return new StringBuffer(template.substring(0, pos)).append(replacement).append(template.substring(pos + placeholder.length())).toString();
    }

    public static String getNotNull(String input) {
        return input == null ? "" : input;
    }

    public static String pruneToFit(String str, String encoding, int maxLength) throws UnsupportedEncodingException {
        int nowLength = str.getBytes(encoding).length;
        if (nowLength <= maxLength) {
            return str;
        }
        int pruneCnt = (nowLength - maxLength) / 3 + 1;
        String s = str.substring(0, str.length() - pruneCnt);

        if (s.getBytes(encoding).length <= maxLength) {
            return s;
        }
        return pruneToFit(s, encoding, maxLength);
    }

    public static String join(int[] array, String separator) {
        if (array != null) {
            if (separator == null) {
                separator = "";
            }
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < array.length; i++) {
                sb.append(array[i]);
                if (i != array.length - 1) {
                    sb.append(separator);
                }
            }
            return sb.toString();
        }
        return "";
    }

    
    public static String join(long[] array, String separator) {
        if (array != null) {
            if (separator == null) {
                separator = "";
            }
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < array.length; i++) {
                sb.append(array[i]);
                if (i != array.length - 1) {
                    sb.append(separator);
                }
            }
            return sb.toString();
        }
        return "";
    }

    public static String join(Object[] array, String separator) {
        if (array != null) {
            if (separator == null) {
                separator = "";
            }
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < array.length; i++) {
                sb.append(array[i]);
                if (i != array.length - 1) {
                    sb.append(separator);
                }
            }
            return sb.toString();
        }
        return "";
    }

    public static String join(long[] array) {
        return join(array, ",");
    }

    public static String join(Object[] array) {
        return join(array, ",");
    }

    public static String printStackTrace(Throwable t) {
        StringWriter sw = new StringWriter();
        t.printStackTrace(new PrintWriter(sw));
        return sw.getBuffer().toString();
    }

    public static String makeAllWordFirstLetterUpperCase(String sqlName) {
        String[] strs = sqlName.toLowerCase().split("_");
        String result = "";
        String preStr = "";
        for (int i = 0; i < strs.length; i++) {
            if (preStr.length() == 1) {
                result += strs[i];
            } else {
                result += capitalize(strs[i]);
            }
            preStr = strs[i];
        }
        return result;
    }

    public static String makeAllWordFirstLetterLowerCase(String sqlName) {
        String[] strs = sqlName.toLowerCase().split("_");
        String result = "";
        String preStr = "";
        for (int i = 0; i < strs.length; i++) {
            if (preStr.length() == 1) {
                result += strs[i];
            } else {
                if (i == 0)
                    result += strs[i];
                else
                    result += capitalize(strs[i]);
            }
            preStr = strs[i];
        }
        return result;
    }

    public static String replace(String inString, String oldPattern, String newPattern) {
        if (inString == null) {
            return null;
        }
        if (oldPattern == null || newPattern == null) {
            return inString;
        }

        StringBuffer sbuf = new StringBuffer();
        // output StringBuffer we'll build up
        int pos = 0; // our position in the old string
        int index = inString.indexOf(oldPattern);
        // the index of an occurrence we've found, or -1
        int patLen = oldPattern.length();
        while (index >= 0) {
            sbuf.append(inString.substring(pos, index));
            sbuf.append(newPattern);
            pos = index + patLen;
            index = inString.indexOf(oldPattern, pos);
        }
        sbuf.append(inString.substring(pos));

        // remember to append any characters to the right of a match
        return sbuf.toString();
    }


    public static String capitalize(String str) {
        return changeFirstCharacterCase(str, true);
    }


    public static String uncapitalize(String str) {
        return changeFirstCharacterCase(str, false);
    }

    /**
     * @param str
     * @param capitalize 是否大写
     * @return
     */
    public static String changeFirstCharacterCase(String str, boolean capitalize) {
        if (str == null || str.length() == 0) {
            return str;
        }
        StringBuffer buf = new StringBuffer(str.length());
        if (capitalize) {
            buf.append(Character.toUpperCase(str.charAt(0)));
        } else {
            buf.append(Character.toLowerCase(str.charAt(0)));
        }
        buf.append(str.substring(1));
        return buf.toString();
    }

    private static final Random RANDOM = new Random();

    public static String randomNumeric(int count) {
        return random(count, false, true);
    }

    public static String random(int count, boolean letters, boolean numbers) {
        return random(count, 0, 0, letters, numbers);
    }

    public static String random(int count, int start, int end, boolean letters, boolean numbers) {
        return random(count, start, end, letters, numbers, null, RANDOM);
    }

    public static String random(int count, int start, int end, boolean letters,
                                boolean numbers, char[] chars, Random random) {
        if (count == 0) {
            return "";
        } else if (count < 0) {
            throw new IllegalArgumentException(
                    "Requested random string length " + count
                            + " is less than 0.");
        }
        if ((start == 0) && (end == 0)) {
            end = 'z' + 1;
            start = ' ';
            if (!letters && !numbers) {
                start = 0;
                end = Integer.MAX_VALUE;
            }
        }

        char[] buffer = new char[count];
        int gap = end - start;

        while (count-- != 0) {
            char ch;
            if (chars == null) {
                ch = (char) (random.nextInt(gap) + start);
            } else {
                ch = chars[random.nextInt(gap) + start];
            }
            if ((letters && Character.isLetter(ch))
                    || (numbers && Character.isDigit(ch))
                    || (!letters && !numbers)) {
                if (ch >= 56320 && ch <= 57343) {
                    if (count == 0) {
                        count++;
                    } else {
                        // low surrogate, insert high surrogate after putting it
                        // in
                        buffer[count] = ch;
                        count--;
                        buffer[count] = (char) (55296 + random.nextInt(128));
                    }
                } else if (ch >= 55296 && ch <= 56191) {
                    if (count == 0) {
                        count++;
                    } else {
                        // high surrogate, insert low surrogate before putting
                        // it in
                        buffer[count] = (char) (56320 + random.nextInt(128));
                        count--;
                        buffer[count] = ch;
                    }
                } else if (ch >= 56192 && ch <= 56319) {
                    // private high surrogate, no effing clue, so skip it
                    count++;
                } else {
                    buffer[count] = ch;
                }
            } else {
                count++;
            }
        }
        return new String(buffer);
    }

    /**
     * Convert a name in camelCase to an underscored name in lower case.
     * Any upper case letters are converted to lower case with a preceding underscore.
     *
     * @param name the string containing original name
     * @return the converted name
     */
    public static String toUnderscoreName(String name, final String profix) {
        if (name == null) return null;
        String filteredName = name;
        //去掉多余的前缀
        if (!"".equals(profix) && profix != null) {
            filteredName = name.replaceFirst(profix, "");
        }
        if (filteredName.indexOf("_") >= 0 && filteredName.equals(filteredName.toUpperCase())) {
            filteredName = filteredName.toLowerCase();
        }
        if (filteredName.indexOf("_") == -1 && filteredName.equals(filteredName.toUpperCase())) {
            filteredName = filteredName.toLowerCase();
        }

        StringBuffer result = new StringBuffer();
        if (filteredName != null && filteredName.length() > 0) {
            result.append(filteredName.substring(0, 1).toLowerCase());
            for (int i = 1; i < filteredName.length(); i++) {
                String preString = filteredName.substring(i - 1, i);
                String s = filteredName.substring(i, i + 1);
                if (s.equals("_")) {
                    result.append("_");
                    continue;
                }
                if (preString.equals("_")) {
                    result.append(s.toLowerCase());
                    continue;
                }
                if (s.equals(s.toUpperCase())) {
                    result.append("_");
                    result.append(s.toLowerCase());
                } else {
                    result.append(s);
                }
            }
        }
        return result.toString();
    }

    public static String replaceBlank(String string) {
        return string.replaceAll("\\s*|\t*|\r|\n", "");
    }

    public static void main(String[] args) {
        String test = "/login.html,\n" +
                "        /login.do,\n" +
                "        /verifyCode.do";
        System.out.println(test);
        test = StringUtils.replaceBlank(test);
        System.out.println(test);
    }
}