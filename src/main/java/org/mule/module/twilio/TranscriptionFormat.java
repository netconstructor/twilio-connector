package org.mule.module.twilio;

public enum TranscriptionFormat {

    XML(".xml"), TXT(".txt");

    private String extension;

    TranscriptionFormat(String extension) {
        this.extension = extension;
    }

    public String getExtension() {
        return extension;
    }
}