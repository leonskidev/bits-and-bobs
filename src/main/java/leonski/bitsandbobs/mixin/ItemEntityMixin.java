package leonski.bitsandbobs.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import leonski.bitsandbobs.event.ItemEntityEvents;
import net.minecraft.entity.ItemEntity;

@Mixin(ItemEntity.class)
public class ItemEntityMixin {
    @Inject(at = @At("HEAD"), method = "tick")
    private void onStartTick(CallbackInfo ci) {
        ItemEntityEvents.START_TICK.invoker().onStartTick((ItemEntity)(Object)this);
    }

    @Inject(at = @At("TAIL"), method = "tick")
    private void onEndTick(CallbackInfo ci) {
        ItemEntityEvents.END_TICK.invoker().onEndTick((ItemEntity)(Object)this);
    }
}
