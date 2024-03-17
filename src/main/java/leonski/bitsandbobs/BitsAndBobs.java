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
    public static final GameRules.Key<GameRules.BooleanRule> DO_ITEM_REPLANT;

    static {
        SEEDS = TagKey.of(RegistryKeys.ITEM, new Identifier("c", "seeds"));
        DO_ITEM_REPLANT = GameRuleRegistry.register("doItemReplant", Category.DROPS, GameRuleFactory.createBooleanRule(true));
    };

    @Override
    public void onInitialize() {
        ItemEntityEvents.TICK.register((entity) -> {
            World world = entity.getWorld();
            ItemStack stack = entity.getStack();
            GameRules gameRules = world.getGameRules();

            boolean doItemReplant = gameRules.getBoolean(DO_ITEM_REPLANT);

            if (doItemReplant && entity.isOnGround() && (stack.isIn(ItemTags.SAPLINGS) || stack.isIn(SEEDS))) {
                Block block = Block.getBlockFromItem(stack.getItem());

                if (block != null) {
                    BlockState blockState = block.getDefaultState();
                    BlockPos blockPos = entity.getBlockPos();

                    if (blockState.canPlaceAt(world, blockPos) && world.setBlockState(blockPos, blockState)) {
                        stack.decrement(1);
                    }
                }
            }
        });
    }
}
