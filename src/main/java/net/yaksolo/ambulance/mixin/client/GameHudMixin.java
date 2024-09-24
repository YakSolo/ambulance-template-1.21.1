package net.yaksolo.ambulance.mixin.client;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.option.AttackIndicator;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.entity.Entity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.RangedWeaponItem;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Arm;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.random.Random;
import net.yaksolo.ambulance.effects.ModEffects;
import net.yaksolo.ambulance.misc.ModHeartType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InGameHud.class)
public class GameHudMixin {

    private static final Identifier HOTBAR_TEXTURE = Identifier.ofVanilla("hud/hotbar");
    private static final Identifier HOTBAR_SELECTION_TEXTURE = Identifier.ofVanilla("hud/hotbar_selection");
    private static final Identifier HOTBAR_OFFHAND_LEFT_TEXTURE = Identifier.ofVanilla("hud/hotbar_offhand_left");
    private static final Identifier HOTBAR_OFFHAND_RIGHT_TEXTURE = Identifier.ofVanilla("hud/hotbar_offhand_right");
    private static final Identifier HOTBAR_ATTACK_INDICATOR_BACKGROUND_TEXTURE = Identifier.ofVanilla("hud/hotbar_attack_indicator_background");
    private static final Identifier HOTBAR_ATTACK_INDICATOR_PROGRESS_TEXTURE = Identifier.ofVanilla("hud/hotbar_attack_indicator_progress");

    @Shadow MinecraftClient client;

    @Inject(method = {"renderHealthBar"}, at = @At("INVOKE"), cancellable = true)
    private void renderHealthBar(DrawContext context, PlayerEntity player, int x, int y, int lines, int regeneratingHeartIndex, float maxHealth, int lastHealth, int health, int absorption, boolean blinking, CallbackInfo info) {
        info.cancel();

        Random random = player.getRandom();
        ModHeartType heartType = ModHeartType.fromPlayerState(player);
        boolean bl = player.getWorld().getLevelProperties().isHardcore();
        int i = MathHelper.ceil((double)maxHealth / 2.0);
        int j = MathHelper.ceil((double)absorption / 2.0);
        int k = i * 2;

        for(int l = i + j - 1; l >= 0; --l) {
            int m = l / 10;
            int n = l % 10;
            int o = x + n * 8;
            int p = y - m * lines;
            if (lastHealth + absorption <= 4) {
                p += random.nextInt(2);
            }

            if (l < i && l == regeneratingHeartIndex) {
                p -= 2;
            }

            drawHeart(context, ModHeartType.CONTAINER, o, p, bl, blinking, false);
            int q = l * 2;
            boolean bl2 = l >= i;
            if (bl2) {
                int r = q - k;
                if (r < absorption) {
                    boolean bl3 = r + 1 == absorption;
                    drawHeart(context, heartType == ModHeartType.WITHERED ? heartType : ModHeartType.ABSORBING, o, p, bl, false, bl3);
                }
            }

            boolean bl4;
            if (blinking && q < health) {
                bl4 = q + 1 == health;
                drawHeart(context, (player.hasStatusEffect(ModEffects.FRUIT_EXTENSION) && (l > 9 && l <= getMaxFruitExpansionHearts(player))) ? ModHeartType.FRUIT_EXTENSION : heartType, o, p, bl, true, bl4);
            }

            if (q < lastHealth) {
                bl4 = q + 1 == lastHealth;
                drawHeart(context, (player.hasStatusEffect(ModEffects.FRUIT_EXTENSION) && (l > 9 && l <= getMaxFruitExpansionHearts(player))) ? ModHeartType.FRUIT_EXTENSION : heartType, o, p, bl, false, bl4);
            }
        }

    }

