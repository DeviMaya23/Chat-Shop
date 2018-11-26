package com.example.lii.chatshop;

/**
 * Created by lii on 1/19/2018.
 */

public class Item {
    int itemID;
    String itemName;
    String itemDescription;
    String image; //URL
    int status;

    public Item()
    {

    }

    public Item(int id, String name, String desc, String img, int stat)
    {
        itemID=id;
        itemName=name;
        itemDescription=desc;
        image=img;
        status=stat;
    }

    public void setItemID(String in)
    {
        itemID= Integer.parseInt(in);
    }

    public int getItemId()
    {
        return itemID;
    }

    public void setItemName(String in)
    {
        itemName = in;
    }

    public String getItemName()
    {
        return itemName;
    }

    public void setItemDescription(String in)
    {
        itemDescription = in;
    }

    public String getItemDescription()
    {
        return itemDescription;
    }

    public void setImage(String in)
    {
        image = in;
    }

    public String getImageURL()
    {
        return image;
    }

    public void setStatus(String in)
    {
        itemID= Integer.parseInt(in);
    }

    public int getStatus()
    {
        return itemID;
    }
}
