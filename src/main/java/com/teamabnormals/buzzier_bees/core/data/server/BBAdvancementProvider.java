package com.teamabnormals.buzzier_bees.core.data.server;

import com.teamabnormals.blueprint.common.advancement.EmptyTrigger;
import com.teamabnormals.buzzier_bees.core.BuzzierBees;
import com.teamabnormals.buzzier_bees.core.other.BBCriteriaTriggers;
import com.teamabnormals.buzzier_bees.core.registry.BBItems;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.FrameType;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.ForgeAdvancementProvider;
import net.minecraftforge.common.data.ForgeAdvancementProvider.AdvancementGenerator;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public class BBAdvancementProvider implements AdvancementGenerator {

	public static ForgeAdvancementProvider create(PackOutput output, CompletableFuture<Provider> provider, ExistingFileHelper helper) {
		return new ForgeAdvancementProvider(output, provider, helper, List.of(new BBAdvancementProvider()));
	}

	@Override
	public void generate(Provider provider, Consumer<Advancement> consumer, ExistingFileHelper helper) {
		createAdvancement("four_leaf_clover", "adventure", new ResourceLocation("adventure/sleep_in_bed"), BBItems.FOUR_LEAF_CLOVER.get(), FrameType.CHALLENGE, true, true, false)
				.addCriterion("four_leaf_clover", InventoryChangeTrigger.TriggerInstance.hasItems(BBItems.FOUR_LEAF_CLOVER.get()))
				.save(consumer, BuzzierBees.MOD_ID + ":adventure/four_leaf_clover");

		createCureAdvancement("use_glazed_porkchop", BBItems.GLAZED_PORKCHOP.get(), BBCriteriaTriggers.GLAZED_PORKCHOP_CURE, consumer);
		createCureAdvancement("use_honey_bread", BBItems.HONEY_BREAD.get(), BBCriteriaTriggers.HONEY_BREAD_CURE, consumer);
		createCureAdvancement("use_honey_apple", BBItems.HONEY_APPLE.get(), BBCriteriaTriggers.HONEY_APPLE_CURE, consumer);
	}

	private static Advancement.Builder createAdvancement(String name, String category, ResourceLocation parent, ItemLike icon, FrameType frame, boolean showToast, boolean announceToChat, boolean hidden) {
		return Advancement.Builder.advancement().parent(Advancement.Builder.advancement().build(parent)).display(icon,
				Component.translatable("advancements." + BuzzierBees.MOD_ID + "." + category + "." + name + ".title"),
				Component.translatable("advancements." + BuzzierBees.MOD_ID + "." + category + "." + name + ".description"),
				null, frame, showToast, announceToChat, hidden);
	}

	private static void createCureAdvancement(String name, ItemLike item, EmptyTrigger trigger, Consumer<Advancement> consumer) {
		createAdvancement(name, "husbandry", new ResourceLocation("husbandry/safely_harvest_honey"), item, FrameType.TASK, true, true, false)
				.addCriterion(name, trigger.createInstance())
				.save(consumer, BuzzierBees.MOD_ID + ":husbandry/" + name);
	}
}