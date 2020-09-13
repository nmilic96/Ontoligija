package com.example.ontoligija;

public class Resource {
    private String mResourceName;
    private String mResourceType;
    private String mResourceData;

    public Resource(String resourceName, String resourceType, String resourceData) {
        mResourceName = resourceName;
        mResourceType = resourceType;
        mResourceData = resourceData;
    }

    public String getResourceName() {
        return mResourceName;
    }

    public String getResourceType() {
        return mResourceType;
    }

    public String getmResourceData() {
        return mResourceData;
    }
}