    @Inject(method = {"renderHotbar"}, at = @At("INVOKE"), cancellable = true)
    private void renderHotbar(DrawContext context, RenderTickCounter tickCounter, CallbackInfo info) {
        info.cancel();

        PlayerEntity playerEntity = getCameraPlayer();
        if (playerEntity != null) {
            ItemStack itemStack = playerEntity.getOffHandStack();
            Arm arm = playerEntity.getMainArm().getOpposite();
            int i = context.getScaledWindowWidth() / 2;
            RenderSystem.enableBlend();
            context.getMatrices().push();
            context.getMatrices().translate(0.0F, 0.0F, -90.0F);
            context.drawGuiTexture(HOTBAR_TEXTURE, i - 91, context.getScaledWindowHeight() - 22, 182, 22);
            context.drawGuiTexture(HOTBAR_SELECTION_TEXTURE, i - 91 - 1 + playerEntity.getInventory().selectedSlot * 20, context.getScaledWindowHeight() - 22 - 1, 24, 23);
            if (!itemStack.isEmpty()) {
                if (arm == Arm.LEFT) {
                    context.drawGuiTexture(HOTBAR_OFFHAND_LEFT_TEXTURE, i - 91 - 29, context.getScaledWindowHeight() - 23, 29, 24);
                } else {
                    context.drawGuiTexture(HOTBAR_OFFHAND_RIGHT_TEXTURE, i + 91, context.getScaledWindowHeight() - 23, 29, 24);
                }
            }

            context.getMatrices().pop();
            RenderSystem.disableBlend();
            int l = 1;

            int m;
            int n;
            int o;
            for(m = 0; m < 9; ++m) {
                n = i - 90 + m * 20 + 2;
                o = context.getScaledWindowHeight() - 16 - 3;
                renderHotbarItem(context, n, o, tickCounter, playerEntity, (ItemStack)playerEntity.getInventory().main.get(m), l++);
            }

            if (!itemStack.isEmpty()) {
                m = context.getScaledWindowHeight() - 16 - 3;
                if (arm == Arm.LEFT) {
                    renderHotbarItem(context, i - 91 - 26, m, tickCounter, playerEntity, itemStack, l++);
                } else {
                    renderHotbarItem(context, i + 91 + 10, m, tickCounter, playerEntity, itemStack, l++);
                }
            }

            if (client.options.getAttackIndicator().getValue() == AttackIndicator.HOTBAR) {
                RenderSystem.enableBlend();
                float f = client.player.getAttackCooldownProgress(0.0F);
                if (f < 1.0F) {
                    n = context.getScaledWindowHeight() - 20;
                    o = i + 91 + 6;
                    if (arm == Arm.RIGHT) {
                        o = i - 91 - 22;
                    }

                    int p = (int)(f * 19.0F);
                    context.drawGuiTexture(HOTBAR_ATTACK_INDICATOR_BACKGROUND_TEXTURE, o, n, 18, 18);
                    context.drawGuiTexture(HOTBAR_ATTACK_INDICATOR_PROGRESS_TEXTURE, 18, 18, 0, 18 - p, o, n + 18 - p, 18, p);
                }

                RenderSystem.disableBlend();
            }

        }
    }

    private void renderHotbarItem(DrawContext context, int x, int y, RenderTickCounter tickCounter, PlayerEntity player, ItemStack stack, int seed) {
        if (!stack.isEmpty()) {
            float f = (float)stack.getBobbingAnimationTime() - tickCounter.getTickDelta(false);
            if (f > 0.0F) {
                float g = 1.0F + f / 5.0F;
                context.getMatrices().push();
                context.getMatrices().translate((float)(x + 8), (float)(y + 12), 0.0F);
                context.getMatrices().scale(1.0F / g, (g + 1.0F) / 2.0F, 1.0F);
                context.getMatrices().translate((float)(-(x + 8)), (float)(-(y + 12)), 0.0F);
            }

            context.drawItem(player, stack, x, y, seed);
            if (f > 0.0F) {
                context.getMatrices().pop();
            }

            context.drawItemInSlot(client.textRenderer, stack, x, y);
        }
    }

    private PlayerEntity getCameraPlayer() {
        Entity var2 = MinecraftClient.getInstance().getCameraEntity();
        PlayerEntity var10000;
        if (var2 instanceof PlayerEntity playerEntity) {
            var10000 = playerEntity;
        } else {
            var10000 = null;
        }

        return var10000;
    }

    private void drawHeart(DrawContext context, ModHeartType type, int x, int y, boolean hardcore, boolean blinking, boolean half) {
        RenderSystem.enableBlend();
        context.drawGuiTexture(type.getTexture(hardcore, half, blinking), x, y, 9, 9);
        RenderSystem.disableBlend();
    }

    public int getMaxFruitExpansionHearts(PlayerEntity p){
        StatusEffectInstance effect = p.getStatusEffect(ModEffects.FRUIT_EXTENSION);
        int a = effect.getAmplifier();
        return a == 0 ? 13 : (10 + ((a + 1) * 7) / 2) - 1;
    }

}
