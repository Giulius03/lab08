package it.unibo.deathnote;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.deathnote.api.DeathNote;
import it.unibo.deathnote.impl.DeathNoteImpl;

class TestDeathNote {
    private DeathNoteImpl testObject;
    private final String GABRIELETESTA = "Gabriele Testa";
    private final String ZWJACKSON = "ZW Jackson";

    /**
     * Configuration step: this is performed BEFORE each test.
     */
    @BeforeEach
    public void setUp() {
        this.testObject = new DeathNoteImpl();
    }

    /**
     * Check that the parameter passed to the function getRule is > 0
     */
    @Test
    public void testGetRule() {
        final int invalidNumber = 0; //this variable is used to be <= 0
        try {
            this.testObject.getRule(invalidNumber);
            Assertions.fail();
        } catch (IllegalArgumentException e) {
            Assertions.assertEquals("The rules' number starts by 1", e.getMessage());
        }
    }

    /**
     * Check that the rules are not null or empty strings
     */
    @Test
    public void testEmpyOrNullRules() {
        for (final String rule: DeathNote.RULES) {
            Assertions.assertTrue(rule != null && rule.isBlank() == false);
        }
    }

    /**
     * Check the validation of the human that shall dies
     */
    @Test
    public void testName() {
        Assertions.assertFalse(this.testObject.isNameWritten(GABRIELETESTA) == true);
        this.testObject.writeName(GABRIELETESTA);
        Assertions.assertTrue(this.testObject.isNameWritten(GABRIELETESTA) == true);
        Assertions.assertTrue(this.testObject.isNameWritten(ZWJACKSON) == false);
        Assertions.assertTrue(this.testObject.isNameWritten("") == false);
    }

    /**
     * Check that the cause of death can be written only within the next 40 milliseconds
     * of writing the human's name
     * @throws InterruptedException only to use Thread.sleep()
     */
    @Test
    public void testWriteCauseOfDeath() throws InterruptedException {
        try {
            this.testObject.writeDeathCause("cutting the head");
            Assertions.fail();
        } catch (IllegalStateException e) {
            Assertions.assertEquals("The name has to be written before the cause of death", e.getMessage());
        }
        this.testObject.writeName(GABRIELETESTA);
        Assertions.assertEquals("heart attack", this.testObject.getDeathCause(GABRIELETESTA));
        this.testObject.writeName(ZWJACKSON);
        Assertions.assertTrue(this.testObject.writeDeathCause("karting accident"));
        Assertions.assertEquals("karting accident", this.testObject.getDeathCause(ZWJACKSON));
        Thread.sleep(100);
        Assertions.assertFalse(this.testObject.writeDeathCause("knife in the neck"));
        Assertions.assertTrue(this.testObject.getDeathCause(ZWJACKSON) == "karting accident");
    }

    /**
     * Check that the details of death can be written only within the next 6 seconds
     * and 40 milliseconds of writing the human's name
     * @throws InterruptedException only to use Thread.sleep()
     */
    @Test
    public void testWriteDetailsOfDeath() throws InterruptedException {
        try {
            this.testObject.writeDetails("goddamn it was a shit");
            Assertions.fail();
        } catch (IllegalStateException e) {
            Assertions.assertEquals("The name has to be written before the details of the death", e.getMessage());
        }
        this.testObject.writeName(GABRIELETESTA);
        Assertions.assertEquals("", this.testObject.getDeathDetails(GABRIELETESTA));
        Assertions.assertTrue(this.testObject.writeDetails("ran for too long"));
        Assertions.assertEquals("ran for too long", this.testObject.getDeathDetails(GABRIELETESTA));
        this.testObject.writeName(ZWJACKSON);
        Thread.sleep(6100);
        Assertions.assertFalse(this.testObject.writeDetails("ran too slow"));
        Assertions.assertTrue(this.testObject.getDeathDetails(ZWJACKSON) == "");
    }
}