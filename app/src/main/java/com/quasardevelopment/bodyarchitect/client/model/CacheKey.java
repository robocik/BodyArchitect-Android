package com.quasardevelopment.bodyarchitect.client.model;

import java.io.Serializable;
import java.util.UUID;

public class CacheKey   implements Serializable
{
    public UUID profileId;

    public UUID customerId;

    public CacheKey(UUID profileId, UUID customerId) {
        this.profileId = profileId;
        this.customerId = customerId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CacheKey cacheKey = (CacheKey) o;

        return cacheKey.profileId .equals(profileId) && ((customerId==null && cacheKey.customerId==null) || cacheKey.customerId .equals(customerId));
    }

    @Override
    public int hashCode() {
        int hash=0;
        if(profileId!=null)
        {
             hash+=profileId.hashCode();
        }
        if(customerId!=null)
        {
            hash+=customerId.hashCode();
        }
        return hash;
    }
}
