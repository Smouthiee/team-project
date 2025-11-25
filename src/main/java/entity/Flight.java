package entity;

public class Flight {

    private final String callSign;
    private final String originCountry;
    private final long timePosition;
    private final String squawk;
    private final boolean onGround;

    public Flight(String callSign,
                  String originCountry,
                  long timePosition,
                  String squawk,
                  boolean onGround) {

        this.callSign = callSign;
        this.originCountry = originCountry;
        this.timePosition = timePosition;
        this.squawk = squawk;
        this.onGround = onGround;
    }

    public String getCallSign() {
        return callSign;
    }

    public String getOriginCountry() {
        return originCountry;
    }

    public long getTimePosition() {
        return timePosition;
    }

    public String getSquawk() {
        return squawk;
    }

    public boolean isOnGround() {
        return onGround;
    }
}
