package com.rbs.url;

import org.apache.commons.lang.StringUtils;

/**
 * 
 * @author Rodrigo Dellinghausen (dellinghausen@gmail.com)
 */
public class UTMParameters {

    private final String source;
    private final String medium;
    private final String campaign;

    public UTMParameters(String source, String medium, String campaign) {
        this.source = source;
        this.medium = medium;
        this.campaign = campaign;
    }

    public boolean isValid() {
        return StringUtils.isNotBlank(source) 
                        && StringUtils.isNotBlank(medium) && StringUtils.isNotBlank(campaign);
    }

    public String asString() {
        //?utm_source=AppsZH&utm_medium=Time&utm_campaign=AppShare
        return String.format("utm_source=%s&utm_medium=%s&utm_campaign=%s", source, medium, campaign);
    }

    public String getSource() {
        return source;
    }

    public String getMedium() {
        return medium;
    }

    public String getCampaign() {
        return campaign;
    }
}
