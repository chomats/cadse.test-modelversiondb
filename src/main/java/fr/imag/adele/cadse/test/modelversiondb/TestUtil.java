package fr.imag.adele.cadse.test.modelversiondb;

import static org.junit.Assert.*;

public class TestUtil {

//	/**
//     * Asserts that two doubles are equal. If they are not an AssertionFailedError is thrown with the given message.
//     */
//    static public void assertEquals(String message, double expected, double actual) {
//        if (Double.compare(expected, actual) != 0) {
//            fail(formatEqualsMessage(message, new Double(expected), new Double(actual)));
//        }
//    }

    static String formatEqualsMessage(String message, Object expected, Object actual) {
        String formatted = "";
        if (message != null) {
            formatted = message + " ";
        }
        return formatted + "expected:<" + expected + "> but was:<" + actual + ">";
    }

    static String formatNotEqualsMessage(String message, Object o1, Object o2) {
        String formatted = "";
        if (message != null) {
            formatted = message + " ";
        }
        return formatted + "o1:<" + o1 + "> is equals to o2:<" + o2 + ">";
    }

    static String formatContainsMessage(String message, Object[] array, Object txt) {
        String formatted = "";
        if (message != null) {
            formatted = message + " ";
        }

        String arr = null;
        for (int i = 0; i < array.length; i++) {
            if (arr == null) {
                arr = "[" + array[i];
            } else {
                arr += "," + array[i];
            }
        }
        arr += "]";

        return formatted + "array:" + arr + " does not contains:<" + txt + ">";
    }

    static public void assertNotEquals(String message, Object o1, Object o2) {
        if (o1.equals(o2)) {
            fail(formatNotEqualsMessage(message, o1, o2));
        }
    }

    static public void assertContains(String message, String[] array, String txt) {
        for (int i = 0; i < array.length; i++) {
            if (array[i].equals(txt)) {
                return;
            }
        }
        fail(formatContainsMessage(message, array, txt));
    }

    static public void assertContains(String message, byte[] array, int txt) {
        for (int i = 0; i < array.length; i++) {
            if (array[i] == txt) {
                return;
            }
        }
        Byte[] bytes = new Byte[array.length];
        for (int i = 0; i < array.length; i++) {
            bytes[i] = new Byte(array[i]);
        }
        fail(formatContainsMessage(message, bytes, new Integer(txt)));
    }

    static public void assertContains(String message, short[] array, int txt) {
        for (int i = 0; i < array.length; i++) {
            if (array[i] == txt) {
                return;
            }
        }
        Short[] bytes = new Short[array.length];
        for (int i = 0; i < array.length; i++) {
            bytes[i] = new Short(array[i]);
        }
        fail(formatContainsMessage(message, bytes, new Integer(txt)));
    }

    static public void assertContains(String message, int[] array, int txt) {
        for (int i = 0; i < array.length; i++) {
            if (array[i] == txt) {
                return;
            }
        }
        Integer[] bytes = new Integer[array.length];
        for (int i = 0; i < array.length; i++) {
            bytes[i] = new Integer(array[i]);
        }
        fail(formatContainsMessage(message, bytes, new Integer(txt)));
    }

    static public void assertContains(String message, long[] array, long txt) {
        for (int i = 0; i < array.length; i++) {
            if (array[i] == txt) {
                return;
            }
        }
        Long[] bytes = new Long[array.length];
        for (int i = 0; i < array.length; i++) {
            bytes[i] = new Long(array[i]);
        }
        fail(formatContainsMessage(message, bytes, new Long(txt)));
    }

    static public void assertContains(String message, float[] array, float txt) {
        for (int i = 0; i < array.length; i++) {
            if (array[i] == txt) {
                return;
            }
        }
        Float[] bytes = new Float[array.length];
        for (int i = 0; i < array.length; i++) {
            bytes[i] = new Float(array[i]);
        }
        fail(formatContainsMessage(message, bytes, new Float(txt)));
    }

    static public void assertContains(String message, double[] array, double txt) {
        for (int i = 0; i < array.length; i++) {
            if (array[i] == txt) {
                return;
            }
        }
        Double[] bytes = new Double[array.length];
        for (int i = 0; i < array.length; i++) {
            bytes[i] = new Double(array[i]);
        }
        fail(formatContainsMessage(message, bytes, new Double(txt)));
    }

    static public void assertContains(String message, char[] array, char txt) {
        for (int i = 0; i < array.length; i++) {
            if (array[i] == txt) {
                return;
            }
        }
        Character[] bytes = new Character[array.length];
        for (int i = 0; i < array.length; i++) {
            bytes[i] = new Character(array[i]);
        }
        fail(formatContainsMessage(message, bytes, new Character(txt)));
    }
}
