package it.unibo.deathnote.impl;

import java.util.HashMap;
import java.util.Map;

import it.unibo.deathnote.api.DeathNote;

/**
 * Implementation of the Deathnote interface
 */
public class DeathNoteImpl implements DeathNote {
    private Map<String, HumanInfo> humans;
    private String lastNameWritten;

    public DeathNoteImpl() {
        this.humans = new HashMap<>();
    }

    @Override
    public String getRule(final int ruleNumber) {
        if (ruleNumber <= 0 || ruleNumber > RULES.size()) {
            throw new IllegalArgumentException("The rules number " + ruleNumber + " does not exist");
        }
        return RULES.get(ruleNumber - 1);
    }

    @Override
    public void writeName(final String name) {
        if (name == null) {
            throw new NullPointerException("Name cannot be null");
        }
        if (!isNameWritten(name)) {
            this.humans.put(name, new HumanInfo(System.currentTimeMillis()));
            this.lastNameWritten = name;
        }
    }

    /**
     * Check if the name is written in the deathnote
     * @param name the name of the person
     */
    private void nameCheck(final String name) {
        if (!isNameWritten(name)) {
            throw new IllegalArgumentException("This name is not written in the death note");
        }
    }

    /**
     * Check if the map of humans is empty and if the value of the info is null
     * @param typeInfo type of the info (it must be "cause" or "details")
     * @param value value of the info to update
     */
    private void valueCheck(final String typeInfo, final String value) {
        if (DeathNoteImpl.this.humans.keySet().isEmpty() || value == null) {
            throw new IllegalStateException("The name has to be written before the " + typeInfo + " of the death");
        }
    }

    @Override
    public boolean writeDeathCause(final String cause) {
        valueCheck("cause", cause);
        return this.humans.get(lastNameWritten).update("cause", cause, 40);
    }

    @Override
    public boolean writeDetails(final String details) {
        valueCheck("details", details);
        return this.humans.get(lastNameWritten).update("details", details, 6040);    
    }

    @Override
    public String getDeathCause(final String name) {
        nameCheck(name);
        return this.humans.get(name).deathCause;
    }

    @Override
    public String getDeathDetails(final String name) {
        nameCheck(name);
        return this.humans.get(name).deathDetails;    
    }

    @Override
    public boolean isNameWritten(final String name) {
        return this.humans.containsKey(name);
    }

    private class HumanInfo {
        private String deathCause = "heart attack";
        private String deathDetails = "";
        private long timeNameInsert;

        private HumanInfo(final long timeNameInsert) {
            this.timeNameInsert = timeNameInsert;
        }

        /**
         * Update one of the info of a human (deathCause or deathDetails)
         * @param typeInfo type of the info (it must be "cause" or "details")
         * @param value value of the info to update
         * @param maxTime the maximum time that can pass to insert the info ("cause" or
         *                "details") from the insertion of the name of the human
         * @return true if the update has been done in time, false otherwise
         */
        private boolean update(final String typeInfo, final String value, final long maxTime) {
            final long timePassedFromNameInsertion = System.currentTimeMillis() - this.timeNameInsert;
            if (timePassedFromNameInsertion <= maxTime) {
                if (typeInfo == "cause") {
                    this.deathCause = value;
                }
                else {
                    this.deathDetails = value;
                }
                return true;
            }
            return false;
        }
    }
}