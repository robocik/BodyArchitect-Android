package com.quasardevelopment.bodyarchitect.client.model;

import com.quasardevelopment.bodyarchitect.client.wcf.Wsdl2Code.WebServices.BasicHttpBinding_IBodyArchitectAccessService.BAGlobalObject;

import java.io.Serializable;
import java.util.UUID;



public class LocalObjectKey   implements Serializable
{
    UUID id;
    KeyType type;

    public LocalObjectKey()
    {

    }
    public LocalObjectKey(UUID id, KeyType keyType)
    {
        setId(id);
        setKeyType(keyType);
    }

    public LocalObjectKey(BAGlobalObject newEntry)
    {
        if (newEntry.isNew())
        {
            setId(newEntry.instanceId);
            setKeyType(KeyType.InstanceId);
        }
        else
        {
            setId(newEntry.globalId);
            setKeyType(KeyType.GlobalId);
        }
    }

    public UUID getId()
    {
        return id;
    }

    public void setId(UUID id)
    {
        this.id=id;
    }

    public KeyType getKeyType()
    {
        return type;
    }

    public void setKeyType(KeyType type)
    {
        this.type=type;
    }

    @Override
    public int hashCode() {
        return (getId().toString() + getKeyType().toString()).hashCode();
    }



    @Override
    public boolean equals(Object obj) {
        LocalObjectKey otherKey = (LocalObjectKey) obj;
        return otherKey != null && otherKey.getId().equals(getId()) && otherKey.getKeyType().equals(getKeyType());
    }

}
