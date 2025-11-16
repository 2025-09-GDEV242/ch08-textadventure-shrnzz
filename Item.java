/**
 * This class Item is for items in a room. Items have a description and weight.
 * 
 *
 * @author Sahar Naz
 * @version 2025.11.16
 */
public class Item
{
    private String description;
    private int weight;

    /**
     * Creates an item with the given description and weight.
     * @param description Description of the item.
     * @param weight The weight of the item.
     * @return desrciption The description of the item 
     * @return weight The weight of the item 
     * @return getLongDescription The long description of the item 
     */
    public Item(String description, int weight)
    {
        this.description = description;
        this.weight = weight;
    }
    // description of this item 
    public String getDescription()
    {
        return description;
    }
    // weight of this item
    public int getWeight()
    {
        return weight;
    }
    // long description of the item 
    public String getLongDescription()
    {
        return description + " (weight: " + weight + ")";
    }
}