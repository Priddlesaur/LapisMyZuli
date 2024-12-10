package nl.priddle.lapismyzuli.mixin;

import net.minecraft.client.gui.screen.ingame.EnchantmentScreen;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.item.Items;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.screen.slot.SlotActionType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(HandledScreen.class)
public abstract class AutoMoveLapisMixin {
    private static final int ENCHANTMENT_LAPIS_SLOT = 1;
    private static final int INVENTORY_START_INDEX = 2;

    @Shadow
    public ScreenHandler handler;

    @Inject(at = @At("TAIL"), method = "init")
    private void init(CallbackInfo info) {
        // Check if this is the EnchantmentScreen.class
        if (((Object) this).getClass() != EnchantmentScreen.class) {
            return;
        }

        // Get the lapis slot in the enchantment table.
        Slot lapisSlot = handler.getSlot(ENCHANTMENT_LAPIS_SLOT);

        // Find all lapis in the inventory and move them to the lapis slot.
        for (int i = INVENTORY_START_INDEX; i < handler.slots.size(); i++) {

            // Check if the lapis slot is full.
            boolean isFull = lapisSlot.hasStack() && lapisSlot.getStack().getCount() == lapisSlot.getMaxItemCount();
            if (isFull) {
                break;
            }

            // Move lapis to the lapis slot.
            Slot slot = handler.getSlot(i);
            if (slot.hasStack() && slot.getStack().isOf(Items.LAPIS_LAZULI)) {
                onMouseClick(slot, slot.id, 0, SlotActionType.PICKUP);
                onMouseClick(lapisSlot, lapisSlot.id, 0, SlotActionType.PICKUP);
                onMouseClick(slot, slot.id, 0, SlotActionType.PICKUP);
            }
        }
    }

    @Shadow
    protected abstract void onMouseClick(Slot slot, int slotId, int button, SlotActionType actionType);
}
