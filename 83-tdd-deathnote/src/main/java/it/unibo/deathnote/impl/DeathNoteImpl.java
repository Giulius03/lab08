package it.unibo.deathnote.impl;

import it.unibo.deathnote.api.DeathNote;

public class DeathNoteImpl implements DeathNote {
    private String name;
    private String cause;
    private String details;

    public String getName() {
        return this.name;
    }

    public String getCause() {
        return this.cause;
    }

    public String getDetails() {
        return this.details;
    }

    @Override
    public String getRule(int ruleNumber) {
        throw new UnsupportedOperationException("Unimplemented method 'getRule'");
    }

    @Override
    public void writeName(String name) {
        throw new UnsupportedOperationException("Unimplemented method 'writeName'");
    }

    @Override
    public boolean writeDeathCause(String cause) {
        throw new UnsupportedOperationException("Unimplemented method 'writeDeathCause'");
    }

    @Override
    public boolean writeDetails(String details) {
        throw new UnsupportedOperationException("Unimplemented method 'writeDetails'");
    }

    @Override
    public String getDeathCause(String name) {
        throw new UnsupportedOperationException("Unimplemented method 'getDeathCause'");
    }

    @Override
    public String getDeathDetails(String name) {
        throw new UnsupportedOperationException("Unimplemented method 'getDeathDetails'");
    }

    @Override
    public boolean isNameWritten(String name) {
        throw new UnsupportedOperationException("Unimplemented method 'isNameWritten'");
    }

}