package leonski.bitsandbobs;

import leonski.bitsandbobs.event.ItemEntityEvents;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.gamerule.v1.GameRuleFactory;
import net.fabricmc.fabric.api.gamerule.v1.GameRuleRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.minecraft.world.GameRules.Category;

public class BitsAndBobs implements ModInitializer {
    public static final TagKey<Item> SEEDS;

    public static final GameRules.Key<GameRules.IntRule> DROPPED_SEEDS_PLANT_DELAY;
    public static final GameRules.Key<GameRules.IntRule> DROPPED_SAPLINGS_PLANT_DELAY;

    static {
        SEEDS = TagKey.of(RegistryKeys.ITEM, new Identifier("c", "seeds"));

        DROPPED_SEEDS_PLANT_DELAY = GameRuleRegistry.register("droppedSeedsPlantDelay", Category.DROPS, GameRuleFactory.createIntRule(100, 0));
        DROPPED_SAPLINGS_PLANT_DELAY = GameRuleRegistry.register("droppedSaplingsPlantDelay", Category.DROPS, GameRuleFactory.createIntRule(100, 0));
    };

    @Override
    public void onInitialize() {
        ItemEntityEvents.END_TICK.register((entity) -> {
            World world = entity.getWorld();
            GameRules gameRules = world.getGameRules();
            ItemStack stack = entity.getStack();

            int droppedSeedsPlantDelay = gameRules.getInt(DROPPED_SEEDS_PLANT_DELAY);
            int droppedSaplingsPlantDelay = gameRules.getInt(DROPPED_SAPLINGS_PLANT_DELAY);

            if (entity.isOnGround()) {
                boolean isValidDroppedSeeds = droppedSeedsPlantDelay != 0 && stack.isIn(SEEDS) && entity.age > droppedSeedsPlantDelay;
                boolean isValidDroppedSaplings = droppedSaplingsPlantDelay != 0 && stack.isIn(ItemTags.SAPLINGS) && entity.age > droppedSaplingsPlantDelay;

                if (isValidDroppedSeeds || isValidDroppedSaplings) {
                    Block block = Block.getBlockFromItem(stack.getItem());
                    BlockState blockState = block.getDefaultState();
                    BlockPos blockPos = entity.getBlockPos();
                    BlockPos aboveBlockPos = blockPos.up();

                    if ((blockState.canPlaceAt(world, blockPos) && world.getBlockState(blockPos).isAir() && world.setBlockState(blockPos, blockState)) || (blockState.canPlaceAt(world, aboveBlockPos) && world.getBlockState(aboveBlockPos).isAir() && world.setBlockState(aboveBlockPos, blockState))) {
                        stack.decrement(1);
                    }
                }
            }
        });
    }
}
