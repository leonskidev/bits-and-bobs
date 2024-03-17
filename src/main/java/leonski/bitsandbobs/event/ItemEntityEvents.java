package leonski.bitsandbobs.event;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.entity.ItemEntity;

public final class ItemEntityEvents {
    private ItemEntityEvents() {}

    /**
     * Called at the start of the {@link ItemEntity} tick.
     */
    public static final Event<StartTick> START_TICK = EventFactory.createArrayBacked(StartTick.class, (listeners) -> (entity) -> {
        for (StartTick listener : listeners) {
            listener.onStartTick(entity);
        }
    });

    /**
     * Called at the end of the {@link ItemEntity} tick.
     */
    public static final Event<EndTick> END_TICK = EventFactory.createArrayBacked(EndTick.class, (listeners) -> (entity) -> {
        for (EndTick listener : listeners) {
            listener.onEndTick(entity);
        }
    });

    @FunctionalInterface
    public interface StartTick {
        void onStartTick(ItemEntity entity);
    }

    @FunctionalInterface
    public interface EndTick {
        void onEndTick(ItemEntity entity);
    }
}
