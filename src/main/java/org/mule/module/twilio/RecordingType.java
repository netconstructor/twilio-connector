package org.mule.module.twilio;

public enum RecordingType {

    WAV(".wav"), MP3(".mp3"), XML(".xml");

    private String extension;

    RecordingType(String extension) {
        this.extension = extension;
    }

    public String getExtension() {
        return extension;
    }

    @Override
    public String toString() {
        return extension;
    }
}