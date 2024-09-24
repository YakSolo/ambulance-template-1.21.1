package net.yaksolo.ambulance.mixin.client;

import com.google.common.collect.Ordering;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.AbstractInventoryScreen;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.texture.StatusEffectSpriteManager;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffectUtil;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.screen.ScreenTexts;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.yaksolo.ambulance.effects.ModEffects;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import static net.minecraft.client.gui.screen.ingame.HandledScreen.BACKGROUND_TEXTURE;

@Mixin(AbstractInventoryScreen.class)
public class AInventoryScreenMixin {

    @Inject(method = {"drawStatusEffects"}, at = @At("INVOKE"), cancellable = true)
    private void drawStatusEffects(DrawContext context, int mouseX, int mouseY, CallbackInfo info) {
        info.cancel();
        AbstractInventoryScreen screen = (AbstractInventoryScreen) (Object) this;

        int i = ((HandledScreenAccessor) screen).getX() + ((HandledScreenAccessor) screen).getBackgroundWidth() + 2;
        int j = screen.width - i;
        Collection<StatusEffectInstance> collection = MinecraftClient.getInstance().player.getStatusEffects();
        if (!collection.isEmpty() && j >= 32) {
            boolean bl = j >= 120;
            int k = 33;
            if (collection.size() > 5) {
                k = 132 / (collection.size() - 1);
            }

            Iterable<StatusEffectInstance> iterable = Ordering.natural().sortedCopy(collection);
            if(iterable.iterator().next().getEffectType().equals(ModEffects.FRUIT_EXTENSION)) {
                k = 40;
            }
            drawStatusEffectBackgrounds(context, i, k, iterable, bl);
            drawStatusEffectSprites(context, i, k, iterable, bl);
            if (bl) {
                drawStatusEffectDescriptions(context, i, k, iterable);
            } else if (mouseX >= i && mouseX <= i + 33) {
                int l = ((HandledScreenAccessor) screen).getY();
                StatusEffectInstance statusEffectInstance = null;

                for(Iterator var12 = iterable.iterator(); var12.hasNext(); l += k) {
                    StatusEffectInstance statusEffectInstance2 = (StatusEffectInstance)var12.next();
                    if (mouseY >= l && mouseY <= l + k) {
                        statusEffectInstance = statusEffectInstance2;
                    }
                }

                if (statusEffectInstance != null) {
                    List<Text> list = List.of(this.getStatusEffectDescription(statusEffectInstance), StatusEffectUtil.getDurationText(statusEffectInstance, 1.0F, 20));
                    context.drawTooltip(((ScreenAccessor) screen).getTextRenderer(), list, Optional.empty(), mouseX, mouseY);
                }
            }
        }
    }

    private void drawStatusEffectSprites(DrawContext context, int x, int height, Iterable<StatusEffectInstance> statusEffects, boolean wide) {
        StatusEffectSpriteManager statusEffectSpriteManager = MinecraftClient.getInstance().getStatusEffectSpriteManager();
        AbstractInventoryScreen screen = (AbstractInventoryScreen) (Object) this;

        int i = ((HandledScreenAccessor) screen).getY();

        for(Iterator var8 = statusEffects.iterator(); var8.hasNext(); i += height) {
            StatusEffectInstance statusEffectInstance = (StatusEffectInstance)var8.next();
            RegistryEntry<StatusEffect> registryEntry = statusEffectInstance.getEffectType();
            Sprite sprite = statusEffectSpriteManager.getSprite(registryEntry);
            context.drawSprite(x + (wide ? 6 : 7), i + 7, 0, 18, 18, sprite);
        }

    }

