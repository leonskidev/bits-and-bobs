package leonski.bitsandbobs.event;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.entity.ItemEntity;

public interface ItemEntityEvents {
    Event<ItemEntityEvents> TICK = EventFactory.createArrayBacked(
        ItemEntityEvents.class,
        (listeners) -> (entity) -> {
            for (ItemEntityEvents listener : listeners) {
                listener.tick(entity);
            }
        }
    );

    void tick(ItemEntity entity);
}
