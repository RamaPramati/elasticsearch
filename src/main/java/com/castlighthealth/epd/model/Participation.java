package com.castlighthealth.epd.model;

import java.util.Set;

public class Participation {

    public static final long NO_PHONE_NUMBER = 0L;
    public static final int NO_PRACTICE_ID = 0;
    private static final int NO_NETWORK_PRIORITY = -1;

    private int providerId;
    private int networkId;
    private int participationId;
    private float latitude;
    private float longitude;
    private int locationId;

    private int practiceId;
    private long phone;
    private int networkPriority;
    private String coverageType;
    private String state;

    public Participation() {

    }

    public Participation(int providerId, int networkId, int participationId, float latitude,
                         float longitude, int locationId, String coverageType, String state) {
        this.providerId = providerId;
        this.networkId = networkId;
        this.participationId = participationId;
        this.latitude = latitude;
        this.longitude = longitude;
        this.locationId = locationId;
        this.coverageType = coverageType == null ? null : coverageType.intern();
        this.state =  state == null ? null : state.intern();
    }


    public int getProviderId() {
        return providerId;
    }

    public void setProviderId(int providerId) {
        this.providerId = providerId;
    }

    public int getNetworkId() {
        return networkId;
    }

    public void setNetworkId(int networkId) {
        this.networkId = networkId;
    }

    public int getParticipationId() {
        return participationId;
    }

    public void setParticipationId(int participationId) {
        this.participationId = participationId;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public Integer getPracticeId() {
        if (practiceId == NO_PRACTICE_ID) {
            return null;
        } else {
            return practiceId;
        }
    }

    public void setPracticeId(Integer practiceId) {
        this.practiceId = practiceId;
    }

    public int getLocationId() {
        return locationId;
    }

    public void setLocationId(int locationId) {
        this.locationId = locationId;
    }

    public String getState() {
        return state;
    }

    /**
     * Sets the phone number to a compact format consisting only of digits stored in a Long.
     *
     * @param phoneNumber
     */
    public void setPhone(String phoneNumber) {
        if (phoneNumber == null || phoneNumber.isEmpty()) {
            phone = NO_PHONE_NUMBER;
        }
        else {
            phoneNumber = phoneNumber.trim();
            StringBuilder sb = new StringBuilder(10);
            for (int i = 0; i < phoneNumber.length(); i++) {
                char c = phoneNumber.charAt(i);
                if (Character.isDigit(c)) sb.append(c);
            }
            try {
                phone = Long.valueOf(sb.toString());
            } catch (NumberFormatException e) {
                phone = NO_PHONE_NUMBER;
            }
        }
    }

    /**
     * Original requirement - Returns a formatted String version of the phone number. The phone number is assumed to be
     * 10 digits that can be formatted as (NPA) NXX-XXXX.
     *
     * As per new requirement, extn. no. also required to support. So if extn. is supplied, it has to be
     * in the format as (NPA) NXX-XXXXxXXXXX . It is assumed that the numbers from 11th position will be extn. number.
     *
     * @return
     */
    public String getPhone() {
        if (phone == NO_PHONE_NUMBER) {
            return null;
        }
        else {
            StringBuilder sb = new StringBuilder(20);
            String asString = Long.toString(phone);
            int startIndex = asString.length() - 1;
            int hyphenIndex = 4;
            int closingBracketIndex=8;
            int openingBracketIndex=13;

            if(asString.length() > 10){
                //phone no. is having extn. no. also specified.
                for(int i=asString.length()-1;i>=10;i--){
                    sb.append(asString.charAt(i));
                }
                sb.append("x");
                startIndex = 9;
                hyphenIndex = hyphenIndex + sb.length();
                openingBracketIndex = openingBracketIndex + sb.length();
                closingBracketIndex = closingBracketIndex + sb.length();
            }
            for (int i = startIndex; i >= 0; i--) {
                sb.append(asString.charAt(i));
                if (sb.length() == hyphenIndex) sb.append("-");
                if (sb.length() == closingBracketIndex) sb.append(" )");
            }
            if (sb.length() == openingBracketIndex) sb.append("(");

            return sb.reverse().toString();
        }
    }

    public String getCoverageType(){
        return coverageType;
    }

    public Integer getNetworkPriority() {
        return networkPriority == NO_NETWORK_PRIORITY ? null : networkPriority;
    }

    public void setNetworkPriority(Integer networkPriority) {
        this.networkPriority = networkPriority == null ? NO_NETWORK_PRIORITY : networkPriority;
    }
}
