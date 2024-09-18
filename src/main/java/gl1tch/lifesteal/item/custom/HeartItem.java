package gl1tch.lifesteal.item.custom;

import gl1tch.lifesteal.util.IEntityDataSaver;
import gl1tch.lifesteal.util.lifestealHealCountFloat;
import gl1tch.lifesteal.util.lifestealHeartCountFloat;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

import static gl1tch.lifesteal.util.LifestealForceRefresh.forceRefresh;

public class HeartItem extends Item {
    public HeartItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if (!world.isClient) {
            if (hand.equals(user.preferredHand)) {
                user.getInventory().getMainHandStack().decrement(1);
                lifestealHeartCountFloat.increment((IEntityDataSaver) user, 2);
                lifestealHealCountFloat.increment((IEntityDataSaver) user, 2);
                forceRefresh((ServerPlayerEntity) user);
            }
        }

        return super.use(world, user, hand);
    }
}