    private void drawStatusEffectBackgrounds(DrawContext context, int x, int height, Iterable<StatusEffectInstance> statusEffects, boolean wide) {
        AbstractInventoryScreen inv = (AbstractInventoryScreen) (Object) this;

        int i = ((HandledScreenAccessor) inv).getY();

        for(Iterator var7 = statusEffects.iterator(); var7.hasNext(); i += height) {
            StatusEffectInstance statusEffectInstance = (StatusEffectInstance)var7.next();
            if (wide) {
                boolean bl = statusEffectInstance.getEffectType().equals(ModEffects.FRUIT_EXTENSION);
                context.drawTexture(BACKGROUND_TEXTURE, x, i, bl ? 32 : 0, bl ? 198 : 166, 120, bl ? 39 : 32);
            } else {
                context.drawTexture(BACKGROUND_TEXTURE, x, i, 0, 198, 32, 32);
            }
        }

    }

    private void drawStatusEffectDescriptions(DrawContext context, int x, int height, Iterable<StatusEffectInstance> statusEffects){
        AbstractInventoryScreen inv = (AbstractInventoryScreen) (Object) this;

        int i = ((HandledScreenAccessor) inv).getY();

        for(Iterator var6 = statusEffects.iterator(); var6.hasNext(); i += height) {
            StatusEffectInstance statusEffectInstance = (StatusEffectInstance)var6.next();

            Text text2 = StatusEffectUtil.getDurationText(statusEffectInstance, 1.0F, 20);
            if(!statusEffectInstance.getEffectType().equals(ModEffects.FRUIT_EXTENSION)){
                Text text = getStatusEffectDescription(statusEffectInstance);
                context.drawTextWithShadow(((ScreenAccessor) inv).getTextRenderer(), text, x + 10 + 18, i + 6, 16777215);
                context.drawTextWithShadow(((ScreenAccessor) inv).getTextRenderer(), text2, x + 10 + 18, i + 6 + 10, 8355711);
            }else{
                Text p1 = getPartedDescription(statusEffectInstance, true);
                Text p2 = getPartedDescription(statusEffectInstance, false);

                context.drawTextWithShadow(((ScreenAccessor) inv).getTextRenderer(), p1, x + 10 + 18, i + 6, 16777215);
                context.drawTextWithShadow(((ScreenAccessor) inv).getTextRenderer(), p2, x + 10 + 18, i + 6 + 10, 16777215);
                context.drawTextWithShadow(((ScreenAccessor) inv).getTextRenderer(), text2, x + 10 + 18, i + 6 + 20, 8355711);
            }
        }
    }

    private Text getStatusEffectDescription(StatusEffectInstance statusEffect) {
        MutableText mutableText = ((StatusEffect)statusEffect.getEffectType().value()).getName().copy();
        if (statusEffect.getAmplifier() >= 1 && statusEffect.getAmplifier() <= 9) {
            MutableText var10000 = mutableText.append(ScreenTexts.SPACE);
            int var10001 = statusEffect.getAmplifier();
            var10000.append(Text.translatable("enchantment.level." + (var10001 + 1)));
        }

        return mutableText;
    }

    private Text getPartedDescription(StatusEffectInstance statusEffect, boolean part) {
        MutableText mutableText = ((StatusEffect)statusEffect.getEffectType().value()).getName().copy();
        String name = mutableText.getString();
        StringBuilder n = new StringBuilder();

        if(name.length() > 14){
            if(part){
                int k = 0;
                for(char i : name.toCharArray()){
                    k++;
                    n.append(i);
                    if(k == 14){
                        break;
                    }
                }
            }else{
                int k = 0;
                for(char i : name.toCharArray()){
                    k++;
                    if(k > 14){
                        if(i != ' ') n.append(i);
                    }
                }
            }
        }

        MutableText result = Text.literal(String.valueOf(n));

        if (statusEffect.getAmplifier() >= 1 && statusEffect.getAmplifier() <= 9 && !part) {
            MutableText var10000 = result.append(ScreenTexts.SPACE);
            int var10001 = statusEffect.getAmplifier();
            var10000.append(Text.translatable("enchantment.level." + (var10001 + 1)));
        }

        return result;
    }

}
