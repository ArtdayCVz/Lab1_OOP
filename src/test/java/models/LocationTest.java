package models;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import models.Location;

class LocationTest {

    @Test
    void trimsAndExposesValues() {
        Location l = new Location("  Kyiv ", " Ukraine ");
        assertEquals("Kyiv", l.getCity());
        assertEquals("Ukraine", l.getCountry());
        assertEquals("Kyiv, Ukraine", l.toString());
    }

    @Test
    void rejectsBlankOrNullValues() {
        assertThrows(IllegalArgumentException.class, () -> new Location(null, "UA"));
        assertThrows(IllegalArgumentException.class, () -> new Location(" ", "UA"));
        assertThrows(IllegalArgumentException.class, () -> new Location("Kyiv", null));
        assertThrows(IllegalArgumentException.class, () -> new Location("Kyiv", ""));
    }

    @Test
    void equalsIgnoresCase() {
        Location a = new Location("Kyiv", "Ukraine");
        assertEquals(a, a);
        assertEquals(a, new Location("KYIV", "ukraine"));
        assertEquals(a.hashCode(), new Location("KYIV", "ukraine").hashCode());
        assertNotEquals(a, new Location("Lviv", "Ukraine"));
        assertNotEquals(a, "Kyiv, Ukraine");
    }
}
