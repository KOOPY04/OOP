public class GildedRose2 {
    public static final String AGED_BRIE = "Aged Brie";
    public static final String BACKSTAGE_PASSES = "Backstage passes to a TAFKAL80ETC concert";
    public static final String SULFURAS = "Sulfuras, Hand of Ragnaros";
    public static final String CONJURED_ITEM = "Conjured Mana Cake";
    public static final int max_quality = 50;    
    public static final int min_quality = 0;

    Item[] items;

    public GildedRose2(Item[] items) {
        this.items = items;
    }

    public void updateQuality() {
        for (Item item : items) {
            updateItemQuality(item);
        }
    }

    private void updateItemQuality(Item item) {
        if (isNormalItem(item)) {
            decreaseQuality(item, 1);
        } else if (isBackstagePass(item)) {
            increaseBackstagePassQuality(item);
        } else if (isAgedBrie(item)) {
            increaseQuality(item);
        } else if (isConjuredItem(item)) {
            decreaseQuality(item, 2);
        } else if(isSulfuras(item)){
            item.quality = item.quality;
            // Sulfuras does not change
        }

        updateSellIn(item);

        if (item.sellIn < 0) {
            if (isNormalItem(item)) {
                decreaseQuality(item, 1);
            } else if (isBackstagePass(item)) {
                item.quality = min_quality;
            } else if (isAgedBrie(item)) {
                increaseQuality(item);
            } else if (isConjuredItem(item)) {
                decreaseQuality(item, 2);
            } else{
                // Sulfuras does not change
            }
        }
    }

    private void updateSellIn(Item item) {
        if (!isSulfuras(item)) {
            item.sellIn--;
        }
    }

    private void increaseBackstagePassQuality(Item item) {
        if (item.quality < max_quality) {
            item.quality++;

            if (item.sellIn < 11 && item.quality < max_quality) {
                item.quality++;
            }

            if (item.sellIn < 6 && item.quality < max_quality) {
                item.quality++;
            }
        }
    }

    private void increaseQuality(Item item) {
        if (item.quality < max_quality) {
            item.quality++;
        }
    }

    private void decreaseQuality(Item item, int decrement) {
        if (item.quality > min_quality) {
            for (int i = 1; i <= decrement; i++) {
                item.quality--;
            }
        }
    }

    private boolean isNormalItem(Item item) {
        return !isAgedBrie(item) && !isBackstagePass(item) && !isSulfuras(item) && !isConjuredItem(item);
    }

    private boolean isAgedBrie(Item item) {
        return AGED_BRIE.equals(item.name);
    }

    private boolean isBackstagePass(Item item) {
        return BACKSTAGE_PASSES.equals(item.name);
    }

    private boolean isSulfuras(Item item) {
        return SULFURAS.equals(item.name);
    }

    private boolean isConjuredItem(Item item) {
        return CONJURED_ITEM.equals(item.name);
    }
}