package app;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

import org.junit.jupiter.api.Test;

class MainTest {

    private String runMainWithInput(String input) {
        InputStream originalIn = System.in;
        PrintStream originalOut = System.out;
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        System.setOut(new PrintStream(buffer));
        try {
            Main.main(new String[0]);
        } finally {
            System.setIn(originalIn);
            System.setOut(originalOut);
        }
        return buffer.toString();
    }

    @Test
    void exitsImmediatelyOnZero() {
        String text = runMainWithInput("0\n");
        assertTrue(text.contains("Bye!"));
    }

    @Test
    void loadsAllSampleTrips() {
        String text = runMainWithInput("1\n0\n");
        long tripLines = text.lines()
        .map(l -> l.startsWith("> ") ? l.substring(2) : l)
        .filter(l -> l.matches(
                "\\d+\\. (Recreation|Excursion|Treatment|Shopping|Cruise): .*"))
        .count();
        assertEquals(6, tripLines);
        for (String city : new String[] {"Barcelona", "Antalya", "Hurghada",
            "Prague", "Truskavets", "Warsaw"}) {
            assertTrue(text.contains(city), "Missing sample trip to " + city);
        }
    }

    @Test
    void sampleDataCoversEveryTripType() {
        String text = runMainWithInput("1\n0\n");
        for (String type : new String[] {"Recreation:", "Excursion:", "Treatment:",
            "Shopping:", "Cruise:"}) {
            assertTrue(text.contains(type), "Missing trip type " + type);
        }
    }
}
