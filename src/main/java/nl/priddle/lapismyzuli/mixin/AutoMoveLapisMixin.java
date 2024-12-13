package nl.priddle.lapismyzuli.mixin;

import net.minecraft.client.gui.screen.ingame.EnchantmentScreen;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.Items;
import net.minecraft.screen.EnchantmentScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.text.Text;
import net.minecraft.util.collection.DefaultedList;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EnchantmentScreen.class)
@SuppressWarnings("unused")
public abstract class AutoMoveLapisMixin extends HandledScreen<EnchantmentScreenHandler> {
    @Unique private static final int ENCHANTMENT_LAPIS_SLOT = 1;
    @Unique private static final int INVENTORY_START_INDEX = 2;

    public AutoMoveLapisMixin(EnchantmentScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
    }

    @Inject(at = @At("TAIL"), method = "init()V")
    public void init(CallbackInfo info) {
        moveLapisToEnchantmentTable(this.handler.slots);
    }

    @Inject(at = @At(value = "RETURN", ordinal = 0), method = "mouseClicked(DDI)Z")
    public void mouseClicked(CallbackInfoReturnable<Boolean> info) {
        moveLapisToEnchantmentTable(this.handler.slots);
    }

    @Unique
    private void moveLapisToEnchantmentTable(DefaultedList<Slot> slots) {
        // Get the lapis slot in the enchantment table.
        Slot lapisSlot = slots.get(ENCHANTMENT_LAPIS_SLOT);

        // Find all lapis in the inventory and move them to the lapis slot.
        for (int i = INVENTORY_START_INDEX; i < slots.size(); i++) {

            // Check if the lapis slot is full.
            if (lapisSlot.hasStack() && lapisSlot.getStack().getCount() >= lapisSlot.getMaxItemCount()) {
                break;
            }

            // Move lapis to the lapis slot.
            Slot slot = slots.get(i);
            if (slot.hasStack() && slot.getStack().isOf(Items.LAPIS_LAZULI)) {
                this.onMouseClick(slot, slot.id, 0, SlotActionType.PICKUP);
                this.onMouseClick(lapisSlot, lapisSlot.id, 0, SlotActionType.PICKUP);
                this.onMouseClick(slot, slot.id, 0, SlotActionType.PICKUP);
            }
        }
    }
}
