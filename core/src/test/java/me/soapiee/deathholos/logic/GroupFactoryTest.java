package me.soapiee.deathholos.logic;

import me.soapiee.deathholos.DeathHolos;
import me.soapiee.deathholos.managers.MessageManager;
import me.soapiee.deathholos.utils.CustomLogger;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.command.CommandSender;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class GroupFactoryTest {

    private GroupFactory groupFactory;
    private CommandSender mockSender;

    private final String VALID_NAME = "name";
    private final String VALID_PRIORITY = "10";
    private final int VALID_PRIORITY_INT = 10;
    private final String VALID_PERMISSION = "deathholos.group.test";
    private final String VALID_SOUND_STRING = "BLOCK_ANVIL_PLACE";
    private final Sound VALID_SOUND = Sound.BLOCK_ANVIL_PLACE;
    private final List<String> VALID_DESIGN = new ArrayList<>();

    @BeforeEach
    void beforeEach() {
        DeathHolos mockMain = mock(DeathHolos.class);
        CustomLogger mockCustomLogger = mock(CustomLogger.class);
        MessageManager mockMessageManager = mock(MessageManager.class);
        mockSender = mock(CommandSender.class);

        when(mockMain.getCustomLogger()).thenReturn(mockCustomLogger);
        when(mockMain.getMessageManager()).thenReturn(mockMessageManager);

        VALID_DESIGN.clear();
        VALID_DESIGN.add("line 1");

        groupFactory = new GroupFactory(mockMain);
    }

    @Test
    void givenGroupFactory_whenInitialised_thenReturnNotNull() {
        assertNotNull(groupFactory);
    }

    @Test
    void givenValidGroup_whenCreate_thenReturnValidGroup() {
        HologramGroup outcomeGroup = groupFactory.create(
                mockSender,
                VALID_NAME,
                VALID_PRIORITY,
                VALID_PERMISSION,
                VALID_DESIGN,
                VALID_SOUND_STRING);

        assertNotNull(outcomeGroup);
        assertEquals(VALID_NAME, outcomeGroup.getName());
        assertEquals(VALID_PRIORITY_INT, outcomeGroup.getPriority());
        assertEquals(VALID_PERMISSION, outcomeGroup.getPermission());
        assertEquals(VALID_DESIGN, outcomeGroup.getText());
    }

    @Test
    void givenPriorityLetter_whenCreate_thenReturnNull() {
        HologramGroup outcomeGroup = groupFactory.create(
                mockSender,
                VALID_NAME,
                "a",
                VALID_PERMISSION,
                VALID_DESIGN,
                VALID_SOUND_STRING);

        assertNull(outcomeGroup);
    }

    @Test
    void givenPriorityNegativeNumber_whenCreate_thenReturnNull() {
        HologramGroup outcomeGroup = groupFactory.create(
                mockSender,
                VALID_NAME,
                "-50",
                VALID_PERMISSION,
                VALID_DESIGN,
                VALID_SOUND_STRING);

        assertNull(outcomeGroup);
    }

    @Test
    void given200Priority_whenCreate_thenReturn200Priority() {
        HologramGroup outcomeGroup = groupFactory.create(
                mockSender,
                VALID_NAME,
                "200",
                VALID_PERMISSION,
                VALID_DESIGN,
                VALID_SOUND_STRING);

        assertNotNull(outcomeGroup);
        assertEquals(VALID_NAME, outcomeGroup.getName());
        assertEquals(200, outcomeGroup.getPriority());
        assertEquals(VALID_PERMISSION, outcomeGroup.getPermission());
        assertEquals(VALID_DESIGN, outcomeGroup.getText());
    }

    @Test
    void givenPermissionNumber_whenCreate_thenReturnNull() {
        HologramGroup outcomeGroup = groupFactory.create(
                mockSender,
                VALID_NAME,
                VALID_PRIORITY,
                "deathholos.group.f4u7",
                VALID_DESIGN,
                VALID_SOUND_STRING);

        assertNull(outcomeGroup);
    }

    @Test
    void givenPermisionInvalidFormat_whenCreate_thenReturnNull() {
        HologramGroup outcomeGroup = groupFactory.create(
                mockSender,
                VALID_NAME,
                VALID_PRIORITY,
                "death.holos.group",
                VALID_DESIGN,
                VALID_SOUND_STRING);

        assertNull(outcomeGroup);
    }

    @Test
    void givenValidPermission_whenCreate_thenReturnValidPermission() {
        HologramGroup outcomeGroup = groupFactory.create(
                mockSender,
                VALID_NAME,
                VALID_PRIORITY,
                "deathholos.testing",
                VALID_DESIGN,
                VALID_SOUND_STRING);

        assertNotNull(outcomeGroup);
        assertEquals(VALID_NAME, outcomeGroup.getName());
        assertEquals(VALID_PRIORITY_INT, outcomeGroup.getPriority());
        assertEquals("deathholos.testing", outcomeGroup.getPermission());
        assertEquals(VALID_DESIGN, outcomeGroup.getText());
    }

    @Test
    void givenDesignNull_whenCreate_thenReturnNull() {
        HologramGroup outcomeGroup = groupFactory.create(
                mockSender,
                VALID_NAME,
                VALID_PRIORITY,
                VALID_PERMISSION,
                null,
                VALID_SOUND_STRING);

        assertNull(outcomeGroup);
    }

    @Test
    void givenDesignEmpty_whenCreate_thenReturnNull() {
        HologramGroup outcomeGroup = groupFactory.create(
                mockSender,
                VALID_NAME,
                VALID_PRIORITY,
                VALID_PERMISSION,
                new ArrayList<>(),
                VALID_SOUND_STRING);

        assertNull(outcomeGroup);
    }

    @Test
    void givenValidDesign_whenCreate_thenReturnValidDesign() {
        ArrayList<String> design = new ArrayList<>();
        design.add("line 1");
        design.add("line 2");
        design.add("line 3");

        HologramGroup outcomeGroup = groupFactory.create(
                mockSender,
                VALID_NAME,
                VALID_PRIORITY,
                VALID_PERMISSION,
                design,
                VALID_SOUND_STRING);

        assertNotNull(outcomeGroup);
        assertEquals(VALID_NAME, outcomeGroup.getName());
        assertEquals(VALID_PRIORITY_INT, outcomeGroup.getPriority());
        assertEquals(VALID_PERMISSION, outcomeGroup.getPermission());
        assertEquals(design, outcomeGroup.getText());
    }

    @Test
    void givenNameNumbered_whenCreate_thenReturnValidName() {
        HologramGroup outcomeGroup = groupFactory.create(
                mockSender,
                "nu-.12er",
                VALID_PRIORITY,
                VALID_PERMISSION,
                VALID_DESIGN,
                VALID_SOUND_STRING);

        assertNotNull(outcomeGroup);
        assertEquals("nu-.12er", outcomeGroup.getName());
        assertEquals(VALID_PRIORITY_INT, outcomeGroup.getPriority());
        assertEquals(VALID_PERMISSION, outcomeGroup.getPermission());
        assertEquals(VALID_DESIGN, outcomeGroup.getText());
    }

    @Test
    void givenNameSymbols_whenCreate_thenReturnValidName() {
        HologramGroup outcomeGroup = groupFactory.create(
                mockSender,
                "na&me",
                VALID_PRIORITY,
                VALID_PERMISSION,
                VALID_DESIGN,
                VALID_SOUND_STRING);

        assertNotNull(outcomeGroup);
        assertEquals("na&me", outcomeGroup.getName());
        assertEquals(VALID_PRIORITY_INT, outcomeGroup.getPriority());
        assertEquals(VALID_PERMISSION, outcomeGroup.getPermission());
        assertEquals(VALID_DESIGN, outcomeGroup.getText());
    }

//    @Test
//    void givenParticle_whenCreate_thenReturnValidParticle() {
//        HologramGroup outcomeGroup = groupFactory.create(
//                mockSender,
//                VALID_NAME,
//                VALID_PRIORITY,
//                VALID_PERMISSION,
//                VALID_DESIGN,
//                VALID_PARTICLE_STRING,
//                VALID_SOUND_STRING);
//
//        assertNotNull(outcomeGroup);
//        assertEquals(VALID_NAME, outcomeGroup.getName());
//        assertEquals(VALID_PRIORITY_INT, outcomeGroup.getPriority());
//        assertEquals(VALID_PERMISSION, outcomeGroup.getPermission());
//        assertEquals(VALID_DESIGN, outcomeGroup.getText());
//        assertEquals(VALID_PARTICLE, outcomeGroup.getParticle());
//        assertEquals(VALID_SOUND, outcomeGroup.getSound());
//    }
//
//    @Test
//    void givenParticle_whenCreate_thenReturnNullParticle() {
//        HologramGroup outcomeGroup = groupFactory.create(
//                mockSender,
//                VALID_NAME,
//                VALID_PRIORITY,
//                VALID_PERMISSION,
//                VALID_DESIGN,
//                "invalid",
//                VALID_SOUND_STRING);
//
//        assertNotNull(outcomeGroup);
//        assertEquals(VALID_NAME, outcomeGroup.getName());
//        assertEquals(VALID_PRIORITY_INT, outcomeGroup.getPriority());
//        assertEquals(VALID_PERMISSION, outcomeGroup.getPermission());
//        assertEquals(VALID_DESIGN, outcomeGroup.getText());
//        assertNull(outcomeGroup.getParticle());
//        assertEquals(VALID_SOUND, outcomeGroup.getSound());
//    }
//
//    @Test
//    void givenNullString_whenCreate_thenReturnNullParticle() {
//        HologramGroup outcomeGroup = groupFactory.create(
//                mockSender,
//                VALID_NAME,
//                VALID_PRIORITY,
//                VALID_PERMISSION,
//                VALID_DESIGN,
//                "null",
//                VALID_SOUND_STRING);
//
//        assertNotNull(outcomeGroup);
//        assertEquals(VALID_NAME, outcomeGroup.getName());
//        assertEquals(VALID_PRIORITY_INT, outcomeGroup.getPriority());
//        assertEquals(VALID_PERMISSION, outcomeGroup.getPermission());
//        assertEquals(VALID_DESIGN, outcomeGroup.getText());
//        assertNull(outcomeGroup.getParticle());
//        assertEquals(VALID_SOUND, outcomeGroup.getSound());
//    }
//
//    @Test
//    void givenNull_whenCreate_thenReturnNullParticle() {
//        HologramGroup outcomeGroup = groupFactory.create(
//                mockSender,
//                VALID_NAME,
//                VALID_PRIORITY,
//                VALID_PERMISSION,
//                VALID_DESIGN,
//                null,
//                VALID_SOUND_STRING);
//
//        assertNotNull(outcomeGroup);
//        assertEquals(VALID_NAME, outcomeGroup.getName());
//        assertEquals(VALID_PRIORITY_INT, outcomeGroup.getPriority());
//        assertEquals(VALID_PERMISSION, outcomeGroup.getPermission());
//        assertEquals(VALID_DESIGN, outcomeGroup.getText());
//        assertNull(outcomeGroup.getParticle());
//        assertEquals(VALID_SOUND, outcomeGroup.getSound());
//    }

    @Test
    void givenSound_whenCreate_thenReturnValidSound() {
        HologramGroup outcomeGroup = groupFactory.create(
                mockSender,
                VALID_NAME,
                VALID_PRIORITY,
                VALID_PERMISSION,
                VALID_DESIGN,
                VALID_SOUND_STRING);

        assertNotNull(outcomeGroup);
        assertEquals(VALID_NAME, outcomeGroup.getName());
        assertEquals(VALID_PRIORITY_INT, outcomeGroup.getPriority());
        assertEquals(VALID_PERMISSION, outcomeGroup.getPermission());
        assertEquals(VALID_DESIGN, outcomeGroup.getText());
        assertEquals(VALID_SOUND, outcomeGroup.getSound());
    }

    @Test
    void givenInvalidSound_whenCreate_thenReturnNullSound() {
        HologramGroup outcomeGroup = groupFactory.create(
                mockSender,
                VALID_NAME,
                VALID_PRIORITY,
                VALID_PERMISSION,
                VALID_DESIGN,
                "invalidSound");

        assertNotNull(outcomeGroup);
        assertEquals(VALID_NAME, outcomeGroup.getName());
        assertEquals(VALID_PRIORITY_INT, outcomeGroup.getPriority());
        assertEquals(VALID_PERMISSION, outcomeGroup.getPermission());
        assertEquals(VALID_DESIGN, outcomeGroup.getText());
        assertNull(outcomeGroup.getSound());
    }

    @Test
    void givenNullString_whenCreate_thenReturnNullSound() {
        HologramGroup outcomeGroup = groupFactory.create(
                mockSender,
                VALID_NAME,
                VALID_PRIORITY,
                VALID_PERMISSION,
                VALID_DESIGN,
                "null");

        assertNotNull(outcomeGroup);
        assertEquals(VALID_NAME, outcomeGroup.getName());
        assertEquals(VALID_PRIORITY_INT, outcomeGroup.getPriority());
        assertEquals(VALID_PERMISSION, outcomeGroup.getPermission());
        assertEquals(VALID_DESIGN, outcomeGroup.getText());
        assertNull(outcomeGroup.getSound());
    }

    @Test
    void givenNull_whenCreate_thenReturnNullSound() {
        HologramGroup outcomeGroup = groupFactory.create(
                mockSender,
                VALID_NAME,
                VALID_PRIORITY,
                VALID_PERMISSION,
                VALID_DESIGN,
                null);

        assertNotNull(outcomeGroup);
        assertEquals(VALID_NAME, outcomeGroup.getName());
        assertEquals(VALID_PRIORITY_INT, outcomeGroup.getPriority());
        assertEquals(VALID_PERMISSION, outcomeGroup.getPermission());
        assertEquals(VALID_DESIGN, outcomeGroup.getText());
        assertNull(outcomeGroup.getSound());
    }

}
