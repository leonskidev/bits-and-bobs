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
    public static final GameRules.Key<GameRules.BooleanRule> DO_ITEMS_PLANT;

    static {
        SEEDS = TagKey.of(RegistryKeys.ITEM, new Identifier("c", "seeds"));
        DO_ITEMS_PLANT = GameRuleRegistry.register("doItemsPlant", Category.DROPS, GameRuleFactory.createBooleanRule(true));
    };

    @Override
    public void onInitialize() {
        ItemEntityEvents.TICK.register((entity) -> {
            World world = entity.getWorld();
            ItemStack stack = entity.getStack();
            GameRules gameRules = world.getGameRules();

            boolean doItemsPlant = gameRules.getBoolean(DO_ITEMS_PLANT);

            if (doItemsPlant && entity.isOnGround() && entity.age > 100 && (stack.isIn(ItemTags.SAPLINGS) || stack.isIn(SEEDS))) {
                Block block = Block.getBlockFromItem(stack.getItem());

                if (block != null) {
                    BlockState blockState = block.getDefaultState();
                    BlockPos blockPos = entity.getBlockPos();
                    BlockPos aboveBlockPos = blockPos.up();

                    if ((blockState.canPlaceAt(world, blockPos) && world.setBlockState(blockPos, blockState)) || (blockState.canPlaceAt(world, aboveBlockPos) && world.setBlockState(aboveBlockPos, blockState))) {
                        stack.decrement(1);
                    }
                }
            }
        });
    }
}
