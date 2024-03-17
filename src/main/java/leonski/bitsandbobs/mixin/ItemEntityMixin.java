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
    private void tick(CallbackInfo info) {
        ItemEntity entity = ((ItemEntity)(Object)this);
        ItemEntityEvents.TICK.invoker().tick(entity);
    }
}
