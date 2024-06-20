import org.junit.*;

public class GildedRoseTest {

    @Test
    public void foo() {
        Item[] items = new Item[] { 
            new Item("foo", 0, 20),
            new Item("+5 Dexterity Vest", 10, 0), //
            new Item("Aged Brie", 2, 0), //
            new Item("Aged Brie", 2, 50),
            new Item("Sulfuras, Hand of Ragnaros", 0, 50), //
            new Item("Backstage passes to a TAFKAL80ETC concert", 10, 20),
            new Item("Backstage passes to a TAFKAL80ETC concert", 5, 20),
            new Item("Backstage passes to a TAFKAL80ETC concert", 0, 20),
            new Item("+5 Dexterity Vest", -1, 0),
            new Item("Conjured Mana Cake", 3, 6),
            new Item("Conjured Mana Cake", 3, 0),
            new Item("Conjured Mana Cake", 0, 4),
            // this conjured item does not work properly yet
    };
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        Assert.assertEquals(18, app.items[0].quality);
        Assert.assertEquals(0, app.items[1].quality);
        Assert.assertEquals(1, app.items[2].quality);
        Assert.assertEquals(50, app.items[3].quality);
        Assert.assertEquals(50, app.items[4].quality);
        Assert.assertEquals(22, app.items[5].quality);
        Assert.assertEquals(23, app.items[6].quality);
        Assert.assertEquals(0, app.items[7].quality);
        Assert.assertEquals(0, app.items[8].quality);
        Assert.assertEquals(4, app.items[9].quality);
        Assert.assertEquals(0, app.items[10].quality);
        Assert.assertEquals(0, app.items[11].quality);
    }

}
