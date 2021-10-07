package fr.superprof.model;

public class PirateException extends Exception {

    private Integer pirateId;

    public PirateException(Integer pirateId) {
        this.pirateId = pirateId;
    }

    public PirateException(String message, Integer pirateId) {
        super(message);
        this.pirateId = pirateId;
    }

    public Integer getPirateId() {
        return pirateId;
    }

    public void setPirateId(Integer pirateId) {
        this.pirateId = pirateId;
    }
}
